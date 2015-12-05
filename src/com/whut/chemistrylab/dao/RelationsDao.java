package com.whut.chemistrylab.dao;

import java.util.List;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.EmGuide;
import com.whut.chemistrylab.model.PhyChmAttr;
import com.whut.chemistrylab.model.Relations;
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * ���ƣ�UserDao.java 
 * �������������ݿ���sd��
 * @author zhaoqp
 * @date��2013-7-31 ����4:13:02
 * @version v1.0
 */
public class RelationsDao extends AbDBDaoImpl<Relations> {
	public RelationsDao(Context context) {
		super(new DBHelper(context),Relations.class);
	}

	public Relations getBeanByUnNo(String unNo){
		List<Relations> list = this.rawQuery("SELECT * FROM Relations WHERE unNo='" + unNo + "'", null);
		if(list!=null && list.size()>0) return list.get(0);
		return null;
	}
}
