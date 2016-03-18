package com.smart.control.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.smart.control.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author ByTom
 */
public class RootActivity extends Activity{
	public RelativeLayout bar_Commen_Top;
	public Button btn_CommonheaderLeft;
	public Button btn_CommonheaderRight;
	public TextView tv_CommonheaderTitle;
	public String userid;
	/**
	 * 
	 */
	public RootActivity(){
		userid = "";
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题   
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	/**
	 * 初始化题头
	 */
	public void initCommonHeader(){
		bar_Commen_Top = (RelativeLayout)findViewById(R.id.relativelayout_commonheader);
		tv_CommonheaderTitle = (TextView)findViewById(R.id.textView_commonheadertitle);
		btn_CommonheaderLeft = (Button)findViewById(R.id.button_commonheaderleft);
		btn_CommonheaderRight = (Button)findViewById(R.id.button_commonheaderright);
	}
	
	/**
	 * 初始化题头。
	 * 根据输入字符串，定制题头名字
	 */
	public void initCommonHeader(String str_Title){
		bar_Commen_Top = (RelativeLayout)findViewById(R.id.relativelayout_commonheader);
		tv_CommonheaderTitle = (TextView)findViewById(R.id.textView_commonheadertitle);
		btn_CommonheaderLeft = (Button)findViewById(R.id.button_commonheaderleft);
		btn_CommonheaderRight = (Button)findViewById(R.id.button_commonheaderright);
		tv_CommonheaderTitle.setText(str_Title);
	}
	
	/**
	 * 清空该ImageView中的图像
	 * @param imageView
	 */
	private void releaseImageView(ImageView imageView){
		Drawable drawable = imageView.getDrawable();
		if(drawable != null){
			drawable.setCallback(null);			
		}
		imageView.setImageDrawable(drawable);
		imageView.setBackgroundDrawable(drawable);
	}
	private List<View> getAllChildViews(View view){
		List<View> allChildren = new ArrayList<View>();
		//测试能否强制转换为ViewGroup
		if(view instanceof ViewGroup)
			return allChildren;	
		ViewGroup vp = (ViewGroup)view;
		int count = vp.getChildCount();
		if(count == 0)
			return allChildren;
		for(int i=0;i<count;i++){
			//获取子View
			View v = vp.getChildAt(i);			
			allChildren.add(v);
			//获取孙view集合
			List<View> list = getAllChildViews(v);
			allChildren.addAll(list);
		}
		return allChildren;
	}
	
	public List<View> getAllChildViews(){
		Window window = this.getWindow();
		View view = window.getDecorView();
		return getAllChildViews(view);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		List<View> list_ChildView = getAllChildViews();
		Iterator<View> iterator = list_ChildView.iterator();
		while(iterator.hasNext()){
			View v = iterator.next();
			Class clazz = v.getClass();
			if(clazz.equals(ImageView.class)){
				System.out.println("~~~~imageview class");
				releaseImageView((ImageView)v);
			}else if(clazz.equals(ImageButton.class)){
				System.out.println("~~~~ImageButton class");
				releaseImageView((ImageButton)v);
			}
		}
	}
}
