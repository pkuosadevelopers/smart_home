 /**
  * tty.h
  *
  * Author: David
  * Date: 2012-7-3
  * Brief: sth about the tty 
  *              
  */
 
#ifndef _TTY_H
#define _TTY_H

#define CN_OPEN -1			//can't open the serial port
#define NO_SPEED -2			//there is not such a baud speed
#define UN_SP_DS -3			//unsupported data size
#define UN_SP_PRT -4		//unsupported parity type
#define UN_SP_STB -5		//unsupported stop bits

/**
 *@brief	open the device
 *@param	dev, the name of device, such as "/dev/ttyUSB0"
 *@return	file descriptor, if success
 *@return	CN_OPEN, if can't open the serial port
 *
 */
int tty_open_dev(char* dev);

/**
 *@brief	set the communication speed of the serial port
 *@param	fd,	the opened file descriptor 
 *@param	speed, the baud rate to be set
 *@return	NO_SPEED, if there is not such a speed
 *@return	0, if success	
 *
 */
int tty_set_speed(int fd, int speed);

/**
 *@brief	set the databits, stopbits and parity of the serial port
 *@param	fd, the opened file descriptor
 *@param	databits, the number of bits for data, 7 and 8 is avaliable
 *@param	stopbits, the number of bits for stop, 1 and 2 is avaliable
 *@param	parity, the type of parity, N, E, O and S is avaliable
 *@return	0, if success
 *@return	UN_SP_DS, UN_SP_PRT, UN_SP_STB if reference given input is out of range 
 *@return	-1, if some error occur when the set
 *
 */
int tty_set_parity(int fd, int databits, int stopbits, int parity);

/**
 *@brief	send the given data to the tty
 *@param	fd, the opened file descriptor
 *@param	send_data, the data needs to be send
 *@param	size, the send size
 *return	the number of bytes that has be sended or -1 if failed		
 */
int tty_send(int fd, char *send_data, int size);

#endif	//_TTY_H
