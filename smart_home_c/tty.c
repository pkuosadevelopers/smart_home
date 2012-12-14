 /**
  * tty.c
  *
  * Author: David
  * Date: 2012-7-3
  * Brief: sth about the tty 
  *              
  */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <errno.h>

#include "tty.h"
#include "log.h"

//woca, B.... here is a macro, so it can be a int, wtf!
int speed_arr[] = { B38400, B19200, B9600, B4800, B2400, B1200, B300,
          B38400, B19200, B9600, B4800, B2400, B1200, B300, };
int name_arr[] = {38400,  19200,  9600,  4800,  2400,  1200,  300, 38400,  
          19200,  9600, 4800, 2400, 1200,  300, };
int tty_open_dev(char* dev)
{
	int fd = open(dev, O_RDWR | O_NOCTTY);
	if(fd == -1)
	{
		DBG("\nCan't open serial port\n");
		return CN_OPEN;
	}
	
	return fd;
}

int tty_set_speed(int fd, int speed)
{
	struct termios opt;
	int i;
	int length = sizeof(speed_arr) / sizeof(int);
	
	tcgetattr(fd, &opt);
	for(i = 0; i < length; i++)
	{
		if(speed == name_arr[i])
		{
			tcflush(fd, TCIOFLUSH);
			cfsetispeed(&opt, speed_arr[i]);
			cfsetospeed(&opt, speed_arr[i]);
			if(tcsetattr(fd, TCSANOW, &opt) != 0)
			{
				DBG("\ntcsetattr fd error\n");
				return -1;
			}
			else
			{
				tcflush(fd, TCIOFLUSH);
				return 0;
			}
		}
	}
	
	//didn't find the baud rate until over the loop, so return a error message.
	return NO_SPEED;
}

int tty_set_parity(int fd,int databits,int stopbits,int parity)
{ 
	struct termios opt; 
	if(tcgetattr(fd, &opt)  !=  0) 
	{ 
		DBG("\ntcgetattr error\n"); 
		return -1;  
	}
	opt.c_cflag &= ~CSIZE; 
	switch (databits) //set data bits
	{   
		case 7:		
			opt.c_cflag |= CS7; 
			break;
		case 8:     
			opt.c_cflag |= CS8;
			break;   
		default:    
			DBG("\nUnsupported data size\n"); 
			return UN_SP_DS;  
	}
	switch (parity) 
	{   
		case 'n':
		case 'N':    
			opt.c_cflag &= ~PARENB;		/* Clear parity enable */
			opt.c_iflag &= ~INPCK;		/* Enable parity checking */ 
			break;  
		case 'o':   
		case 'O':     
			opt.c_cflag |= (PARODD | PARENB); /* set to odd parity*/  
			opt.c_iflag |= INPCK;             /* Disnable parity checking */ 
			break;  
		case 'e':  
		case 'E':   
			opt.c_cflag |= PARENB;		/* Enable parity */    
			opt.c_cflag &= ~PARODD;		/* set to even parity*/     
			opt.c_iflag |= INPCK;       /* Disnable parity checking */
			break;
		case 'S': 
		case 's':						/*as no parity*/ 
			opt.c_cflag &= ~PARENB;
			opt.c_cflag &= ~CSTOPB;
			break;  
		default: 
			DBG("\nUnsupported parity\n");    
			return UN_SP_PRT;
	}
	switch(stopbits)
	{
		case 1:
			opt.c_cflag &= ~CSTOPB;
			break;
		case 2:
			opt.c_cflag |= CSTOPB;
			break;
		default:
			DBG("\nUnsupported stop bits\n");
			return UN_SP_STB;	
	}
	
	//set input parity option
	if(parity != 'n' && parity != 'N')
	{
		opt.c_iflag |= INPCK;
	}
	tcflush(fd, TCIFLUSH);
	opt.c_cc[VTIME] = 150;		//set waiting time
	opt.c_cc[VMIN] = 0;			//set the minmum number to receive
	if(tcsetattr(fd, TCSANOW, &opt) != 0)
	{
		DBG("\ntcsetattr\n");
		return -1;
	}
	tcflush(fd, TCIFLUSH);

	return 0;
}

int tty_send(int fd, char *send_data, int size)
{
	return write(fd, send_data, size);
}
