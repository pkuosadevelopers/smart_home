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

public class SubActivityTemperature extends Activity implements OnClickListener 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_temperature);
		
		init();
	}
	
	private void init()
	{
		tv = (TextView)findViewById(R.id.tv_temp_1);
		
		btnTempGet = (Button)findViewById(R.id.btn_temp_get);
		btnTempGet.setOnClickListener(this);
		
		in = SocketThread.getBufferedReader();
		out = SocketThread.getBufferedWriter();
	}
	
	public void onClick(View v)
	{
		switch (v.getId()) 
		{ 
			case R.id.btn_temp_get:	
				getTemp();
				break;
		}
	}
	
	private void getTemp()
	{
		try
		{
			out.write(Const.TEMP_REQUEST);
			out.flush();
			System.out.println("temp request send");
	
			char content[] = new char[8];
			in.read(content);
			
			String strResult = new String(content);			//"#12.230$"
			String strValue = strResult.substring(1, 6);	//"12.23"
			
			tv.setText("Temperatur is: " + strValue);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BufferedReader in;
	private BufferedWriter out;
	
	private Button btnTempGet;
	
	private TextView tv;
}
