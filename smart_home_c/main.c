#include <stdio.h> 
#include <stdlib.h>
#include <string.h>
#include <sys/ioctl.h>
#include <pthread.h>
#include <sys/socket.h>
#include <error.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>
#include <ctype.h>
#include <signal.h>
#include <sys/select.h>

#include "client.h"
#include "log.h"
#include "tty.h"
#include "message.h"

#define T_MAX 20
#define PORT  1234

//the server thread
void pthread_server(int* p_listen_fd);

//when a new connect is comming, then create a thread to run it
void pthread_receive_from_client(CLIENT_NODE *p);

//to check all the clients whether they are  online or not
void pthread_check_online_status();

//keep receving data from tty
void pthread_receive_from_tty();

//act like a man
void act_like_a_man(int send_sockfd, char *send_data);

//the first node of the client nodes list
CLIENT_NODE *head;

//the first node of the message nodes list
MESSAGE_NODE *head_message;

//the file descriptor of tty
int _tty_fd;

//the socket file descriptor of the client which needs the data from tty
int send_sockfd;
//the mutex for variable *head_message
pthread_mutex_t mutex_head_message;

int main(int argc,char **argv)
{
	struct sockaddr_in ssin;	//the server socket
	int listen_fd;				//the listened file description
	
	head_message = (MESSAGE_NODE*)malloc(sizeof(MESSAGE_NODE));
	head_message->next = NULL;
	pthread_mutex_init (&mutex_head_message, NULL); 

	//open the DBG and log function default
	debug_open_default();

	signal(SIGPIPE,SIG_IGN);	//prevent client to shutdown when the server close the socket 
	signal(SIGCHLD,SIG_IGN);	//prevent chlild thread to create zombie process

	head=(CLIENT_NODE*)malloc(sizeof(CLIENT_NODE));
	head->sockfd=0;
	head->next=NULL;
	strcpy(head->client_name,"admin");

	//initialize socket
	bzero(&ssin,sizeof(ssin));
	ssin.sin_family=AF_INET;
	ssin.sin_addr.s_addr=INADDR_ANY;
	ssin.sin_port=htons(PORT);
	listen_fd=socket(AF_INET,SOCK_STREAM,0);
	{
		//config socket
		int opt=1;
		setsockopt(listen_fd,SOL_SOCKET,SO_REUSEADDR, &opt, sizeof(opt));
	}
	//bind socket
	if(bind(listen_fd, (struct sockaddr *)&ssin, sizeof(ssin)) < 0)
	{
		DBG("bind error");
		exit(1);
	}
	//listen socket
	listen(listen_fd,T_MAX);
	DBG("\n accepting connections......\n");

	//server thread
	pthread_t pth_server;
	if(pthread_create(&pth_server, NULL, (void*)pthread_server, &listen_fd) != 0)
	{
		DBG("\nserver thread create is failed!\n");
		return 1;
	}

	//check whether the clients are online or not
	pthread_t pth_check_online;
	if(pthread_create(&pth_check_online, NULL, (void*)pthread_check_online_status, NULL) != 0)
	{
		DBG("\ncheck onlie thread create is failed\n");
		return 1;
	}
	
	
	//receive from tty thread
	pthread_t pth_receive_from_tty;
	if(pthread_create(&pth_server, NULL, (void*)pthread_receive_from_tty, NULL) != 0)
	{
		DBG("\nreceive from tty thread create is failed!\n");
		return 1;
	}


	//wait for the pthread server to be accomplished
	pthread_join(pth_server, NULL);

	return 0;
}

void pthread_server(int* p_listen_fd)
{
	int conn_fd;
	pthread_t tid[T_MAX];

	DBG("\n serverThread begin!!! \n");
	int i=0;
	while(1)
	{
		struct sockaddr_in rsin;
		socklen_t address_size;
		address_size=sizeof(rsin);
		conn_fd = accept(*p_listen_fd, (struct sockaddr *)&rsin, &address_size);
		DBG("\nnew client is add!\n");
		CLIENT_NODE* new_client = (CLIENT_NODE*)malloc(sizeof(CLIENT_NODE));
		new_client->sockfd = conn_fd;
		new_client->alive = 1;		
		new_client->no_check_in = 0;
		send(conn_fd, "connect success\n", strlen("connect success\n"), 0);
		strcpy(new_client->client_name, "unname");
		new_client->next = NULL;
		client_insert(head, new_client);

		pthread_create(&tid[i], NULL, (void*)pthread_receive_from_client, new_client);
		i++;
	}
}

//read data from the client
void pthread_receive_from_client(CLIENT_NODE *p)
{
	int n,socket;
	char buff[50];
	socket = p->sockfd;

	fd_set rdfds;			//define a set of socket that we want to check
	struct timeval tv;		//define a time variable to set timeout
	int ret;				//keep the return value

	while(1)
	{
		if(p->no_check_in > 3)		//it has more than 3 times not checked in
		{
			DBG("\n%d client say goodbye to server\n", p->sockfd);
			break;
		}
		memset(buff,0,sizeof(buff));

		FD_ZERO(&rdfds);
		FD_SET(socket, &rdfds);
		tv.tv_sec = 5;			//set the timeout to about 5.5s
		tv.tv_usec = 500;

		//check if there is info can be read from the set of socket
		ret = select(socket + 1, &rdfds, NULL, NULL, &tv);
		if(ret < 0)
		{
			DBG("\n%d is Error\n", socket);
		}
		else if(ret == 0)
		{
			DBG("\n%d is Out Of Time\n", socket);
		}
		else	//that is ret > 0
		{
			if(FD_ISSET(socket, &rdfds))		//detect whether socket can be read
			{
				n = recv(socket, buff, 8, 0);
				if(n == -1)		//socket error 
				{
					DBG("\n%d socket is error: %s\n", socket, strerror(errno));
					break;
				}
				if(n == 0)		//socket closed normally
				{
					DBG("\n%d socket is closed\n", socket);
					break;
				}

				p->alive = 1;	//receive data successfully, so it is alive

				if(strstr(buff, "0000000$") != NULL)	//so the message is a i-am-alive-message	
				{
					DBG("\n%d received %d from client :%s\n", socket, n, buff);
					continue;	
				}
				else if(is_req_msg(buff, strlen(buff)))
				{
					//definetion and initilize the new message node by the connnected client socket
					//and data from the client and the length of data
					MESSAGE_NODE *new_message_node = (MESSAGE_NODE*)malloc(sizeof(MESSAGE_NODE));
					new_message_node->sockfd = socket;
					new_message_node->data = (char*)malloc(sizeof(char) * 10);
					strcpy(new_message_node->data, buff);
					new_message_node->size = strlen(buff);
					new_message_node->next = NULL;
					
					pthread_mutex_lock(&mutex_head_message);	//lock the gloable variable head_message
					message_insert(head_message, new_message_node);
					pthread_mutex_unlock(&mutex_head_message);	//unlock the gloable variable head_message
					//send to tty	
					int count = tty_send(_tty_fd, buff, strlen(buff));
					DBG("\n%d received %d from client :%s and %d is sended, REQ\n", socket, n, buff, count);
				}
				else
				{
					int count = tty_send(_tty_fd, buff, strlen(buff));
					DBG("\n%d received %d from client :%s and %d is sended\n", socket, n, buff, count);
				}
			}	
		}
	}
	DBG("\n\n%d will be finished at once\n\n", socket);
	client_delete(head, socket);
	close(socket);
	pthread_exit(NULL);
}

void pthread_check_online_status()
{
	while(1)
	{
		CLIENT_NODE *tmp = head -> next;
		while(tmp != NULL)
		{
			//if detected the client is not check in , then increase the no_check_in number 
			if(tmp->alive == 0)
			{											
				tmp->no_check_in++;
			}
			else	//if the client has already checked in, then arrange the no_check_in number to 0 
			{										
				tmp->alive = 0;
				tmp->no_check_in = 0;
			}
			if(tmp->no_check_in > 3)
			{
				DBG("\n%d is offline\n", tmp -> sockfd);
			}
			DBG("\nfd:%d alive:%d times:%d\n", tmp->sockfd, tmp->alive, tmp->no_check_in);
			tmp = tmp -> next;
		}
		DBG("\n\nanother check in 5s\n\n");
		sleep(5);
	}
}

void pthread_receive_from_tty()
{
	int fd;
	int nread;
	int i = 0;
	char buff[500];
	char *pbuf;
	char *dev = "/dev/ttyUSB0";
	int baud = 9600;

	DBG("\ninit tty begins\n");
	fd = tty_open_dev(dev);
	if(fd == -1)
	{
		DBG("\nOpen dev failed\n");
		exit(-1);
	}
	DBG("\nopen success\n");

	if(tty_set_speed(fd, baud) != 0)
	{
		DBG("\nSet speed failed\n");
		exit(-1);
	}
	DBG("\nset speed success\n");

	if(tty_set_parity(fd, 8, 1, 'N') != 0)
	{
		DBG("\nSet parity failed\n");
	}
	DBG("\nset parity success\n");

	_tty_fd = fd;

	while(1)
	{
		pbuf = buff;		//reset the pbuf point to the beginning of buff
		int n;
		while(1)
		{
			n = read(fd, pbuf, 1);
			if(*pbuf++ == '$')
			{
				*pbuf = '\0';
				break;
			}
		}
		printf("%s, the size is %ld\n", buff, pbuf - buff);

		if(is_ack_msg(buff, pbuf - buff))	//if this is an ACK message, read another message 
		{
			send_sockfd = find_client_to_send(head_message, buff, strlen(buff));
			pbuf = buff;		//reset the pbuf point to the beginning of buff
			while(1)
			{
				read(fd, pbuf, 1);
				if(*pbuf++ == '$')
				{
					*pbuf = '\0';
					break;
				}
			}
			//send to client
			int vv = client_send(send_sockfd, buff, strlen(buff));

			printf("second time: %s, the size is %ld\n", buff, pbuf - buff);
			printf("second time: %d, the send_sockfd is %d\n", vv, send_sockfd);

			pthread_mutex_lock(&mutex_head_message);	//lock the gloable variable head_message
			message_delete(head_message, send_sockfd);
			pthread_mutex_unlock(&mutex_head_message);	//unlock the gloable variable head_message
		}
	}
}

void act_like_a_man(int send_sockfd, char *send_data)
{
	int n = send(send_sockfd, send_data, strlen(send_data), 0);
	DBG("\n\n%d send to client is : %s, %d\n\n", send_sockfd, send_data, n);
}

