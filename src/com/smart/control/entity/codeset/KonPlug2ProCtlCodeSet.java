/**

 * Title: KPlug2PROCtlCodeSet.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��27��
 */
package com.smart.control.entity.codeset;

import android.os.Bundle;

import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;

/**
 * ###��ûд###
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class KonPlug2ProCtlCodeSet extends SuperCtlCodeSet{
	public KonPlug2ProCtlCodeSet(SuperDeviceModel deviceModel, DeviceCtlMode deviceCtlMode){
		super.deviceModel = deviceModel;
		super.deviceCtlMode = deviceCtlMode;
	}
	
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType, java.lang.String)
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
