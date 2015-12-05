package com.whut.chemistrylab;

import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpPool;
import com.ab.titlebar.AbTitleBar;
import com.whut.chemistrylab.example.DBOrmActivity;
import com.whut.chemistrylab.global.ChmApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EmPlanActivity extends AbActivity {

	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
	
	@ViewInject(id=R.id.emplan_btn_first) Button btnFirst;
	@ViewInject(id=R.id.emplan_btn_second) Button btnSecond;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setAbContentView(R.layout.activity_emplan);
		FinalActivity.initInjectedView(this);
		
		this.showProgressDialog();
		
		application = (ChmApplication) abApplication;

		initTitleBarLayout();
		initTitleRightLayout();
		initButtonListeners();
		
		this.removeProgressDialog();
	}

	private void initTitleBarLayout() {
		mAbTitleBar = this.getTitleBar();
		mAbTitleBar.setTitleText(R.string.emplan);
		mAbTitleBar.setLogo(R.drawable.button_selector_back);
		mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
		mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
		mAbTitleBar.setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
		
		mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void initTitleRightLayout(){
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


	private void initButtonListeners() {
		// TODO Auto-generated method stub
		btnFirst.setOnClickListener(emplanBtnListener);
		btnSecond.setOnClickListener(emplanBtnListener);
	}
	
	private OnClickListener emplanBtnListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id=v.getId();
			Intent it=new Intent();
			switch(id)
			{
			case R.id.emplan_btn_first:
				it.setClass(EmPlanActivity.this, EmPlanCoverTabActivity.class);
				break;
			case R.id.emplan_btn_second:
				it.setClass(EmPlanActivity.this, EmCommTabActivity.class);
				break;
			}
			EmPlanActivity.this.startActivity(it);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}