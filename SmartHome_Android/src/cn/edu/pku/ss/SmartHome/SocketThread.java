package cn.edu.pku.ss.SmartHome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.edu.pku.ss.Util.Const;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class SocketThread extends Thread
{
	public SocketThread(Handler handler)
	{
		mHandler = handler;
		mSocketState = Const.MSG_CONNECT_FAIL;
	}

	public void run()
	{
		Looper.prepare();
		Message msg = null;
		Socket socket = null;
		SocketAddress remoteAddr = null;
		try
		{
			socket = new Socket();
			remoteAddr = new InetSocketAddress(Const.SERVER_IP,
					Const.SERVER_PORT);
			socket.connect(remoteAddr, TIMEOUT);
			msg = new Message();
			msg.what = Const.MSG_CONNECT_SUCCESS;
			mSocketState = Const.MSG_CONNECT_SUCCESS;
			
			//initialize the input and output stream by use of the socket
			mBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			mBufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String content = mBufferedReader.readLine();
			Bundle bundle = new Bundle();  
			bundle.putString("connectInfo", content);
			msg.setData(bundle);
		}
		catch (SocketTimeoutException e)
		{
			msg = new Message();
			msg.what = Const.MSG_CONNECT_FAIL;
			mSocketState = Const.MSG_CONNECT_FAIL;
			System.out.println("failed to connect");
			e.printStackTrace();
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			mHandler.sendMessage(msg);
		}
		while(mSocketState == Const.MSG_CONNECT_SUCCESS)
		{
			try
			{
				sleep(TIMEOUT);
				mBufferedWriter.write(Const.CONNECT_I_AM_ALIVE_MSG);
				mBufferedWriter.flush();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}// end run()

	public int getmSocketState()
	{
		return mSocketState;
	}

	public void setmSocketState(int mSocketState)
	{
		this.mSocketState = mSocketState;
	}
	
	public static BufferedWriter getBufferedWriter()
	{
		return mBufferedWriter;
	}
	
	public static BufferedReader getBufferedReader()
	{
		return mBufferedReader;
	}

	private Handler mHandler;
	private int mSocketState;
	
	private static BufferedWriter mBufferedWriter;
	private static BufferedReader mBufferedReader;

	private static final int TIMEOUT = 5000;
}
