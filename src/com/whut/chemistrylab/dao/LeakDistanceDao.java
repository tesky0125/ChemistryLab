package com.whut.chemistrylab.dao;

import java.util.List;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.FireMeans;
import com.whut.chemistrylab.model.LeakDistance;
import com.whut.chemistrylab.model.User;
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * 名称：UserDao.java 
 * 描述：本地数据库在sd中
 * @author zhaoqp
 * @date：2013-7-31 下午4:13:02
 * @version v1.0
 */
public class LeakDistanceDao extends AbDBDaoImpl<LeakDistance> {
	public LeakDistanceDao(Context context) {
		super(new DBHelper(context),LeakDistance.class);
	}

	public LeakDistance getBeanByUnNo(String unNo){
		List<LeakDistance> list = this.rawQuery("SELECT * FROM LeakDistance WHERE unNo='" + unNo + "'", null);
		if(list!=null && list.size()>0) return list.get(0);
		return null;
	}
}
