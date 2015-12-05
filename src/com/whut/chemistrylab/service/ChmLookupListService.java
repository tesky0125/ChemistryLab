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
import com.whut.chemistrylab.dao.PicturesDao;
import com.whut.chemistrylab.db.DBHelper;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.Pictures;

public class ChmLookupListService {

	private Context context;
	DBHelper dbHelper;
	ChmIdentityDao chmIdentityDao;
	PicturesDao picturesDao;

	public ChmLookupListService(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
		chmIdentityDao=new ChmIdentityDao(context);
		picturesDao=new PicturesDao(context);
	}

	public int getLookupCount(int whereType, String whereClause){
		if(whereType==0){//UNºÅ
			return this.getLookupCountByUN(whereClause);
		}
		else if(whereType==1){//CN±àºÅ
			return this.getLookupCountByCN(whereClause);
		}
		else if(whereType==2){//CAS±àºÅ
			return this.getLookupCountByCAS(whereClause);
		}
		else{//if(whereType==3)//ÖÐÎÄÃû
			return this.getLookupCountByChName(whereClause);
		}
	}
	
	private int getLookupCountByUN(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="unNo like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}
	
	private int getLookupCountByCN(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="emCnNo like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}
	
	private int getLookupCountByCAS(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="casNo like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}
	
	private int getLookupCountByChName(String whereClause){
		String sql=null;
		if(whereClause!=null && !whereClause.trim().equals(""))
			sql="chName like '%"+whereClause+"%'";
		return chmIdentityDao.queryCount(sql, null);
	}

	public List<Map<String, Object>> getLookupList(int whereType, int currentPageNum,
			int pageSize, String whereClause) {
		if(whereType==0){
			return this.getLookupListByUN(currentPageNum, pageSize, whereClause);
		}
		else if(whereType==1){
			return this.getLookupListByCN(currentPageNum, pageSize, whereClause);
		}
		else if(whereType==2){
			return this.getLookupListByCAS(currentPageNum, pageSize, whereClause);
		}
		else /*if(whereType==3)*/{
			return this.getLookupListByChName(currentPageNum, pageSize, whereClause);
		}
	}
	
	private List<Map<String, Object>> getLookupListByUN(int currentPageNum,
			int pageSize, String whereClause) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,unNo,chName FROM ChmIdentity LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,unNo,chName FROM ChmIdentity WHERE unNo like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
//System.out.println(sql);
			cursor = db.rawQuery(sql, null);

			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String unNo = cursor.getString(cursor.getColumnIndex("unNo"));
				String chName = cursor.getString(cursor
						.getColumnIndex("chName"));
				Pictures pic = picturesDao.getMainDangerTypeByUnNo(unNo);
				String picLoc = (pic==null)?null:pic.toString();
//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", picLoc/*R.drawable.image_no*/);// Í¼Æ¬Ãû
				map.put("itemsTitle", unNo);
				map.put("itemsText", chName);
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
	
	private List<Map<String, Object>> getLookupListByCN(int currentPageNum,
			int pageSize, String whereClause) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,emCnNo,chName FROM ChmIdentity LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,unNo,emCnNo,casNo,chName FROM ChmIdentity WHERE emCnNo like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
System.out.println(sql);
			cursor = db.rawQuery(sql, null);
			
			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String emCnNo = cursor.getString(cursor.getColumnIndex("emCnNo"));
				String chName = cursor.getString(cursor
						.getColumnIndex("chName"));
				Pictures pic = picturesDao.getMainDangerTypeByUnNo(chmIdentityDao.getBeanByID(_id).getUnNo());
				String picLoc = (pic==null)?null:pic.toString();
//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", picLoc/*R.drawable.image_no*/);// Í¼Æ¬Ãû
				map.put("itemsTitle", emCnNo);
				map.put("itemsText", chName);
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
	
	private List<Map<String, Object>> getLookupListByCAS(int currentPageNum,
			int pageSize, String whereClause) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,casNo,chName FROM ChmIdentity LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,casNo,chName FROM ChmIdentity WHERE casNo like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
System.out.println(sql);
			cursor = db.rawQuery(sql, null);
			
			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String casNo = cursor.getString(cursor.getColumnIndex("casNo"));
				String chName = cursor.getString(cursor
						.getColumnIndex("chName"));
				Pictures pic = picturesDao.getMainDangerTypeByUnNo(chmIdentityDao.getBeanByID(_id).getUnNo());
				String picLoc = (pic==null)?null:pic.toString();
//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", picLoc/*R.drawable.image_no*/);// Í¼Æ¬Ãû
				map.put("itemsTitle", casNo);
				map.put("itemsText", chName);
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
	private List<Map<String, Object>> getLookupListByChName(int currentPageNum,
			int pageSize, String whereClause) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			String sql="SELECT _id,unNo,chName FROM ChmIdentity LIMIT "
					+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
			if(whereClause!=null && !whereClause.trim().equals(""))
				sql="SELECT _id,unNo,chName FROM ChmIdentity WHERE chName like '%" + whereClause + "%' LIMIT "
						+ pageSize + " OFFSET " + (currentPageNum - 1) * pageSize;
//System.out.println(sql);
			cursor = db.rawQuery(sql, null);

			Map<String, Object> map = null;
			while (cursor.moveToNext()) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String unNo = cursor.getString(cursor.getColumnIndex("unNo"));
				String chName = cursor.getString(cursor
						.getColumnIndex("chName"));
				Pictures pic = picturesDao.getMainDangerTypeByUnNo(unNo);
				String picLoc = (pic==null)?null:pic.toString();
//System.out.println(picLoc);
				map = new HashMap<String, Object>();
				map.put("_id", _id);
				map.put("itemsIcon", picLoc/*R.drawable.image_no*/);// Í¼Æ¬Ãû
				map.put("itemsTitle", unNo);
				map.put("itemsText", chName);
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
