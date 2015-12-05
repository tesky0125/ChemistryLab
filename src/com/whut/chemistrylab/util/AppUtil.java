package com.whut.chemistrylab.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {
	
	public static String getTextFormPath(String path, String encode){
		InputStream is = AppUtil.class.getClassLoader().getResourceAsStream(path);//相对于CLASSPATH
		InputStreamReader reader = null;
		try {
			//Windows新建文本默认编码GBK
			//JVM默认编码GBK
			//AVM默认编码UTF_8
			//reader = new InputStreamReader(is,"GBK");
			//reader = new InputStreamReader(is,"UTF-8");
			reader = new InputStreamReader(is,encode);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(reader);
		String text = "";
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				text += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//System.out.print(text);
		return text;
	}

	public static boolean isPhoneNumberValid(String phoneNumber){  
		if(phoneNumber==null || phoneNumber.equals("")) return false;
		
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNumber);

		return m.matches();
    } 
}
