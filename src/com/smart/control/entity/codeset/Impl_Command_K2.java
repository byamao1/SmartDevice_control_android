package com.smart.control.entity.codeset;

import java.text.SimpleDateFormat;

import android.util.Log;

public class Impl_Command_K2 implements Interface_Command{
	private static final String TAG = "command_K2";
	@Override
	public String get_SmartConfigOut_command(String str_Ssid, String str_Key) {
//		byte[] aa={3};
//		String str_t = new String(aa);
		String str_SmartConfigOut_Command = "phone%"+str_Ssid+"%"+str_Key+"%nopassword%name%GMT+0800" + "\0" + "V"+"\0\0";		
		Log.i(TAG,"命令：" + str_SmartConfigOut_Command);
		return str_SmartConfigOut_Command;
	}
	@Override
	public String get_Ctl_Command(String str_Input) {
		String str_Output = null;
		//远程控制开灯命令
		if(str_Input.equals("wan_light_on")){
			str_Output = "wan_phone%28-d9-8a-05-13-ba%nopassword%open%light";
		}
		//本地控制开灯命令
		if(str_Input.equals("lan_light_on")){
//			str_Output = "lan_phone%mac%nopassword%" + get_DataTime() + "%heart";
			str_Output = "lan_phone%28-d9-8a-05-13-ba%nopassword%close%light";
		}
		return str_Output;
	}
	private String get_DataTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
		String str_DataTime = sDateFormat.format(new java.util.Date());
		return str_DataTime;
	}
}
