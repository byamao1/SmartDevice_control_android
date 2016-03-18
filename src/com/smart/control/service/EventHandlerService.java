/**

 * Title: EventHandlerService.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月24日
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
 * （不用）处理EventBus上的事件
 * @职责 
 * @属层 
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
		// 注册事件总线对象
        EventBus.getDefault().register(this);

		return super.onStartCommand(intent, flags, startId);
	}
	
	 /**
	 * 错误事件通过Toast显示出来。
	 * @param event
	 */
	@Subcriber(tag = "error")
    private void handleErrorMsg(ErrorMsgEvent event) {
		 Log.i(TAG, "错误事件来自："+ event.getFrom() +" 内容:"+event.getMsg());
		 showToast("错误:"+event.getMsg());
	 }
	 
	 @Override
	 public void onDestroy() {
		 // 不要忘记注销！！！！
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
