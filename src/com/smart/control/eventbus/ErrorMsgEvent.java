/**

 * Title: ErrorEvent.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月22日
 */
package com.smart.control.eventbus;

/**
 * （不用）
 * @职责 
 * @属层 
 * @author ByTom
 */
public class ErrorMsgEvent {
	private String mFrom;  
    private String mMsg;  
    
    /**
     * @param from 从哪里来的。可以是类名。作为TAG。
     * @param msg 错误内容。
     */
    public ErrorMsgEvent(String from, String msg) {   
    	mFrom = from;
        mMsg = msg;  
    }  
    
    public String getFrom(){  
        return mFrom;  
    } 
    
    public String getMsg(){  
        return mMsg;  
    }  
}
