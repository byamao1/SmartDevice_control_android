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
	//aes��key
	private String str_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";
	//����
	private Interface_Command cmd = new Impl_Command_K2();
	//tcp����
	private Abstract_Socket tcp = new Socket_TCP();
	private final String str_Ip_K2AP = "192.168.145.253"; 
	private final int port_K2AP = 37092;
	private final int timeout = 100000;
	private byte[] bytes_Rec;
	//���µ�ǰ�ȵ��id
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
		//�������ܼҾ�AP�ȵ�
		String str_Connect_Result = connect_SmartDevice();
		if(!str_Connect_Result.equals("���� 0K_SP3 �ɹ�")){
			Log.i(TAG,str_Connect_Result);
			return ;		
		}
		//��������״̬��Ϣ,�ȵ����ӳɹ���tcp
//		long l_Start_Time = System.currentTimeMillis();	
//		long l_Max_Time = 12000;				
//		do{		
//			long l_havePassed_Time  = System.currentTimeMillis() - l_Start_Time;
//			//��4���ѯһ��
//			if((l_havePassed_Time % 4000)==0){
//				wifiAdmin.updataState(ct);
//				Log.i(TAG,"�ѹ�ȥ��"+String.valueOf(l_havePassed_Time));
//				if(wifiAdmin.isWifiConnected() == true){
//					sleep(3000);
//					Log.i(TAG,"�����˳�ѭ��");
//					break;
//				}
//			}
//			//���l_Max_Time��Ҫ�˳�ѭ�� 		
//			if(l_havePassed_Time>l_Max_Time){
//				Log.i(TAG,"��ʱ�˳�ѭ��:"+String.valueOf(l_havePassed_Time));
//				break;						
//			}
//		}while(true);		
		//��wifi����ʱ��		
		sleep(10000);	
		
		//������Ҫ���͵ļ��ܺ�����	
		byte[] bytes_EnCryptData = generate_SmartConfigOut_EnCryptData(str_Ssid, str_Key);			
		//ͨ��TCP���ӷ�������
		sendRecData_ByTcp(bytes_EnCryptData);
		//��tcp���ݴ���ʱ��		
		sleep(3000);
		//��������ԭ����wifi
		wifiAdmin.connectWifi_ById(id_Last);
		Log.i(TAG,"���óɹ���");
		return ;
	}
	//
	private byte[] generate_SmartConfigOut_EnCryptData(String str_Ssid, String str_Key){
		//��ȡ��������
		String str_SmartConfigOut_Command = get_SmartConfigOut_command(str_Ssid, str_Key);
		//�����������������aes����						
		byte[] bytes_Result = AES_Cipher.encrypt(str_SmartConfigOut_Command, str_seed);	
//		String str_Result = AES_Cipher.toHex(bytes_Result);	
//		Log.i(TAG,"���ܽ����" + str_Result);
//		String strResult2 = AES_Cipher.decrypt(bytes_Result, str_seed);		
//		Log.i(TAG,"���ܽ����" + strResult2);
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
	//������������
	private String get_SmartConfigOut_command(String str_Ssid, String str_Key){
		String str_Command = cmd.get_SmartConfigOut_command(str_Ssid, str_Key);
		return str_Command;
	}
	//�������ܼҾ�AP�ȵ�
	private String connect_SmartDevice(){
		//��
		wifiAdmin.openWifi();	
		//��鵱ǰ�Ƿ�����wifi
//		if(wifiAdmin.isWifiConnected()==false)
//			return "��ǰδ����wifi";
		//���µ�ǰ�ȵ��id
		id_Last = wifiAdmin.getNetworkId();
		//�Ͽ���ǰ����
		if(wifiAdmin.disconnectWifi_ById(id_Last) == false)
			return "�Ͽ�����ʧ��";
		//����0K_SP3
		if(wifiAdmin.connectWifi_BySsid(str_Ssid_K2AP) == false)	
			return "���� 0K_SP3 ʧ��";	
//		if(wifiAdmin.isWifiConnected())
//			Log.e(TAG,"���Ӽ��ɹ�");
		
		return "���� 0K_SP3 �ɹ�";
	}
	
	
	
	@Override
	public void confirm_ConfigSuccess() {
		String strResult = AES_Cipher.decrypt(bytes_Rec, str_seed);		
		Log.d(TAG,"�յ����ݵĽ��ܽ����" + strResult);	 
		controller.dismiss_WaitDialog_SmartConfig();
	}


	
	
	
}
