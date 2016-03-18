/**

 * Title: KPlug2CtlCodeSet.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.codeset;

import android.os.Bundle;

import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;

/**
 * ###还没写###
 * @职责 
 * @属层 
 * @author ByTom
 */
public class KonPlug1CtlCodeSet extends SuperCtlCodeSet{
	public KonPlug1CtlCodeSet(SuperDeviceModel deviceModel, DeviceCtlMode deviceCtlMode){
		super.deviceModel = deviceModel;
		super.deviceCtlMode = deviceCtlMode;
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType)
	 */
	@Override
	public String getCtlCode(DeviceCtlMode mode, Cmd cmdType, Bundle params){
		return "";
	}

	/* (non-Javadoc)
	 * @see com.smart.control.entity.codeset.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.Cmd, android.os.Bundle)
	 */
	@Override
	public String getCtlCode(Cmd cmdType, Bundle params) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	
}
