/**

 * Title: DeviceCtlCommand.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.command;

import com.smart.control.common.AES_Cipher;
import com.smart.control.common.LogUtil;
import com.smart.control.config.INetConfigInfo;
import com.smart.control.entity.codeset.SuperCtlCodeSet;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;
import com.smart.control.entity.endecoder.SuperEnDecoder;
import com.smart.control.entity.netclient.ISuperNetClient;
import com.smart.control.entity.parsemsg.KonParseMsg;
import com.smart.control.entity.parsemsg.SuperParseMsg;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

/**
 * 通用的设备控制命令。
 * （没必要按照抽象工厂的做法，将该类扩展几个子类，避免类爆炸）
 * @职责 完整的负责发送数据和处理接收的数据。
 * @属层 
 * @author ByTom
 */
public class DeviceCtlCommand implements Runnable{
	private String TAG = "DeviceCtlCommand";
	//
//	private SuperDeviceModel deviceModel;
//	private DeviceCtlMode deviceCtlMode;
	private Cmd cmd;
	private Bundle params;
	//
	private SuperCtlCmdConfigFactory ctlCmdConfigFactory;
	private SuperCtlCodeSet codeSet;
	private SuperEnDecoder endecoder;
	private ISuperNetClient netClient;
	private String prefix;
	
	public DeviceCtlCommand(){
//		if(deviceModel == null){
//			//向错误事件总线发消息：DeviceCtlCommand类收到null参数
//		}else{
//			this.deviceModel = deviceModel;
//		}
	}
	
	
	/**
	 * 设置控制方式、网络参数、命令及其参数。必须调用它以后，才能运行本线程。
	 * @param ct
	 * @param deviceCtlMode
	 * @param netConfigInfo
	 * @param prefix	命令前缀
	 * @param cmd
	 * @param params
	 */
	public void prepare(String prefix,  Cmd cmd, Bundle params, SuperCtlCmdConfigFactory ctlCmdConfigFactory){

		this.ctlCmdConfigFactory = ctlCmdConfigFactory;
//		this.ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(ct, deviceModel, deviceCtlMode, netConfigInfo, cmd);	
		this.prefix = prefix;
		this.cmd = cmd;
		this.params = params;	
	}
	
	public boolean sendMsg(){
		//检查
		if(ctlCmdConfigFactory == null){
			//向事件总线发送错误事件：”DeviceCtlCommand“”sendMsg函数有null参数！“
			return false;
		}	
		this.codeSet = ctlCmdConfigFactory.createCodeSet();
		this.endecoder = ctlCmdConfigFactory.createEnDecoder();
		this.netClient = ctlCmdConfigFactory.createNetClient();		
		
		//获得code。允许使用空命令集。
		String str_code = codeSet.getCtlCode(cmd, params);
		LogUtil.i(TAG, "明文命令："+str_code);
//		if("".equals(str_code)){
			//向错误事件总线发送消息：”DeviceCtlCommand“”得到的命令语句为空“
//			return false;
//		}
		//code加密编码。允许使用空编码器。
		String str_Result = endecoder.getEncode(prefix, str_code);
		Log.i(TAG, "密文命令："+str_Result);
		//发送处理过的code给设备
		netClient.sendMsg(str_Result);	
		return true;
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		sendMsg();		
	}	
		
}
