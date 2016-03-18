package com.smart.control.entity.device;


/**
 * 
 * @author ByTom
 */
public class SuperDeviceModel extends Object{
	private String str_Name;
	private String str_Mac;
	private String str_Pwd;
	private DeviceType mType;
	private boolean isOnline;
	private int mPosition;//在GridView中的位置
	private boolean isDraged;//在GridView中被拖动事件影响的标志（不仅仅指被拖动的对象），这样就不必在拖动后将所有记录都更新
	
	public SuperDeviceModel(String name,DeviceType type){
		str_Name = name;
		mType = type;
		
		isOnline = false;
		mPosition = 0;
		isDraged = false;
	}
	public void setName(String str_Input){
		str_Name = str_Input;
	}
	public void setMac(String str_Input){
		str_Mac = str_Input;
	}
	public void setPwd(String str_Input){
		str_Pwd = str_Input;
	}
	public void setType(DeviceType input){
		mType = input;
	}
	public void setPosition(int input){
		mPosition = input;
	}
	public void setIsDraged(boolean input){
		isDraged = input;
	}
	//
	public String getName(){
		return str_Name;
	}
	public String getMac(){
		return str_Mac;
	}
	public String getPwd(){
		return str_Pwd;
	}
	public DeviceType getType(){
		return mType;
	}	
	public int getPosition(){
		return mPosition;
	}
	public boolean getIsDraged(){
		return isDraged;
	}
}

