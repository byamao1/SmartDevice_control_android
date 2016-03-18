/**

 * Title: DebugHintUtil.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��25��
 */
package com.smart.control.common;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class DebugHintUtil {
	static private String TAG = "DebugHintUtil";
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
		//����ǰ׺
		msg = "Debug: " + msg;
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
