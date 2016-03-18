/**

 * Title: WaitDialog.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��3��2��
 */
package com.smart.control.component;

import com.smart.control.common.LogUtil;
import com.smart.control.common.UserHintUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;


/**
 * �ȴ��Ի���
 * ��ʧ������Ϊ����ʱ�Զ���ʧ��dismiss���������outside��
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class WaitDialog{
	private static final String TAG = "WaitDialog";
	private static final long MAXTIMEOUT = 10000;
	private ProgressDialog pDialog;
	private Context ct;
	private String title = "�ȴ�";
	private String msg = "����Ϣ";
	private String timeout_hint = "";
	private long timeout = MAXTIMEOUT;
	private boolean outsideCancelAble = true;
	
	
	public WaitDialog(Context ct) {
		if(ct != null){
			this.ct = ct;
			pDialog = new ProgressDialog(ct);
		}else{
			LogUtil.e(TAG, "ContextΪnull");
		}
		//����Ĭ������
		setDialog();
	}
	
	/**
	 * @param ct
	 * @param title ����
	 * @param msg ��ʾ����
	 * @param timeout_hint Ϊnull�򡰡����򲻻��г�ʱ��ʾ
	 * @param timeout Ϊ0����ᱻ����ΪĬ�ϵ�MAXTIMEOUT��
	 * @param outsideCancelAble �ڶԻ��������Ƿ�����öԻ�����ʧ
	 */
	public WaitDialog(Context ct, String title, String msg, String timeout_hint, long timeout, boolean outsideCancelAble) {
		if(ct != null){
			this.ct = ct;
			pDialog = new ProgressDialog(ct);
		}else{
			LogUtil.e(TAG, "ContextΪnull");
		}
		//��������
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
			LogUtil.e(TAG, "ProgressDialogΪnull");
			return;
		}
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(timeout);//������ʾtimeout���ȡ��ProgressDialog
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				if(pDialog.isShowing() == true){
					if(pDialog != null){						
						pDialog.dismiss();		
					}
					
					if(!"".equals(timeout_hint)){
						//Ϊ���̴߳���Looper����ʾToast
						Looper.prepare(); 
						UserHintUtil.showToast(timeout_hint);
						//������Ĳ�ִ�У���������ڲ�Ӧ����һ��ѭ��
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
