/**

 * Title: CmdType.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.command;

/**
 * 
 * @职责 
 * @属层 业务逻辑层
 * @author ByTom
 */
public enum Cmd {
	
	NET_CONFIG,
	GET_FRIEND,
	CHECK_TIMER, CHECK_BRMODE, CHECK_LIGHT, CHECK_DEVICE,	//检查设备各状态
	DEVICE_ON, DEVICE_OFF, LIGHT_ON, LIGHT_OFF,
	DEVICE_ON_TIMER, DEVICE_OFF_TIMER, LIGHT_ON_TIMER, LIGHT_OFF_TIMER,
	DEVICE_ON_DELAY, DEVICE_OFF_DELAY;
	
}
