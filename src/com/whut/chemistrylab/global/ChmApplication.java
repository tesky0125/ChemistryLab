package com.whut.chemistrylab.global;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.NotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;

import com.ab.cache.AbImageCache;
import com.ab.global.AbConstant;
import com.ab.util.AbFileUtil;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.EmPlan;
import com.whut.chemistrylab.model.EmPlanCover;
import com.whut.chemistrylab.model.User;


public class ChmApplication extends Application {

	//登录用户
    public User mUser = null;
    public boolean userPasswordRemember = false;
    
    //记录用户当前查询的化学品标识信息
    public ChmIdentity chmIdentity;
    //public Experts experts;
    public EmPlanCover emPlanCover;
    public EmPlan emPlan;
    
	@Override
	public void onCreate() {
		super.onCreate();
		
		initDatabase();
		initLoginParams();
		initImageCache();
		
		emPlanCover=new EmPlanCover();
		//用到时再取出数据
		//emPlanCover = new EmPlanCoverService().getEmPlanCover();
	}
	
	/*
	 * 初始化数据库
	 */
	private void initDatabase() {
		// TODO Auto-generated method stub
		boolean dbOK = checkDatabase();
		
		if (!dbOK) {// 不对就把raw里的数据库写入手机
			try {
				copyDatabase();
				saveDbVersion();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
				
	}

	/**
	 * 判断数据库是否存在且版本正确
	 * @return false or true
	 */
	public boolean checkDatabase() {
		//判断数据库版本是否正确
		boolean okVersion=checkDbVersion();
System.out.println("dbfile version ok:"+okVersion);		
		if(!okVersion) return false;
		
		//判断数据库文件是否存在且能正常操作
		SQLiteDatabase db = null;
		try {
			String databaseFilename = Constant.DATABASE_PATH + Constant.dbName;
			db = SQLiteDatabase.openDatabase(databaseFilename, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		
		return (db != null) ? true : false;
	}

	private boolean checkDbVersion() {
		// TODO Auto-generated method stub
		SharedPreferences  sp = getSharedPreferences(Constant.sharePath, Context.MODE_PRIVATE);
		int dbflieVersion = sp.getInt(Constant.DBVERSIONCOOKIE, 0);
System.out.println("dbflie Version"+dbflieVersion);
		if(dbflieVersion==Constant.dbFileVersion){
			return true;
		}
		return false;
	}

	/**
	 * 复制数据库到手机指定文件夹下
	 * @throws IOException
	 */
	public void copyDatabase() throws IOException {
		String databaseFilename = Constant.DATABASE_PATH + Constant.dbName;
		File dir = new File(Constant.DATABASE_PATH);
		if (!dir.exists())//判断文件夹是否存在，不存在就新建一个
			dir.mkdir();
		File dbFile = new File(databaseFilename);
		if(dbFile.exists())//删除旧数据库文件
			dbFile.delete();

		FileOutputStream os = null;
		InputStream is = null;
		
		try{
			os = new FileOutputStream(databaseFilename);// 得到数据库文件的写入流
			is = this.getResources().openRawResource(R.raw.chemistrylab);// 得到数据库文件的数据流
			
			byte[] buffer = new byte[8192];
			int count = 0;
			try {
				while ((count = is.read(buffer)) > 0) {
					os.write(buffer, 0, count);
					os.flush();
				}
			} catch (IOException e) {
			}
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(NotFoundException e) {
			e.printStackTrace();
		}
				
		
	}
	
	/**
	 * 保存数据库新版本
	 */
	private void saveDbVersion() {
		SharedPreferences  sp = getSharedPreferences(Constant.sharePath, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(Constant.DBVERSIONCOOKIE, Constant.dbFileVersion);
		editor.commit();
	}

	/**
	 * 上次登录参数
	 * @throws 
	 * @date：2012-7-17 下午06:07:29
	 * @version v1.0
	 */
	private void initLoginParams() {
		SharedPreferences  sp = getSharedPreferences(Constant.sharePath, Context.MODE_PRIVATE);
		String userName = sp.getString(Constant.USERNAMECOOKIE, null);
		String userPwd = sp.getString(Constant.USERPASSWORDCOOKIE, null);
		Boolean userPwdRemember = sp.getBoolean(Constant.USERPASSWORDREMEMBERCOOKIE, false);
		if(userName!=null){
			mUser = new User();
			mUser.setName(userName);
			mUser.setPassword(userPwd);
			userPasswordRemember = userPwdRemember;
		}
	}
	
	/**
	 * 
	 * 描述：缓存几张图片
	 * @throws 
	 */
	public void initImageCache(){
		//内存缓存几个默认图片
  		if (AbImageCache.getBitmapFromMemCache(AbConstant.IMAGENOURL) == null) {  
  			Bitmap imageNo = AbFileUtil.getBitmapFormSrc(AbConstant.IMAGENOURL);
  			AbImageCache.addBitmapToMemoryCache(AbConstant.IMAGENOURL, imageNo);
  		}  
  		if (AbImageCache.getBitmapFromMemCache(AbConstant.IMAGELOADINGURL) == null) {  
  			Bitmap imageLoading = AbFileUtil.getBitmapFormSrc(AbConstant.IMAGELOADINGURL);
  			AbImageCache.addBitmapToMemoryCache(AbConstant.IMAGELOADINGURL, imageLoading);
  		}  
  		if (AbImageCache.getBitmapFromMemCache(AbConstant.IMAGEERRORURL) == null) {  
  			Bitmap imageError = AbFileUtil.getBitmapFormSrc(AbConstant.IMAGEERRORURL);
  			AbImageCache.addBitmapToMemoryCache(AbConstant.IMAGEERRORURL, imageError);
  		}
  		
  		//内存缓存危险品图片
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE1_1) == null) {  
  			Bitmap image1_1 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE1_1);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE1_1, image1_1);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE1_2) == null) {  
  			Bitmap image1_2 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE1_2);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE1_2, image1_2);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE1_3) == null) {  
  			Bitmap image1_3 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE1_3);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE1_3, image1_3);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE1_4) == null) {  
  			Bitmap image1_4 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE1_4);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE1_4, image1_4);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE1_5) == null) {  
  			Bitmap image1_5 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE1_5);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE1_5, image1_5);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE2_1) == null) {  
  			Bitmap image2_1 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE2_1);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE2_1, image2_1);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE2_2) == null) {  
  			Bitmap image2_2 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE2_2);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE2_2, image2_2);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE2_3) == null) {  
  			Bitmap image2_3 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE2_3);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE2_3, image2_3);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE3) == null) {  
  			Bitmap image3 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE3);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE3, image3);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE4_1) == null) {  
  			Bitmap image4_1 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE4_1);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE4_1, image4_1);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE4_2) == null) {  
  			Bitmap image4_2 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE4_2);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE4_2, image4_2);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE4_3) == null) {  
  			Bitmap image4_3 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE4_3);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE4_3, image4_3);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE5_1) == null) {  
  			Bitmap image5_1 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE5_1);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE5_1, image5_1);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE5_2) == null) {  
  			Bitmap image5_2 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE5_2);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE5_2, image5_2);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE6_1) == null) {  
  			Bitmap image6_1 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE6_1);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE6_1, image6_1);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE6_2) == null) {  
  			Bitmap image6_2 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE6_2);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE6_2, image6_2);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE7) == null) {  
  			Bitmap image7 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE7);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE7, image7);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE8) == null) {  
  			Bitmap image8 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE8);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE8, image8);
  		}
  		if (AbImageCache.getBitmapFromMemCache(Constant.IMAGEDANGERTYPE9) == null) {  
  			Bitmap image9 = AbFileUtil.getBitmapFormSrc(Constant.IMAGEDANGERTYPE9);
  			AbImageCache.addBitmapToMemoryCache(Constant.IMAGEDANGERTYPE9, image9);
  		}
	}


	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		if(chmIdentity!=null) chmIdentity=null;
		if(emPlanCover!=null) emPlanCover=null;
		if(emPlan!=null) emPlan=null;
		
		super.onTerminate();
	}


}
