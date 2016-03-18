package com.smart.control;

import com.smart.control.common.net.Abstract_ControlDevice;
import com.smart.control.common.net.Abstract_SmartConfig;
import com.smart.control.common.net.ControlDevice_K2;
import com.smart.control.common.net.SmartConfig_K2;
import com.smart.control.entity.codeset.Impl_Command_K2;
import com.smart.control.entity.codeset.Interface_Command;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

//MVC�еĿ�����
public class Controller {
	private static final String TAG = "Controller";
	private MainActivity activity_SmartConfig;
	private Abstract_ControlDevice controlDevice = new ControlDevice_K2();
	//���캯��
	public Controller(MainActivity activity){
		this.activity_SmartConfig = activity;
	}
	//��ʼ����smartConfig
	public void start_SmartConfig(Context ct,String str_Ssid, String str_Key){
		Abstract_SmartConfig sc = new SmartConfig_K2(ct,this);	
		sc.set_SsidKey(str_Ssid, str_Key);
		new Thread(sc).start();		
	}
	//���յ��豸ͨ��tcp��������Ϣʱ��ȡ���ȴ��Ի���
	public void dismiss_WaitDialog_SmartConfig(){
		activity_SmartConfig.dismiss_WaitDialog_SmartConfig();
	}
	
	//�����豸
	//��ڲ��� 
	//str_Ctl_Info:���ƵĶ�����        str_Distance:  wlanԶ��     lan���� 
	public void control_Device(String str_Ctl_Info,String str_Distance){
		controlDevice.set_Ctl_Info(str_Ctl_Info,str_Distance);
		new Thread(controlDevice).start();
		
	}
}
