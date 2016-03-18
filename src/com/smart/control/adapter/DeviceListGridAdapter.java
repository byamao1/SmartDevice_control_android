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

//��HomeDeviceFragment��DeviceList��ʾ�õ�
//����������ݵõ���DeviceModel�е�DeviceType���ض�Ӧ��ͼ�꣨IconFactory����
public class DeviceListGridAdapter extends BaseAdapter{
	private static final String TAG = "DeviceListGridAdapter";
//	View temp=null;
	private Context mContext;
	private List<SuperDeviceModel> mList;
	private IIconFactory iconFactory;
	private int mResource;
	private int[] mTo;
	LayoutInflater layoutInflater;
//	Map<View, DeviceModel> map_ViewAndDevice;//��������View��DeviceModelӳ�䣬Ŀ�������϶������DeviceModel�е�position
//	private ImageView mImageView;
//	private TextView mTextView;
	
	//������
	public DeviceListGridAdapter(Context context, List<SuperDeviceModel> list, IIconFactory factory, int resource, int[] to){
		mContext = context;
		mList = list;
		iconFactory = factory;
		mResource = resource;
		mTo = to;
		layoutInflater = LayoutInflater.from(context);	
	}
	
	//GridView�����������������ķ���������GridView����getView����
	@Override
	public int getCount() {
		return mList.size();//ע��˴�
	}
	
	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	//��mList���������������ʾ
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
//					Log.d(TAG, "���");
//				else
//					Log.d(TAG, "����");
		}else{
			//ϵͳ����ʱ�Ĵ������ﲻ��ʾ
//			imageView.setBackgroundResource(R.drawable.add_more);
//			textView.setText("������");
//			Log.d(TAG, String.valueOf(position)+"---end---");
						
		}
		return convertView;
	}

}
