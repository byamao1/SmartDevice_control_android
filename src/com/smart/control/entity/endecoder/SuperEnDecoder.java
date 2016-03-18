/**

 * Title: SuperEnDecoder.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��27��
 */
package com.smart.control.entity.endecoder;

import com.smart.control.entity.device.DeviceCtlMode;

/**
 * ��������ĳ��ࡣһ�ҹ�˾һ���������ࡣ
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public abstract class SuperEnDecoder {
	
	protected DeviceCtlMode deviceCtlMode;
	
	/**
	 * ��ǰ׺�ı���
	 * @param prefix
	 * @param str_code
	 * @return
	 */
	abstract public String getEncode(String prefix, String str_code);
	
	/**
	 * ����Ͳ��ع�����ʲôǰ׺��ȥ�����ǡ�
	 * @param str_code
	 * @return
	 */
	abstract public String getDecode(String str_code);
}
