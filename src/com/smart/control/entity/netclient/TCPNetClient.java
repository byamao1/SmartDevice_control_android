/**

 * Title: TCPNetClient.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��23��
 */
package com.smart.control.entity.netclient;

import com.smart.control.common.LogUtil;
import com.smart.control.common.net.XMPPUtil;
import com.smart.control.common.net.mina.MinaTCPUtil;

/**
 * TCPЭ�������ͻ��ˡ�
 * @ְ�� 
 * @���� 
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
		//���
		if((str_msgBody == null)||("".equals(str_msgBody))){
			LogUtil.i(TAG, "sendMsg�����Ĳ���Ϊ��,��ִ�з���");
			return;
		}
		//��ȡʵ��
		minaTcpUtil = MinaTCPUtil.getInstance();
		
		if(!minaTcpUtil.sendMessage(str_msgBody)){
			//������¼����߷�����Ϣ���������������Ϣʧ��
			
		}
		
	}

}
