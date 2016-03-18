/**

 * Title: XMPPNetClient.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月3日
 */
package com.smart.control.entity.netclient;

import com.smart.control.common.LogUtil;
import com.smart.control.common.net.NetUtil;
import com.smart.control.common.net.XMPPUtil;
import com.smart.control.entity.command.Cmd;

import android.content.Context;
import android.util.Log;

/**
 * XMPP协议的网络客户端。
 * @职责 
 * @属层 
 * @author ByTom
 */
public class XMPPNetClient implements ISuperNetClient{	
	private String TAG = "XMPPNetClient";
	private XMPPUtil xmppUtil;
	private Context ct;
//	private XmppTool xmppTool;
	
	public XMPPNetClient(Context ct){
		this.ct = ct;
	}	
	
	/**
	 * 检查xmppUtil及与服务器连接情况
	 * @return true：没有问题	false：有问题
	 */
	private boolean checkXmppUtil() {
		//非空检测
		if(xmppUtil == null){
			//向错误事件总线发消息：xmppUtil为null
			
			return false;
		}
		if(!isNetConnect()){
			//向错误事件总线发消息：请重新login XMPP服务器
			
			return false;
		}
		return true;
	}
	
	private static boolean isNetConnect(){
		if(XMPPUtil.connection != null){
			if(XMPPUtil.connection.isConnected()){
				if(XMPPUtil.connection.isAuthenticated()){
					return true;
				}
			}
		}
		return false;
	}	
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.netclient.ISuperNetClient#netConfig(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void netConfig(String server_URL, int server_port, String account,
			String pwd, String target) {
		//进行客户端配置
		XMPPUtil.netConfig(server_URL, server_port, account, pwd, target);		
		
	}

	/* (non-Javadoc)
	 * @see com.smart.control.entity.netclient.ISuperNetClient#sendMsg(java.lang.String, com.smart.control.entity.CmdType)
	 */
	@Override
	public void sendMsg(String str_msgBody) {
		//检测
		if((str_msgBody == null)||("".equals(str_msgBody))){
			LogUtil.i(TAG, "sendMsg函数的参数为空,不执行发送");
			return;
		}
		//获取实例
		xmppUtil = XMPPUtil.getInstance(ct);
		//检查
		if(!checkXmppUtil()){
			//向错误事件总线发消息：xmppUtil检查有问题
			
			return;
		}
		
		if(!xmppUtil.sendMessage(str_msgBody)){
			//向错误事件总线发送消息：向服务器发送消息失败
			
		}
		
	}



}
