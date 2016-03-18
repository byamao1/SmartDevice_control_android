package com.smart.control.base;

import com.smart.control.R;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SuperBaseFragment extends Fragment{
	public Button btn_CommonheaderLeft,btn_CommonheaderRight;
	public TextView tv_CommonheaderTitle;
	public View view_Root;
	public void initCommonHeader(){
		tv_CommonheaderTitle = (TextView)view_Root.findViewById(R.id.textView_commonheadertitle);
		btn_CommonheaderLeft = (Button)view_Root.findViewById(R.id.button_commonheaderleft);
		btn_CommonheaderRight = (Button)view_Root.findViewById(R.id.button_commonheaderright);
	}
	public void initCommonHeader(String str_Title){
		tv_CommonheaderTitle = (TextView)view_Root.findViewById(R.id.textView_commonheadertitle);
		btn_CommonheaderLeft = (Button)view_Root.findViewById(R.id.button_commonheaderleft);
		btn_CommonheaderRight = (Button)view_Root.findViewById(R.id.button_commonheaderright);
		tv_CommonheaderTitle.setText(str_Title);
	}
}
