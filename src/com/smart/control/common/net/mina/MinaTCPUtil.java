/**

 * Title: MinaUtil.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��3��1��
 */
package com.smart.control.common.net.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.smart.control.common.LogUtil;


/**
 * 
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class MinaTCPUtil {
	private static final String TAG = "MinaTCPUtil";
	
	private static MinaTCPUtil minaTcpUtil;
//	public static boolean isNetConnect;
	
	private IoSession session = null;
	private static String server_URL;
	private static int server_port;
	
	public static boolean netConfig(String input_server_URL, int input_server_port){
		if((input_server_URL == null)||("".equals(server_URL))){
			LogUtil.e(TAG, "input_server_URL�����Ƿ�");
			return false;			
		}			
		server_URL = input_server_URL;
		server_port = input_server_port;
		return true;
	}
	
	/**
	 * �ڻ�ȡʵ��ǰ����Ҫ����netConfig�������ø�������
	 * @return
	 */
	public static MinaTCPUtil getInstance(){
		if(minaTcpUtil == null){			
			minaTcpUtil = new MinaTCPUtil();			
		}
		
		return minaTcpUtil;
	}
	
	//���ͼ�����Ϣ��Ǽ�����Ϣ
	public boolean sendMessage(String str_msgBody){
		LogUtil.i(TAG, "�ͻ������ӿ�ʼ...");
		IoConnector connector = new NioSocketConnector();
		//�������ӳ�ʱʱ��
		connector.setConnectTimeoutMillis(30000);
//		connector.getFilterChain().addLast( "logger", new LoggingFilter()); 
		//��ӹ�����
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				 new TextLineCodecFactory(Charset.forName("UTF-8"))));
		//Ϊ������������ù������
		connector.setHandler(new MinaTcpHandler());
		try{
			ConnectFuture future = connector.connect(new InetSocketAddress(server_URL, server_port));//��������
			future.awaitUninterruptibly();// �ȴ����Ӵ������
			session = future.getSession();//���session
			session.write(str_msgBody);
		}catch (Exception e){
			LogUtil.e(TAG, "�ͻ��������쳣...");
			return false;
		}
	
		//�ر�  
        if(session!=null){  
            if(session.isConnected()){  
                session.getCloseFuture().awaitUninterruptibly();  //�ȴ����ӶϿ�
            }  
            LogUtil.i(TAG, "�ͻ��˶Ͽ�...");
            connector.dispose(true);  
        }  
		
		
		return true;
	}
}
