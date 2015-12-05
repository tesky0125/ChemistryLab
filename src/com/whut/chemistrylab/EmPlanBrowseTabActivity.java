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
import com.whut.chemistrylab.browse.Fragment_EmPlan;
import com.whut.chemistrylab.global.ChmApplication;

public class EmPlanBrowseTabActivity extends AbActivity {
	
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

		Fragment_EmPlan page1 = new Fragment_EmPlan();//

		List<Fragment> mFragments = new ArrayList<Fragment>();
		mFragments.add(page1);
		
		List<String> tabTexts = new ArrayList<String>();
		tabTexts.add("应急处置措施");
		mAbSlidingTabView.addItemViews(tabTexts, mFragments);
		
		mAbSlidingTabView.setTabColor(Color.BLACK);
		mAbSlidingTabView.setTabSelectColor(Color.rgb(86, 186, 70));
		mAbSlidingTabView.setTabLayoutBackground(R.drawable.slide_top);
		
		
		
	}
	
	private void initTitleBarLayout() {
		mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.main_third_btn);
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