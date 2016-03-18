package com.smart.control.common.net;

public abstract class Abstract_ControlDevice implements Runnable{
	String str_Ctl_Info = null; //这个字符串是接收短命令以生成长命令
	String str_Distance = null;
	
	//
	public void set_Ctl_Info(String str_Ctl_Info,String str_Distance){
		this.str_Ctl_Info = str_Ctl_Info;
		this.str_Distance = str_Distance;
	}
	//发送命令并接收数据
	abstract public boolean sendCtlCmd_RecData();
	public void run() {
		sendCtlCmd_RecData();
	}
}
