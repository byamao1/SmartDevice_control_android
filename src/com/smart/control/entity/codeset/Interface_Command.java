package com.smart.control.entity.codeset;

public interface Interface_Command {
	//��ȡ�����豸��smartConfig����
	public String get_SmartConfigOut_command(String str_Ssid, String str_Key);
	//��ȡ���豸���Ƶ�������������ַ������ɣ�
	public String get_Ctl_Command(String str_Input);
}
