package cn.edu.pku.ss.SmartHome;

import cn.edu.pku.ss.Util.Const;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SmartHomeActivity extends Activity implements OnClickListener 
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		init();
 
		mHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				switch(msg.what)
				{ 
				case Const.MSG_CONNECT_FAIL:
					Toast.makeText(SmartHomeActivity.this, Const.CONNECT_FAIL_INFO, 
							Toast.LENGTH_SHORT).show();
					connSuccess = false;
					break;
				case Const.MSG_CONNECT_SUCCESS:
					Toast.makeText(SmartHomeActivity.this, Const.CONNECT_SUCCESS_INFO,
							Toast.LENGTH_SHORT).show();
					tvShow.setText(msg.getData().getString("connectInfo"));
					connSuccess = true;
					break;
				case Const.MSG_BULB:
					tvBulb.setText("Send i_am_alive " + msg.arg1 + " times");
					break;
				}
			}
		};
	}
	
	/**
	 * do some init
	 */
	private void init()
	{
		initWidgets();
	}
	
	
	/**
	 * initialize  all the widgets
	 */
	private void initWidgets()
	{
		tvShow = (TextView)findViewById(R.id.tv_show);
		
		btnIP = (Button)findViewById(R.id.btn_ip);
		btnIP.setOnClickListener(this);
		tvIP = (TextView)findViewById(R.id.tv_ip);
		
		btnBulbStart = (Button) findViewById(R.id.btn_bulb_start);
		btnBulbStart.setOnClickListener(this);
		tvBulb = (TextView)findViewById(R.id.tv_bulb);
		
		btnTempStart = (Button) findViewById(R.id.btn_temp_start);
		btnTempStart.setOnClickListener(this);
		
		btnHumidityStart = (Button) findViewById(R.id.btn_humidity_start);
		btnHumidityStart.setOnClickListener(this);
		
		btnPhotoSensorStart = (Button) findViewById(R.id.btn_photosensor_start);
		btnPhotoSensorStart.setOnClickListener(this);
		
		btnMP3Start = (Button) findViewById(R.id.btn_mp3_start);
		btnMP3Start.setOnClickListener(this);
		
		btnAlarmStart = (Button) findViewById(R.id.btn_alarm_start);
		btnAlarmStart.setOnClickListener(this);
		
		btnSteppingMotorStart = (Button) findViewById(R.id.btn_motor_start);
		btnSteppingMotorStart.setOnClickListener(this);
		
		btnHumidifierStart = (Button) findViewById(R.id.btn_humidifier_start);
		btnHumidifierStart.setOnClickListener(this);
		
		btnWaterHeaterStart = (Button) findViewById(R.id.btn_water_heater_start);
		btnWaterHeaterStart.setOnClickListener(this);
		
		btnAirCleanerStart = (Button) findViewById(R.id.btn_air_cleaner_start);
		btnAirCleanerStart.setOnClickListener(this);
		
		btnExhaustFanStart = (Button) findViewById(R.id.btn_exhaust_fan_start);
		btnExhaustFanStart.setOnClickListener(this);
	}
	
	/**
	 * initialize all the monitors, after this we can control the related devices
	 */
	private void initThreads()
	{
		//server thread
		mSocketThread = new SocketThread(mHandler);
		mSocketThread.start();
	}
	
	//the onClickListener to all the widgets
	public void onClick(View v)
	{
		switch (v.getId()) 
		{
			case R.id.btn_ip:
				Const.SERVER_IP = tvIP.getText().toString();
				initThreads();
				break;
			case R.id.btn_bulb_start:
				switchActivity(SubActivityBulb.class);
				break;
			case R.id.btn_temp_start:
				switchActivity(SubActivityTemperature.class);
				break;
			case R.id.btn_humidity_start:
				switchActivity(SubActivityHumidity.class);
				break;
			case R.id.btn_photosensor_start:
				switchActivity(SubActivityPhotosensor.class);
				break;
			case R.id.btn_mp3_start:
				switchActivity(SubActivityMP3.class);
				break;
			case R.id.btn_alarm_start:
				switchActivity(SubActivityAlarm.class);
				break;
			case R.id.btn_motor_start:
				switchActivity(SubActivitySteppingMotor.class);
				break;
			case R.id.btn_humidifier_start:
				switchActivity(SubActivityHumidifier.class);
				break;
			case R.id.btn_water_heater_start:
				switchActivity(SubActivityWaterHeater.class);
				break;
			case R.id.btn_air_cleaner_start:
				switchActivity(SubActivityAirCleaner.class);
				break;
			case R.id.btn_exhaust_fan_start:
				switchActivity(SubActivityExhaustFan.class);
				break;
		}
	}
	
	//switch to the given class
	public void switchActivity(Class<?> cls)
	{
		Intent intent = new Intent();
		if(connSuccess)
		{
			intent.setClass(SmartHomeActivity.this, cls);
			startActivity(intent);
		}
		else
		{
			Toast.makeText(SmartHomeActivity.this, Const.CONNECT_FAIL_INFO,
					Toast.LENGTH_SHORT).show();
		}
	}
	
	//exit the activity
	public void onBackPressed() {
		System.exit(RESULT_OK);
    }
	
	private TextView tvShow;

	//about the bulb
	private Button btnBulbStart;
	private TextView tvBulb;
	
	//start the temperature activity
	private Button btnTempStart;
	
	//start the humidity activity
	private Button btnHumidityStart;
	
	//start the photosensor activity
	private Button btnPhotoSensorStart;
	
	//start the mp3 activity
	private Button btnMP3Start;
	
	//start the alarm activity
	private Button btnAlarmStart;
	
	//start the stepping motor activity
	private Button btnSteppingMotorStart;
	
	//start the humidifier activity
	private Button btnHumidifierStart;
	
	//start the water heater activity
	private Button btnWaterHeaterStart;
	
	//start the air cleaner activity
	private Button btnAirCleanerStart;
	
	//start the exhaust fan activity
	private Button btnExhaustFanStart;
	
	//about the IP
	private Button btnIP;
	private TextView tvIP;
	
	private Handler mHandler;
	private SocketThread mSocketThread;
	private boolean connSuccess;
}
