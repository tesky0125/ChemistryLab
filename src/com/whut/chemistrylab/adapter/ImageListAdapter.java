package com.whut.chemistrylab.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.cache.AbImageCache;
import com.ab.global.AbConstant;
import com.ab.net.AbImageDownloadCallback;
import com.ab.net.AbImageDownloadItem;
import com.ab.net.AbImageDownloadQueue;
import com.ab.util.AbStrUtil;
import com.whut.chemistrylab.global.Constant;
/**
 * Copyright (c) 2011 All rights reserved
 * ���ƣ�ImageListAdapter
 * ��������Adapter���ͷ�Bitmap
 * @author zhaoqp
 * @date 2011-12-10
 * @version
 */
public class ImageListAdapter extends BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xmlתView����
    private LayoutInflater mInflater;
    //���еĲ���
    private int mResource;
    //�б�չ�ֵ�����
    private List mData;
    //Map�е�key
    private String[] mFrom;
    //view��id
    private int[] mTo;
    private AbImageDownloadQueue mAbImageDownloadQueue = null;
    
   /**
    * ���췽��
    * @param context
    * @param data �б�չ�ֵ�����
    * @param resource ���еĲ���
    * @param from Map�е�key
    * @param to view��id
    */
    public ImageListAdapter(Context context, List data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //���ڽ�xmlתΪView
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mAbImageDownloadQueue = AbImageDownloadQueue.getInstance();
    }   
    
    @Override
    public int getCount() {
        return mData.size();
    }
    
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
      return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	  final ViewHolder holder;
          if(convertView == null){
	           //ʹ���Զ����list_items��ΪLayout
	           convertView = mInflater.inflate(mResource, parent, false);
	           //����findView�Ĵ���
			   holder = new ViewHolder();
	           //��ʼ�������е�Ԫ��
			   holder.itemsIcon = ((ImageView) convertView.findViewById(mTo[0])) ;
			   holder.itemsTitle = ((TextView) convertView.findViewById(mTo[1]));
			   holder.itemsText = ((TextView) convertView.findViewById(mTo[2]));
			   convertView.setTag(holder);
          }else{
        	   holder = (ViewHolder) convertView.getTag();
          }
          
		  //��ȡ���е�����
          final Map<String, Object>  dataSet = (Map<String, Object>)mData.get(position);
          if (dataSet == null) {
              return null;
          }
          Integer _id=(Integer)dataSet.get(mFrom[0]);
          String imageUrl = (String)dataSet.get(mFrom[1]);
          Object title = dataSet.get(mFrom[2]);
          if(title==null) title="";
          Object text = dataSet.get(mFrom[3]); 
          if(text==null) text="";
          
          holder.itemsTitle.setText(title.toString());
          holder.itemsText.setText(text.toString());
          
          if(!AbStrUtil.isEmpty(imageUrl)){
        	  //���ڴ滺���л�ȡͼƬ������Ҫ����ᵼ��ҳ������
        	  Bitmap bitmap = AbImageCache.getBitmapFromMemCache(imageUrl);
        	  
//System.out.println(imageUrl+":���ص�Bitmap��"+bitmap);
        	 
        	  //�ڴ滺����û�У����SD�������ȡ�����SD����û�У������url�������ȡ
        	  if(bitmap == null){
	        	  //���������� 
	              AbImageDownloadItem item = new AbImageDownloadItem(); 
	    	      //������ʾ�Ĵ�С
	    	      item.width = 80;
	    	      item.height = 80;
	    	      //����Ϊ����
	    	      item.type = AbConstant.SCALEIMG;
	    	      item.imageUrl = imageUrl;
	              holder.itemsIcon.setImageBitmap(AbImageCache.getBitmapFromMemCache(AbConstant.IMAGELOADINGURL));
	    	      //������ɺ���½���
	    	      item.callback = new AbImageDownloadCallback() { 
	    	            @Override 
	    	            public void update(Bitmap bitmap, String imageUrl) { 
	    	            	if(bitmap!=null){
	    	            		holder.itemsIcon.setImageBitmap(bitmap); 
	    	            	}else{
	    	            		holder.itemsIcon.setImageBitmap(AbImageCache.getBitmapFromMemCache(AbConstant.IMAGEERRORURL));
	    	            	}
	    	            } 
	    	      }; 
	    	      mAbImageDownloadQueue.download(item); 
        	  }else{
        		  //���ڴ滺����ȡ��ֱ����ʾ
    			  holder.itemsIcon.setImageBitmap(bitmap);
        	  }
          }else{
        	  holder.itemsIcon.setImageBitmap(AbImageCache.getBitmapFromMemCache(AbConstant.IMAGENOURL));
          }
          
          return convertView;
    }
    
    /**
	 * ViewԪ��
	 */
	static class ViewHolder {
		ImageView itemsIcon;
		TextView itemsTitle;
		TextView itemsText;
	}
    
}