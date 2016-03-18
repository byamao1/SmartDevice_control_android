/**

 * Title: KPlug1CtlCodeSet.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.codeset;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.smart.control.common.net.NetUtil;
import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;

/**
 * 控客公司的插座二代，控制命令语句集
 * @职责 
 * @属层 
 * @author ByTom
 */
public class KonPlug2CtlCodeSet extends SuperCtlCodeSet{
	private String TAG = "KonPlug2CtlCodeSet";
	private Context ct;
	public KonPlug2CtlCodeSet(Context ct, SuperDeviceModel deviceModel, DeviceCtlMode deviceCtlMode){
		this.ct = ct;
		super.deviceModel = deviceModel;
		super.deviceCtlMode = deviceCtlMode;
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.codeset.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.Cmd, android.os.Bundle)
	 */
	@Override
	public String getCtlCode(Cmd cmd, Bundle params) {		
		return getCtlCode(deviceCtlMode, cmd, params);
	}	
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType)
	 */
	@Override
	public String getCtlCode(DeviceCtlMode mode, Cmd cmd, Bundle params){
		if((deviceCtlMode == null)||(cmd == null)){
			Log.i(TAG, "getCtlCode函数的参数mode或cmd为null！");
			return "";
		}
		String str_code = "";
		String str_mac = "";
		//根据cmd设定mac。查询命令大部分都是设备mac，控制命令都是手机mac
		switch(cmd){
			case CHECK_TIMER:str_mac = deviceModel.getMac();break;
			case CHECK_BRMODE:str_mac = deviceModel.getMac();break;
			case CHECK_LIGHT: str_mac = deviceModel.getMac();break;
			case CHECK_DEVICE: str_mac = deviceModel.getMac();break;
			default: str_mac = NetUtil.getMacAddress(ct);break;
		}
		//命令前部
		String str_forCode = "";
		switch(deviceCtlMode){
			case XMPP: str_forCode = "wan_phone%" + str_mac + "%" + deviceModel.getPwd() + "%";break;
			case WLAN_TCP:str_forCode = "wan_phone%" + str_mac + "%" + deviceModel.getPwd() + "%";break;
			default: str_forCode = "";break;
		}
		//
	    switch(cmd){
	    	case GET_FRIEND:str_code = "wan_phone%" + str_mac + "%nopassword%getfriend%freq";break;//这个命令特殊，没有密码
	    	case CHECK_TIMER:str_code = str_forCode + "check#relay%timer";break;
	    	case CHECK_BRMODE: str_code = str_forCode + "check%brmode";break;
	    	case CHECK_LIGHT: str_code = str_forCode + "check%light";break;
	    	case CHECK_DEVICE:str_code = str_forCode + "check%relay";break;
			case DEVICE_ON: str_code = str_forCode + "open%relay";break;
			case DEVICE_OFF: str_code = str_forCode + "close%relay";break;
			case LIGHT_ON: str_code = str_forCode + "open%light";break;
			case LIGHT_OFF: str_code = str_forCode + "close%light";break;
			default : str_code = "";break;
		}	    
	    if(params != null){
	    	
	    }
		return str_code;		
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.codeset.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.Cmd, android.os.Bundle)
	 */
	@Override
	public String getNetConfigCode(String str_Ssid, String str_Key){
		String str_code = "phone%"+str_Ssid+"%"+str_Key+"%nopassword%name%GMT+0800" + "\0" + "V"+"\0\0";		
//		Log.i(TAG,"命令：" + str_code);
		return str_code;
	}	
}
