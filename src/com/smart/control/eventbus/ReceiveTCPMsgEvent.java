/**

 * Title: ReceiveTCPMsgEvent.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年3月1日
 */
package com.smart.control.eventbus;

/**
 * 
 * @职责 
 * @属层 
 * @author ByTom
 */
public class ReceiveTCPMsgEvent {
private String mMsg;  
    
    
    public ReceiveTCPMsgEvent(String msg) {       	
        mMsg = msg;  
    }  
    
    public String getMsg(){  
        return mMsg;  
    }  
}
