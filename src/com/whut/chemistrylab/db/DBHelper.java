package com.whut.chemistrylab.db;

import android.content.Context;

import com.ab.db.orm.AbDBHelper;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.DailySupervision;
import com.whut.chemistrylab.model.DangerDesc;
import com.whut.chemistrylab.model.EmGuide;
import com.whut.chemistrylab.model.EmMeans;
import com.whut.chemistrylab.model.EmMember;
import com.whut.chemistrylab.model.EmPlan;
import com.whut.chemistrylab.model.EnSenseTarget;
import com.whut.chemistrylab.model.Experts;
import com.whut.chemistrylab.model.FireMeans;
import com.whut.chemistrylab.model.Headquaters;
import com.whut.chemistrylab.model.LeakDistance;
import com.whut.chemistrylab.model.LeakEmMeans;
import com.whut.chemistrylab.model.PhyChmAttr;
import com.whut.chemistrylab.model.Pictures;
import com.whut.chemistrylab.model.Relations;
import com.whut.chemistrylab.model.User;

public class DBHelper extends /*AbSDDBHelper*/AbDBHelper {
	// 数据库名
	private static final String DBNAME = "chemistrylab.db";
	// 数据库 存放路径
    //private static final String DBPATH = "ChmistryLabDB";
    
    // 当前数据库的版本
	//当添加新表时，需要更新数据库版本号
	private static final int DBVERSION = 2;
	
	// 要初始化的表
	private static final Class<?>[] clazz = { 
		User.class,
		
		ChmIdentity.class,
		PhyChmAttr.class,
		DangerDesc.class,
		
		FireMeans.class,
		LeakEmMeans.class,
		LeakDistance.class,
		EmMeans.class,
		
		DailySupervision.class,
		EmGuide.class,
		
		Relations.class,
		Headquaters.class,
		Experts.class,
		EmMember.class,
		Pictures.class,
		
		EmPlan.class,
		EnSenseTarget.class,
		
		};

	public DBHelper(Context context) {
		//super(context,DBPATH, DBNAME, null, DBVERSION, clazz);
		super(context, DBNAME, null, DBVERSION, clazz);
	}

}



