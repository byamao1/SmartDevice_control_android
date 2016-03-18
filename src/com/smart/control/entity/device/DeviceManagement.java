package com.smart.control.entity.device;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 该类被托管DeviceModel，采用单例模式。
 * @职责 对presentation层提供DeviceModel引用；读取、修改DeviceModel在数据层的数据
 * @属层 业务逻辑层
 * @author ByTom
 */
public class DeviceManagement {
	/**
	 * 单例对象
	 */
	private static DeviceManagement instance;
	private static List<SuperDeviceModel> mList;
	/**
	 * 初始化单例
	 */
	public static void initInstance(){
		if (instance == null)
		{
			// Create the instance
			instance = new DeviceManagement();
		}
	}

	public static DeviceManagement getInstance(){
		// Return the instance
		return instance;
	}

	private DeviceManagement(){
		mList = null;
		//初始化时候就要从数据层获取设备数据
		loadDeviceDataFromDB();
	}
	
	/**
	 * 获取所有的DeviceModel
	 * @param 
	 * @return 
	 */
	static public List<SuperDeviceModel> getAllDeviceModel(){
		return mList;
	}

	/**
	 * 根据设备的position，获取指定的DeviceModel
	 * @param 
	 * @return 
	 */
	static public SuperDeviceModel getDeviceModel(int position){
		return mList.get(position);
	}
	
	/**
	 * 根据设备的mac，获取指定的DeviceModel
	 * @param str_deviceMac
	 * @return
	 */
	static public SuperDeviceModel getDeviceModel(String str_deviceMac){
		for(SuperDeviceModel superDeviceModel:mList){
			if(superDeviceModel.getMac().equals(str_deviceMac))
				return superDeviceModel;				
		}
		return null;
	}
	
	/**
	 * 向mList和数据层加入1个设备
	 * @param input
	 * @return
	 */
	public boolean addDeviceModel(SuperDeviceModel input){
		// 向数据层加入设备
		//如果成功，就向mList加入一个，并返回true
		mList.add(input);
		return true;
		//如果失败，用log.e打出“向数据层加入一个设备记录失败”，并返回false
//		return false;
	}

	/**
	 * 更新被拖动动作影响的DeviceModel在数据层的position
	 * @return
	 */
	public boolean refreshDragedDBPosition(){
		//做循环，只选择list中的isDraged为true的DeviceModel保存
			// 向数据层更新一条记录
			//如果成功，则将该DeviceModel的isDraged设为false，继续，直至返回true
			return true;
			//如果失败，就再从数据库中提取数据，用log.e打出“更新数据层中设备信息的position记录失败”，并返回false
//			getDBDeviceModel();
//			return false;
	}
	//从数据层获取设备数据
	//如果失败，向提示事件总线发送消息，并返回null
	private boolean loadDeviceDataFromDB(){
		if(mList == null){
			mList = new ArrayList<SuperDeviceModel>();
		}else{
			mList.clear();
		}
		//从数据库加载数据到mList,没有数据就不加载
		mList.add(new SuperDeviceModel("1",DeviceType.KON_PLUG_1));
		mList.add(new SuperDeviceModel("2",DeviceType.KON_PLUG_2));
		mList.add(new SuperDeviceModel("3",DeviceType.KON_PLUG_2PRO));
		mList.add(new SuperDeviceModel("4",DeviceType.KON_PLUG_MINI));
		
		KonPlug2DeviceModel dv = new KonPlug2DeviceModel("5",DeviceType.KON_PLUG_2);
		dv.setPwd("48228");
		dv.setMac("28-d9-8a-05-13-ba");
		mList.add(dv);
		return true;
		//如果失败，向提示事件总线发送消息，并返回false
//		return false;
	}
}
