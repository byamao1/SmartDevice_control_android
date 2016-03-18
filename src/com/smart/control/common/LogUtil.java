/**

 * Title: LogUtil.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��25��
 */
package com.smart.control.common;

import android.util.Log;

/**
 * 
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class LogUtil {
	public static String mTag = "[�Զ���]";
	private static String ERROR_PART = "��������>>";
	private static String INFO_PART = "��ʾ>>";
	
	static public void e(String from, String msg){
		Log.e(mTag, ERROR_PART + " From= " + from + "  Msg= " + msg);
	}
	static public void i(String from, String msg){
		Log.i(mTag, INFO_PART + " From= " + from + "  Msg= " + msg);
	}

	static public void setTag(String tag) {
		mTag = tag + "[�Զ���]";
	}
}
