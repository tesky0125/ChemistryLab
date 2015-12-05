package com.whut.chemistrylab.dao;

import java.util.List;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.EmGuide;
import com.whut.chemistrylab.model.PhyChmAttr;
import com.whut.chemistrylab.model.Pictures;
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
public class PicturesDao extends AbDBDaoImpl<Pictures> {
	public PicturesDao(Context context) {
		super(new DBHelper(context),Pictures.class);
	}

	public Pictures getMainDangerTypeByUnNo(String unNo){
		//��������UN�Ų�Ψһ������������ȡǰһ��UN��
		if(unNo==null || unNo.equals("")) return null;
		if(unNo.contains(","))
			unNo=unNo.substring(0, unNo.indexOf(","));
		List<Pictures> list = this.rawQuery("SELECT * FROM Pictures WHERE dangerType = (SELECT mainDangerType FROM Relations WHERE unNo =  '" + unNo + "')", null);
		if(list!=null && list.size()>0) return list.get(0);
		return null;
	}
	
	public Pictures getViceDangerTypeByUnNo(String unNo){
		//��������UN�Ų�Ψһ������������ȡǰһ��UN��
		if(unNo==null || unNo.equals("")) return null;
		if(unNo.contains(","))
			unNo=unNo.substring(0, unNo.indexOf(","));
		List<Pictures> list = this.rawQuery("SELECT * FROM Pictures WHERE dangerType = (SELECT viceDangerType FROM Relations WHERE unNo =  '" + unNo + "')", null);
		if(list!=null && list.size()>0) return list.get(0);
		return null;
	}
}
