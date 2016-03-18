/**

 * Title: IconFactory.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��23��
 */
package com.smart.control.component;

import com.smart.control.entity.device.DeviceType;

/**
 * ����ͼ��Ĺ���ģʽ�ӿ�
 * @ְ�� ����DeviceModel��DeviceType���ɶ�Ӧ��ͼ��
 * @���� presentation��
 * @author ByTom
 */
public interface IIconFactory {
	/**
	 * ����DeviceModel��DeviceType���ɶ�Ӧ��ͼ��
	 * @param type
	 * @return
	 */
	public int createIcon(DeviceType type);
}
