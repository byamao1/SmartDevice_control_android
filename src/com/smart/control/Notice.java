package com.smart.control;

import android.content.Context;
import android.widget.Toast;

public class Notice {
	static private Context ct = null;
	//通知、提示
	static private Toast mToast;
	static public void setContext(Context context){
		ct = context;
	}
	static public void showToast(String msg){ 
		if(ct == null)
    		return;
        if(mToast == null){            	
            mToast = Toast.makeText(ct, msg, Toast.LENGTH_SHORT);    
        }else{    
            mToast.setText(msg);    
            mToast.setDuration(Toast.LENGTH_SHORT);  
        }    
        mToast.show();    
    } 

}
