package cn.edu.pku.ss.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;

import cn.edu.pku.ss.Util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SubActivityWaterHeater extends Activity implements OnClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_water_heater);

		init();
	}

	private void init()
	{
		out = SocketThread.getBufferedWriter();

		btnWaterHeaterOpen = (Button) findViewById(R.id.btn_water_heater_open);
		btnWaterHeaterOpen.setOnClickListener(this);
		btnWaterHeaterClose = (Button) findViewById(R.id.btn_water_heater_close);
		btnWaterHeaterClose.setOnClickListener(this);
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_water_heater_open:
			open(1, 1); 
			break;
		case R.id.btn_water_heater_close:
			close(1, 1);
			break;
		}
	}
	
	public void open(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_WATER_HEATER;
		command += roomNum;
		command += devNum;
		command += Const.CONTROL_TYPE_ON_OFF;
		command += Const.WATER_HEATER_OPEN;
		command += Const.TERMINAL_SYMBOL;

		sendCommand(command); // send command
	}
	
	public void close(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_WATER_HEATER;
		command += roomNum;
		command += devNum;
		command += Const.CONTROL_TYPE_ON_OFF;
		command += Const.WATER_HEATER_CLOSE;
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command); // send command
	}
	
	private void sendCommand(String command)
	{
		try
		{
			out.write(command);
			out.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BufferedWriter out;

	private Button btnWaterHeaterOpen;
	private Button btnWaterHeaterClose;
}
