#
# Author: David
# Date:	2012-6-18
#

my_headfiles = client.h log.h tty.h message.h util.h upnp_light.h
my_object = client.o log.o tty.o message.o util.o upnp_light.o
my_gcc = gcc -g 
linkvar = `pkg-config --cflags --libs gtk+-2.0` `pkg-config --cflags --libs gthread-2.0` `pkg-config --cflags --libs gupnp-1.0` -luuid
main : main.o $(my_object)
	$(my_gcc) -o main main.o $(my_object) -lpthread $(linkvar)
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
util.o : util.c
	$(my_gcc) -c util.c
upnp_light.o : upnp_light.c
	$(my_gcc) -c upnp_light.c $(linkvar) 
clean:
	rm main.o main $(my_object)

