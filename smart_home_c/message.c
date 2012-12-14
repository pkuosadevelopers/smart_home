/**
 * message.c
 *
 * Author: David
 * Date: 2012-7-6
 * Brief: Today is Friday
 */

#include <stdlib.h>
#include <string.h>

#include "log.h"
#include "message.h"

void message_insert(MESSAGE_NODE *head, MESSAGE_NODE *new_message_node)
{
	MESSAGE_NODE *p;
	
	p = head;
	while(p->next != NULL)
	{
		p = p->next;		
	}
	p->next = new_message_node;
	
	return;
}

void message_delete(MESSAGE_NODE *head, int sockfd)
{
	MESSAGE_NODE *p, *q;
	while(p->next != NULL)
	{
		if(p->next->sockfd == sockfd)	//find the node to be deleted
		{
			q = p->next;
			p->next = q->next;
			free(q);
			return;
		}
	}
}

int find_client_to_send(MESSAGE_NODE *head, char *data, int size)
{
	MESSAGE_NODE *p;
	int i;

	DBG("\ndata is %s, size is %d\n", data, size);

	p = head;
	while(p->next != NULL)
	{
		p = p->next;
		//there is no need to check all the *data characters, for the last one is always'$'
		//and the one before last is must be different.so the rounds of loop shoud be size - 1.	
		//e.g. REQ is 0211201$, the ACK should be 0211202$, and the 7th character is diffrent.
		for(i = 0; i < size - 1; i++)
		{
			if(p->data[i] == data[i])
			{
				continue;
			}
		}
		if(i == size - 1)	//find the matched node
		{
			return p->sockfd;	
		}
	}
	return -1;
}
		
int is_ack_msg(char *buff, int size)
{
	//e....the variable size is not used actually, because it is 8 for ever

	//start with '#' is the data message, e.g. "#12.340$"
	//the ack message is sth like "1300202$"
	if(buff[0] != '#' && buff[4] == '2' && buff[6] == '2')
	{
		return 1;
	}
	else
	{
		return 0;
	}
}


int is_req_msg(char *buff, int size)
{
	//e....the variable size is not used actually, because it is 8 for ever

	//the req message is sth like "1300201$"
	if(buff[4] == '2' && buff[6] == '1')	
	{
		return 1;
	}
	else
	{
		return 0;
	}
}


