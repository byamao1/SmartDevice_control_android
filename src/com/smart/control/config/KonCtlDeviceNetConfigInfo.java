/**

 * Title: KonNetConfig.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月19日
 */
package com.smart.control.config;

import android.content.Context;

import com.smart.control.common.net.NetUtil;
import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;

/**
 * 控客公司：
 * 控制设备的网络参数配置。
 * @职责 
 * @属层 
 * @author ByTom
 */
public class KonCtlDeviceNetConfigInfo implements INetConfigInfo{
//	DeviceCtlMode deviceCtlMode;
//	private Context ct;
	public KonCtlDeviceNetConfigInfo(){
//		this.ct = ct;
//		this.deviceCtlMode = deviceCtlMode;
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType)
	 */
	@Override
	public String getAddress(DeviceCtlMode deviceCtlMode){
		String str_address = "";
		switch(deviceCtlMode){
			case XMPP: str_address = "kankun-smartplug.com";break;
			case AP_TCP: str_address = "192.168.145.253";break;
			case WLAN_TCP:str_address = "kankun-smartplug.com";break;				
			default:str_address = "";break;
		}
		
		
		return str_address;
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType)
	 */
	@Override
	public int getPort(DeviceCtlMode deviceCtlMode){
		int port = 0;
		switch(deviceCtlMode){
			case XMPP:port = 5222;break;
			case AP_TCP: port = 37092;break;
			case WLAN_TCP:port = 9123;break;
			default:port = 0;break;
		}
		return port;
	}
}

