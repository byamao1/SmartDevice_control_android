/**

 * Title: CtlCmdConfigFactory.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.command;

import android.content.Context;

import com.smart.control.common.net.NetUtil;
import com.smart.control.config.INetConfigInfo;
import com.smart.control.entity.codeset.*;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;
import com.smart.control.entity.endecoder.*;
import com.smart.control.entity.netclient.*;

/**
 * 类型：控客公司的二代插座。
 * 生成DeviceCtlCommand类所需要的命令集、编码器、解码器、网络的对象。
 * 没必要按照抽象工厂的做法，将该类扩展几个子类，避免类爆炸。
 * @职责 
 * @属层 
 * @author ByTom
 */
public class KonPlug2CtlCmdConfigFactory extends SuperCtlCmdConfigFactory{
	
	private Context ct;
	private SuperDeviceModel deviceModel;
	private DeviceCtlMode deviceCtlMode;
	private INetConfigInfo netConfigInfo;
	private Cmd cmd;
	
	
	public KonPlug2CtlCmdConfigFactory(Context ct, SuperDeviceModel deviceModel, DeviceCtlMode deviceCtlMode, INetConfigInfo netConfigInfo, Cmd cmd){
		this.ct = ct;
		this.deviceModel = deviceModel;
		this.deviceCtlMode = deviceCtlMode;
		this.netConfigInfo = netConfigInfo;
		this.cmd = cmd;
	}	
	 
	@Override
	public SuperCtlCodeSet createCodeSet(){
		SuperCtlCodeSet codeSet;
		//检测对象
		if(deviceModel == null){
			//向提示事件总线发送消息：createCodeSet函数的deviceModel为null
			return new NoCtlCodeSet();
		}
			
		switch(deviceModel.getType()){
			case KON_PLUG_2: codeSet = new KonPlug2CtlCodeSet(ct, deviceModel, deviceCtlMode);break;			
			default: 	codeSet = new NoCtlCodeSet();break;	//默认为空集
		}
		return codeSet;
	}
	
	@Override
	public SuperEnDecoder createEnDecoder(){
		SuperEnDecoder encoder;
		//检测对象
		if((deviceModel == null)||(deviceCtlMode == null)){
			//向错误事件总线发送消息：createEnDecoder函数有变量为null
			return new NoEnDecoder();
		}
		switch(deviceModel.getType()){
			case KON_PLUG_2: encoder = new KonEnDecoder(deviceCtlMode);break;
			default: 	encoder = new NoEnDecoder();break;	//默认为空集
		}
		return encoder;
	}
	
	@Override
	public ISuperNetClient createNetClient(){
		ISuperNetClient netClient;
		//检测对象
		if((deviceModel == null)||(deviceCtlMode == null)){
			//向错误事件总线发送消息：createEnDecoder函数有变量为null
			return new NoNetClient();
		}
		
		switch(deviceCtlMode){
			case XMPP: 
				netClient = new XMPPNetClient(ct);
				netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
						NetUtil.getMacAddress(ct), NetUtil.getMacAddress(ct), 
						deviceModel.getMac() + "@" + netConfigInfo.getAddress(deviceCtlMode));
				
				
				break;			
			case AP_TCP:
				netClient = new TCPNetClient();
				//还没写配置。。。
				break;	
			case WLAN_TCP:
				netClient = new TCPNetClient();
				//对于TCP协议还要根据cmd分不同的地址
				switch(cmd){
					case CHECK_TIMER:
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											NetUtil.getMacAddress(ct)+"@timetask.kankun-smartplug.com");
						break;
					case CHECK_BRMODE:
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											"xx@getDeviceStatus.kankun-smartplug.com");
						break;
					case CHECK_LIGHT: 
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											"xx@getDeviceStatus.kankun-smartplug.com");
						break;
					case CHECK_DEVICE: 
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											"xx@getDeviceStatus.kankun-smartplug.com");
						break;
					default: 	
						netClient.netConfig(null, 0, null, null, null);
						break;
				}
				break;
			default: 	
				netClient = new NoNetClient();break;	//默认为空集
		}
		
		return netClient;
	}	
}
