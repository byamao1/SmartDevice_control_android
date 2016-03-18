/**

 * Title: UserHintUtil.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��25��
 */
package com.smart.control.common;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * �����ʹ���û���ʾ��
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class UserHintUtil{
	static private String TAG = "UserHintUtil";
	static private Toast mToast;
	static private Context mCt;
	
	/**
	 * ��ʾ������ǰ��ҪsetCt()ȷ��ContextΪ��null
	 * @param msg
	 */
	static public void showToast(String msg){    
		if(mCt == null){
			LogUtil.e(TAG, "ContextΪnull,�޷�toast��");
			return;
		}
		
		if(mToast == null){    
           mToast = Toast.makeText(mCt, msg, Toast.LENGTH_SHORT);    
		}else{    
           mToast.setText(msg);    
           mToast.setDuration(Toast.LENGTH_SHORT);  
		}    
		mToast.show(); 
	 }

	static public void setCt(Context ct) {
		mCt = ct;
	}   
	
}
