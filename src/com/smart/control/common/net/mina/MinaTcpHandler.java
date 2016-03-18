/**

 * Title: MinaClientHandler.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月18日
 */
package com.smart.control.common.net.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.simple.eventbus.EventBus;

import com.smart.control.common.LogUtil;
import com.smart.control.eventbus.ReceiveTCPMsgEvent;
import com.smart.control.eventbus.ReceiveXMPPMsgEvent;

/**
 * 
 * @职责 
 * @属层 
 * @author ByTom
 */
public class MinaTcpHandler extends IoHandlerAdapter{
	
	private static final String TAG = "MinaTcpHandler";
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)throws Exception {
		LogUtil.e(TAG, "客户端发生异常");
		if(session != null){
			session.close(true);
		}
		super.exceptionCaught(session, cause);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)throws Exception {
		String msg = message.toString();
		LogUtil.i(TAG, "MinaTCP客户端收到信息");
		//向接收网络消息事件总线发消息：message.getBody()  tag:tcp
		EventBus.getDefault().post(new ReceiveTCPMsgEvent(msg), "tcp");
		
		
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {	
		super.messageSent(session, message);
	}
	
//	@Override  
//    public void sessionClosed(IoSession session) throws Exception {  
//        // TODO Auto-generated method stub  
//		LogUtil.i(TAG, "client与:"+session.getRemoteAddress().toString()+"断开连接");
////		if(session != null){
////			super.sessionClosed(session);
////		}
////		super.sessionClosed(session);
//    }  
//  
    @Override  
    public void sessionCreated(IoSession session) throws Exception {  
        // TODO Auto-generated method stub  
        LogUtil.i(TAG, "client与:"+session.getRemoteAddress().toString()+"建立连接");  
        super.sessionCreated(session);
    }  
  
  
//    @Override  
//    public void sessionOpened(IoSession session) throws Exception {  
//        // TODO Auto-generated method stub  
//        LogUtil.i(TAG, "MinaTcp打开连接");  
//    }  
}
