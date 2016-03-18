/**

 * Title: TCPNetClient.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月23日
 */
package com.smart.control.entity.netclient;

import com.smart.control.common.LogUtil;
import com.smart.control.common.net.XMPPUtil;
import com.smart.control.common.net.mina.MinaTCPUtil;

/**
 * TCP协议的网络客户端。
 * @职责 
 * @属层 
 * @author ByTom
 */
public class TCPNetClient implements ISuperNetClient{
	private String TAG = "TCPNetClient";
	private MinaTCPUtil minaTcpUtil;
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.netclient.ISuperNetClient#netConfig(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void netConfig(String server_URL, int server_port, String account,
			String pwd, String target) {
		MinaTCPUtil.netConfig(server_URL, server_port);
		
	}

	/* (non-Javadoc)
	 * @see com.smart.control.entity.netclient.ISuperNetClient#sendMsg(java.lang.String)
	 */
	@Override
	public void sendMsg(String str_msgBody) {
		//检测
		if((str_msgBody == null)||("".equals(str_msgBody))){
			LogUtil.i(TAG, "sendMsg函数的参数为空,不执行发送");
			return;
		}
		//获取实例
		minaTcpUtil = MinaTCPUtil.getInstance();
		
		if(!minaTcpUtil.sendMessage(str_msgBody)){
			//向错误事件总线发送消息：向服务器发送消息失败
			
		}
		
	}

}
