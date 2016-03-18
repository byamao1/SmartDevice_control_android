package com.smart.control;

import com.smart.control.entity.device.DeviceManagement;
import com.smart.control.service.EventHandlerService;
import com.smart.control.common.*;
import android.app.Application;
import android.content.Intent;
import android.util.Log;


/**
 * �������Ϊ�˱�֤DeviceManagement��ҵ���߼���Ĺ�����־û��õ�
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class MyApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.i("app","����app");
		// Initialize the singletons so their instances
		// are bound to the application process.
		initSingletons();
		
		//�趨LogUtil��tag
		LogUtil.setTag("com.smart.control");
		//�趨Context
		UserHintUtil.setCt(getApplicationContext());
		DebugHintUtil.setCt(getApplicationContext());
		//����service		
//		this.startService(new Intent(this, EventHandlerService.class));
	}

	protected void initSingletons()
	{
		// Initialize the instance of Singleton
		DeviceManagement.initInstance();
	}

	public void customAppMethod()
	{
		// Custom application method
	}
}