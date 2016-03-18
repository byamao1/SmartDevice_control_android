/**

 * Title: DefaultControlActivity.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年1月20日
 */
package com.smart.control.activity;

import android.os.Bundle;

import com.smart.control.R;
import com.smart.control.base.RootActivity;

/**
 * 如果没有对应产品，默认为当前控制页面
 * @author ByTom
 */
public class DefaultControlActivity extends RootActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_defaultcontrol);		
	
	}
}
