/**

 * Title: INetConfig.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月19日
 */
package com.smart.control.config;

import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;

/**
 * 网络配置信息接口
 * @职责 
 * @属层 
 * @author ByTom
 */
public interface INetConfigInfo {
	
	
	
	/**
	 * 目标服务器地址
	 * @param deviceCtlMode
	 * @param cmd
	 * @return
	 */
	public String getAddress(DeviceCtlMode deviceCtlMode);
	/**
	 * 目标端口号
	 * @param deviceCtlMode
	 * @param cmd
	 * @return
	 */
	public int getPort(DeviceCtlMode deviceCtlMode);
}
