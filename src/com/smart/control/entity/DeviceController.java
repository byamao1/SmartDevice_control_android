/**

 * Title: DeviceRemoter.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��27��
 */
package com.smart.control.entity;

import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.command.DeviceCtlCommand;
import com.smart.control.entity.command.KonPlug2CtlCmdConfigFactory;
import com.smart.control.entity.device.DeviceCtlMode;
import com.smart.control.entity.device.SuperDeviceModel;
import com.smart.control.eventbus.ReceiveXMPPMsgEvent;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

/**
 * �������ҳ����DeviceCtlCommand��֮��Ĺ�ͨ
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class DeviceController implements Runnable{
	private Context ct;
	private SuperDeviceModel deviceModel;
	private Handler handler;
	private DeviceCtlCommand deviceCtlCommand;
	private DeviceCtlMode deviceCtlMode;
	private Cmd cmd;
	private Bundle params;
	private KonPlug2CtlCmdConfigFactory konPlug2CtlCmdConfigFactory;
	
	public DeviceController(Context ct, SuperDeviceModel deviceModel, Handler handler){
		this.ct = ct;
		this.deviceModel = deviceModel;
		this.handler = handler;
	}
	
	/**
	 * ��������DeviceControllerǰ��׼��������������Ժ󣬲������б��̡߳�
	 * @param mode
	 * @param cmd
	 * @param params
	 * @return
	 */
//	public void prepare(DeviceCtlMode deviceCtlMode, Cmd cmd, Bundle params){
//		this.deviceCtlMode = deviceCtlMode;
//		this.cmd = cmd;
//		this.params = params;		
//		ctlCmdConfigFactory = new CtlCmdConfigFactory(ct, deviceModel, deviceCtlMode);
//		deviceCtlCommand = new DeviceCtlCommand(ctlCmdConfigFactory);
//	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
//		deviceCtlCommand.sendMsg(cmd, params);		
	}
	
//	public void handleMsg(String src) {
//    	//���н���
//		String str_decode = deviceCtlCommand.decodeMsg(src);
//		//��ȡmac������
//		String str_deviceMac = deviceCtlCommand.parseMsg(str_decode, 1, 2);
//		String str_op = deviceCtlCommand.parseMsg(str_decode, 3);
//		String str_op = deviceCtlCommand.parseMsg(str_decode, 4);
//		//��DeviceModel���и���(���DeviceModel��һ�������DeviceController��)
//		DeviceModel dv= DeviceManagement.getDeviceModel(str_deviceMac);
//		if(dv != null){
//			//����״̬
//			
//		}
//		//����Ϣ�����DeviceController������
//		if(deviceModel.getMac().equals(str_deviceMac)){
//			//��handler����Ϣ���õȴ�dialog��ʧ
//		}
//    }
	
}
