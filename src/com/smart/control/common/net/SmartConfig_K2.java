package com.smart.control.common.net;

import android.content.Context;
import android.util.Log;









import com.smart.control.Controller;
import com.smart.control.Notice;
import com.smart.control.common.AES_Cipher;
import com.smart.control.entity.codeset.Impl_Command_K2;
import com.smart.control.entity.codeset.Interface_Command;

public class SmartConfig_K2 extends Abstract_SmartConfig {
	private static final String TAG = "SmartConfig_K2";
//	private Context ct;
	//wifiAdmin
//	private WifiAdmin wifiAdmin;
	//aes的key
	private String str_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";
	//命令
	private Interface_Command cmd = new Impl_Command_K2();
	//tcp连接
	private Abstract_Socket tcp = new Socket_TCP();
	private final String str_Ip_K2AP = "192.168.145.253"; 
	private final int port_K2AP = 37092;
	private final int timeout = 100000;
	private byte[] bytes_Rec;
	//记下当前热点的id
	int id_Last = -1;
	//
	String str_Ssid_K2AP = "0K_SP3";
	
	
	public SmartConfig_K2(Context ct,Controller controller){
		this.ct = ct;
		this.controller = controller;
		this.wifiAdmin = new WifiAdmin(ct);
		bytes_Rec = null;
	}
	
		
	@Override
	public void send_ConfigInfo() {
		//连接智能家居AP热点
		String str_Connect_Result = connect_SmartDevice();
		if(!str_Connect_Result.equals("连接 0K_SP3 成功")){
			Log.i(TAG,str_Connect_Result);
			return ;		
		}
		//更新网络状态信息,等到连接成功再tcp
//		long l_Start_Time = System.currentTimeMillis();	
//		long l_Max_Time = 12000;				
//		do{		
//			long l_havePassed_Time  = System.currentTimeMillis() - l_Start_Time;
//			//隔4秒查询一次
//			if((l_havePassed_Time % 4000)==0){
//				wifiAdmin.updataState(ct);
//				Log.i(TAG,"已过去："+String.valueOf(l_havePassed_Time));
//				if(wifiAdmin.isWifiConnected() == true){
//					sleep(3000);
//					Log.i(TAG,"连接退出循环");
//					break;
//				}
//			}
//			//最多l_Max_Time就要退出循环 		
//			if(l_havePassed_Time>l_Max_Time){
//				Log.i(TAG,"超时退出循环:"+String.valueOf(l_havePassed_Time));
//				break;						
//			}
//		}while(true);		
		//给wifi连接时间		
		sleep(10000);	
		
		//生成需要发送的加密后数据	
		byte[] bytes_EnCryptData = generate_SmartConfigOut_EnCryptData(str_Ssid, str_Key);			
		//通过TCP连接发送数据
		sendRecData_ByTcp(bytes_EnCryptData);
		//给tcp数据传输时间		
		sleep(3000);
		//重新连接原来的wifi
		wifiAdmin.connectWifi_ById(id_Last);
		Log.i(TAG,"配置成功！");
		return ;
	}
	//
	private byte[] generate_SmartConfigOut_EnCryptData(String str_Ssid, String str_Key){
		//获取配置命令
		String str_SmartConfigOut_Command = get_SmartConfigOut_command(str_Ssid, str_Key);
		//对网络配置命令进行aes加密						
		byte[] bytes_Result = AES_Cipher.encrypt(str_SmartConfigOut_Command, str_seed);	
//		String str_Result = AES_Cipher.toHex(bytes_Result);	
//		Log.i(TAG,"加密结果：" + str_Result);
//		String strResult2 = AES_Cipher.decrypt(bytes_Result, str_seed);		
//		Log.i(TAG,"解密结果：" + strResult2);
		return bytes_Result;
	}
	//
	private void sendRecData_ByTcp(byte[] bytes_Result){
		tcp.setDstInfo(str_Ip_K2AP, port_K2AP);
		tcp.setSendInfo(bytes_Result, timeout);
		bytes_Rec = tcp.sendRecData();
		if(bytes_Rec != null){
			confirm_ConfigSuccess();
		}
//		new Thread(tcp).start();
	}
	//生成配置命令
	private String get_SmartConfigOut_command(String str_Ssid, String str_Key){
		String str_Command = cmd.get_SmartConfigOut_command(str_Ssid, str_Key);
		return str_Command;
	}
	//连接智能家居AP热点
	private String connect_SmartDevice(){
		//开
		wifiAdmin.openWifi();	
		//检查当前是否连接wifi
//		if(wifiAdmin.isWifiConnected()==false)
//			return "当前未连接wifi";
		//记下当前热点的id
		id_Last = wifiAdmin.getNetworkId();
		//断开当前连接
		if(wifiAdmin.disconnectWifi_ById(id_Last) == false)
			return "断开连接失败";
		//连接0K_SP3
		if(wifiAdmin.connectWifi_BySsid(str_Ssid_K2AP) == false)	
			return "连接 0K_SP3 失败";	
//		if(wifiAdmin.isWifiConnected())
//			Log.e(TAG,"连接检查成功");
		
		return "连接 0K_SP3 成功";
	}
	
	
	
	@Override
	public void confirm_ConfigSuccess() {
		String strResult = AES_Cipher.decrypt(bytes_Rec, str_seed);		
		Log.d(TAG,"收到数据的解密结果：" + strResult);	 
		controller.dismiss_WaitDialog_SmartConfig();
	}


	
	
	
}
