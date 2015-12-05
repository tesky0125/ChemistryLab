package com.whut.chemistrylab;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.ab.activity.AbActivity;
import com.ab.titlebar.AbTitleBar;
import com.ab.view.sliding.AbSlidingTabView;
import com.whut.chemistrylab.emplancover.Fragment_EmPlanCover1;
import com.whut.chemistrylab.emplancover.Fragment_EmPlanCover2;
import com.whut.chemistrylab.emplancover.Fragment_EmPlanCover3;
import com.whut.chemistrylab.global.ChmApplication;

public class EmPlanCoverTabActivity extends AbActivity {
	
	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
	
	
	@ViewInject(id=R.id.mAbSlidingTabView) AbSlidingTabView mAbSlidingTabView;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.frag_sliding_tab);
		FinalActivity.initInjectedView(this);
		
		application = (ChmApplication) abApplication;
		
		initTitleBarLayout();
		initTitleRightLayout();

		
		//禁止滑动
		/*mAbSlidingTabView.getViewPager().setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});*/

		Fragment_EmPlanCover1 page1 = new Fragment_EmPlanCover1();//适用范围及情况
		Fragment_EmPlanCover2 page2 = new Fragment_EmPlanCover2();//组织与管理
		Fragment_EmPlanCover3 page3 = new Fragment_EmPlanCover3();//事故报告
		
		List<Fragment> mFragments = new ArrayList<Fragment>();
		mFragments.add(page1);
		mFragments.add(page2);
		mFragments.add(page3);
		
		List<String> tabTexts = new ArrayList<String>();
		tabTexts.add("范围及情况");
		tabTexts.add("组织与管理");
		tabTexts.add("事故报告");
		mAbSlidingTabView.addItemViews(tabTexts, mFragments);
		
		mAbSlidingTabView.setTabColor(Color.BLACK);
		mAbSlidingTabView.setTabSelectColor(Color.rgb(86, 186, 70));
		mAbSlidingTabView.setTabLayoutBackground(R.drawable.slide_top);
		
		
		
	}
	
	private void initTitleBarLayout() {
		mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.emplan_first_btn);
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


	private void initTitleRightLayout() {
		mAbTitleBar.clearRightView();
    	View rightViewMore = mInflater.inflate(R.layout.btn_more, null);
    	mAbTitleBar.addRightView(rightViewMore);
    	Button about = (Button)rightViewMore.findViewById(R.id.moreBtn);
    	
    	about.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View v) {
 			}
         });
	}
}