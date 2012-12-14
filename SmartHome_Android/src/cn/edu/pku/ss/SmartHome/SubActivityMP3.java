package cn.edu.pku.ss.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;

import cn.edu.pku.ss.Util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SubActivityMP3 extends Activity implements OnClickListener 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mp3);
		
		init();
	}

	public void onClick(View v)
	{
		switch (v.getId()) 
		{ 
			case R.id.btn_mp3_play:
				play(1, 1);
				break;
			case R.id.btn_mp3_pause:
				pause(1, 1);
				break;
			case R.id.btn_mp3_pre:
				pre(1, 1);
				break;
			case R.id.btn_mp3_next:
				next(1, 1);
				break;
			case R.id.btn_mp3_volume_up:
				volumeUp(1, 1);
				break;
			case R.id.btn_mp3_volume_down:
				volumeDown(1, 1);
				break;
			case R.id.btn_mp3_volume_open:
				volumeOpen(1, 1);
				break;
			case R.id.btn_mp3_volume_close:
				volumeClose(1, 1);
				break;
			case R.id.btn_mp3_mode:
				mode(1, 1);
				break;
			case R.id.btn_mp3_fm:
				fm(1, 1);
				break;
			case R.id.btn_mp3_back:
				back(1, 1);
				break;
		}
	}
	
	public void init()
	{
		out = SocketThread.getBufferedWriter();
		
		btnMP3Play = (Button)findViewById(R.id.btn_mp3_play);
		btnMP3Play.setOnClickListener(this);
		
		btnMP3Pause = (Button)findViewById(R.id.btn_mp3_pause);
		btnMP3Pause.setOnClickListener(this);
		
		btnMP3Pre = (Button)findViewById(R.id.btn_mp3_pause);
		btnMP3Pre.setOnClickListener(this);
		
		btnMP3Next = (Button)findViewById(R.id.btn_mp3_next);
		btnMP3Next.setOnClickListener(this);
		
		btnMP3VolumeUp = (Button)findViewById(R.id.btn_mp3_volume_up);
		btnMP3VolumeUp.setOnClickListener(this);
		
		btnMP3VolumeDown = (Button)findViewById(R.id.btn_mp3_volume_down);
		btnMP3VolumeDown.setOnClickListener(this);
		
		btnMP3VolumeOpen = (Button)findViewById(R.id.btn_mp3_volume_open);
		btnMP3VolumeOpen.setOnClickListener(this);
		
		btnMP3VolumeClose = (Button)findViewById(R.id.btn_mp3_volume_close);
		btnMP3VolumeClose.setOnClickListener(this);
		
		btnMP3Mode = (Button)findViewById(R.id.btn_mp3_mode);
		btnMP3Mode.setOnClickListener(this);
		
		btnMP3FM = (Button)findViewById(R.id.btn_mp3_fm);
		btnMP3FM.setOnClickListener(this);
		
		btnMP3Back = (Button)findViewById(R.id.btn_mp3_back);
		btnMP3Back.setOnClickListener(this);
	}
	
	//play the mp3
	public void play(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_PLAY;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//pause the mp3
	public void pause(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_PAUSE;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//play the previous music
	public void pre(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_PRE;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//play the next music
	public void next(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_NEXT;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//turn up the volume
	public void volumeUp(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_VOLUME_UP;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//turn down the volume
	public void volumeDown(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_VOLUME_DOWN;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//open the volume
	public void volumeOpen(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_VOLUME_OPEN;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//close the volume
	public void volumeClose(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_VOLUME_CLOSE;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//get into mode setting
	public void mode(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_MODE;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//open FM
	public void fm(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;		
		command += roomNum;						
		command += devNum;						
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_FM;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
	}
	
	//back to previous view of the mp3
	public void back(int roomNum, int devNum)
	{
		String command = "";
		command += Const.DEVICE_TYPE_MP3;
		command += roomNum;
		command += devNum;	
		command += Const.CONTROL_TYPE_ON_OFF;	
		command += Const.MP3_BACK;				
		command += Const.TERMINAL_SYMBOL;
		
		sendCommand(command);		//send command
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
	
	private Button btnMP3VolumeOpen;
	private Button btnMP3VolumeClose;
	private Button btnMP3VolumeUp;
	private Button btnMP3VolumeDown;
	private Button btnMP3Next;
	private Button btnMP3Pre;
	private Button btnMP3Pause;
	private Button btnMP3Play;
	private Button btnMP3Mode;
	private Button btnMP3FM;
	private Button btnMP3Back;
}
