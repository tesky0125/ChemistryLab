package com.whut.chemistrylab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.titlebar.AbTitleBar; 
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.util.AppUtil;

public class AboutActivity extends AbActivity {
	
	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
   
	@ViewInject(id=R.id.version_val) TextView version_val;
	@ViewInject(id=R.id.description) TextView description;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_about);
        FinalActivity.initInjectedView(this);
        
        application = (ChmApplication)abApplication;
        
        initTitleBarLayout();
        //initTitleRightLayout();
        
        String version = null;
        try {
			PackageInfo pinfo = getPackageManager().getPackageInfo("com.whut.chemistrylab", PackageManager.GET_CONFIGURATIONS);
			version = pinfo.versionName;
			version_val.setText("V"+version);
			description.setText(AppUtil.getTextFormPath("text/about/about.txt", "GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

	private void initTitleBarLayout() {
		mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.about);
        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
	    
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	private void initTitleRightLayout(){
		mAbTitleBar.clearRightView();
		final View searchView=mInflater.inflate(R.layout.btn_search, null);
		
		mAbTitleBar.addRightView(searchView);
		
    	Button searchBtn=(Button) searchView.findViewById(R.id.searchBtn);
    	searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    }
	
	
    
}


