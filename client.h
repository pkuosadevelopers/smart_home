/**
 * client.h
 *
 * Author: David
 * Date: 2012-6-28
 * Description: some definition about the client that connected
 *              to the server via socket
 */

#ifndef _CLIENT_H
#define _CLIENT_H

#define NAME_SIZE 50

#define DEL_SUCC 1				//delete successfully
#define DEL_ERROR_NO_SOCK -1	//there is not such a socket number

//the description of each client node 
typedef struct client_node
{
	char client_name[NAME_SIZE];
	int sockfd;			//the socket number
	int alive;			//1 online; 0 offline
	int no_check_in;	//how many times that didn't check in
	struct client_node *next;
}CLIENT_NODE;

/**
 *@brief	insert a new client to the tail of the node list
 *@param	*head, the head pointer of the client nodes list
 *@param	*new_client, the pointer of the new client node that
 *			needs to be insert
 */
void client_insert(CLIENT_NODE *head, CLIENT_NODE *new_client);

/*
 *@brief	delete a client by the given socket number
 *@param	*head, the head pointer of the client nodes list
 *@param	sockfd, the socket number that belongs to the client which
 *			to be deleted
 *@return	DEL_SUCC if success  
 *@return	DEL_ERROR_NO_SOCK otherwise
 *
 */
int client_delete(CLIENT_NODE *head, int sockfd);

/**
 *@brief	send the given data to specified client
 *@param	send_sockfd, the socket file descriptor that will be sended to	
 *@param	send_data, the data that will be sended to
 *@param	size, the send_data's size	
 *@return	the number of bytes that has be sended, or -1 if failed	
 */
int client_send(int send_sockfd, char* send_data, int size);

#endif //_CLIENT_H
