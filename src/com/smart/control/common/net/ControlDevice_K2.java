package com.smart.control.common.net;

import android.util.Log;

import com.smart.control.common.AES_Cipher;
import com.smart.control.entity.codeset.Impl_Command_K2;
import com.smart.control.entity.codeset.Interface_Command;

public class ControlDevice_K2 extends Abstract_ControlDevice{
	private static final String TAG = "ControlDevice_K2";
	private final String str_Ip_Broadcast = "255.255.255.255"; 
	private final String str_Ip_Server = "123.103.13.252"; 
	private final int port_K2 = 27431;
	private final int port_Server = 50975;
	//aes的key
	private String str_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";
	//
	@Override
	public boolean sendCtlCmd_RecData() {
		//获取控制命令
		Interface_Command cmd = new Impl_Command_K2();
		if(str_Ctl_Info == null || str_Distance == null)
			return false;
		String str_CtlCmd = cmd.get_Ctl_Command(str_Ctl_Info);
		Log.i(TAG,str_CtlCmd);
		//加密
		byte[] bytes_Result = AES_Cipher.encrypt(str_CtlCmd, str_seed);
		//发送“控制命令”给设备
		Abstract_Socket udp = new Socket_UDP();	
		if(str_Distance.equals("lan")){					
			udp.setDstInfo(str_Ip_Broadcast, port_K2);
			udp.setSendInfo(bytes_Result);
			udp.sendRecData();
		}else{
			udp.setDstInfo(str_Ip_Broadcast, port_Server);
			udp.setSendInfo(bytes_Result);
			udp.sendRecData();
		}
		return false;
	}

}
