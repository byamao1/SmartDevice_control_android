/**

 * Title: ReceiveTCPMsgEvent.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��3��1��
 */
package com.smart.control.eventbus;

/**
 * 
 * @ְ�� 
 * @���� 
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
