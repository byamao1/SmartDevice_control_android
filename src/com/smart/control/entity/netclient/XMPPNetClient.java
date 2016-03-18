/**

 * Title: XMPPNetClient.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��3��
 */
package com.smart.control.entity.netclient;

import com.smart.control.common.LogUtil;
import com.smart.control.common.net.NetUtil;
import com.smart.control.common.net.XMPPUtil;
import com.smart.control.entity.command.Cmd;

import android.content.Context;
import android.util.Log;

/**
 * XMPPЭ�������ͻ��ˡ�
 * @ְ�� 
 * @���� 
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
	 * ���xmppUtil����������������
	 * @return true��û������	false��������
	 */
	private boolean checkXmppUtil() {
		//�ǿռ��
		if(xmppUtil == null){
			//������¼����߷���Ϣ��xmppUtilΪnull
			
			return false;
		}
		if(!isNetConnect()){
			//������¼����߷���Ϣ��������login XMPP������
			
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
		//���пͻ�������
		XMPPUtil.netConfig(server_URL, server_port, account, pwd, target);		
		
	}

	/* (non-Javadoc)
	 * @see com.smart.control.entity.netclient.ISuperNetClient#sendMsg(java.lang.String, com.smart.control.entity.CmdType)
	 */
	@Override
	public void sendMsg(String str_msgBody) {
		//���
		if((str_msgBody == null)||("".equals(str_msgBody))){
			LogUtil.i(TAG, "sendMsg�����Ĳ���Ϊ��,��ִ�з���");
			return;
		}
		//��ȡʵ��
		xmppUtil = XMPPUtil.getInstance(ct);
		//���
		if(!checkXmppUtil()){
			//������¼����߷���Ϣ��xmppUtil���������
			
			return;
		}
		
		if(!xmppUtil.sendMessage(str_msgBody)){
			//������¼����߷�����Ϣ���������������Ϣʧ��
			
		}
		
	}



}
