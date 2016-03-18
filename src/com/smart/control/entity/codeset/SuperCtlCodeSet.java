/**

 * Title: SuperCtlCodeSet.java

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
 * ����������伯�ĳ���
 * @ְ�� 
 * @���� ҵ���߼���
 * @author ByTom
 */
public abstract class SuperCtlCodeSet {
	
	protected SuperDeviceModel deviceModel;	
	protected DeviceCtlMode deviceCtlMode;
	
	/**
	 * ��ȡ�豸�������
	 * ��DeviceCtlMode����
	 * @param mode
	 * @param cmdType
	 * @param params
	 * @return
	 */
	public abstract String getCtlCode(DeviceCtlMode mode, Cmd cmdType, Bundle params);
	
	/**
	 * ��ȡ�豸�������
	 * ����DeviceCtlMode����
	 * @param cmdType
	 * @param params
	 * @return
	 */
	public abstract String getCtlCode(Cmd cmdType, Bundle params);
	
	/**
	 * ��ȡ�������õ����
	 * ����ŵ�getCtlCode�����У���������getCtlCode����ssid��key���������������Բ��˷ֿ���
	 * @param cmdType
	 * @param params
	 * @return
	 */
	public String getNetConfigCode(String str_Ssid, String str_Key){
		return null;
	}
}
