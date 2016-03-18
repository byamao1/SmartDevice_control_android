/**

 * Title: DeviceCtlCommand.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��27��
 */
package com.smart.control.entity.command;

import com.smart.control.common.AES_Cipher;
import com.smart.control.common.LogUtil;
import com.smart.control.config.INetConfigInfo;
import com.smart.control.entity.codeset.SuperCtlCodeSet;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;
import com.smart.control.entity.endecoder.SuperEnDecoder;
import com.smart.control.entity.netclient.ISuperNetClient;
import com.smart.control.entity.parsemsg.KonParseMsg;
import com.smart.control.entity.parsemsg.SuperParseMsg;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

/**
 * ͨ�õ��豸�������
 * ��û��Ҫ���ճ��󹤳�����������������չ�������࣬�����౬ը��
 * @ְ�� �����ĸ��������ݺʹ�����յ����ݡ�
 * @���� 
 * @author ByTom
 */
public class DeviceCtlCommand implements Runnable{
	private String TAG = "DeviceCtlCommand";
	//
//	private SuperDeviceModel deviceModel;
//	private DeviceCtlMode deviceCtlMode;
	private Cmd cmd;
	private Bundle params;
	//
	private SuperCtlCmdConfigFactory ctlCmdConfigFactory;
	private SuperCtlCodeSet codeSet;
	private SuperEnDecoder endecoder;
	private ISuperNetClient netClient;
	private String prefix;
	
	public DeviceCtlCommand(){
//		if(deviceModel == null){
//			//������¼����߷���Ϣ��DeviceCtlCommand���յ�null����
//		}else{
//			this.deviceModel = deviceModel;
//		}
	}
	
	
	/**
	 * ���ÿ��Ʒ�ʽ�������������������������������Ժ󣬲������б��̡߳�
	 * @param ct
	 * @param deviceCtlMode
	 * @param netConfigInfo
	 * @param prefix	����ǰ׺
	 * @param cmd
	 * @param params
	 */
	public void prepare(String prefix,  Cmd cmd, Bundle params, SuperCtlCmdConfigFactory ctlCmdConfigFactory){

		this.ctlCmdConfigFactory = ctlCmdConfigFactory;
//		this.ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(ct, deviceModel, deviceCtlMode, netConfigInfo, cmd);	
		this.prefix = prefix;
		this.cmd = cmd;
		this.params = params;	
	}
	
	public boolean sendMsg(){
		//���
		if(ctlCmdConfigFactory == null){
			//���¼����߷��ʹ����¼�����DeviceCtlCommand����sendMsg������null��������
			return false;
		}	
		this.codeSet = ctlCmdConfigFactory.createCodeSet();
		this.endecoder = ctlCmdConfigFactory.createEnDecoder();
		this.netClient = ctlCmdConfigFactory.createNetClient();		
		
		//���code������ʹ�ÿ������
		String str_code = codeSet.getCtlCode(cmd, params);
		LogUtil.i(TAG, "�������"+str_code);
//		if("".equals(str_code)){
			//������¼����߷�����Ϣ����DeviceCtlCommand�����õ����������Ϊ�ա�
//			return false;
//		}
		//code���ܱ��롣����ʹ�ÿձ�������
		String str_Result = endecoder.getEncode(prefix, str_code);
		Log.i(TAG, "�������"+str_Result);
		//���ʹ������code���豸
		netClient.sendMsg(str_Result);	
		return true;
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		sendMsg();		
	}	
		
}
