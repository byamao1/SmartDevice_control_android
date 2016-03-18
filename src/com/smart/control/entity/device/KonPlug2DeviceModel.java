/**

 * Title: KonPlug2DeviceModel.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月22日
 */
package com.smart.control.entity.device;

/**
 * DeviceModel。
 * 控客公司：小K二代插座
 * @职责 
 * @属层 
 * @author ByTom
 */
public class KonPlug2DeviceModel extends SuperDeviceModel{
	
	//-------状态变量（3种状态）------------
	//    0 未获取状态  1 关闭   2  打开
	public int isPlugOn;
	public int isWifiEnhance;
	public int isChargeProtect;
	public int isTimerTask;
	public int isDelayedTask;
	public int isNightLightOn;
	//--------------------------------------
		
	/**
	 * @param name
	 * @param type
	 */
	public KonPlug2DeviceModel(String name, DeviceType type) {
		super(name, type);
		initStatusData();
	}
	
	/**
	 * 初始化状态数据
	 */
	protected void initStatusData(){
		isPlugOn = 0;
		isWifiEnhance = 0;
		isChargeProtect = 0;
		isTimerTask = 0;
		isDelayedTask = 0;
		isNightLightOn = 0;
	}

}
