package com.smart.control;

import com.smart.control.entity.device.DeviceManagement;
import com.smart.control.service.EventHandlerService;
import com.smart.control.common.*;
import android.app.Application;
import android.content.Intent;
import android.util.Log;


/**
 * 这个类是为了保证DeviceManagement等业务逻辑层的管理类持久化用的
 * @职责 
 * @属层 
 * @author ByTom
 */
public class MyApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.i("app","这是app");
		// Initialize the singletons so their instances
		// are bound to the application process.
		initSingletons();
		
		//设定LogUtil的tag
		LogUtil.setTag("com.smart.control");
		//设定Context
		UserHintUtil.setCt(getApplicationContext());
		DebugHintUtil.setCt(getApplicationContext());
		//开启service		
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