/**

 * Title: NoEnDecoder.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��1��27��
 */
package com.smart.control.entity.endecoder;

import com.smart.control.common.LogUtil;

import android.util.Log;

/**
 * �ձ������
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class NoEnDecoder extends SuperEnDecoder{

	private String TAG = "NoEnDecoder";
	
	public NoEnDecoder(){
		LogUtil.i(TAG, "ʹ�ÿձ������");
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.encoder.SuperEncoder#getEncode(java.lang.String)
	 */
	@Override
	public String getEncode(String prefix, String str_code) {
		// �����κδ���
		return str_code;
	}

	/* (non-Javadoc)
	 * @see com.smart.control.entity.endecoder.SuperEnDecoder#getDecode(java.lang.String)
	 */
	@Override
	public String getDecode(String str_code) {
		// �����κδ���
		return str_code;
	}

}
