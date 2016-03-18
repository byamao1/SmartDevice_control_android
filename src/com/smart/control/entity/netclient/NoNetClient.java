/**

 * Title: NoNetClient.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��13��
 */
package com.smart.control.entity.netclient;

import android.util.Log;

import com.smart.control.common.LogUtil;
import com.smart.control.entity.command.Cmd;

/**
 * 
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class NoNetClient implements ISuperNetClient{
	
	private String TAG = "NoNetClient";
	
	public NoNetClient(){
		LogUtil.i(TAG, "ʹ�ÿ����磨NoNetClient��");
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.netclient.ISuperNetClient#sendMsg(java.lang.String, com.smart.control.entity.CmdType)
	 */
	@Override
	public void sendMsg(String str_msgBody) {
		
	}

	/* (non-Javadoc)
	 * @see com.smart.control.entity.netclient.ISuperNetClient#netConfig(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void netConfig(String server_URL, int server_port, String account,
			String pwd, String target) {
		// TODO Auto-generated method stub
		
	}

}
