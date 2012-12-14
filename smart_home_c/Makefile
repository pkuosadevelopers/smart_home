#
# Author: David
# Date:	2012-6-18
#

my_headfiles = client.h log.h tty.h message.h
my_object = client.o log.o tty.o message.o
my_gcc = gcc -g
main : main.o $(my_object)
	$(my_gcc) -o main main.o $(my_object) -lpthread
main.o : main.c $(my_headfiles)
	$(my_gcc) -c main.c -lpthread
client.o : client.c
	$(my_gcc) -c client.c
log.o : log.c
	$(my_gcc) -c log.c
tty.o : tty.c
	$(my_gcc) -c tty.c
message.o : message.c
	$(my_gcc) -c message.c
clean:
	rm main.o main $(my_object)

