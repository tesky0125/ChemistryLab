package com.whut.chemistrylab.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.EmPlan;
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * ���ƣ�UserDao.java 
 * �������������ݿ���sd��
 * @author zhaoqp
 * @date��2013-7-31 ����4:13:02
 * @version v1.0
 */
public class EmPlanDao extends AbDBDaoImpl<EmPlan> {
	public EmPlanDao(Context context) {
		super(new DBHelper(context),EmPlan.class);
	}
	
	public EmPlan getBeanByID(int id){
		return this.rawQuery("SELECT * FROM EmPlan WHERE _id=" + id + "", null).get(0);
	}
	

}
