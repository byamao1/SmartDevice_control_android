package com.smart.control.common.net;

import com.smart.control.Controller;

import android.content.Context;
import android.widget.GridView;

//�����ܼҾӽ�������SSID������
public abstract class Abstract_SmartConfig implements Runnable{
	protected Context ct;
	protected Controller controller;
	//wifiAdmin
	protected WifiAdmin wifiAdmin;
	//
	String str_Ssid = "";
	String str_Key = "";	

	
	//�����ܼҾӷ���ssid��key  ����ֵ�������ַ�����ɹ�
	abstract public void send_ConfigInfo();
	//ȷ�����ܼҾ������ɹ�
	abstract public void confirm_ConfigSuccess();
	//����SSID������
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
