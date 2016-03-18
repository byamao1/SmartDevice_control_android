/**

 * Title: INetConfig.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��19��
 */
package com.smart.control.config;

import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;

/**
 * ����������Ϣ�ӿ�
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public interface INetConfigInfo {
	
	
	
	/**
	 * Ŀ���������ַ
	 * @param deviceCtlMode
	 * @param cmd
	 * @return
	 */
	public String getAddress(DeviceCtlMode deviceCtlMode);
	/**
	 * Ŀ��˿ں�
	 * @param deviceCtlMode
	 * @param cmd
	 * @return
	 */
	public int getPort(DeviceCtlMode deviceCtlMode);
}
