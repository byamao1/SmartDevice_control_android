/**

 * Title: ReceiveNetMsgEvent.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��18��
 */
package com.smart.control.eventbus;

/**
 * 
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class ReceiveXMPPMsgEvent {
	private String mFrom;  
    private String mMsg;  
    
    /**
     * @param from ����������
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
