/**

 * Title: SuperCtlCodeSet.java

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
 * 控制命令语句集的超类
 * @职责 
 * @属层 业务逻辑层
 * @author ByTom
 */
public abstract class SuperCtlCodeSet {
	
	protected SuperDeviceModel deviceModel;	
	protected DeviceCtlMode deviceCtlMode;
	
	/**
	 * 获取设备控制命令。
	 * 带DeviceCtlMode参数
	 * @param mode
	 * @param cmdType
	 * @param params
	 * @return
	 */
	public abstract String getCtlCode(DeviceCtlMode mode, Cmd cmdType, Bundle params);
	
	/**
	 * 获取设备控制命令。
	 * 不带DeviceCtlMode参数
	 * @param cmdType
	 * @param params
	 * @return
	 */
	public abstract String getCtlCode(Cmd cmdType, Bundle params);
	
	/**
	 * 获取网络配置的命令。
	 * 本想放到getCtlCode函数中，但是它比getCtlCode多了ssid与key两个参数，兼容性差，因此分开。
	 * @param cmdType
	 * @param params
	 * @return
	 */
	public String getNetConfigCode(String str_Ssid, String str_Key){
		return null;
	}
}
