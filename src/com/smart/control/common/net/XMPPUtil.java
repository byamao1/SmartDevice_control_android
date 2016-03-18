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
	
	private static int index;	//这个是对连接次数的计数
	
	private XMPPUtil(Context ct){
		this.ct = ct;
		this.index = 0;
		this.connection = null;
	}	
		
	/**
	 * 设置xmpp服务器地址、端口号、账号、密码、目标
	 * @param input_server_URL
	 * @param input_server_port
	 * @param input_account
	 * @param input_pwd
	 * @param input_target	目标一定要写全。例如："28-d9-8a-05-13-ba@kankun-smartplug.com"，而不能只写"28-d9-8a-05-13-ba"
	 * @return
	 */
	public static boolean netConfig(String input_server_URL, int input_server_port, String input_account, String input_pwd, String input_target){
		if((input_server_URL == null)||("".equals(server_URL))){
			Log.d(TAG, "input_server_URL参数非法");
			return false;			
		}	
		if((input_account == null)||("".equals(input_account))){
			Log.d(TAG, "input_account参数非法");
			return false;			
		}
		if((input_pwd == null)||("".equals(input_pwd))){
			Log.d(TAG, "input_pwd参数非法");
			return false;			
		}
		if((input_target == null)||("".equals(input_target))){
			Log.d(TAG, "input_target参数非法");
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
	 * 在获取实例前，需要调用netConfig函数设置各参数。
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
		//未连接或登录的情况处理
		if(xmppUtil.isConnected() == false){
			xmppUtil.conServer();
//			account = NetUtil.getMacAddress(ct);
			xmppUtil.login(account, account);
		}
		//最多试4次
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
		connection.DEBUG_ENABLED = true;//显示xmpp各阶段信息
		try {
			connection.connect();
			
			connection.addConnectionListener(new ConnectionListener() {
				
				@Override
				public void reconnectionSuccessful() {
					// TODO Auto-generated method stub
					Log.i(TAG, "重连成功");
				}
				
				@Override
				public void reconnectionFailed(Exception arg0) {
					// TODO Auto-generated method stub
					Log.i(TAG, "重连失败");
				}
				
				@Override
				public void reconnectingIn(int arg0) {
					// TODO Auto-generated method stub
					Log.i(TAG, "重连中");
				}
				
				@Override
				public void connectionClosedOnError(Exception e) {
					// TODO Auto-generated method stub
					Log.i(TAG, "连接出错");
					if(e.getMessage().contains("conflict")){
						Log.i(TAG, "被挤掉了");
//						disConnectServer();
					}
				}
				
				@Override
				public void connectionClosed() {
					// TODO Auto-generated method stub
					Log.i(TAG, "连接关闭");
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
    			Log.d(TAG, "xmpp验证未通过");
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
	//发送加密信息与非加密信息
	public boolean sendMessage(String str_msgBody){		
		//发消息（命令）
		ChatManager charManager = connection.getChatManager();
//		String str_Target = device_mac + "@" + server_URL;
		Chat chat = charManager.createChat(target, new MessageListener() {
			@Override
			public void processMessage(Chat arg0, Message message) {
				if (message.getBody() != null) {
					LogUtil.i(TAG, "XMPP客户端收到信息");
					//向接收网络消息事件总线发消息：message.getBody()  tag:xmpp
					EventBus.getDefault().post(new ReceiveXMPPMsgEvent(message.getFrom(), message.getBody()), "xmpp");
		          }				
			}
		});
		try {
			chat.sendMessage(str_msgBody);	
			return true;
		} catch (XMPPException e) {
			Log.e(TAG, "发送消息出错！");
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
//		connection.DEBUG_ENABLED = true;//显示xmpp各阶段信息
//		
//		try {
//			connection.connect();
//		} catch (XMPPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(!connection.isConnected()){
//			Log.d(TAG, "xmpp断开");
//			return;
//		}
//		
//		AccountManager am = connection.getAccountManager();
//		//创建账户
////		try {
////			am.createAccount("28-d9-8a-05-13-ba", "28-d9-8a-05-13-ba");
////		} catch (XMPPException e) {
////			e.printStackTrace();
////		}
//		
//		//获取本地mac
////		WifiAdmin wifiAdmin = new WifiAdmin(ct);
////		String str_Mac = wifiAdmin.getMac();
////		Log.d(TAG, "本地Mac:"+str_Mac);
//		//
//		String str_K_mac = "28-d9-8a-05-13-ba";
//		String str_Mi_mac = "ac-f7-f3-5f-9a-05";
//		//登录账户
//		try {
//			/** 登录 */  
////            SASLAuthentication.supportSASLMechanism("PLAIN", 0);  
//            connection.login(str_Mi_mac, str_Mi_mac,"android");
//            if(!connection.isAuthenticated()){
//    			Log.d(TAG, "xmpp验证未通过");
//    			return;
//    		}
//            // 设置登录状态：在线  
////             Presence presence = new Presence(Presence.Type.available);  
////             XmppTool.getConnection().sendPacket(presence); 
//			
//		} catch (XMPPException e) {
//			e.printStackTrace();
//		}
////		Log.d(TAG, "ID:"+connection.getConnectionID());
//		
//		
//		//获取账户好友
////		Roster roster = connection.getRoster();
////		Collection<RosterEntry> collection = roster.getEntries();
////		Log.d(TAG,"好友信息："+ collection.size());
//		//发消息（命令）
//		ChatManager charManager = connection.getChatManager();
////		String str_Target = "28-d9-8a-05-13-ba@kankun-smartplug.com";
////		String str_Target = "admin@xjtu";
//		Chat chat = charManager.createChat(target, null);
//		try {
//			String str_Cmd = "wan_phone%ac-f7-f3-5f-9a-05%11131%open%light";//
//			//先aes加密，再base64编码 
//			String str_seed = "fdsl;mewrjope456fds4fbvfnjwaugfo";
//			byte[] bytes_Aes = AES_Cipher.encrypt(str_Cmd, str_seed);
////			Log.d(TAG, "aes加密:"+ bytes_Aes.toString());
//			String str_Base64 = Base64.encodeToString(bytes_Aes, Base64.DEFAULT);
//			Log.d(TAG, "base64编码且aes:"+str_Base64);
//			//去除'\n'
//			str_Base64 = str_Base64.replace("\n", "");
//			String str_Result = "encryption:"+str_Base64;
//			Log.d(TAG, "发送数据:"+str_Result);
//			chat.sendMessage(str_Result);
//			
//			
//		} catch (XMPPException e) {
//			Log.e(TAG, "发送消息出错！");
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
