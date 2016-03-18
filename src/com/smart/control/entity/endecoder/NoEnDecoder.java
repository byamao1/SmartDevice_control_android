/**

 * Title: NoEnDecoder.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.endecoder;

import com.smart.control.common.LogUtil;

import android.util.Log;

/**
 * 空编解码器
 * @职责 
 * @属层 
 * @author ByTom
 */
public class NoEnDecoder extends SuperEnDecoder{

	private String TAG = "NoEnDecoder";
	
	public NoEnDecoder(){
		LogUtil.i(TAG, "使用空编解码器");
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.encoder.SuperEncoder#getEncode(java.lang.String)
	 */
	@Override
	public String getEncode(String prefix, String str_code) {
		// 不做任何处理
		return str_code;
	}

	/* (non-Javadoc)
	 * @see com.smart.control.entity.endecoder.SuperEnDecoder#getDecode(java.lang.String)
	 */
	@Override
	public String getDecode(String str_code) {
		// 不做任何处理
		return str_code;
	}

}
