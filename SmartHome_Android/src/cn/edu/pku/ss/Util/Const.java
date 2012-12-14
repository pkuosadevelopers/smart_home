package cn.edu.pku.ss.Util;

/**
 * 
 * @author David
 *
 * @describe a class used to arrange constant variables
 *
 * @date Jun 20, 2012
 *
 */
public class Const {
	
	/*************************************** Server Info *****************************/
	public static String SERVER_IP;	//this is not a final variable
	public static final int SERVER_PORT = 1234;
	/*************************************** Server Info *****************************/
	
	/***********************Control Type (the 5th byte of command) *******************/
	public static final String CONTROL_TYPE_ON_OFF = "1";
	public static final String CONTROL_TYPE_REQ_ACK = "2";
	/***********************Control Type (the 5th byte of command) *******************/
	
	/***********************Terminal Symbol (the 8th byte of command) *******************/
	public static final String TERMINAL_SYMBOL = "$";
	/***********************Terminal Symbol (the 8th byte of command) *******************/
	
	/*************************************** Connect State *****************************/
	public static final String CONNECT_FAIL_INFO = "can't get connected to gateway";
	public static final String CONNECT_SUCCESS_INFO = "connected to the gateway success";
	public static final String CONNECT_I_AM_ALIVE_MSG = "0000000$";
	/*************************************** Connect State *****************************/
	
	/*************************************** Bulb *****************************/
	public static final String DEVICE_TYPE_BULB = "01";	//device type of bulb
	public static final String BULB_OPEN = "01";
	public static final String BULB_CLOSE = "02";
	/*************************************** Bulb *****************************/
	
	/********************************* Temperature ************************/
	public static final String TEMP_REQUEST = "0211201$";
	/********************************* Temperature ************************/
	
	/********************************* Humidity ************************/
	public static final String DEVICE_TYPE_HUMIDITY = "03";
	public static final String HUMIDITY_REQUEST = "01";
	/********************************* Humidity ************************/
	
	/*************************************** Photosensor *****************************/
	public static final String DEVICE_TYPE_PHOTOSENSOR = "04";
	public static final String PHOTOSENSOR_REQUEST = "01";
	/*************************************** Photosensor *****************************/
	
	/*************************************** MP3 *****************************/
	public static final String DEVICE_TYPE_MP3 = "07";
	public static final String MP3_VOLUME_OPEN = "01";
	public static final String MP3_VOLUME_CLOSE = "02";
	public static final String MP3_PLAY = "03";
	public static final String MP3_PAUSE = "04";
	public static final String MP3_PRE = "05";
	public static final String MP3_NEXT = "06";
	public static final String MP3_VOLUME_UP = "07";
	public static final String MP3_VOLUME_DOWN = "08";
	public static final String MP3_MODE = "09";
	public static final String MP3_FM = "10";
	public static final String MP3_BACK = "11";
	/*************************************** MP3 *****************************/

	/*************************************** Alarm *****************************/
	public static final String ALM_OPEN = "0800101$";
	public static final String ALM_CLOSE = "0800102$";
	public static final String ALM_BEEP_1 = "0800301$";
	public static final String ALM_BEEP_2 = "0800302$";
	public static final String ALM_BEEP_3 = "0800303$";
	public static final String ALM_BEEP_4 = "0800304$";
	public static final String ALM_BEEP_5 = "0800305$";
	/*************************************** Alarm *****************************/
	
	/*************************************** Stepping Motor **************************/
	public static final String DEVICE_TYPE_MOTOR = "09";
	public static final String MOTOR_CLOSE = "01";
	public static final String MOTOR_POSITIVE_OPEN = "02";
	public static final String MOTOR_REVERSE_OPEN = "03";
	/*************************************** Stepping Motor **************************/
	
	/*************************** HUMIDIFIER ************************/
	public static final String DEVICE_TYPE_HUMIDIFIER = "10";
	public static final String HUMIDIFIER_OPEN = "01";
	public static final String HUMIDIFIER_CLOSE = "02";
	/*************************** HUMIDIFIER ************************/
	
	/********************************** Water Heater ********************/
	public static final String DEVICE_TYPE_WATER_HEATER = "11";
	public static final String WATER_HEATER_OPEN = "01";
	public static final String WATER_HEATER_CLOSE = "02";
	/********************************** Water Heater********************/
	
	/********************************** Air Cleaner ********************/
	public static final String DEVICE_TYPE_AIR_CLEANER = "12";
	public static final String AIR_CLEANER_OPEN = "01";
	public static final String AIR_CLEANER_CLOSE = "02";
	/********************************** Air Cleaner ********************/
	
	/********************************** Exhaust Fan ********************/
	public static final String DEVICE_TYPE_EXHAUST_FAN = "13";
	public static final String EXHAUST_FAN_OPEN = "01";
	public static final String EXHAUST_FAN_CLOSE = "02";
	/********************************** Exhaust Fan ********************/
	
	/*************************************** Messages *****************************/
	public static final int MSG_CONNECT_FAIL = 99;
	public static final int MSG_CONNECT_SUCCESS = 100;
	
	public static final int MSG_BULB = 1;
	public static final int MSG_LIGHT = 2;
	public static final int MSG_MP3 = 3;
	/*************************************** Messages *****************************/
}
