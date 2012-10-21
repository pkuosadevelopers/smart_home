 gcc *.c `pkg-config --cflags --libs gtk+-2.0` `pkg-config --cflags --libs gthread-2.0` `pkg-config --cflags --libs gupnp-1.0` -luuid
