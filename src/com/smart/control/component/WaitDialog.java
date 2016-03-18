/**

 * Title: WaitDialog.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年3月2日
 */
package com.smart.control.component;

import com.smart.control.common.LogUtil;
import com.smart.control.common.UserHintUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;


/**
 * 等待对话框。
 * 消失方法分为：超时自动消失、dismiss函数、点击outside。
 * @职责 
 * @属层 
 * @author ByTom
 */
public class WaitDialog{
	private static final String TAG = "WaitDialog";
	private static final long MAXTIMEOUT = 10000;
	private ProgressDialog pDialog;
	private Context ct;
	private String title = "等待";
	private String msg = "无消息";
	private String timeout_hint = "";
	private long timeout = MAXTIMEOUT;
	private boolean outsideCancelAble = true;
	
	
	public WaitDialog(Context ct) {
		if(ct != null){
			this.ct = ct;
			pDialog = new ProgressDialog(ct);
		}else{
			LogUtil.e(TAG, "Context为null");
		}
		//参数默认设置
		setDialog();
	}
	
	/**
	 * @param ct
	 * @param title 标题
	 * @param msg 提示内容
	 * @param timeout_hint 为null或“”，则不会有超时提示
	 * @param timeout 为0，则会被设置为默认的MAXTIMEOUT秒
	 * @param outsideCancelAble 在对话框外点击是否可以让对话框消失
	 */
	public WaitDialog(Context ct, String title, String msg, String timeout_hint, long timeout, boolean outsideCancelAble) {
		if(ct != null){
			this.ct = ct;
			pDialog = new ProgressDialog(ct);
		}else{
			LogUtil.e(TAG, "Context为null");
		}
		//参数设置
		setParam(title, msg,  timeout_hint, timeout, outsideCancelAble);
	}
	
	public void setParam(String title, String msg, String timeout_hint, long timeout, boolean outsideCancelAble){
		if(title != null){
			this.title = title;
		}
		if(msg != null){
			this.msg = msg;
		}
		if(timeout_hint != null){
			this.timeout_hint = timeout_hint;
		}
		if(timeout != 0){
			this.timeout = timeout;
		}
		this.outsideCancelAble = outsideCancelAble;			
		setDialog();	
	}

	public void show(){
		if(pDialog != null){
			pDialog.show();
		}else{
			LogUtil.e(TAG, "ProgressDialog为null");
			return;
		}
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(timeout);//让他显示timeout秒后，取消ProgressDialog
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				if(pDialog.isShowing() == true){
					if(pDialog != null){						
						pDialog.dismiss();		
					}
					
					if(!"".equals(timeout_hint)){
						//为此线程创建Looper以显示Toast
						Looper.prepare(); 
						UserHintUtil.showToast(timeout_hint);
						//此语句后的不执行，这个函数内部应该是一个循环
						Looper.loop();
					}
				}
				
			}
			
		});
		t.start();
	}
	
	public void dismiss(){
		if((pDialog != null)&&(pDialog.isShowing() == true))
			pDialog.dismiss();
	}
	
	private void setDialog(){		
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setTitle(title);
		pDialog.setMessage(msg);
		pDialog.setCanceledOnTouchOutside(outsideCancelAble);
		pDialog.setCancelable(true);
	}
	
}
