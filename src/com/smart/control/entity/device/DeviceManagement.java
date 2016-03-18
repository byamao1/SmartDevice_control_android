package com.smart.control.entity.device;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ���౻�й�DeviceModel�����õ���ģʽ��
 * @ְ�� ��presentation���ṩDeviceModel���ã���ȡ���޸�DeviceModel�����ݲ������
 * @���� ҵ���߼���
 * @author ByTom
 */
public class DeviceManagement {
	/**
	 * ��������
	 */
	private static DeviceManagement instance;
	private static List<SuperDeviceModel> mList;
	/**
	 * ��ʼ������
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
		//��ʼ��ʱ���Ҫ�����ݲ��ȡ�豸����
		loadDeviceDataFromDB();
	}
	
	/**
	 * ��ȡ���е�DeviceModel
	 * @param 
	 * @return 
	 */
	static public List<SuperDeviceModel> getAllDeviceModel(){
		return mList;
	}

	/**
	 * �����豸��position����ȡָ����DeviceModel
	 * @param 
	 * @return 
	 */
	static public SuperDeviceModel getDeviceModel(int position){
		return mList.get(position);
	}
	
	/**
	 * �����豸��mac����ȡָ����DeviceModel
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
	 * ��mList�����ݲ����1���豸
	 * @param input
	 * @return
	 */
	public boolean addDeviceModel(SuperDeviceModel input){
		// �����ݲ�����豸
		//����ɹ�������mList����һ����������true
		mList.add(input);
		return true;
		//���ʧ�ܣ���log.e����������ݲ����һ���豸��¼ʧ�ܡ���������false
//		return false;
	}

	/**
	 * ���±��϶�����Ӱ���DeviceModel�����ݲ��position
	 * @return
	 */
	public boolean refreshDragedDBPosition(){
		//��ѭ����ֻѡ��list�е�isDragedΪtrue��DeviceModel����
			// �����ݲ����һ����¼
			//����ɹ����򽫸�DeviceModel��isDraged��Ϊfalse��������ֱ������true
			return true;
			//���ʧ�ܣ����ٴ����ݿ�����ȡ���ݣ���log.e������������ݲ����豸��Ϣ��position��¼ʧ�ܡ���������false
//			getDBDeviceModel();
//			return false;
	}
	//�����ݲ��ȡ�豸����
	//���ʧ�ܣ�����ʾ�¼����߷�����Ϣ��������null
	private boolean loadDeviceDataFromDB(){
		if(mList == null){
			mList = new ArrayList<SuperDeviceModel>();
		}else{
			mList.clear();
		}
		//�����ݿ�������ݵ�mList,û�����ݾͲ�����
		mList.add(new SuperDeviceModel("1",DeviceType.KON_PLUG_1));
		mList.add(new SuperDeviceModel("2",DeviceType.KON_PLUG_2));
		mList.add(new SuperDeviceModel("3",DeviceType.KON_PLUG_2PRO));
		mList.add(new SuperDeviceModel("4",DeviceType.KON_PLUG_MINI));
		
		KonPlug2DeviceModel dv = new KonPlug2DeviceModel("5",DeviceType.KON_PLUG_2);
		dv.setPwd("48228");
		dv.setMac("28-d9-8a-05-13-ba");
		mList.add(dv);
		return true;
		//���ʧ�ܣ�����ʾ�¼����߷�����Ϣ��������false
//		return false;
	}
}
