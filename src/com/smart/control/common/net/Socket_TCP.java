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
        	Log.e(TAG, "bytes_Dataû������!");
        	return null;
        }
		if(timeout < 0){
        	Log.e(TAG, "timeout����Ϊ0!");
        	return null;
        }		
		try {           
			Socket socket = new Socket(str_DstIp, dstPort);	
			
            //����timeout��֮����Ϊ�ǳ�ʱ	        
	        socket.setSoTimeout(timeout);	
            //�������ݸ������
	        OutputStream out = socket.getOutputStream();
            out.write(bytes_Data);
            out.flush();
//            socket.shutdownOutput();
            //��ȡ����
            InputStream in = socket.getInputStream();  
            byte[] bytes_Rec;
            bytes_Rec = inputStreamToBytes(in);
//            //------------test
//            String str_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";
//            String strResult2 = AES_Cipher.decrypt(bytes_Buffer, str_seed);		
//            Log.d(TAG,"���ܽ����" + strResult2);	 
//            //------------
            socket.close();
            return bytes_Rec;
        } catch (UnknownHostException e) {
            Log.e(TAG, "���Է����������ݣ�UnknownHost");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "���Է����������ݣ�IOException");            
            e.printStackTrace();
        }
		return null;
	}
	
}
