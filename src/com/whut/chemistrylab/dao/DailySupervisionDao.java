package com.whut.chemistrylab.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.DailySupervision;
import com.whut.chemistrylab.model.DangerDesc;
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * 名称：UserDao.java 
 * 描述：本地数据库在sd中
 * @author zhaoqp
 * @date：2013-7-31 下午4:13:02
 * @version v1.0
 */
public class DailySupervisionDao extends AbDBDaoImpl< DailySupervision> {
	public DailySupervisionDao(Context context) {
		super(new DBHelper(context), DailySupervision.class);
	}

	public DailySupervision getBeanByID(int id){
		return this.rawQuery("SELECT * FROM DailySupervision WHERE _id=" + id + "", null).get(0);
	}
	
	public DailySupervision getBeanByUnNo(String unNo){
		return this.rawQuery("SELECT * FROM DailySupervision WHERE unNo='" + unNo + "'", null).get(0);
	}
}
