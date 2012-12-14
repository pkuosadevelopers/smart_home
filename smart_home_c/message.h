 /**
  * message.h
  *
  * Author: David
  * Date: 2012-7-6
  * Brief: there is a fucking sb bb the whole day!!!
  *              
  */

#ifndef _MESSAGE_H
#define _MESSAGE_H

typedef struct message_node
{
	int sockfd;		//the socket file descriptor reference to which client has this message
	char* data;		//the data
	int size;		//the number of bytes of the data
	struct message_node *next;	
}MESSAGE_NODE;


/**
 *@brief	insert a new message to the tail of the message node list	
 *@param	*head, the head pointer of the message nodes list
 *@param	*new_client, the pointer of the new message node that
 *			needs to be insert
 */
void message_insert(MESSAGE_NODE *head, MESSAGE_NODE *new_message_node);

/**
 *@brief	delete a message node whose sockfd value matched the given sockfd
 *@param	*head, the head pointer of the message nodes list
 *@param	sockfd, the value that used to find out which node matched a same one
 */
void message_delete(MESSAGE_NODE *head, int sockfd);

/**
 *@brief	find which client to send.A client send a REQ message to the gateway,
 *			then the gateway send to tty and tty return a ACK data.After the ACK message,
 *			there will be a data message, so we want to know who is waiting for this data
 *			message.and this function will return the socket of the connected client.
 *@param	head, the head node pointer of the message node list
 *@param	data, the ACK data from tty
 *@param	size, the size of data to send
 *@return	sockfd, the sockfd of the client that needs the specified data
 *@return	-1, if failed to match the ACK and REQ message
 */
int find_client_to_send(MESSAGE_NODE *head, char *data, int size);

/**
 *@brief	to check whether the given string is an ACK message or not
 *@param	buff, the string to be checked
 *@param	size, the length of the string
 *@return	1 for this is a ACK message or 0 if not
 */
int is_ack_msg(char *buff, int size);

/**
 *@brief	to check whether the given string is an REQ message or not
 *@param	buff, the string to be checked
 *@param	size, the length of the string
 *@return	1 for this is a REQ message or 0 if not
 */
int is_req_msg(char *buff, int size);

#endif	//_MESSAGE_H
