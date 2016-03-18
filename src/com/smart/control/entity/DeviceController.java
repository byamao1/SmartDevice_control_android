/**

 * Title: DeviceRemoter.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
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
 * 负责控制页面与DeviceCtlCommand类之间的沟通
 * @职责 
 * @属层 
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
	 * 做好运行DeviceController前的准备。必须调用它以后，才能运行本线程。
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
//    	//进行解码
//		String str_decode = deviceCtlCommand.decodeMsg(src);
//		//提取mac和命令
//		String str_deviceMac = deviceCtlCommand.parseMsg(str_decode, 1, 2);
//		String str_op = deviceCtlCommand.parseMsg(str_decode, 3);
//		String str_op = deviceCtlCommand.parseMsg(str_decode, 4);
//		//对DeviceModel进行更新(这个DeviceModel不一定是这个DeviceController的)
//		DeviceModel dv= DeviceManagement.getDeviceModel(str_deviceMac);
//		if(dv != null){
//			//更新状态
//			
//		}
//		//该消息是这个DeviceController发出的
//		if(deviceModel.getMac().equals(str_deviceMac)){
//			//向handler发消息：让等待dialog消失
//		}
//    }
	
}
