package cn.edu.pku.ss.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;

import cn.edu.pku.ss.Util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SubActivityHumidifier extends Activity implements OnClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_humidifier);

		init();
	}

	private void init()
	{
		out = SocketThread.getBufferedWriter();

		btnHumidifierOpen = (Button) findViewById(R.id.btn_humidifier_open);
		btnHumidifierOpen.setOnClickListener(this);
		btnHumidifierClose = (Button) findViewById(R.id.btn_humidifier_close);
		btnHumidifierClose.setOnClickListener(this);
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_humidifier_open:
			open(1, 1);
			break;
		case R.id.btn_humidifier_close:
			close(1, 1);
			break;
		}
	}
	
	public void open(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_HUMIDIFIER;
		command += roomNum;
		command += devNum;
		command += Const.CONTROL_TYPE_ON_OFF;
		command += Const.HUMIDIFIER_OPEN;
		command += Const.TERMINAL_SYMBOL;

		sendCommand(command); // send command
	}
	
	public void close(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_HUMIDIFIER;
		command += roomNum;
		command += devNum;
		command += Const.CONTROL_TYPE_ON_OFF;
		command += Const.HUMIDIFIER_CLOSE;
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

	private Button btnHumidifierOpen;
	private Button btnHumidifierClose;
}
