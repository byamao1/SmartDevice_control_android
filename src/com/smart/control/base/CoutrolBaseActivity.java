/**

 * Title: CoutrolBaseActivity.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月22日
 */
package com.smart.control.base;

import org.simple.eventbus.EventBus;

import android.os.Bundle;

import com.smart.control.entity.DeviceController;
import com.smart.control.entity.device.DeviceManagement;
import com.smart.control.entity.device.SuperDeviceModel;

/**
 * 控制页面的超类
 * @职责 
 * @属层 
 * @author ByTom
 */
public class CoutrolBaseActivity extends RootActivity{
//	protected SuperDeviceModel deviceModel;
	protected DeviceController deviceController;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		// 注册事件总线对象
        EventBus.getDefault().register(this);
	}
	
	@Override
    protected void onDestroy() {
        // 注销事件总线对象
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
