/**

 * Title: SuperEnDecoder.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.endecoder;

import com.smart.control.entity.device.DeviceCtlMode;

/**
 * 编解码器的超类。一家公司一个编码器类。
 * @职责 
 * @属层 
 * @author ByTom
 */
public abstract class SuperEnDecoder {
	
	protected DeviceCtlMode deviceCtlMode;
	
	/**
	 * 带前缀的编码
	 * @param prefix
	 * @param str_code
	 * @return
	 */
	abstract public String getEncode(String prefix, String str_code);
	
	/**
	 * 解码就不必关心是什么前缀，去掉就是。
	 * @param str_code
	 * @return
	 */
	abstract public String getDecode(String str_code);
}
