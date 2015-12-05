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

public class MainActivity extends AbActivity {

	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
	
	@ViewInject(id=R.id.main_btn_first) Button btnFirst;
	@ViewInject(id=R.id.main_btn_second) Button btnSecond;
	@ViewInject(id=R.id.main_btn_third) Button btnThird;
	//@ViewInject(id=R.id.main_btn_fourth) Button btnFourth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setAbContentView(R.layout.activity_main);
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
		mAbTitleBar.setTitleText(R.string.app_name);
		mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
		mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
		mAbTitleBar.setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
	}
	
	private void initTitleRightLayout(){
    	mAbTitleBar.clearRightView();
    	View rightViewMore = mInflater.inflate(R.layout.btn_more, null);
    	View rightViewMenu = mInflater.inflate(R.layout.btn_menu, null);
    	mAbTitleBar.addRightView(rightViewMenu);
    	mAbTitleBar.addRightView(rightViewMore);
    	Button about = (Button)rightViewMore.findViewById(R.id.moreBtn);
    	Button menu = (Button)rightViewMenu.findViewById(R.id.menuBtn);
    	
    	menu.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View v) {
 			}
         });
    	
    	about.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View v) {
 				Intent intent = new Intent(MainActivity.this,AboutActivity.class); 
 				startActivity(intent);
 			}
         });
    }


	private void initButtonListeners() {
		// TODO Auto-generated method stub
		btnFirst.setOnClickListener(mainBtnListener);
		btnSecond.setOnClickListener(mainBtnListener);
		btnThird.setOnClickListener(mainBtnListener);
		//btnFourth.setOnClickListener(mainBtnListener);
	}
	
	private OnClickListener mainBtnListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id=v.getId();
			Intent it=new Intent();
			switch(id)
			{
			case R.id.main_btn_first:
				it.setClass(MainActivity.this, ChmLookupListActivity.class);
				break;
			case R.id.main_btn_second:
				it.setClass(MainActivity.this, EmPlanActivity.class);
				break;
			case R.id.main_btn_third:
				it.setClass(MainActivity.this, EmPlanBrowseListActivity.class);
				break;
//			case R.id.main_btn_fourth:
//				it.setClass(MainActivity.this, AboutActivity.class);
//				break;
			}
			MainActivity.this.startActivity(it);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private static Boolean isExit = false;  
    private static Boolean hasTask = false;  
    Timer tExit = new Timer();  
    TimerTask task = new TimerTask() {  
        @Override 

        public void run() {  
            isExit = true;  
            hasTask = true;  
        }  
    };  

	
	@Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
            if(isExit == false ) {  
                isExit = true;  
                showToast("再按一次退出程序");  
                if(!hasTask) {  
                    tExit.schedule(task, 2000);  
                }  
            } else {  
                finish();  
                System.exit(0);  
            }  
        }  
        return false;  

    } 

}
