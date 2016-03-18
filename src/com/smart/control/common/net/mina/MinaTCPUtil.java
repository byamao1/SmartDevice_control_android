/**

 * Title: MinaUtil.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年3月1日
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
 * @职责 
 * @属层 
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
			LogUtil.e(TAG, "input_server_URL参数非法");
			return false;			
		}			
		server_URL = input_server_URL;
		server_port = input_server_port;
		return true;
	}
	
	/**
	 * 在获取实例前，需要调用netConfig函数设置各参数。
	 * @return
	 */
	public static MinaTCPUtil getInstance(){
		if(minaTcpUtil == null){			
			minaTcpUtil = new MinaTCPUtil();			
		}
		
		return minaTcpUtil;
	}
	
	//发送加密信息与非加密信息
	public boolean sendMessage(String str_msgBody){
		LogUtil.i(TAG, "客户端链接开始...");
		IoConnector connector = new NioSocketConnector();
		//设置链接超时时间
		connector.setConnectTimeoutMillis(30000);
//		connector.getFilterChain().addLast( "logger", new LoggingFilter()); 
		//添加过滤器
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				 new TextLineCodecFactory(Charset.forName("UTF-8"))));
		//为发送与接收设置管理服务
		connector.setHandler(new MinaTcpHandler());
		try{
			ConnectFuture future = connector.connect(new InetSocketAddress(server_URL, server_port));//创建链接
			future.awaitUninterruptibly();// 等待连接创建完成
			session = future.getSession();//获得session
			session.write(str_msgBody);
		}catch (Exception e){
			LogUtil.e(TAG, "客户端链接异常...");
			return false;
		}
	
		//关闭  
        if(session!=null){  
            if(session.isConnected()){  
                session.getCloseFuture().awaitUninterruptibly();  //等待连接断开
            }  
            LogUtil.i(TAG, "客户端断开...");
            connector.dispose(true);  
        }  
		
		
		return true;
	}
}
