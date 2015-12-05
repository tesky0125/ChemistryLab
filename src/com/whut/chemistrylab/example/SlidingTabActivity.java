package com.whut.chemistrylab.example;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ab.activity.AbActivity;
import com.ab.titlebar.AbTitleBar;
import com.ab.view.sliding.AbSlidingTabView;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.R.drawable;
import com.whut.chemistrylab.R.id;
import com.whut.chemistrylab.R.layout;
import com.whut.chemistrylab.R.string;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.lookup.Fragment_DailySupervison;
import com.whut.chemistrylab.lookup.Fragment_EmGuide;
import com.whut.chemistrylab.lookup.Fragment_DangerProperty;
import com.whut.chemistrylab.lookup.Fragment_SceneEmMeans;

public class SlidingTabActivity extends AbActivity {
	
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

		
		//��ֹ����
		/*mAbSlidingTabView.getViewPager().setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});*/

		Fragment_DangerProperty page1 = new Fragment_DangerProperty();//Σ��Ʒ����
		Fragment_DailySupervison page2 = new Fragment_DailySupervison();//�ճ����
		Fragment_SceneEmMeans page3 = new Fragment_SceneEmMeans();//�ֳ�Ӧ����ʩ
		Fragment_EmGuide page4 = new Fragment_EmGuide();//Ӧ����Ԯָ��
		
		List<Fragment> mFragments = new ArrayList<Fragment>();
		mFragments.add(page1);
		mFragments.add(page2);
		mFragments.add(page3);
		mFragments.add(page4);
		
		List<String> tabTexts = new ArrayList<String>();
		tabTexts.add("����");
		tabTexts.add("���");
		tabTexts.add("Ӧ����ʩ");
		tabTexts.add("ָ��");
		mAbSlidingTabView.addItemViews(tabTexts, mFragments);
		
		mAbSlidingTabView.setTabColor(Color.BLACK);
		mAbSlidingTabView.setTabSelectColor(Color.rgb(86, 186, 70));
		mAbSlidingTabView.setTabLayoutBackground(R.drawable.slide_top);
		
		
		
	}
	
	private void initTitleBarLayout() {
		mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.main_first_btn);
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

	}
}