/**

 * Title: CoutrolBaseActivity.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��22��
 */
package com.smart.control.base;

import org.simple.eventbus.EventBus;

import android.os.Bundle;

import com.smart.control.entity.DeviceController;
import com.smart.control.entity.device.DeviceManagement;
import com.smart.control.entity.device.SuperDeviceModel;

/**
 * ����ҳ��ĳ���
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class CoutrolBaseActivity extends RootActivity{
//	protected SuperDeviceModel deviceModel;
	protected DeviceController deviceController;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		// ע���¼����߶���
        EventBus.getDefault().register(this);
	}
	
	@Override
    protected void onDestroy() {
        // ע���¼����߶���
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
