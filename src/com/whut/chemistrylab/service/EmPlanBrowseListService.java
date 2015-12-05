package com.whut.chemistrylab.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ab.global.AbConstant;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.dao.EmPlanDao;
import com.whut.chemistrylab.dao.PicturesDao;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.EmPlan;
import com.whut.chemistrylab.model.Pictures;

public class EmPlanBrowseListService {

	private Context context;
	DBHelper dbHelper;
	EmPlanDao chmIdentityDao;

	public EmPlanBrowseListService(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
		chmIdentityDao=new EmPlanDao(context);
	}

	public int getLookupCount(int whereType, String whereClause){
		if(whereType==0){//货种/名字
			return this.getLookupCountByName(whereClause);
		}
		else if(whereType==1){//类别
			return this.getLookupCountByType(whereClause);
		}
		else if(whereType==2){//地点
			return this.getLookupCountByAddr(whereClause);
		}
		else{//if(whereType==3)//源头
			return this.getLookupCountBySrc(whereClause);
		}
	}
	
	private int getLookupCountByName(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="leakChm like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}
	
	private int getLookupCountByType(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="chmType like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}
	
	private int getLookupCountByAddr(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="leakAddr like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}
	
	private int getLookupCountBySrc(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="leakSrc like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}

	public List<Map<String, Object>> getLookupList(int whereType, int currentPageNum,
			int pageSize, String whereClause) {
		if(whereType==0){
			return this.getLookupListByName(currentPageNum, pageSize, whereClause);
		}
		else if(whereType==1){
			return this.getLookupListByType(currentPageNum, pageSize, whereClause);
		}
		else if(whereType==2){
			return this.getLookupListByAddr(currentPageNum, pageSize, whereClause);
		}
		else /*if(whereType==3)*/{
			return this.getLookupListBySrc(currentPageNum, pageSize, whereClause);
		}
	}
	
	private List<Map<String, Object>> getLookupListByName(int currentPageNum,
			int pageSize, String whereClause) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,leakChm,chmType FROM EmPlan LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,leakChm,chmType FROM EmPlan WHERE leakChm like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
//System.out.println(sql);
			cursor = db.rawQuery(sql, null);

			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String leakChm = cursor.getString(cursor.getColumnIndex("leakChm"));
				String chmType = cursor.getString(cursor
						.getColumnIndex("chmType"));

//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", AbConstant.IMAGENOURL/*R.drawable.image_no*/);// 图片名
				map.put("itemsTitle", leakChm);
				map.put("itemsText", chmType);
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
	
	private List<Map<String, Object>> getLookupListByType(int currentPageNum,
			int pageSize, String whereClause) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,leakChm,chmType FROM EmPlan LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,leakChm,chmType FROM EmPlan WHERE chmType like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
//System.out.println(sql);
			cursor = db.rawQuery(sql, null);
			
			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String leakChm = cursor.getString(cursor.getColumnIndex("leakChm"));
				String chmType = cursor.getString(cursor
						.getColumnIndex("chmType"));

//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", AbConstant.IMAGENOURL);// 图片名
				map.put("itemsTitle", leakChm);
				map.put("itemsText", chmType);
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
	
	private List<Map<String, Object>> getLookupListByAddr(int currentPageNum,
			int pageSize, String whereClause) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,leakChm,chmType FROM EmPlan LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,leakChm,chmType FROM EmPlan WHERE leakAddr like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
System.out.println(sql);
			cursor = db.rawQuery(sql, null);
			
			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String leakChm = cursor.getString(cursor.getColumnIndex("leakChm"));
				String chmType = cursor.getString(cursor
						.getColumnIndex("chmType"));

//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", AbConstant.IMAGENOURL);// 图片名
				map.put("itemsTitle", leakChm);
				map.put("itemsText", chmType);
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
	private List<Map<String, Object>> getLookupListBySrc(int currentPageNum,
			int pageSize, String whereClause) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,leakChm,chmType FROM EmPlan LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,leakChm,chmType FROM EmPlan WHERE leakSrc like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
//System.out.println(sql);
			cursor = db.rawQuery(sql, null);

			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String leakChm = cursor.getString(cursor.getColumnIndex("leakChm"));
				String chmType = cursor.getString(cursor
						.getColumnIndex("chmType"));

//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", AbConstant.IMAGENOURL);// 图片名
				map.put("itemsTitle", leakChm);
				map.put("itemsText", chmType);
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
}
