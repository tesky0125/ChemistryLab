package com.whut.chemistrylab.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.ChmIdentity;
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * ���ƣ�UserDao.java 
 * �������������ݿ���sd��
 * @author zhaoqp
 * @date��2013-7-31 ����4:13:02
 * @version v1.0
 */
public class ChmIdentityDao extends AbDBDaoImpl<ChmIdentity> {
	public ChmIdentityDao(Context context) {
		super(new DBHelper(context),ChmIdentity.class);
	}
	
	public ChmIdentity getBeanByID(int id){
		return this.rawQuery("SELECT * FROM ChmIdentity WHERE _id=" + id + "", null).get(0);
	}
	
	public ChmIdentity getBeanByUnNo(String unNo){
		return this.rawQuery("SELECT * FROM ChmIdentity WHERE unNo='" + unNo + "'", null).get(0);
	}
}
