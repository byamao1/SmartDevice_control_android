/**

 * Title: IconFactory.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月23日
 */
package com.smart.control.component;

import com.smart.control.entity.device.DeviceType;

/**
 * 生成图标的工厂模式接口
 * @职责 根据DeviceModel的DeviceType生成对应的图标
 * @属层 presentation层
 * @author ByTom
 */
public interface IIconFactory {
	/**
	 * 根据DeviceModel的DeviceType生成对应的图标
	 * @param type
	 * @return
	 */
	public int createIcon(DeviceType type);
}
