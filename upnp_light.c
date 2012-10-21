/*
 *Author: David
 *Brief: this ia a sample of upnp-light. It is a control point, if there is a upnp light service in 
 *       the same network, it can control that upnp-light by send command "on|off|toggle".We just
 *       send the diffenrent commands automatically by this thread.
 *       All of above, this is just a test.
 *Date:  2012-9-21 20:54:33
 */

#include "upnp_light.h"
 
static GMainLoop *main_loop;

static enum
{
	OFF = 0,
	ON = 1,
	TOGGLE
} mode;


static void service_proxy_available_cb (GUPnPControlPoint *cp, GUPnPServiceProxy *proxy)
{
	GError *error = NULL;
	gboolean target;

	if (mode == TOGGLE) {
		/* We're toggling, so first fetch the current status */
		if (!gupnp_service_proxy_send_action
				(proxy, "GetStatus", &error,
				 /* IN args */ NULL,
				 /* OUT args */ "ResultStatus", G_TYPE_BOOLEAN, &target, NULL)) {
			goto error;
		}
		/* And then toggle it */
		target = ! target;
	} else {
		/* Mode is a boolean, so the target is the mode thanks to our well chosen
		   enumeration values. */
		target = mode;
	}

	/* Set the target */
	if (!gupnp_service_proxy_send_action (proxy, "SetTarget", &error,
				/* IN args */
				"NewTargetValue", G_TYPE_BOOLEAN, target, NULL,
				/* OUT args */
				NULL)) {
		goto error;
	} else {
		g_print ("Set switch to %s.\n", target ? "on" : "off");
	}

done:
	/* Only manipulate the first light switch that is found */
	g_main_loop_quit (main_loop);
	return;

error:
	g_printerr ("Cannot set switch: %s\n", error->message);
	g_error_free (error);
	goto done;
}

static void usage (void)
{
	g_printerr ("$ light-client [on|off|toggle]\n");
}

int upnp_light_main (int argc, char *argv)
{
	GError *error = NULL;
	GUPnPContext *context;
	GUPnPControlPoint *cp;

	g_type_init ();

	/* Check and parse command line arguments */
	if (argc != 2) {
		usage ();
		return EXIT_FAILURE;
	}

	if (g_str_equal (argv, "on")) {
		mode = ON;
	} else if (g_str_equal (argv, "off")) {
		mode = OFF;
	} else if (g_str_equal (argv, "toggle")) {
		mode = TOGGLE;
	} else {
		usage ();
		return EXIT_FAILURE;
	}

	/* Create the UPnP context */
	context = gupnp_context_new (NULL, NULL, 0, &error);
	if (error) {
		g_printerr ("Error creating the GUPnP context: %s\n",
				error->message);
		g_error_free (error);

		return EXIT_FAILURE;
	}

	/* Create the control point, searching for SwitchPower services */
	cp = gupnp_control_point_new (context,
			"urn:schemas-upnp-org:service:SwitchPower:1");

	/* Connect to the service-found callback */
	g_signal_connect (cp,
			"service-proxy-available",
			G_CALLBACK (service_proxy_available_cb),
			NULL);

	/* Start searching when the main loop runs */
	gssdp_resource_browser_set_active (GSSDP_RESOURCE_BROWSER (cp), TRUE);

	/* Run the main loop */
	main_loop = g_main_loop_new (NULL, FALSE);
	g_main_loop_run (main_loop);

	/* Cleanup */
	g_main_loop_unref (main_loop);
	g_object_unref (cp);
	g_object_unref (context);

	return EXIT_SUCCESS;
}
