/**

 * Title: KSwitch1ControlActivity.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月20日
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
 * 控客公司-开关-2代的控制页面
 * @职责 
 * @属层 presentation层
 * @author ByTom
 */
public class KonPlug2ControlActivity  extends CoutrolBaseActivity implements OnClickListener, Handler.Callback{
	private static final String TAG = "KonPlug1ControlActivity";
	//题头下的信息显示
	private TextView tv_TitleInfo;
	
	//---------------状态控件---------------
	//插座开关
	private ImageButton imageBtn_PlugSwitch;
	//电量统计
	private TextView tv_ElecStatis;
	//wifi增强
	private TextView tv_WifiEnhance;
	//充电保护
	private TextView tv_ChargeProtect;
	//定时任务
	private TextView tv_TimerTask;
	//延时任务
	private TextView tv_DelayedTask;
	//小夜灯
	private TextView tv_NightLight;
	//--------------------------------------

	//--------------------------------------
	//设备模型
	private KonPlug2DeviceModel deviceModel;
	//页面呈现控制
	private Handler handler;
	//设备控制
	private DeviceCtlCommand deviceCtlCommand;
	//网络设置
	private KonCtlDeviceNetConfigInfo ctlDeviceNetConfigInfo;
	private DeviceCtlMode deviceCtlMode;
	//更新状态线程
	UpdateStatusThread updateStatusThread;
	//超时
	private long timeout = 8000;
	WaitDialog waitDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_kplug2control);		
		Intent intent = this.getIntent();     //获取已有的intent对象   
		Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象   
		int position = bundle.getInt("position"); 
		//从DeviceManagement获得DeviceModel
		deviceModel = (KonPlug2DeviceModel)DeviceManagement.getDeviceModel(position);
		//设置题头
		initCommonHeader(deviceModel.getName());
		getCtl();
		//初始化Handler
		handler = new Handler((Callback)this);
		deviceCtlCommand = null;
		//初始化网络设置
		ctlDeviceNetConfigInfo = new KonCtlDeviceNetConfigInfo();
		deviceCtlMode = DeviceCtlMode.XMPP;		
		//从网络更新状态数据
		updateStatusThread = new UpdateStatusThread("");
		updateStatusThread.start();
		//显示WaitDialog
    	waitDialog = new WaitDialog(this, "等待", "获取状态...", "超时", timeout, true);
    	waitDialog.show();
	}
	
	/**
	 * 左按钮设为back，右按钮设为不可见
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
	
	//获取控件，设定点击响应
	private void getCtl(){
		//题头下的信息显示
		tv_TitleInfo = (TextView) findViewById(R.id.textView_titleInfo);
		//插座开关
		imageBtn_PlugSwitch = (ImageButton) findViewById(R.id.imageButton_plugSwitch);
		imageBtn_PlugSwitch.setOnClickListener(this);
		//电量统计
		tv_ElecStatis = (TextView) findViewById(R.id.textView_elecStatis);
		tv_ElecStatis.setOnClickListener(this);
		//wifi增强
		tv_WifiEnhance = (TextView) findViewById(R.id.textView_wifiEnhance);
		tv_WifiEnhance.setOnClickListener(this);
		//充电保护
		tv_ChargeProtect = (TextView) findViewById(R.id.textView_chargeProtect);
		tv_ChargeProtect.setOnClickListener(this);
		//定时任务
		tv_TimerTask = (TextView) findViewById(R.id.textView_timerTask);
		tv_TimerTask.setOnClickListener(this);
		//延时任务
		tv_DelayedTask = (TextView) findViewById(R.id.textView_delayedTask);
		tv_DelayedTask.setOnClickListener(this);
		//小夜灯
		tv_NightLight = (TextView) findViewById(R.id.textView_nightLight);
		tv_NightLight.setOnClickListener(this);
		
	}
	
	/**
	 * 状态显示。目前只更新插座开关和夜灯状态
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
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//不调用它不显示图片
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
	 * 根据cmd获得指定的命令
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
    	waitDialog = new WaitDialog(this, "等待", "请稍候...", "超时", timeout, true);
		switch (v.getId()) {
		case R.id.imageButton_plugSwitch:
			switch(deviceModel.isPlugOn){
				case 0:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_DEVICE);							
					break;//更新状态
				case 1:
					deviceCtlCommand = getCtlCommand(Cmd.DEVICE_ON);						
					break;
				case 2:
					deviceCtlCommand = getCtlCommand(Cmd.DEVICE_OFF);
					break;	
				default:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_DEVICE);
					break;//更新状态
			}
			new Thread(deviceCtlCommand).start();			
//			dialog.show();
			break;
		case R.id.textView_nightLight:
			switch(deviceModel.isNightLightOn){
				case 0:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_LIGHT);
					break;//更新状态
				case 1:
					deviceCtlCommand = getCtlCommand(Cmd.LIGHT_ON);
					break;
				case 2:
					deviceCtlCommand = getCtlCommand(Cmd.LIGHT_OFF);
					break;
				default:
					deviceCtlCommand = getCtlCommand(Cmd.CHECK_LIGHT);
					break;//更新状态
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
         // 提取cmd
         Bundle b = msg.getData(); 
         String str_cmd = b.getString("cmd"); 
         //-----------更新状态数据------------//
		 //当收到设备打开的消息 
         if(str_cmd.contains("open%rack"))
        	 deviceModel.isPlugOn = 2;
         //当收到设备关闭的消息
         if(str_cmd.contains("close%rack"))
        	 deviceModel.isPlugOn = 1;
		 //当收到夜灯打开
         if(str_cmd.contains("open%lack"))
        	 deviceModel.isNightLightOn = 2;
         //当收到夜灯关闭
         if(str_cmd.contains("close%lack"))
        	 deviceModel.isNightLightOn = 1;
         //-----------更新状态显示------------//
         displayStatus();
         return false;
	}
	
	// 当用户post事件时,只有指定了"tag"的事件才会触发该函数,执行在UI线程
    @Subcriber(tag = "xmpp")
    private void handleXmppMsg(ReceiveXMPPMsgEvent event) {    	
    	if(waitDialog != null){
    		waitDialog.dismiss();
    	}
    	LogUtil.i(TAG, "处理xmpp数据事件");    	
    	//XMPP的解码器
    	KonEnDecoder enDecoder = new KonEnDecoder(DeviceCtlMode.XMPP);
    	//分析器。设定为对接收的消息分析模式
    	KonParseMsg parseMsg = new KonParseMsg();
    	
    	//----------进行分析处理
    	//去除前缀
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
    	LogUtil.i(TAG, "处理tcp数据事件");    	
    	//WLAN_TCP的解码器
    	KonEnDecoder enDecoder = new KonEnDecoder(DeviceCtlMode.WLAN_TCP);
    	//分析器。设定为对接收的消息分析模式
    	KonParseMsg parseMsg = new KonParseMsg();    	
    	//----------进行分析处理
    	//wlan_tcp没有前缀
    	ReceiveNetMsgHandler netMsgHandler = new ReceiveNetMsgHandler(deviceModel, enDecoder, parseMsg, handler);
    	netMsgHandler.handleMsg(event.getMsg());
    }

	@Override
	protected void onResume() {
		super.onResume();
		//显示状态
		displayStatus();
	}
	
	
	/**
	 * 更新设备各个状态。
	 * @职责 逐个更新设备的各个状态。对于更新成功的状态其数据放入DeviceModel中；对更新超时的要发错误事件报错。
	 * @属层 
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
			// 注册事件总线对象
	        EventBus.getDefault().register(this);
		}
		
		
		private List<DeviceCtlCommand> list_checkCommand;
		private int checkIndex;//记录检查到第几个状态
//		private int statusNum;
		private Handler handler;
		//是否是第一个被更新状态
		private boolean isFirst;
		//配合interrupt()让其停止
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
			if(toStop == false){		//为终止进程用的
				if(isFirst == true){
					//开始更新第一个状态
			    	updateNextStatus();	
			    	isFirst = false;			    	
				}else{
					//超时了，发送消息：“更新状态超时”
					LogUtil.e(TAG, "线程更新状态超时");
//					UserHintUtil.showToast("错误：更新状态超时");
					//更新下一个状态
					updateNextStatus();	
				}
			}
		}
		
		// 当上一个更新状态任务成功后执行
	    @Subcriber(tag = "tcp")
	    private void handleXmppMsg(ReceiveTCPMsgEvent event) {
	    	//取消延时任务
	    	handler.removeCallbacks(this);
	    	//更新下一个状态
	    	updateNextStatus();
	    }
		
		private void updateNextStatus(){
			if(handler == null){
				//错误："UpdateStatusRunnable"“handler为null”
				LogUtil.e("UpdateStatusRunnable", "handler为null");
			}
				
			try{
				//从服务器获取状态	
				DeviceCtlCommand command = list_checkCommand.get(checkIndex);
				new Thread(command).start();
				checkIndex += 1;
				//向handler放入延时任务
				handler.postDelayed(this, timeout);
				LogUtil.i(TAG, "更新第" + String.valueOf(checkIndex) + "个状态");
			}catch(Exception e){	
				//结束更新状态，不能让handleXmppMsg函数继续工作
				EventBus.getDefault().unregister(updateStatusThread);
				LogUtil.i(TAG, "更新状态：更新结束");
			}
		}

	}

	@Override
	protected void onStop() {
		//中止状态线程
		if(updateStatusThread != null){			
			updateStatusThread.toStop = true;
			// 注销updateStatusRunnable的事件总线对象
	        EventBus.getDefault().unregister(updateStatusThread);
			//如果线程不用标志（toStop=true）停下来，单独调用interrupt()没用
			updateStatusThread.interrupt();
		}
		super.onStop();
	}
	
	
}
