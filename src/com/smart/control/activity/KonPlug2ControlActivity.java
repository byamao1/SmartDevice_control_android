/**

 * Title: KSwitch1ControlActivity.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��20��
 */
package com.smart.control.activity;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.control.R;
import com.smart.control.base.CoutrolBaseActivity;
import com.smart.control.common.*;
import com.smart.control.component.WaitDialog;
import com.smart.control.config.INetConfigInfo;
import com.smart.control.config.KonCtlDeviceNetConfigInfo;
import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.command.DeviceCtlCommand;
import com.smart.control.entity.command.KonPlug2CtlCmdConfigFactory;
import com.smart.control.entity.device.*;
import com.smart.control.entity.endecoder.KonEnDecoder;
import com.smart.control.entity.parsemsg.KonParseMsg;
import com.smart.control.eventbus.*;

/**
 * �ؿ͹�˾-����-2���Ŀ���ҳ��
 * @ְ�� 
 * @���� presentation��
 * @author ByTom
 */
public class KonPlug2ControlActivity  extends CoutrolBaseActivity implements OnClickListener, Handler.Callback{
	private static final String TAG = "KonPlug1ControlActivity";
	//��ͷ�µ���Ϣ��ʾ
	private TextView tv_TitleInfo;
	
	//---------------״̬�ؼ�---------------
	//��������
	private ImageButton imageBtn_PlugSwitch;
	//����ͳ��
	private TextView tv_ElecStatis;
	//wifi��ǿ
	private TextView tv_WifiEnhance;
	//��籣��
	private TextView tv_ChargeProtect;
	//��ʱ����
	private TextView tv_TimerTask;
	//��ʱ����
	private TextView tv_DelayedTask;
	//Сҹ��
	private TextView tv_NightLight;
	//--------------------------------------

	//--------------------------------------
	//�豸ģ��
	private KonPlug2DeviceModel deviceModel;
	//ҳ����ֿ���
	private Handler handler;
	//�豸����
	private DeviceCtlCommand deviceCtlCommand;
	//��������
	private KonCtlDeviceNetConfigInfo ctlDeviceNetConfigInfo;
	private DeviceCtlMode deviceCtlMode;
	//����״̬�߳�
	UpdateStatusThread updateStatusThread;
	//��ʱ
	private long timeout = 8000;
	WaitDialog waitDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_kplug2control);		
		Intent intent = this.getIntent();     //��ȡ���е�intent����   
		Bundle bundle = intent.getExtras();    //��ȡintent�����bundle����   
		int position = bundle.getInt("position"); 
		//��DeviceManagement���DeviceModel
		deviceModel = (KonPlug2DeviceModel)DeviceManagement.getDeviceModel(position);
		//������ͷ
		initCommonHeader(deviceModel.getName());
		getCtl();
		//��ʼ��Handler
		handler = new Handler((Callback)this);
		deviceCtlCommand = null;
		//��ʼ����������
		ctlDeviceNetConfigInfo = new KonCtlDeviceNetConfigInfo();
		deviceCtlMode = DeviceCtlMode.XMPP;		
		//���������״̬����
		updateStatusThread = new UpdateStatusThread("");
		updateStatusThread.start();
		//��ʾWaitDialog
    	waitDialog = new WaitDialog(this, "�ȴ�", "��ȡ״̬...", "��ʱ", timeout, true);
    	waitDialog.show();
	}
	
	/**
	 * ��ť��Ϊback���Ұ�ť��Ϊ���ɼ�
	 */
	@Override
	public void initCommonHeader(String str_Title){
		super.initCommonHeader(str_Title);
		btn_CommonheaderLeft.setBackgroundResource(R.drawable.mm_title_back); 
		btn_CommonheaderLeft.setOnClickListener(new View.OnClickListener() {					
			@Override
			public void onClick(View v) {	
				finish();
			}
		});
		btn_CommonheaderRight.setVisibility(View.INVISIBLE);
	}
	
	//��ȡ�ؼ����趨�����Ӧ
	private void getCtl(){
		//��ͷ�µ���Ϣ��ʾ
		tv_TitleInfo = (TextView) findViewById(R.id.textView_titleInfo);
		//��������
		imageBtn_PlugSwitch = (ImageButton) findViewById(R.id.imageButton_plugSwitch);
		imageBtn_PlugSwitch.setOnClickListener(this);
		//����ͳ��
		tv_ElecStatis = (TextView) findViewById(R.id.textView_elecStatis);
		tv_ElecStatis.setOnClickListener(this);
		//wifi��ǿ
		tv_WifiEnhance = (TextView) findViewById(R.id.textView_wifiEnhance);
		tv_WifiEnhance.setOnClickListener(this);
		//��籣��
		tv_ChargeProtect = (TextView) findViewById(R.id.textView_chargeProtect);
		tv_ChargeProtect.setOnClickListener(this);
		//��ʱ����
		tv_TimerTask = (TextView) findViewById(R.id.textView_timerTask);
		tv_TimerTask.setOnClickListener(this);
		//��ʱ����
		tv_DelayedTask = (TextView) findViewById(R.id.textView_delayedTask);
		tv_DelayedTask.setOnClickListener(this);
		//Сҹ��
		tv_NightLight = (TextView) findViewById(R.id.textView_nightLight);
		tv_NightLight.setOnClickListener(this);
		
	}
	
	/**
	 * ״̬��ʾ��Ŀǰֻ���²������غ�ҹ��״̬
	 */
	private void displayStatus(){
		Drawable drawable = null;
		//
		switch(deviceModel.isPlugOn){
			case 0:imageBtn_PlugSwitch.setBackgroundResource(R.drawable.device_mul_loading);break;
			case 1:imageBtn_PlugSwitch.setBackgroundResource(R.drawable.device_mul_off);break;
			case 2:imageBtn_PlugSwitch.setBackgroundResource(R.drawable.device_mul_on);break;
			default:imageBtn_PlugSwitch.setBackgroundResource(R.drawable.device_mul_loading);break;
		}			
			
		//		
		drawable = getResources().getDrawable(R.drawable.device_electricty);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//������������ʾͼƬ
		tv_ElecStatis.setCompoundDrawables(null, drawable, null, null);
		//
		drawable = getResources().getDrawable(R.drawable.device_wifi);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_WifiEnhance.setCompoundDrawables(null, drawable, null, null);
		//
//		if(deviceModel.isChargeProtect == false)
			drawable = getResources().getDrawable(R.drawable.device_protect);
//			drawable = getResources().getDrawable(R.drawable.device_protect_pressed);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_ChargeProtect.setCompoundDrawables(null, drawable, null, null);
		//
//		if(deviceModel.isTimerTask == false)
			drawable = getResources().getDrawable(R.drawable.device_timer);
//			drawable = getResources().getDrawable(R.drawable.device_timer_pressed);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_TimerTask.setCompoundDrawables(null, drawable, null, null);
		//
//		if(deviceModel.isDelayedTask == false)
			drawable = getResources().getDrawable(R.drawable.device_delay);
//			drawable = getResources().getDrawable(R.drawable.device_delay_pressed);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_DelayedTask.setCompoundDrawables(null, drawable, null, null);
		//
		switch(deviceModel.isNightLightOn){
			case 0:drawable = getResources().getDrawable(R.drawable.device_slight_un);break;
			case 1:drawable = getResources().getDrawable(R.drawable.device_slight);break;
			case 2:drawable = getResources().getDrawable(R.drawable.device_slight_pressed);break;
			default:drawable = getResources().getDrawable(R.drawable.device_slight_un);break;
		}	
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_NightLight.setCompoundDrawables(null, drawable, null, null);
	}
	
	/**
	 * ����cmd���ָ��������
	 * @param cmd
	 * @return
	 */
	private DeviceCtlCommand getCtlCommand(Cmd cmd){
		DeviceCtlCommand command = new DeviceCtlCommand();
		KonPlug2CtlCmdConfigFactory ctlCmdConfigFactory = null;
		switch(cmd){
		case CHECK_DEVICE:
			ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(this, deviceModel, DeviceCtlMode.WLAN_TCP, ctlDeviceNetConfigInfo, cmd);	
			command.prepare("getDeviceStatus:", cmd, null, ctlCmdConfigFactory);
			break;
		case CHECK_LIGHT:
			ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(this, deviceModel, DeviceCtlMode.WLAN_TCP, ctlDeviceNetConfigInfo, cmd);
			command.prepare("getDeviceStatus:", cmd, null, ctlCmdConfigFactory);
			break;
		case DEVICE_ON:
			ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(this, deviceModel, deviceCtlMode, ctlDeviceNetConfigInfo, cmd);
			command.prepare("encryption:", cmd, null, ctlCmdConfigFactory);
			break;
		case DEVICE_OFF:
			ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(this, deviceModel, deviceCtlMode, ctlDeviceNetConfigInfo, cmd);
			command.prepare("encryption:", cmd, null, ctlCmdConfigFactory);
		case LIGHT_ON:
			ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(this, deviceModel, deviceCtlMode, ctlDeviceNetConfigInfo, cmd);
			command.prepare("encryption:", cmd, null, ctlCmdConfigFactory);
			break;
		case LIGHT_OFF:
			ctlCmdConfigFactory = new KonPlug2CtlCmdConfigFactory(this, deviceModel, deviceCtlMode, ctlDeviceNetConfigInfo, cmd);
			command.prepare("encryption:", cmd, null, ctlCmdConfigFactory);
			break;
		default:
			command.prepare(null, cmd, null, null);
			break;
		}
		return command;
	}
		
	
	@Override
	public void onClick(View v) {
    	waitDialog = new WaitDialog(this, "�ȴ�", "���Ժ�...", "��ʱ", timeout, true);
		switch (v.getId()) {
		case R.id.imageButton_plugSwitch:
			switch(deviceModel.isPlugOn){
				case 0:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_DEVICE);							
					break;//����״̬
				case 1:
					deviceCtlCommand = getCtlCommand(Cmd.DEVICE_ON);						
					break;
				case 2:
					deviceCtlCommand = getCtlCommand(Cmd.DEVICE_OFF);
					break;	
				default:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_DEVICE);
					break;//����״̬
			}
			new Thread(deviceCtlCommand).start();			
//			dialog.show();
			break;
		case R.id.textView_nightLight:
			switch(deviceModel.isNightLightOn){
				case 0:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_LIGHT);
					break;//����״̬
				case 1:
					deviceCtlCommand = getCtlCommand(Cmd.LIGHT_ON);
					break;
				case 2:
					deviceCtlCommand = getCtlCommand(Cmd.LIGHT_OFF);
					break;
				default:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_LIGHT);
					break;//����״̬
			}			
			new Thread(deviceCtlCommand).start();
//			dialog.show();
			break;
		}
		waitDialog.show();
	}
	
	/* (non-Javadoc)
	 * @see android.os.Handler.Callback#handleMessage(android.os.Message)
	 */
	@Override
	public boolean handleMessage(Message msg) {
         // ��ȡcmd
         Bundle b = msg.getData(); 
         String str_cmd = b.getString("cmd"); 
         //-----------����״̬����------------//
		 //���յ��豸�򿪵���Ϣ 
         if(str_cmd.contains("open%rack"))
        	 deviceModel.isPlugOn = 2;
         //���յ��豸�رյ���Ϣ
         if(str_cmd.contains("close%rack"))
        	 deviceModel.isPlugOn = 1;
		 //���յ�ҹ�ƴ�
         if(str_cmd.contains("open%lack"))
        	 deviceModel.isNightLightOn = 2;
         //���յ�ҹ�ƹر�
         if(str_cmd.contains("close%lack"))
        	 deviceModel.isNightLightOn = 1;
         //-----------����״̬��ʾ------------//
         displayStatus();
         return false;
	}
	
	// ���û�post�¼�ʱ,ֻ��ָ����"tag"���¼��Żᴥ���ú���,ִ����UI�߳�
    @Subcriber(tag = "xmpp")
    private void handleXmppMsg(ReceiveXMPPMsgEvent event) {    	
    	if(waitDialog != null){
    		waitDialog.dismiss();
    	}
    	LogUtil.i(TAG, "����xmpp�����¼�");    	
    	//XMPP�Ľ�����
    	KonEnDecoder enDecoder = new KonEnDecoder(DeviceCtlMode.XMPP);
    	//���������趨Ϊ�Խ��յ���Ϣ����ģʽ
    	KonParseMsg parseMsg = new KonParseMsg();
    	
    	//----------���з�������
    	//ȥ��ǰ׺
		String[] strings = event.getMsg().split(":", 2);
		String str_divide = strings[1];
    	ReceiveNetMsgHandler netMsgHandler = new ReceiveNetMsgHandler(deviceModel, enDecoder, parseMsg, handler);
    	netMsgHandler.handleMsg(str_divide);  
	}
    
    @Subcriber(tag = "tcp")
    private void handleWlanTcpMsg(ReceiveTCPMsgEvent event) {
    	if(waitDialog != null){
    		waitDialog.dismiss();
    	}
    	LogUtil.i(TAG, "����tcp�����¼�");    	
    	//WLAN_TCP�Ľ�����
    	KonEnDecoder enDecoder = new KonEnDecoder(DeviceCtlMode.WLAN_TCP);
    	//���������趨Ϊ�Խ��յ���Ϣ����ģʽ
    	KonParseMsg parseMsg = new KonParseMsg();    	
    	//----------���з�������
    	//wlan_tcpû��ǰ׺
    	ReceiveNetMsgHandler netMsgHandler = new ReceiveNetMsgHandler(deviceModel, enDecoder, parseMsg, handler);
    	netMsgHandler.handleMsg(event.getMsg());
    }

	@Override
	protected void onResume() {
		super.onResume();
		//��ʾ״̬
		displayStatus();
	}
	
	
	/**
	 * �����豸����״̬��
	 * @ְ�� ��������豸�ĸ���״̬�����ڸ��³ɹ���״̬�����ݷ���DeviceModel�У��Ը��³�ʱ��Ҫ�������¼�����
	 * @���� 
	 * @author ByTom
	 */
	private class UpdateStatusThread extends HandlerThread{
		
		/**
		 * @param name
		 */
		public UpdateStatusThread(String name) {
			super(name);
			handler = new Handler();
			isFirst = true;			
			toStop = false;
			initCheckCommandList();
			checkIndex = 0;
			// ע���¼����߶���
	        EventBus.getDefault().register(this);
		}
		
		
		private List<DeviceCtlCommand> list_checkCommand;
		private int checkIndex;//��¼��鵽�ڼ���״̬
//		private int statusNum;
		private Handler handler;
		//�Ƿ��ǵ�һ��������״̬
		private boolean isFirst;
		//���interrupt()����ֹͣ
		public boolean toStop;
		
		private void initCheckCommandList(){
			list_checkCommand = new ArrayList<DeviceCtlCommand>();
			DeviceCtlCommand command = null;
			command = getCtlCommand(Cmd.CHECK_DEVICE);
			list_checkCommand.add(command);
			command = getCtlCommand(Cmd.CHECK_LIGHT);
			list_checkCommand.add(command);
			
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if(toStop == false){		//Ϊ��ֹ�����õ�
				if(isFirst == true){
					//��ʼ���µ�һ��״̬
			    	updateNextStatus();	
			    	isFirst = false;			    	
				}else{
					//��ʱ�ˣ�������Ϣ��������״̬��ʱ��
					LogUtil.e(TAG, "�̸߳���״̬��ʱ");
//					UserHintUtil.showToast("���󣺸���״̬��ʱ");
					//������һ��״̬
					updateNextStatus();	
				}
			}
		}
		
		// ����һ������״̬����ɹ���ִ��
	    @Subcriber(tag = "tcp")
	    private void handleXmppMsg(ReceiveTCPMsgEvent event) {
	    	//ȡ����ʱ����
	    	handler.removeCallbacks(this);
	    	//������һ��״̬
	    	updateNextStatus();
	    }
		
		private void updateNextStatus(){
			if(handler == null){
				//����"UpdateStatusRunnable"��handlerΪnull��
				LogUtil.e("UpdateStatusRunnable", "handlerΪnull");
			}
				
			try{
				//�ӷ�������ȡ״̬	
				DeviceCtlCommand command = list_checkCommand.get(checkIndex);
				new Thread(command).start();
				checkIndex += 1;
				//��handler������ʱ����
				handler.postDelayed(this, timeout);
				LogUtil.i(TAG, "���µ�" + String.valueOf(checkIndex) + "��״̬");
			}catch(Exception e){	
				//��������״̬��������handleXmppMsg������������
				EventBus.getDefault().unregister(updateStatusThread);
				LogUtil.i(TAG, "����״̬�����½���");
			}
		}

	}

	@Override
	protected void onStop() {
		//��ֹ״̬�߳�
		if(updateStatusThread != null){			
			updateStatusThread.toStop = true;
			// ע��updateStatusRunnable���¼����߶���
	        EventBus.getDefault().unregister(updateStatusThread);
			//����̲߳��ñ�־��toStop=true��ͣ��������������interrupt()û��
			updateStatusThread.interrupt();
		}
		super.onStop();
	}
	
	
}
