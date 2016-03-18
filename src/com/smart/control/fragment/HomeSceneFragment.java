package com.smart.control.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.control.R;
import com.smart.control.base.SuperBaseFragment;

public class HomeSceneFragment extends SuperBaseFragment{
	 @Override  
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  
	 {  
		 View view_Fragment = inflater.inflate(R.layout.tab_homescene, container, false);
		 view_Root = view_Fragment;
		 initCommonHeader("³¡¾°");
		 return view_Fragment;  	
	 }  
}
