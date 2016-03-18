package com.smart.control.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class Socket_UDP extends Abstract_Socket{
	private static final String TAG = "UDP";
	private static final int MAX_DATA_PACKET_LENGTH = 80; 
	private byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];
	 
	@Override
	public byte[] sendRecData() {		
		DatagramPacket dataPacket = null;
		DatagramSocket udpSocket = null;
		try {
			udpSocket = new DatagramSocket(dstPort);
		} catch (SocketException e1) {			
			e1.printStackTrace();
		}	  
		try {    	          
	        dataPacket = new DatagramPacket(buffer, MAX_DATA_PACKET_LENGTH);           
		    dataPacket.setData( bytes_Data );
		    dataPacket.setLength( bytes_Data.length );
		    dataPacket.setPort(dstPort);    
		    InetAddress addr = InetAddress.getByName(str_DstIp);
		    dataPacket.setAddress(addr);
		    udpSocket.send(dataPacket);
		    udpSocket.close();
		    return null;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			udpSocket.close();
			return null;
	  	}		
//		udpSocket.close();
		
	}
}
