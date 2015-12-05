package com.whut.chemistrylab.dao;

import java.util.List;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.Headquaters;
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * 名称：UserDao.java 
 * 描述：本地数据库在sd中
 * @author zhaoqp
 * @date：2013-7-31 下午4:13:02
 * @version v1.0
 */
public class HeadquatersDao extends AbDBDaoImpl<Headquaters> {
	public HeadquatersDao(Context context) {
		super(new DBHelper(context),Headquaters.class);
	}
	
	public Headquaters getBeanByID(int _id){
		List<Headquaters> list = this.rawQuery("SELECT * FROM Headquaters WHERE _id=" + _id + "", null);
		if(list!=null && list.size()>0) return list.get(0);
		return null;
	}
}
