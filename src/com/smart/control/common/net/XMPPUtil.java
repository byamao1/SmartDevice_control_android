package com.smart.control.common.net;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.ProviderManager;
import org.simple.eventbus.EventBus;

import com.smart.control.common.AES_Cipher;
import com.smart.control.common.LogUtil;
import com.smart.control.eventbus.*;

import android.content.Context;
import android.provider.ContactsContract.Presence;
import android.util.Base64;
import android.util.Log;

public class XMPPUtil{
	private static final String TAG = "XMPPUtil";
	private Context ct;
	private static XMPPUtil xmppUtil;
	public static XMPPConnection connection;
	public static boolean isNetConnect;
	
	private static String server_URL;
	private static int server_port;
	private static String account;
	private static String pwd;
	private static String target;
	
	private static int index;	//����Ƕ����Ӵ����ļ���
	
	private XMPPUtil(Context ct){
		this.ct = ct;
		this.index = 0;
		this.connection = null;
	}	
		
	/**
	 * ����xmpp��������ַ���˿ںš��˺š����롢Ŀ��
	 * @param input_server_URL
	 * @param input_server_port
	 * @param input_account
	 * @param input_pwd
	 * @param input_target	Ŀ��һ��Ҫдȫ�����磺"28-d9-8a-05-13-ba@kankun-smartplug.com"��������ֻд"28-d9-8a-05-13-ba"
	 * @return
	 */
	public static boolean netConfig(String input_server_URL, int input_server_port, String input_account, String input_pwd, String input_target){
		if((input_server_URL == null)||("".equals(server_URL))){
			Log.d(TAG, "input_server_URL�����Ƿ�");
			return false;			
		}	
		if((input_account == null)||("".equals(input_account))){
			Log.d(TAG, "input_account�����Ƿ�");
			return false;			
		}
		if((input_pwd == null)||("".equals(input_pwd))){
			Log.d(TAG, "input_pwd�����Ƿ�");
			return false;			
		}
		if((input_target == null)||("".equals(input_target))){
			Log.d(TAG, "input_target�����Ƿ�");
			return false;			
		}	
		
		server_URL = input_server_URL;
		server_port = input_server_port;
		account = input_account;
		pwd = input_pwd;
		target = input_target;
		return true;
	}
	
	/**
	 * �ڻ�ȡʵ��ǰ����Ҫ����netConfig�������ø�������
	 * @param ct
	 * @return
	 */
	public static XMPPUtil getInstance(Context ct){
		if(xmppUtil == null){
			try {
				Class.forName("org.jivesoftware.smack.ReconnectionManager");
			} catch (Exception e) {
				e.printStackTrace();
			}
			xmppUtil = new XMPPUtil(ct);			
		}else{
			xmppUtil.ct = ct;
		}
		//δ���ӻ��¼���������
		if(xmppUtil.isConnected() == false){
			xmppUtil.conServer();
//			account = NetUtil.getMacAddress(ct);
			xmppUtil.login(account, account);
		}
		//�����4��
		if(connection == null){
			index += 1;
			if(index != 4 )
				xmppUtil = getInstance(ct);
		}
		return xmppUtil;
	}
	
	public void conServer(){
		ConnectionConfiguration connConfig = new ConnectionConfiguration(
				server_URL, server_port);
		connConfig.setSASLAuthenticationEnabled(true);
		connConfig.setReconnectionAllowed(true);
		connection = new XMPPConnection(connConfig);
		connection.DEBUG_ENABLED = true;//��ʾxmpp���׶���Ϣ
		try {
			connection.connect();
			
			connection.addConnectionListener(new ConnectionListener() {
				
				@Override
				public void reconnectionSuccessful() {
					// TODO Auto-generated method stub
					Log.i(TAG, "�����ɹ�");
				}
				
				@Override
				public void reconnectionFailed(Exception arg0) {
					// TODO Auto-generated method stub
					Log.i(TAG, "����ʧ��");
				}
				
				@Override
				public void reconnectingIn(int arg0) {
					// TODO Auto-generated method stub
					Log.i(TAG, "������");
				}
				
				@Override
				public void connectionClosedOnError(Exception e) {
					// TODO Auto-generated method stub
					Log.i(TAG, "���ӳ���");
					if(e.getMessage().contains("conflict")){
						Log.i(TAG, "��������");
//						disConnectServer();
					}
				}
				
				@Override
				public void connectionClosed() {
					// TODO Auto-generated method stub
					Log.i(TAG, "���ӹر�");
				}
			});
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public int register(String str_Account,String str_Password){
//		
//	}
	public boolean login(String str_account,String str_password){
		if(connection == null){
			return false;
		}
		if(connection.isConnected() == false){
			return false;
		}
		try {
            connection.login(str_account, str_password, "android");            
            if(!connection.isAuthenticated()){
    			Log.d(TAG, "xmpp��֤δͨ��");
    			return false;
    		}
            return true;
		}catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isConnected(){
		if(connection == null){
			return false;
		}
		return connection.isConnected();
	}
	
//	public boolean isAuthenticated(){
//		
//	}
//	public List getRoster(){
//		
//	}
//	public Collection getRoster(){
//		
//	}
	//���ͼ�����Ϣ��Ǽ�����Ϣ
	public boolean sendMessage(String str_msgBody){		
		//����Ϣ�����
		ChatManager charManager = connection.getChatManager();
//		String str_Target = device_mac + "@" + server_URL;
		Chat chat = charManager.createChat(target, new MessageListener() {
			@Override
			public void processMessage(Chat arg0, Message message) {
				if (message.getBody() != null) {
					LogUtil.i(TAG, "XMPP�ͻ����յ���Ϣ");
					//�����������Ϣ�¼����߷���Ϣ��message.getBody()  tag:xmpp
					EventBus.getDefault().post(new ReceiveXMPPMsgEvent(message.getFrom(), message.getBody()), "xmpp");
		          }				
			}
		});
		try {
			chat.sendMessage(str_msgBody);	
			return true;
		} catch (XMPPException e) {
			Log.e(TAG, "������Ϣ����");
			e.printStackTrace();
			return false;
		}
		
	}
	
//	public void test(){
////		XMPPConnection con = new XMPPConnection("kankun-smartplug.com");		
//		
//		ConnectionConfiguration connConfig = new ConnectionConfiguration(
//				"kankun-smartplug.com", 5222);
//		connConfig.setSASLAuthenticationEnabled(true);
//		XMPPConnection connection = new XMPPConnection(connConfig);
//		connection.DEBUG_ENABLED = true;//��ʾxmpp���׶���Ϣ
//		
//		try {
//			connection.connect();
//		} catch (XMPPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(!connection.isConnected()){
//			Log.d(TAG, "xmpp�Ͽ�");
//			return;
//		}
//		
//		AccountManager am = connection.getAccountManager();
//		//�����˻�
////		try {
////			am.createAccount("28-d9-8a-05-13-ba", "28-d9-8a-05-13-ba");
////		} catch (XMPPException e) {
////			e.printStackTrace();
////		}
//		
//		//��ȡ����mac
////		WifiAdmin wifiAdmin = new WifiAdmin(ct);
////		String str_Mac = wifiAdmin.getMac();
////		Log.d(TAG, "����Mac:"+str_Mac);
//		//
//		String str_K_mac = "28-d9-8a-05-13-ba";
//		String str_Mi_mac = "ac-f7-f3-5f-9a-05";
//		//��¼�˻�
//		try {
//			/** ��¼ */  
////            SASLAuthentication.supportSASLMechanism("PLAIN", 0);  
//            connection.login(str_Mi_mac, str_Mi_mac,"android");
//            if(!connection.isAuthenticated()){
//    			Log.d(TAG, "xmpp��֤δͨ��");
//    			return;
//    		}
//            // ���õ�¼״̬������  
////             Presence presence = new Presence(Presence.Type.available);  
////             XmppTool.getConnection().sendPacket(presence); 
//			
//		} catch (XMPPException e) {
//			e.printStackTrace();
//		}
////		Log.d(TAG, "ID:"+connection.getConnectionID());
//		
//		
//		//��ȡ�˻�����
////		Roster roster = connection.getRoster();
////		Collection<RosterEntry> collection = roster.getEntries();
////		Log.d(TAG,"������Ϣ��"+ collection.size());
//		//����Ϣ�����
//		ChatManager charManager = connection.getChatManager();
////		String str_Target = "28-d9-8a-05-13-ba@kankun-smartplug.com";
////		String str_Target = "admin@xjtu";
//		Chat chat = charManager.createChat(target, null);
//		try {
//			String str_Cmd = "wan_phone%ac-f7-f3-5f-9a-05%11131%open%light";//
//			//��aes���ܣ���base64���� 
//			String str_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";
//			byte[] bytes_Aes = AES_Cipher.encrypt(str_Cmd, str_seed);
////			Log.d(TAG, "aes����:"+ bytes_Aes.toString());
//			String str_Base64 = Base64.encodeToString(bytes_Aes, Base64.DEFAULT);
//			Log.d(TAG, "base64������aes:"+str_Base64);
//			//ȥ��'\n'
//			str_Base64 = str_Base64.replace("\n", "");
//			String str_Result = "encryption:"+str_Base64;
//			Log.d(TAG, "��������:"+str_Result);
//			chat.sendMessage(str_Result);
//			
//			
//		} catch (XMPPException e) {
//			Log.e(TAG, "������Ϣ����");
//			e.printStackTrace();
//		}
//		
////		connection.disconnect();
//	}
//	
//	@Override
//	public void run() {
//		test();		
//	}
}
