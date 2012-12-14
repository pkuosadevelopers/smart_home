package cn.edu.pku.ss.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;

import cn.edu.pku.ss.Util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SubActivityAlarm extends Activity implements OnClickListener 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_alarm);
		
		init();
	}
	
	private void init()
	{
		out = SocketThread.getBufferedWriter();
		
		btnAlarmOn = (Button)findViewById(R.id.btn_alarm_on);
		btnAlarmOn.setOnClickListener(this);
		
		btnAlarmOff = (Button)findViewById(R.id.btn_alarm_off);
		btnAlarmOff.setOnClickListener(this);
		
		btnAlarmBeep1 = (Button)findViewById(R.id.btn_alarm_beep_1);
		btnAlarmBeep1.setOnClickListener(this);
		
		btnAlarmBeep2 = (Button)findViewById(R.id.btn_alarm_beep_2);
		btnAlarmBeep2.setOnClickListener(this);
		
		btnAlarmBeep3 = (Button)findViewById(R.id.btn_alarm_beep_3);
		btnAlarmBeep3.setOnClickListener(this);
		
		btnAlarmBeep4 = (Button)findViewById(R.id.btn_alarm_beep_4);
		btnAlarmBeep4.setOnClickListener(this);
		
		btnAlarmBeep5 = (Button)findViewById(R.id.btn_alarm_beep_5);
		btnAlarmBeep5.setOnClickListener(this);
	}
	
	public void onClick(View v)
	{
		switch (v.getId()) 
		{ 
			case R.id.btn_alarm_on:
				openAlarm();
				break;
			case R.id.btn_alarm_off:
				closeAlarm();
				break;
			case R.id.btn_alarm_beep_1:
				alarmBeep(1);
				break;
			case R.id.btn_alarm_beep_2:
				alarmBeep(2);
				break;
			case R.id.btn_alarm_beep_3:
				alarmBeep(3);
				break;
			case R.id.btn_alarm_beep_4:
				alarmBeep(4);
				break;
			case R.id.btn_alarm_beep_5:
				alarmBeep(5);
				break;
		}
	}
	
	private void openAlarm()
	{
		try
		{
			out.write(Const.ALM_OPEN);
			out.flush();
			System.out.println("open alarm");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void closeAlarm()
	{
		try
		{
			out.write(Const.ALM_CLOSE);
			out.flush();
			System.out.println("close alarm");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void alarmBeep(int x)
	{
		try
		{
			switch(x)
			{
			case 1:
				out.write(Const.ALM_BEEP_1);
				break;
			case 2:
				out.write(Const.ALM_BEEP_2);
				break;
			case 3:
				out.write(Const.ALM_BEEP_3);
				break;
			case 4:
				out.write(Const.ALM_BEEP_4);
				break;
			case 5:
				out.write(Const.ALM_BEEP_5);
				break;
			}
			out.flush();
			System.out.println("alarm beep " + x + "time(s)");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BufferedWriter out;
	private Button btnAlarmOn;
	private Button btnAlarmOff;
	private Button btnAlarmBeep1;
	private Button btnAlarmBeep2;
	private Button btnAlarmBeep3;
	private Button btnAlarmBeep4;
	private Button btnAlarmBeep5;
}
