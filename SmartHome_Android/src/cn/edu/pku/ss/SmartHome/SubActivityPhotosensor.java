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

public class SubActivityPhotosensor extends Activity implements OnClickListener 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_photosensor);
		
		init();
	}
	
	private void init()
	{
		btnPhotosensorGet = (Button)findViewById(R.id.btn_photosensor_get);
		btnPhotosensorGet.setOnClickListener(this);
		
		btnIntensityValue = (Button)findViewById(R.id.btn_photosensor_value);
		
		btnIntensityLevel = (Button)findViewById(R.id.btn_photosensor_level);
		
		in = SocketThread.getBufferedReader();
		out = SocketThread.getBufferedWriter();
	}
	
	public void onClick(View v)
	{
		switch (v.getId()) 
		{ 
			case R.id.btn_photosensor_get:	
				getIntensity(1, 1);
				showResult();
				break;
		}
	}
	
	private void showResult()
	{
		btnIntensityValue.setText(intensityValue + "");
		btnIntensityLevel.setText(intensityLevel);
	}
	
	private void getIntensity(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_PHOTOSENSOR;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_REQ_ACK;	
		command += Const.PHOTOSENSOR_REQUEST;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);	//send request command and get intensity
	}
	
	private void sendCommand(String command)
	{
		try
		{
			out.write(command);
			out.flush();
			
			char content[] = new char[8];
			in.read(content);
			String strResult = new String(content);			//"#1.2300$"
			String strValue = strResult.substring(1, 5);	//"1.23"
			intensityValue = Double.parseDouble(new String(strValue));
			intensityLevel = getIntensityLevel(intensityValue);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getIntensityLevel(double n)
	{
		if(n > 0 && n <= 0.33)
		{
			return LV_1;
		}
		else if(n <= 0.66)	//that's 0.33 < n <= 0.66
		{
			return LV_2;
		}
		else if(n <= 0.99)	//that's 0.66 < n <= 0.99
		{
			return LV_3;
		}
		else if(n <= 1.32)	//that's 0.99 < n <= 1.32
		{
			return LV_4;
		}
		else if(n <= 1.65)
		{
			return LV_5;
		}
		else if(n <= 1.98)
		{
			return LV_6;
		}
		else if(n <= 2.31)
		{
			return LV_7;
		}
		else if(n <= 2.64)
		{
			return LV_8;
		}
		else if(n <= 2.97)
		{
			return LV_9;
		}
		else if(n <= 3.3)
		{
			return LV_10;
		}
		else
		{
			return LV_10;
		}
	}
	
	private final String LV_1 = "HIGH";		//0.00-0.33
	private final String LV_2 = "MIDDLE";		//0.33-0.66
	private final String LV_3 = "MIDDLE";		//0.66-0.99
	private final String LV_4 = "MIDDLE";		//0.99-1.32
	private final String LV_5 = "MIDDLE";		//1.32-1.65
	private final String LV_6 = "NORMAL";		//1.65-1.98
	private final String LV_7 = "NORMAL";		//1.98-2.31
	private final String LV_8 = "NORMAL";		//2.31-2.64
	private final String LV_9 = "LOW";			//2.64-2.97
	private final String LV_10 = "LOW";		//2.97-3.30
	
	private BufferedReader in;
	private BufferedWriter out;
	
	private double intensityValue;
	private String intensityLevel;
	
	private Button btnPhotosensorGet;
	private Button btnIntensityValue;
	private Button btnIntensityLevel;
}
