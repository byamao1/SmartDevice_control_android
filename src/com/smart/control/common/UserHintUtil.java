/**

 * Title: UserHintUtil.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月25日
 */
package com.smart.control.common;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * 给软件使用用户提示。
 * @职责 
 * @属层 
 * @author ByTom
 */
public class UserHintUtil{
	static private String TAG = "UserHintUtil";
	static private Toast mToast;
	static private Context mCt;
	
	/**
	 * 显示。调用前需要setCt()确保Context为非null
	 * @param msg
	 */
	static public void showToast(String msg){    
		if(mCt == null){
			LogUtil.e(TAG, "Context为null,无法toast！");
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
