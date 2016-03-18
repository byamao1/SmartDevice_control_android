package com.smart.control.entity.codeset;

public interface Interface_Command {
	//获取配置设备的smartConfig命令
	public String get_SmartConfigOut_command(String str_Ssid, String str_Key);
	//获取对设备控制的命令（根据输入字符串生成）
	public String get_Ctl_Command(String str_Input);
}
