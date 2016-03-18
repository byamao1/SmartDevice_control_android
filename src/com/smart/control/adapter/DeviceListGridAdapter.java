package com.smart.control.adapter;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.smart.control.R;
import com.smart.control.component.IIconFactory;
import com.smart.control.entity.*;
import com.smart.control.entity.device.SuperDeviceModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//给HomeDeviceFragment的DeviceList显示用的
//适配器会根据得到的DeviceModel中的DeviceType加载对应的图标（IconFactory负责）
public class DeviceListGridAdapter extends BaseAdapter{
	private static final String TAG = "DeviceListGridAdapter";
//	View temp=null;
	private Context mContext;
	private List<SuperDeviceModel> mList;
	private IIconFactory iconFactory;
	private int mResource;
	private int[] mTo;
	LayoutInflater layoutInflater;
//	Map<View, DeviceModel> map_ViewAndDevice;//用来保存View与DeviceModel映射，目的是在拖动后更新DeviceModel中的position
//	private ImageView mImageView;
//	private TextView mTextView;
	
	//构造器
	public DeviceListGridAdapter(Context context, List<SuperDeviceModel> list, IIconFactory factory, int resource, int[] to){
		mContext = context;
		mList = list;
		iconFactory = factory;
		mResource = resource;
		mTo = to;
		layoutInflater = LayoutInflater.from(context);	
	}
	
	//GridView会调用这个函数，它的返回数就是GridView调用getView次数
	@Override
	public int getCount() {
		return mList.size();//注意此处
	}
	
	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	//将mList里逐项提出设置显示
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layoutInflater.inflate(mResource, null);
		ImageView imageView = (ImageView) convertView.findViewById(mTo[0]);
		TextView textView = (TextView) convertView.findViewById(mTo[1]);
		if (position < mList.size()) {
			SuperDeviceModel dm = mList.get(position);
			int icon = 0;
			if(iconFactory==null)
				return null;
			else
				icon = iconFactory.createIcon(dm.getType());
			imageView.setBackgroundResource(icon);
			textView.setText(dm.getName());	
			
//			Log.d(TAG, String.valueOf(position));
//			if(position==1)
//				if(temp==null)
//					temp = convertView;
//				else if(temp==convertView)
//					Log.d(TAG, "相等");
//				else
//					Log.d(TAG, "不等");
		}else{
			//系统多算时的处理，这里不显示
//			imageView.setBackgroundResource(R.drawable.add_more);
//			textView.setText("。。。");
//			Log.d(TAG, String.valueOf(position)+"---end---");
						
		}
		return convertView;
	}

}
