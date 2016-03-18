/**

 * Title: ErrorEvent.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��22��
 */
package com.smart.control.eventbus;

/**
 * �����ã�
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class ErrorMsgEvent {
	private String mFrom;  
    private String mMsg;  
    
    /**
     * @param from ���������ġ���������������ΪTAG��
     * @param msg �������ݡ�
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
