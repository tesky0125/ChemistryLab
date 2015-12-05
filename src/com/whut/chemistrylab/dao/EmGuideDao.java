package com.whut.chemistrylab.dao;

import java.util.List;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.DailySupervision;
import com.whut.chemistrylab.model.EmGuide;
import com.whut.chemistrylab.model.LeakDistance;
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
public class EmGuideDao extends AbDBDaoImpl<EmGuide> {
	public EmGuideDao(Context context) {
		super(new DBHelper(context),EmGuide.class);
	}

	public EmGuide getBeanByUnNo(String unNo){
		//��������UN�Ų�Ψһ������������ȡǰһ��UN��
		if(unNo.contains(","))
			unNo=unNo.substring(0, unNo.indexOf(","));
		List<EmGuide> list = this.rawQuery("SELECT * FROM EmGuide WHERE guideNo = (SELECT guideNo FROM Relations WHERE unNo = '" + unNo + "')", null);
		if(list!=null && list.size()>0) return list.get(0);
		return null;
	}
}
