package cn.edu.pku.ss.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;

import cn.edu.pku.ss.Util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SubActivityBulb extends Activity implements OnClickListener 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bulb);
		
		init();
	}
	
	private void init()
	{
		out = SocketThread.getBufferedWriter();
		
		btnBulbOn_1 = (Button)findViewById(R.id.btn_bulb_on_1);
		btnBulbOn_1.setOnClickListener(this);
		btnBulbOff_1 = (Button)findViewById(R.id.btn_bulb_off_1);
		btnBulbOff_1.setOnClickListener(this);
		
		btnBulbOn_2 = (Button)findViewById(R.id.btn_bulb_on_2);
		btnBulbOn_2.setOnClickListener(this);
		btnBulbOff_2 = (Button)findViewById(R.id.btn_bulb_off_2);
		btnBulbOff_2.setOnClickListener(this);
		
		btnBulbOn_3 = (Button)findViewById(R.id.btn_bulb_on_3);
		btnBulbOn_3.setOnClickListener(this);
		btnBulbOff_3 = (Button)findViewById(R.id.btn_bulb_off_3);
		btnBulbOff_3.setOnClickListener(this);
		
		btnBulbOn_4 = (Button)findViewById(R.id.btn_bulb_on_4);
		btnBulbOn_4.setOnClickListener(this);
		btnBulbOff_4 = (Button)findViewById(R.id.btn_bulb_off_4);
		btnBulbOff_4.setOnClickListener(this);
	}
	
	public void onClick(View v)
	{
		switch (v.getId()) 
		{ 
			case R.id.btn_bulb_on_1:
				openBulb(1, 1);		//open bulb 1 in room 1
				break;
			case R.id.btn_bulb_off_1:
				closeBulb(1, 1);
				break;
			case R.id.btn_bulb_on_2:
				openBulb(1, 2);
				break;
			case R.id.btn_bulb_off_2:
				closeBulb(1, 2);
				break;
			case R.id.btn_bulb_on_3:
				openBulb(1, 3);
				break;
			case R.id.btn_bulb_off_3:
				closeBulb(1, 3);
				break;
			case R.id.btn_bulb_on_4:
				openBulb(1, 4);
				break;
			case R.id.btn_bulb_off_4:
				closeBulb(1, 4);
				break;
		}
	}
	
	/**
	 * @brief open the specified bulb in the specified room 
	 * @param roomNum  the specified room number 
	 * @param devNum  the specified device number
	 */
	private void openBulb(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_BULB;		//now is "01"
		command += roomNum;						//now is "011"
		command += devNum;						//now is "0111"
		command += Const.CONTROL_TYPE_ON_OFF;	//now is "01111"
		command += Const.BULB_OPEN;				//now is "0111101"
		command += Const.TERMINAL_SYMBOL;		//now is "0111101$", completed.
		
		sendCommand(command);		//send command
	}
	
	/**
	 * @brief close the specified bulb in the specified room 
	 * @param roomNum  the specified room number 
	 * @param devNum  the specified device number
	 */
	private void closeBulb(int roomNum, int devNum)
	{
		String command = "";
		
		command += Const.DEVICE_TYPE_BULB;		//now is "01"
		command += roomNum;						//now is "011"
		command += devNum;						//now is "0111"
		command += Const.CONTROL_TYPE_ON_OFF;	//now is "01111"
		command += Const.BULB_CLOSE;			//now is "0111102"
		command += Const.TERMINAL_SYMBOL;		//now is "0111102$", completed.leted.
		
		sendCommand(command);		//send command
	}
	
	/**
	 * @brief send the command by use of the defined output stream
	 * @param command the command needs to be send
	 */
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
	private Button btnBulbOn_1;
	private Button btnBulbOff_1;
	private Button btnBulbOn_2;
	private Button btnBulbOff_2;
	private Button btnBulbOn_3;
	private Button btnBulbOff_3;
	private Button btnBulbOn_4;
	private Button btnBulbOff_4;
}
