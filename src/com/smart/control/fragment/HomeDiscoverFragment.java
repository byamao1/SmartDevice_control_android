package com.smart.control.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.control.R;
import com.smart.control.base.SuperBaseFragment;

public class HomeDiscoverFragment extends SuperBaseFragment{
	 @Override  
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  
	 {  
        View view_Fragment = inflater.inflate(R.layout.tab_homediscover, container, false);
        view_Root = view_Fragment;
        initCommonHeader("·¢ÏÖ");
        return view_Fragment;  
		
	 }  
}
