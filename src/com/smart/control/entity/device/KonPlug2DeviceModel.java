/**

 * Title: KonPlug2DeviceModel.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��22��
 */
package com.smart.control.entity.device;

/**
 * DeviceModel��
 * �ؿ͹�˾��СK��������
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class KonPlug2DeviceModel extends SuperDeviceModel{
	
	//-------״̬������3��״̬��------------
	//    0 δ��ȡ״̬  1 �ر�   2  ��
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
	 * ��ʼ��״̬����
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
