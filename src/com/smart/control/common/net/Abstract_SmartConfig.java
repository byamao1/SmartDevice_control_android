package com.smart.control.common.net;

import com.smart.control.Controller;

import android.content.Context;
import android.widget.GridView;

//对智能家居进行配置SSID和密码
public abstract class Abstract_SmartConfig implements Runnable{
	protected Context ct;
	protected Controller controller;
	//wifiAdmin
	protected WifiAdmin wifiAdmin;
	//
	String str_Ssid = "";
	String str_Key = "";	

	
	//向智能家居发送ssid与key  返回值：错误字符串或成功
	abstract public void send_ConfigInfo();
	//确认智能家居联网成功
	abstract public void confirm_ConfigSuccess();
	//设置SSID和密码
	public void set_SsidKey(String str_Ssid, String str_Key){
		this.str_Ssid = str_Ssid;
		this.str_Key = str_Key;
	}
	
	protected void sleep(long time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		send_ConfigInfo();		
	}
}
