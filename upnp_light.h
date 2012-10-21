/*
 *Author: David
 *Brief: this ia a sample of upnp-light. It is a control point, if there is a upnp light service in 
 *		 the same network, it can control that upnp-light by send command "on|off|toggle".We just
 *       send the diffenrent commands automatically by this thread.
 *		 All of above, this is just a test.
 *Date:	 2012-9-21 20:54:33
 */

#ifndef _UPNP_LIGHT_H
#define _UPNP_LIGHT_H

#include <libgupnp/gupnp.h>
#include <stdlib.h>
#include <gmodule.h>

static void service_proxy_available_cb(GUPnPControlPoint *cp, GUPnPServiceProxy *proxy);

static void usage(void);

int upnp_light_main(int argc, char *argv);

#endif	//_UPNP_LIGHT_H
