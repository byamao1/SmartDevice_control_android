/**

 * Title: KonParseMsg.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月19日
 */
package com.smart.control.entity.parsemsg;

import android.util.Log;

import com.smart.control.entity.command.Cmd;

/**
 * 消息分析器。
 * @职责 对收到的消息进行信息提取。
 * @属层 
 * @author ByTom
 */
public class KonParseMsg extends SuperParseMsg{	
	protected String TAG = "KonParseMsg";
//	private boolean isReceive;
	
	public KonParseMsg(){
		
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType)
	 */
	@Override
	public String getMac(String src){		
		if(src == null||"".equals(src)){
			Log.e(TAG, "消息分析器的getMac函数收到非法参数！");
			return "";
		}
		String[] strings = src.split("%");
		//从第1到2个%之间
		return strings[1];
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType)
	 */
	@Override
	public String getCmd(String src){
		if(src == null||"".equals(src)){
			Log.e(TAG, "消息分析器的getCmd函数收到非法参数！");
			return "";
		}
		
//		if(isReceive == true){
//			String[] strings = src.split("%",3);
//			//从第2个%到最后
//			return strings[2];
//		}else{
			String[] strings = src.split("%",4);
			//从第3到最后
			return strings[3];
//		}
		
	}
}
