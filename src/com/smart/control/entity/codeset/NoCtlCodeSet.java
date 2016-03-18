/**

 * Title: NoCtlCodeSet.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.codeset;

import android.os.Bundle;
import android.util.Log;

import com.smart.control.common.LogUtil;
import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;

/**
 * 空控制命令语句集
 * @职责 
 * @属层 
 * @author ByTom
 */
public class NoCtlCodeSet extends SuperCtlCodeSet{
		
	private String TAG = "NoCtlCodeSet";
	
	public NoCtlCodeSet(){
		LogUtil.i(TAG, "使用空控制命令集");
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
