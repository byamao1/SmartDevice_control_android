/**

 * Title: CtlCmdConfigFactory.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��27��
 */
package com.smart.control.entity.command;

import android.content.Context;

import com.smart.control.common.net.NetUtil;
import com.smart.control.config.INetConfigInfo;
import com.smart.control.entity.codeset.*;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;
import com.smart.control.entity.endecoder.*;
import com.smart.control.entity.netclient.*;

/**
 * ���ͣ��ؿ͹�˾�Ķ���������
 * ����DeviceCtlCommand������Ҫ���������������������������Ķ���
 * û��Ҫ���ճ��󹤳�����������������չ�������࣬�����౬ը��
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class KonPlug2CtlCmdConfigFactory extends SuperCtlCmdConfigFactory{
	
	private Context ct;
	private SuperDeviceModel deviceModel;
	private DeviceCtlMode deviceCtlMode;
	private INetConfigInfo netConfigInfo;
	private Cmd cmd;
	
	
	public KonPlug2CtlCmdConfigFactory(Context ct, SuperDeviceModel deviceModel, DeviceCtlMode deviceCtlMode, INetConfigInfo netConfigInfo, Cmd cmd){
		this.ct = ct;
		this.deviceModel = deviceModel;
		this.deviceCtlMode = deviceCtlMode;
		this.netConfigInfo = netConfigInfo;
		this.cmd = cmd;
	}	
	 
	@Override
	public SuperCtlCodeSet createCodeSet(){
		SuperCtlCodeSet codeSet;
		//������
		if(deviceModel == null){
			//����ʾ�¼����߷�����Ϣ��createCodeSet������deviceModelΪnull
			return new NoCtlCodeSet();
		}
			
		switch(deviceModel.getType()){
			case KON_PLUG_2: codeSet = new KonPlug2CtlCodeSet(ct, deviceModel, deviceCtlMode);break;			
			default: 	codeSet = new NoCtlCodeSet();break;	//Ĭ��Ϊ�ռ�
		}
		return codeSet;
	}
	
	@Override
	public SuperEnDecoder createEnDecoder(){
		SuperEnDecoder encoder;
		//������
		if((deviceModel == null)||(deviceCtlMode == null)){
			//������¼����߷�����Ϣ��createEnDecoder�����б���Ϊnull
			return new NoEnDecoder();
		}
		switch(deviceModel.getType()){
			case KON_PLUG_2: encoder = new KonEnDecoder(deviceCtlMode);break;
			default: 	encoder = new NoEnDecoder();break;	//Ĭ��Ϊ�ռ�
		}
		return encoder;
	}
	
	@Override
	public ISuperNetClient createNetClient(){
		ISuperNetClient netClient;
		//������
		if((deviceModel == null)||(deviceCtlMode == null)){
			//������¼����߷�����Ϣ��createEnDecoder�����б���Ϊnull
			return new NoNetClient();
		}
		
		switch(deviceCtlMode){
			case XMPP: 
				netClient = new XMPPNetClient(ct);
				netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
						NetUtil.getMacAddress(ct), NetUtil.getMacAddress(ct), 
						deviceModel.getMac() + "@" + netConfigInfo.getAddress(deviceCtlMode));
				
				
				break;			
			case AP_TCP:
				netClient = new TCPNetClient();
				//��ûд���á�����
				break;	
			case WLAN_TCP:
				netClient = new TCPNetClient();
				//����TCPЭ�黹Ҫ����cmd�ֲ�ͬ�ĵ�ַ
				switch(cmd){
					case CHECK_TIMER:
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											NetUtil.getMacAddress(ct)+"@timetask.kankun-smartplug.com");
						break;
					case CHECK_BRMODE:
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											"xx@getDeviceStatus.kankun-smartplug.com");
						break;
					case CHECK_LIGHT: 
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											"xx@getDeviceStatus.kankun-smartplug.com");
						break;
					case CHECK_DEVICE: 
						netClient.netConfig(netConfigInfo.getAddress(deviceCtlMode), netConfigInfo.getPort(deviceCtlMode), 
											null, null, 
											"xx@getDeviceStatus.kankun-smartplug.com");
						break;
					default: 	
						netClient.netConfig(null, 0, null, null, null);
						break;
				}
				break;
			default: 	
				netClient = new NoNetClient();break;	//Ĭ��Ϊ�ռ�
		}
		
		return netClient;
	}	
}
