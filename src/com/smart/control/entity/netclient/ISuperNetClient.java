/**

 * Title: ISuperNetClient.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月3日
 */
package com.smart.control.entity.netclient;

import com.smart.control.entity.command.Cmd;

/**
 * 网络客户端的接口。
 * @职责 
 * @属层 
 * @author ByTom
 */
public interface ISuperNetClient{
		

	public void netConfig(String server_URL, int server_port, String account, String pwd, String target);
	
	/**
	 * 发送加密信息与非加密信息。如果有返回信息，通过接收网络消息事件总线进行通知。
	 * 发送前需要必须调用netConfig函数设置。
	 * @param str_msgBody
	 */
	public void sendMsg(String str_msgBody);
}
