/**

 * Title: ISuperNetClient.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016��2��3��
 */
package com.smart.control.entity.netclient;

import com.smart.control.entity.command.Cmd;

/**
 * ����ͻ��˵Ľӿڡ�
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public interface ISuperNetClient{
		

	public void netConfig(String server_URL, int server_port, String account, String pwd, String target);
	
	/**
	 * ���ͼ�����Ϣ��Ǽ�����Ϣ������з�����Ϣ��ͨ������������Ϣ�¼����߽���֪ͨ��
	 * ����ǰ��Ҫ�������netConfig�������á�
	 * @param str_msgBody
	 */
	public void sendMsg(String str_msgBody);
}
