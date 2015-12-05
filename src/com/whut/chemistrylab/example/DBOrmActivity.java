package com.whut.chemistrylab.example;

import android.os.Bundle;

import com.ab.activity.AbActivity;
import com.ab.titlebar.AbTitleBar;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.dao.ChmIdentityDao;
import com.whut.chemistrylab.dao.DailySupervisionDao;
import com.whut.chemistrylab.dao.DangerDescDao;
import com.whut.chemistrylab.dao.EmGuideDao;
import com.whut.chemistrylab.dao.EmMeansDao;
import com.whut.chemistrylab.dao.EmMemberDao;
import com.whut.chemistrylab.dao.EmPlanDao;
import com.whut.chemistrylab.dao.EnSenseTargetDao;
import com.whut.chemistrylab.dao.ExpertsDao;
import com.whut.chemistrylab.dao.FireMeansDao;
import com.whut.chemistrylab.dao.HeadquatersDao;
import com.whut.chemistrylab.dao.LeakDistanceDao;
import com.whut.chemistrylab.dao.LeakEmMeansDao;
import com.whut.chemistrylab.dao.PhyChmAttrDao;
import com.whut.chemistrylab.dao.PicturesDao;
import com.whut.chemistrylab.dao.RelationsDao;
import com.whut.chemistrylab.global.ChmApplication;


public class DBOrmActivity extends AbActivity {
	
	private ChmApplication application;
	//定义数据库操作实现类
	ChmIdentityDao dao1=new ChmIdentityDao(this);
	PhyChmAttrDao dao2=new PhyChmAttrDao(this);
	DangerDescDao dao3=new DangerDescDao(this);
	
	FireMeansDao dao4=new FireMeansDao(this);
	LeakEmMeansDao dao5=new LeakEmMeansDao(this);
	LeakDistanceDao dao6=new LeakDistanceDao(this);
	EmMeansDao dao7=new EmMeansDao(this);
	
	DailySupervisionDao dao8=new DailySupervisionDao(this);
	EmGuideDao dao9=new EmGuideDao(this);
	
	RelationsDao dao10=new RelationsDao(this);
	
	HeadquatersDao dao11=new HeadquatersDao(this);
	ExpertsDao dao12=new ExpertsDao(this);
	EmMemberDao dao13=new EmMemberDao(this);
	
	PicturesDao dao14=new PicturesDao(this);
	
	EmPlanDao dao15=new EmPlanDao(this);
	EnSenseTargetDao dao16=new EnSenseTargetDao(this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setAbContentView(R.layout.activity_example);
        
        AbTitleBar mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.db_name);
        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
	    
	    application = (ChmApplication)abApplication;
	    
	    //初始化数据库操作实现类
//	    modelADao = new ModelADao(DBOrmActivity.this);
//	    int totalCountA = modelADao.queryCount(null, null);
	    
	    //要注意在DBHelper中注册model
	    
	    //生成数据库表
	    int cnt1 = dao1.queryCount(null, null);
	    int cnt2 = dao2.queryCount(null, null);
	    int cnt3 = dao3.queryCount(null, null);
	    int cnt4 = dao4.queryCount(null, null);
	    int cnt5 = dao5.queryCount(null, null);
	    int cnt6 = dao6.queryCount(null, null);
	    int cnt7 = dao7.queryCount(null, null);
	    int cnt8 = dao8.queryCount(null, null);
	    int cnt9 = dao9.queryCount(null, null);
	    int cnt10 = dao10.queryCount(null, null);
	    int cnt11 = dao11.queryCount(null, null);
	    int cnt12 = dao12.queryCount(null, null);
	    int cnt13 = dao13.queryCount(null, null);
	    int cnt14 = dao14.queryCount(null, null);
	    int cnt15 = dao15.queryCount(null, null);
	    int cnt16 = dao16.queryCount(null, null);
	    
	    System.out.println("db tables initialized!");
	    
	    
    }
}