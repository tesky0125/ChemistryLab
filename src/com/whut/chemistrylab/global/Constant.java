package com.whut.chemistrylab.global;

public class Constant {
	
	public static final boolean DEBUG = true;
	
	public static final int pageSize = 30;
	
	public static final int dbFileVersion=4;//���ݿ��ļ��汾��ָraw�е��ļ��Ƿ����
	
	public static final String dbName="chemistrylab.db";
	public static final String DATABASE_PATH="/data/data/com.whut.chemistrylab/databases/";
	
	public static final String sharePath = "chemistry_lab_share";
    public static final String USERSID = "user";
    //cookies
    public static final String DBVERSIONCOOKIE = "dbVersion";
    public static final String USERNAMECOOKIE = "cookieName";
    public static final String USERPASSWORDCOOKIE = "cookiePassword";
    public static final String USERPASSWORDREMEMBERCOOKIE = "cookieRemember";
    
	public static final String imagePath = "/image/";
	public static final String imageFormat = ".png";
	
	//19��ͼ
    public static final String IMAGEDANGERTYPE1_1 = imagePath+"1.1"+imageFormat;
    public static final String IMAGEDANGERTYPE1_2 = imagePath+"1.2"+imageFormat;
    public static final String IMAGEDANGERTYPE1_3 = imagePath+"1.3"+imageFormat;
    public static final String IMAGEDANGERTYPE1_4 = imagePath+"1.4"+imageFormat;
    public static final String IMAGEDANGERTYPE1_5 = imagePath+"1.5"+imageFormat;
    public static final String IMAGEDANGERTYPE2_1 = imagePath+"2.1"+imageFormat;
    public static final String IMAGEDANGERTYPE2_2 = imagePath+"2.2"+imageFormat;
    public static final String IMAGEDANGERTYPE2_3 = imagePath+"2.3"+imageFormat;
    public static final String IMAGEDANGERTYPE3 = imagePath+"3"+imageFormat;
    public static final String IMAGEDANGERTYPE4_1 = imagePath+"4.1"+imageFormat;
    public static final String IMAGEDANGERTYPE4_2 = imagePath+"4.2"+imageFormat;
    public static final String IMAGEDANGERTYPE4_3 = imagePath+"4.3"+imageFormat;
    public static final String IMAGEDANGERTYPE5_1 = imagePath+"5.1"+imageFormat;
    public static final String IMAGEDANGERTYPE5_2 = imagePath+"5.2"+imageFormat;
    public static final String IMAGEDANGERTYPE6_1 = imagePath+"6.1"+imageFormat;
    public static final String IMAGEDANGERTYPE6_2 = imagePath+"6.2"+imageFormat;
    public static final String IMAGEDANGERTYPE7 = imagePath+"7"+imageFormat;
    public static final String IMAGEDANGERTYPE8 = imagePath+"8"+imageFormat;
    public static final String IMAGEDANGERTYPE9 = imagePath+"9"+imageFormat;
    
    // ���ӳ�ʱ
 	public static final int timeOut = 12000;
 	// ��������
 	public static final int connectOut = 12000;
 	// ��ȡ����
 	public static final int getOut = 60000;
 	
 	//1��ʾ���������
 	public static final int downloadComplete = 1;
 	//1��ʾδ��ʼ����
 	public static final int undownLoad = 0;
 	//2��ʾ�ѿ�ʼ����
 	public static final int downInProgress = 2;
 	//3��ʾ������ͣ
 	public static final int downLoadPause = 3;
 	
}
