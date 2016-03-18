package com.smart.control.common.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public abstract class Abstract_Socket implements Runnable {	
	private static final String TAG = "Socket";
	protected String str_DstIp = "";
	protected int dstPort = 0;
	protected byte[] bytes_Data;
	protected int timeout = 10000;	
	
	public void setDstInfo(String str_DstIp, int dstPort) {
		this.str_DstIp = str_DstIp;
		this.dstPort = dstPort;
		
	}		
	
	public void setSendInfo(byte[] bytes_Data, int timeout) {
		this.bytes_Data = bytes_Data;
		this.timeout = timeout;		
	}
	public void setSendInfo(byte[] bytes_Data) {
		this.bytes_Data = bytes_Data;		
	}
	protected byte[] inputStreamToBytes(InputStream in){
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
		int ch; 
		try{
		    while ((ch = in.read()) != -1) {  
		    	bytestream.write(ch);  
		    }  
		} catch (IOException e) {
			Log.e(TAG, "InputStream读取完毕或失败：IOException");   
			e.printStackTrace();
		}
	    byte[] bytes_Result = bytestream.toByteArray();  
	    try {
			bytestream.close();
		} catch (IOException e) {
			Log.e(TAG, "bytestream关闭失败：IOException");   
			e.printStackTrace();
		}
	    return bytes_Result;
	}

	public void run() {
		sendRecData();		
	}	
	
	//负责发送和接收数据,  返回值：接收到的数据
	abstract public byte[] sendRecData();
}
