package com.smart.control;


import com.smart.control.common.net.WifiAdmin;
import com.smart.control.common.net.XMPPUtil;
import com.smart.control.fragment.*;
import com.smart.control.service.EventHandlerService;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity  {
	private static final String TAG = "MainActivity";
	//��ͼ���
	private FragmentTabHost mTabHost = null;;  
    private View indicator = null;
	//�ؼ���
	private EditText editText_SSID,editText_Key;
	private Button btn_SmartConfig,btn_Lignt_On,btn_Login;
	private ProgressDialog dialog_Wait_SmartConfig;
	//MVC
	private Controller controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȡ������   
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
       
		setContentView(R.layout.activity_main);
		//��ʼ����ͼ���
		initViewFrame();
		//��ʼ������
		init();
		
	}
	//��ʼ����ͼ���
	private void initViewFrame(){
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);  
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);  
        //------------��һ��tabҳ��
        // ���tab��indicator 
        indicator = getIndicatorView("�豸", R.layout.indicator_homedevice);  
        //��tab��fragment��indicator����
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator),
        		HomeDeviceFragment.class, null); 
        //------------�ڶ���tabҳ��
        indicator = getIndicatorView("����", R.layout.indicator_homescene);  
        mTabHost.addTab(mTabHost.newTabSpec("scene").setIndicator(indicator),  
        		HomeSceneFragment.class, null);  
    	//------------������tabҳ��
        indicator = getIndicatorView("����", R.layout.indicator_homediscover);  
        mTabHost.addTab(mTabHost.newTabSpec("discover").setIndicator(indicator),  
        		HomeDiscoverFragment.class, null);
        //------------���ĸ�tabҳ��
        indicator = getIndicatorView("��", R.layout.indicator_homeprofile);  
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator(indicator),  
        		HomeProfileFragment.class, null); 
	}
	//��layoutId��Ӧ��xml�����ļ�����ʾ���ֽ����趨
	private View getIndicatorView(String name, int layoutId) {  
        View v = getLayoutInflater().inflate(layoutId, null);  
        TextView tv = (TextView) v.findViewById(R.id.tabText);  
        tv.setText(name);  
        return v;  
    } 
	private void init(){
		//������ʾ
		Notice.setContext(this);
		dialog_Wait_SmartConfig = new ProgressDialog(this);
//		dialog_Wait_SmartConfig.setContentView(R.layout.activity_wait_smartconfig);
		
//		//����MVC
//		controller =  new Controller(this);
//		//���ÿؼ�
//		editText_SSID = (EditText)findViewById(R.id.editText_SSID);
//		editText_Key = (EditText)findViewById(R.id.editText_Key);
//		btn_SmartConfig = (Button)findViewById(R.id.button_SmartConfig);		
//		btn_Lignt_On = (Button)findViewById(R.id.button_Light_On);
//		btn_Login = (Button)findViewById(R.id.button_Login);
//		btn_SmartConfig.setOnClickListener(new View.OnClickListener() {					
//			@Override
//			public void onClick(View v) {					
//				String str_Ssid = editText_SSID.getText().toString();
//				String str_Key = editText_Key.getText().toString();;
//				if(str_Ssid.length()==0||str_Key.length()==0){
//					Notice.showToast("������SSID�����룡");
//					return;
//				}
//				//��鵱ǰ�Ƿ�����wifi
//				final WifiAdmin wifiAdmin = new WifiAdmin(v.getContext());
//				if(wifiAdmin.isWifiConnected()==false){
//					Notice.showToast("δ����wifi��");
//					return ;
//				}
//				dialog_Wait_SmartConfig.setTitle("��ʾ");
//				dialog_Wait_SmartConfig.setMessage("�������á�����");
//				dialog_Wait_SmartConfig.show();
//				//��ʼsmartConfig
//				controller.start_SmartConfig(v.getContext(),str_Ssid, str_Key);						
//			}		
//		});
//		btn_Lignt_On.setOnClickListener(new View.OnClickListener() {					
//			@Override
//			public void onClick(View v) {					
//				//����	
//				controller.control_Device("wan_light_on","wan");
//			}		
//		});
//		btn_Login.setOnClickListener(new View.OnClickListener() {					
//			@Override
//			public void onClick(View v) {	
//				XMPPUtil xmpp = new XMPPUtil(v.getContext());
//				new Thread(xmpp).start();
//			}		
//		});
	}
	//���յ��豸ͨ��tcp��������Ϣʱ��ȡ���ȴ��Ի���
	public void dismiss_WaitDialog_SmartConfig(){
		dialog_Wait_SmartConfig.dismiss();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTabHost = null;
	}
	

}
