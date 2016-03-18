/**

 * Title: ReceiveXmppMsgHandler.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��19��
 */
package com.smart.control.eventbus;

import com.smart.control.config.INetConfigInfo;
import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;
import com.smart.control.entity.endecoder.SuperEnDecoder;
import com.smart.control.entity.parsemsg.SuperParseMsg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * �������������Ϣ���д���
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class ReceiveNetMsgHandler{
	private SuperDeviceModel deviceModel;
	private SuperEnDecoder enDecoder;
	private SuperParseMsg parseMsg;
	private Handler handler;
//	private INetConfigInfo netConfigInfo;
	public ReceiveNetMsgHandler(SuperDeviceModel deviceModel,SuperEnDecoder enDecoder, SuperParseMsg parseMsg, Handler handler){
		this.deviceModel = deviceModel;
		this.enDecoder = enDecoder;
		this.parseMsg = parseMsg;
//		this.netConfigInfo = netConfigInfo;
		this.handler = handler;
	}


	/**
	 * 
	 * @param str_encode ���ܵ��ַ���
	 */
	public void handleMsg(String str_encode) {				
		//����
		String str_decode = enDecoder.getDecode(str_encode);
		//��ȡmac
		String str_deviceMac = parseMsg.getMac(str_decode);
		//��mac�Ƿ���ͬ
		if(deviceModel.getMac().equals(str_deviceMac)){
			//��ȡcmd
			String str_cmd = parseMsg.getCmd(str_decode);
			//����Message,֪ͨhandler����״̬
			Message msg = new Message();			 
            Bundle b = new Bundle();// ������� 
            b.putString("cmd", str_cmd); 
            msg.setData(b); 
			handler.sendMessage(msg);
		}
		
	}
}
