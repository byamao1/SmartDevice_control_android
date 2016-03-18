/**

 * Title: ControlActivityFactory.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��20��
 */
package com.smart.control.activity;

import com.smart.control.base.RootActivity;
import com.smart.control.entity.device.DeviceType;

/**
 * ����DeviceModel�����ͣ�������Ӧ�Ŀ���ҳ��
 * @author ByTom
 */
public class ControlActivityFactory {
	static public Class createCtlActivity(DeviceType type){
		switch(type){
		case KON_PLUG_1:return KonPlug1ControlActivity.class; 
		case KON_PLUG_2:return KonPlug2ControlActivity.class; 
		default:		return DefaultControlActivity.class;
		}
	}
}
