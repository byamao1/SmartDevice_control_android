package com.smart.control.fragment;

import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.smart.control.R;
import com.smart.control.activity.ControlActivityFactory;
import com.smart.control.adapter.DeviceListGridAdapter;
import com.smart.control.base.SuperBaseFragment;
import com.smart.control.component.DragGridView;
import com.smart.control.component.DragGridView.OnChanageListener;
import com.smart.control.component.IconFactoryImpl;
import com.smart.control.entity.device.DeviceManagement;
import com.smart.control.entity.device.SuperDeviceModel;
import com.smart.control.entity.device.DeviceType;

/**
 * �Ѽ�����豸��ʾҳ��
 * @ְ�� 
 * @���� presentation��
 * @author ByTom
 */
public class HomeDeviceFragment extends SuperBaseFragment{
	//�豸����
	DeviceManagement deviceManagement;
	//�豸����ʾ
	private DragGridView gv_Devicelist;
	private DeviceListGridAdapter adapter_DeviceList;
	private List<SuperDeviceModel> list_DeviceModel;
	
	@Override  	 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  
	{  
	    view_Root = inflater.inflate(R.layout.tab_homedevice, container, false);
	    initCommonHeader("�豸");
	    deviceManagement = DeviceManagement.getInstance();
	    initGridView();
	    return view_Root;  
		
	}
	 
	@Override
	public void initCommonHeader(String str_Title){
		 super.initCommonHeader(str_Title);
		 super.btn_CommonheaderLeft.setText("+");
		 super.btn_CommonheaderLeft.setOnClickListener(new View.OnClickListener() {					
				@Override
				public void onClick(View v) {					
					//����豸	
					SuperDeviceModel dm = new SuperDeviceModel("���",DeviceType.KON_PLUG_2PRO);
					addDeviceItem(dm);
				}		
		 });
	}	
	
	private void initGridView(){
		 gv_Devicelist = (DragGridView)view_Root.findViewById(R.id.gridView_devicelist); 
		 //׼��Ҫ��ӵ�������Ŀ 
		 list_DeviceModel = deviceManagement.getAllDeviceModel();
		 //ʵ���������� 
	     adapter_DeviceList = new DeviceListGridAdapter(view_Root.getContext(), 
	    		 									 list_DeviceModel, 
	    		 									 new IconFactoryImpl(),
	                                                 R.drawable.grid_item, 
	                                                 new int[]{R.id.image_item, R.id.text_item}); 
	     //�����϶�ͼ������ݸ��µķ���
	     gv_Devicelist.setOnChangeListener(new OnChanageListener() {             
	            @Override
	            public void onChange(int from, int to) {
//	                DeviceModel temp = list_DeviceModel.get(from);	                 
	                //����Ĵ�����Ҫע����
	                list_DeviceModel.get(from).setIsDraged(true);
	                if(from < to){	                	
	                    for(int i=from; i<to; i++){	    
	                    	list_DeviceModel.get(i+1).setIsDraged(true);
	                        Collections.swap(list_DeviceModel, i, i+1);
	                    }
	                }else{
	                	for(int i=from; i>to; i--){
	                		list_DeviceModel.get(i-1).setIsDraged(true);
	                        Collections.swap(list_DeviceModel, i-1, i);
	                    }
	                }
//	                list_DeviceModel.set(to, temp);	                 
	                adapter_DeviceList.notifyDataSetChanged();
	            }
	        });
	     //���õ��Item�¼���Ӧ
	     gv_Devicelist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle(); //����Bundle����   
				bundle.putInt("position", arg2);//װ������   
				intent.putExtras(bundle);				
				Class clazz = ControlActivityFactory.createCtlActivity(list_DeviceModel.get(arg2).getType());
				intent.setClass(view_Root.getContext(), clazz);
				startActivity(intent);
			}
	     });
	     //ΪGridView���������� 
	     gv_Devicelist.setAdapter(adapter_DeviceList); 
	}
	 //������Դ�����豸�������GridView
	public void addDeviceItem(SuperDeviceModel dm){
		 //���¼ӵ�Deviceд��DeviceManagement
		 if(!deviceManagement.addDeviceModel(dm)){
			 //���ʧ�ܣ������¼����߷�������豸ʧ����Ϣ
		 }
		 //֪ͨGridView����
		 adapter_DeviceList.notifyDataSetChanged(); 
	 }
	

}
