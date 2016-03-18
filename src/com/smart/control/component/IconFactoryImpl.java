/**

 * Title: IconFactoryImpl.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月23日
 */
package com.smart.control.component;

import com.smart.control.R;
import com.smart.control.entity.device.DeviceType;

/**
 * 生成图标的工厂模式实现
 * @职责 
 * @属层 presentation层
 * @author ByTom
 */
public class IconFactoryImpl implements IIconFactory{

	/* (non-Javadoc)
	 * @see com.smart.control.component.IIconFactory#createIcon(com.smart.control.entity.DeviceType)
	 */
	@Override
	public int createIcon(DeviceType type) {
		int resource = 0;
		switch(type){
		 case KON_PLUG_1:
			resource = R.drawable.add_k1;
			break;
		 case KON_PLUG_2:
			resource = R.drawable.add_k2;
			break;
		 case KON_PLUG_2PRO:
			resource = R.drawable.add_k2pro;
			break;
		 case KON_PLUG_MINI:
			resource = R.drawable.add_mini;
			break;
		 case KON_TELECONTROLLER:
			resource = R.drawable.add_yk;
			break;
		 default:
			resource = R.drawable.add_no;
			break;	
		 }
		return resource;
	}

}
