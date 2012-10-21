/**
 * util.c
 * Author: David
 * Date: 2012-7-18
 * Brief: some common tools
 */

#include <string.h>

#include "log.h"
#include "util.h"

void find_ack(char *dest, char *start, char *end, int offset)
{
	char *p;
	
	if(end - offset - start < 0)		//overflow
	{
		*dest = '\0';
		return;
	}
	p = end - offset;			//set the offset
	strncpy(dest, p, offset);	//copy n characters and n=offset, but the strncpy() is not null-terminated
	dest[offset] = '\0';		//so, make the one after last char to be '\0'
}
