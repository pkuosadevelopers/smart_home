/**
 * client.c
 *
 * Author: David
 * Date: 2012-7-2
 * Brief: nothing except i'm hungry
 */

#include <stdlib.h>
#include <string.h>

#include "client.h"

void client_insert(CLIENT_NODE *head, CLIENT_NODE *new_client)
{
	CLIENT_NODE *p;
	
	p = head;
	while(p->next != NULL)
	{
		p = p->next;	
	}
	p->next = new_client;
	
	return; 
}

int client_delete(CLIENT_NODE *head, int sockfd)
{
	CLIENT_NODE *p, *q;
	p = head;

	while(p->next != NULL)
	{
		if( p->next->sockfd == sockfd)
		{
			q = p->next;
			p->next = q->next;
			free(q);
			return DEL_SUCC;			
		}
	}
	return DEL_ERROR_NO_SOCK;
}

int client_send(int send_sockfd, char *send_data, int size)
{
	return send(send_sockfd, send_data, strlen(send_data), 0);
}

