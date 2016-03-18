/**

 * Title: DeviceCtlMode.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.device;

/**
 * 控制设备的模式
 * @职责 
 * @属层 
 * @author ByTom
 */
public enum DeviceCtlMode {

	XMPP, 
	AP_TCP, LAN_TCP, WLAN_TCP, //设备AP、局域网、互联网
	LAN_UDP, SMS
}
