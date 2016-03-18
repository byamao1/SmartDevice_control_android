/**

 * Title: KonParseMsg.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��19��
 */
package com.smart.control.entity.parsemsg;

import android.util.Log;

import com.smart.control.entity.command.Cmd;

/**
 * ��Ϣ��������
 * @ְ�� ���յ�����Ϣ������Ϣ��ȡ��
 * @���� 
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
			Log.e(TAG, "��Ϣ��������getMac�����յ��Ƿ�������");
			return "";
		}
		String[] strings = src.split("%");
		//�ӵ�1��2��%֮��
		return strings[1];
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.base.SuperCtlCodeSet#getCtlCode(com.smart.control.entity.CmdType)
	 */
	@Override
	public String getCmd(String src){
		if(src == null||"".equals(src)){
			Log.e(TAG, "��Ϣ��������getCmd�����յ��Ƿ�������");
			return "";
		}
		
//		if(isReceive == true){
//			String[] strings = src.split("%",3);
//			//�ӵ�2��%�����
//			return strings[2];
//		}else{
			String[] strings = src.split("%",4);
			//�ӵ�3�����
			return strings[3];
//		}
		
	}
}
