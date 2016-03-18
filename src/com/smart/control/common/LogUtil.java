/**

 * Title: LogUtil.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月25日
 */
package com.smart.control.common;

import android.util.Log;

/**
 * 
 * @职责 
 * @属层 
 * @author ByTom
 */
public class LogUtil {
	public static String mTag = "[自定义]";
	private static String ERROR_PART = "发生错误>>";
	private static String INFO_PART = "提示>>";
	
	static public void e(String from, String msg){
		Log.e(mTag, ERROR_PART + " From= " + from + "  Msg= " + msg);
	}
	static public void i(String from, String msg){
		Log.i(mTag, INFO_PART + " From= " + from + "  Msg= " + msg);
	}

	static public void setTag(String tag) {
		mTag = tag + "[自定义]";
	}
}
