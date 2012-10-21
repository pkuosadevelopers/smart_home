 /**
  * util.h
  *
  * Author: David
  * Date: 2012-7-18
  * Brief: some common tools 
  *              
  */

#ifndef _UTIL_H
#define _UTIL_H

/**
 *@brief	copy n=offset number of characters from char sequence *start to *dest, but the beginning 
 *			of the copy may not be the *start, it's begin from end - offset.
 *			But if end - offset is pre_over start, then it is a overflow, we set the first
 *			char of dest to be '\0'
 *@param	to keep the ack value
 *@param	start of the string that contain a ack value
 *@param	end of the string that contain a ack value
 *@param	the offset that the begin of the ack-string should before the variable *end
 */
void find_ack(char *dest, char *start, char *end, int offset);

#endif	//_UTIL_H
