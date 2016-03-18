/**

 * Title: ReceiveXmppMsgHandler.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月19日
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
 * 对来自网络的消息进行处理
 * @职责 
 * @属层 
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
	 * @param str_encode 加密的字符串
	 */
	public void handleMsg(String str_encode) {				
		//解码
		String str_decode = enDecoder.getDecode(str_encode);
		//获取mac
		String str_deviceMac = parseMsg.getMac(str_decode);
		//看mac是否相同
		if(deviceModel.getMac().equals(str_deviceMac)){
			//获取cmd
			String str_cmd = parseMsg.getCmd(str_decode);
			//发送Message,通知handler更新状态
			Message msg = new Message();			 
            Bundle b = new Bundle();// 存放数据 
            b.putString("cmd", str_cmd); 
            msg.setData(b); 
			handler.sendMessage(msg);
		}
		
	}
}
