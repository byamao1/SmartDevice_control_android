/**

 * Title: ReceiveNetMsgEvent.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月18日
 */
package com.smart.control.eventbus;

/**
 * 
 * @职责 
 * @属层 
 * @author ByTom
 */
public class ReceiveXMPPMsgEvent {
	private String mFrom;  
    private String mMsg;  
    
    /**
     * @param from 从哪里来的
     * @param msg
     */
    public ReceiveXMPPMsgEvent(String from, String msg) {   
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
