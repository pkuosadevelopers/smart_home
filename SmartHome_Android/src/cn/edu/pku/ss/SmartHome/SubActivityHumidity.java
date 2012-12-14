package cn.edu.pku.ss.SmartHome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import cn.edu.pku.ss.Util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SubActivityHumidity extends Activity implements OnClickListener 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_humidity);
		
		init();
	}
	
	private void init()
	{
		tv = (TextView)findViewById(R.id.tv_humidity_1);
		
		btnHumidityGet = (Button)findViewById(R.id.btn_humidity_get);
		btnHumidityGet.setOnClickListener(this);
		
		in = SocketThread.getBufferedReader();
		out = SocketThread.getBufferedWriter();
	}
	
	public void onClick(View v)
	{
		switch (v.getId()) 
		{ 
			case R.id.btn_humidity_get:	
				getHumidity(1, 1);
				break;
		}
	}
	
	private void getHumidity(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_HUMIDITY;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_REQ_ACK;	
		command += Const.HUMIDITY_REQUEST;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);	//send request command and get intensity
		
		tv.setText("Humidity is: " + humidityValue);
	}
	
	private void sendCommand(String command)
	{
		try
		{
			out.write(command);
			out.flush();
			
			char content[] = new char[8];
			in.read(content);
			String strResult = new String(content);			//"#12.230$"
			String strValue = strResult.substring(1, 6);	//"12.23"
			humidityValue = Double.parseDouble(new String(strValue));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BufferedReader in;
	private BufferedWriter out;
	
	private Button btnHumidityGet;
	
	private double humidityValue;
	
	private TextView tv;
}
