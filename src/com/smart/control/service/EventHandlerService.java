/**

 * Title: EventHandlerService.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��24��
 */
package com.smart.control.service;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import com.smart.control.eventbus.ErrorMsgEvent;
import com.smart.control.eventbus.ReceiveXMPPMsgEvent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * �����ã�����EventBus�ϵ��¼�
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class EventHandlerService extends Service{
	private String TAG = "EventHandlerService";
	private Toast mToast;
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//      Log.i(TAG,"onStartCommand");
		// ע���¼����߶���
        EventBus.getDefault().register(this);

		return super.onStartCommand(intent, flags, startId);
	}
	
	 /**
	 * �����¼�ͨ��Toast��ʾ������
	 * @param event
	 */
	@Subcriber(tag = "error")
    private void handleErrorMsg(ErrorMsgEvent event) {
		 Log.i(TAG, "�����¼����ԣ�"+ event.getFrom() +" ����:"+event.getMsg());
		 showToast("����:"+event.getMsg());
	 }
	 
	 @Override
	 public void onDestroy() {
		 // ��Ҫ����ע����������
		 EventBus.getDefault().unregister(this);
		 super.onDestroy();
	 }

	 private void showToast(String msg){    
		 if(mToast == null){    
            mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);    
		 }else{    
            mToast.setText(msg);    
            mToast.setDuration(Toast.LENGTH_SHORT);  
		 }    
		 mToast.show();    
	 }   
	

}
