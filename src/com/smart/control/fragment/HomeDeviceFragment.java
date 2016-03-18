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
 * 已加入的设备显示页面
 * @职责 
 * @属层 presentation层
 * @author ByTom
 */
public class HomeDeviceFragment extends SuperBaseFragment{
	//设备管理
	DeviceManagement deviceManagement;
	//设备项显示
	private DragGridView gv_Devicelist;
	private DeviceListGridAdapter adapter_DeviceList;
	private List<SuperDeviceModel> list_DeviceModel;
	
	@Override  	 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  
	{  
	    view_Root = inflater.inflate(R.layout.tab_homedevice, container, false);
	    initCommonHeader("设备");
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
					//添加设备	
					SuperDeviceModel dm = new SuperDeviceModel("添加",DeviceType.KON_PLUG_2PRO);
					addDeviceItem(dm);
				}		
		 });
	}	
	
	private void initGridView(){
		 gv_Devicelist = (DragGridView)view_Root.findViewById(R.id.gridView_devicelist); 
		 //准备要添加的数据条目 
		 list_DeviceModel = deviceManagement.getAllDeviceModel();
		 //实例化适配器 
	     adapter_DeviceList = new DeviceListGridAdapter(view_Root.getContext(), 
	    		 									 list_DeviceModel, 
	    		 									 new IconFactoryImpl(),
	                                                 R.drawable.grid_item, 
	                                                 new int[]{R.id.image_item, R.id.text_item}); 
	     //设置拖动图标后，数据更新的方法
	     gv_Devicelist.setOnChangeListener(new OnChanageListener() {             
	            @Override
	            public void onChange(int from, int to) {
//	                DeviceModel temp = list_DeviceModel.get(from);	                 
	                //这里的处理需要注意下
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
	     //设置点击Item事件响应
	     gv_Devicelist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle(); //创建Bundle对象   
				bundle.putInt("position", arg2);//装入数据   
				intent.putExtras(bundle);				
				Class clazz = ControlActivityFactory.createCtlActivity(list_DeviceModel.get(arg2).getType());
				intent.setClass(view_Root.getContext(), clazz);
				startActivity(intent);
			}
	     });
	     //为GridView设置适配器 
	     gv_Devicelist.setAdapter(adapter_DeviceList); 
	}
	 //向数据源加入设备项，并更新GridView
	public void addDeviceItem(SuperDeviceModel dm){
		 //将新加的Device写入DeviceManagement
		 if(!deviceManagement.addDeviceModel(dm)){
			 //如果失败，则向事件总线发送添加设备失败消息
		 }
		 //通知GridView更新
		 adapter_DeviceList.notifyDataSetChanged(); 
	 }
	

}
