package com.smart.control.common.net;

public abstract class Abstract_ControlDevice implements Runnable{
	String str_Ctl_Info = null; //����ַ����ǽ��ն����������ɳ�����
	String str_Distance = null;
	
	//
	public void set_Ctl_Info(String str_Ctl_Info,String str_Distance){
		this.str_Ctl_Info = str_Ctl_Info;
		this.str_Distance = str_Distance;
	}
	//���������������
	abstract public boolean sendCtlCmd_RecData();
	public void run() {
		sendCtlCmd_RecData();
	}
}
