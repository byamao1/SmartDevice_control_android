/**

 * Title: KonEnDecoder.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月27日
 */
package com.smart.control.entity.endecoder;

import android.util.Base64;

import com.smart.control.common.AES_Cipher;
import com.smart.control.entity.command.Cmd;
import com.smart.control.entity.device.DeviceCtlMode;

/**
 * 控客公司的编解码器。
 * isNetConfig为true，则为smartConfig的编解码器；为false，则为控制命令的编解码器。
 * @职责 
 * @属层 
 * @author ByTom
 */
public class KonEnDecoder extends SuperEnDecoder{
	
	/**
	 * aes的key
	 */
	private String AES_seed;
	
	public KonEnDecoder(DeviceCtlMode deviceCtlMode){
		AES_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";		
		super.deviceCtlMode = deviceCtlMode;
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.endecoder.SuperEnDecoder#getEncode(java.lang.String)
	 */
	@Override
	public String getEncode(String prefix, String str_code){
		String str_encode = "";
		switch(deviceCtlMode){ 
			case XMPP:str_encode =  getXmppEncode(prefix, str_code);break;
			case WLAN_TCP: str_encode =  getWlanTcpEncode(prefix, str_code);break;
			case AP_TCP: str_encode =  getAPTcpEncode(prefix, str_code);break;
			default: str_encode =  "";break;
		}		
		return str_encode;
	}
	
	/* (non-Javadoc)
	 * @see com.smart.control.entity.endecoder.SuperEnDecoder#getDecode(java.lang.String)
	 */
	@Override
	public String getDecode(String str_code){
		String str_decode = "";
		switch(deviceCtlMode){
		case XMPP:str_decode =  getXmppDecode(str_code);break;
		case WLAN_TCP:str_decode =  getWlanTcpDecode(str_code);break;
		case AP_TCP: str_decode =  getAPTcpDecode(str_code);break;
		default: str_decode =  "";break;
		}		
		return str_decode;
	}			
	
	private String getXmppEncode(String prefix, String str_src){
		//code加密编码
		byte[] bytes_Aes = AES_Cipher.encrypt(str_src, AES_seed);
		String str_Base64 = Base64.encodeToString(bytes_Aes, Base64.DEFAULT);
		//去除尾部'\n'
		str_Base64 = str_Base64.replace("\n", "");
		//加上前缀
//		String str_Result = "encryption:" + str_Base64;
		String str_Result = null;
		if(prefix != null)
			str_Result = prefix + str_Base64;
		else
			str_Result = str_Base64;
		return str_Result;
	}
	
	private String getWlanTcpEncode(String prefix, String str_src){
		return getXmppEncode(prefix, str_src);
	}	
	
	private String getAPTcpEncode(String prefix, String str_src){
		return null;
	}	
	
	private String getXmppDecode(String str_src) {		
		//解码
		byte[] bytes_Base64 = Base64.decode(str_src, Base64.DEFAULT);
		String str_Result = AES_Cipher.decrypt(bytes_Base64, AES_seed);
		return str_Result;
	}
	
	private String getWlanTcpDecode(String str_src) {
		return getXmppDecode(str_src);
	}
	
	private String getAPTcpDecode(String str_src) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
