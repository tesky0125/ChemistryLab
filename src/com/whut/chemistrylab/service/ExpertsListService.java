package com.whut.chemistrylab.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whut.chemistrylab.R;
import com.whut.chemistrylab.dao.ChmIdentityDao;
import com.whut.chemistrylab.dao.ExpertsDao;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.util.AppUtil;

public class ExpertsListService {

	private Context context;
	DBHelper dbHelper;
	ExpertsDao expertsDao;

	public ExpertsListService(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
		expertsDao=new ExpertsDao(context);
	}

	public int getExpertsCount(int whereType, String whereClause){
		if(whereType==0){//姓名
			return this.getExpertsCountByName(whereClause);
		}else{//if(whereType==1)//单位
			return this.getExpertsCountByWorkspace(whereClause);
		}
	}
	
	private int getExpertsCountByName(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="name like '%"+whereClause+"%'";
		return expertsDao.queryCount(sql, null);
	}
	
	private int getExpertsCountByWorkspace(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="workplace like '%"+whereClause+"%'";
		return expertsDao.queryCount(sql, null);
	}

	public List<Map<String, Object>> getExpertsList(int whereType, int currentPageNum,
			int pageSize, String whereClause) {
		if(whereType==0){
			return this.getExpertsListByName(currentPageNum, pageSize, whereClause);
		}else{//if(whereType==1)
			return this.getExpertsListByWorkspace(currentPageNum, pageSize, whereClause);
		}
	}
	
	private List<Map<String, Object>> getExpertsListByName(int currentPageNum,
			int pageSize, String whereClause) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,name,workplace,contact FROM Experts LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,name,workplace,contact FROM Experts WHERE name like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
//			System.out.println(sql);
			cursor = db.rawQuery(sql, null);

			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String workplace = cursor.getString(cursor
						.getColumnIndex("workplace"));
				String contact = cursor.getString(cursor
						.getColumnIndex("contact"));

				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", R.drawable.login);// 图片名
				map.put("itemsTitle", name);
				map.put("itemsText", contact);
				map.put("itemsExpand", false);
				map.put("itemsCheck", false);
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
			if (db != null && db.isOpen()) {
				db.close();
				db = null;
			}
		}

		return list;
	}
	
	private List<Map<String, Object>> getExpertsListByWorkspace(int currentPageNum,
			int pageSize, String whereClause) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,name,workplace,contact FROM Experts LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,name,workplace,contact FROM Experts WHERE workplace like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
//			System.out.println(sql);
			cursor = db.rawQuery(sql, null);

			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String workplace = cursor.getString(cursor
						.getColumnIndex("workplace"));
				String contact = cursor.getString(cursor
						.getColumnIndex("contact"));

				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", R.drawable.login);// 图片名
				map.put("itemsTitle", name);
				map.put("itemsText", contact);
				map.put("itemsExpand", false);
				map.put("itemsCheck", false);
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
			if (db != null && db.isOpen()) {
				db.close();
				db = null;
			}
		}

		return list;
	}
	
	public List<String> getExpertsPhoneList() {

		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT contact FROM Experts";		
//			System.out.println(sql);
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				
				String contact = cursor.getString(cursor
						.getColumnIndex("contact"));
				
				if(AppUtil.isPhoneNumberValid(contact))
				list.add(contact);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
			if (db != null && db.isOpen()) {
				db.close();
				db = null;
			}
		}

		return list;
	}
}
