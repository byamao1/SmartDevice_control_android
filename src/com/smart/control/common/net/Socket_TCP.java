package com.smart.control.common.net;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.smart.control.common.AES_Cipher;

import android.util.Log;

public class Socket_TCP extends Abstract_Socket{
	private static final String TAG = "TCP";

	
	@Override
	public byte[] sendRecData() {
		if(bytes_Data.length == 0){
        	Log.e(TAG, "bytes_Data没有数据!");
        	return null;
        }
		if(timeout < 0){
        	Log.e(TAG, "timeout不能为0!");
        	return null;
        }		
		try {           
			Socket socket = new Socket(str_DstIp, dstPort);	
			
            //设置timeout秒之后即认为是超时	        
	        socket.setSoTimeout(timeout);	
            //发送数据给服务端
	        OutputStream out = socket.getOutputStream();
            out.write(bytes_Data);
            out.flush();
//            socket.shutdownOutput();
            //读取数据
            InputStream in = socket.getInputStream();  
            byte[] bytes_Rec;
            bytes_Rec = inputStreamToBytes(in);
//            //------------test
//            String str_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";
//            String strResult2 = AES_Cipher.decrypt(bytes_Buffer, str_seed);		
//            Log.d(TAG,"解密结果：" + strResult2);	 
//            //------------
            socket.close();
            return bytes_Rec;
        } catch (UnknownHostException e) {
            Log.e(TAG, "来自服务器的数据：UnknownHost");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "来自服务器的数据：IOException");            
            e.printStackTrace();
        }
		return null;
	}
	
}
