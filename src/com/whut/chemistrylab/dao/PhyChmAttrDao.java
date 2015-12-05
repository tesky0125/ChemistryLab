package com.whut.chemistrylab.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.LeakEmMeans;
import com.whut.chemistrylab.model.PhyChmAttr;
import com.whut.chemistrylab.model.User;
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * ���ƣ�UserDao.java 
 * �������������ݿ���sd��
 * @author zhaoqp
 * @date��2013-7-31 ����4:13:02
 * @version v1.0
 */
public class PhyChmAttrDao extends AbDBDaoImpl<PhyChmAttr> {
	public PhyChmAttrDao(Context context) {
		super(new DBHelper(context),PhyChmAttr.class);
	}

	public PhyChmAttr getBeanByID(int id){
		return this.rawQuery("SELECT * FROM PhyChmAttr WHERE _id=" + id + "", null).get(0);
	}
	
	public PhyChmAttr getBeanByUnNo(String unNo){
		return this.rawQuery("SELECT * FROM PhyChmAttr WHERE unNo='" + unNo + "'", null).get(0);
	}
}
