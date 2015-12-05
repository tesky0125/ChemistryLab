package com.whut.chemistrylab.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ab.view.sliding.AbSlidingButton;

/**
 * Copyright (c) 2011 All rights reserved 
 * ���ƣ�CheckListViewAdapter 
 * ������ListView�Զ���Adapter����
 * @author zhaoqp
 * @date 2011-11-8
 * @version
 */
public class CheckListViewAdapter extends BaseAdapter{
  
	private Context mContext;
    //���еĲ���
    private int mResource;
    //�б�չ�ֵ�����
    private List<? extends Map<String, ?>> mData;
    //Map�е�key
    private String[] mFrom;
    //view��id
    private int[] mTo;
    
   /**
    * ���췽��
    * @param context
    * @param data �б�չ�ֵ�����
    * @param resource ���еĲ���
    * @param from Map�е�key
    * @param to view��id
    */
    public CheckListViewAdapter(Context context, List<? extends Map<String, ?>> data,
            int resource, String[] from, int[] to){
    	 mContext = context;
    	 mData = data;
         mResource = resource;
         mFrom = from;
         mTo = to;
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
	          convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
	          //ʹ�ü���findView�Ĵ���
			  holder = new ViewHolder();
			  holder.itemsTitle = ((TextView) convertView.findViewById(mTo[0]));
			  holder.itemsText = ((TextView) convertView.findViewById(mTo[1]));
			  holder.itemsCheck = ((AbSlidingButton) convertView.findViewById(mTo[2])) ;
			  //���ñ��
			  convertView.setTag(holder);
          }else{
        	  holder = (ViewHolder) convertView.getTag();
          }
	      //��������
          final Map<String, ?> dataSet = mData.get(position);
          if (dataSet == null) {
              return null;
          }
          //��ȡ��������
          final Object data0 = dataSet.get(mFrom[0]);
          final Object data1 = dataSet.get(mFrom[1]);
          final Object data2 = dataSet.get(mFrom[2]);
          //�������ݵ�View
          holder.itemsTitle.setText(data0.toString());
          holder.itemsText.setText(data1.toString());
          holder.itemsCheck.setChecked((Boolean)data2, false, false);
          return convertView;
    }
    
    /**
	 * ViewHolder��
	 */
	static class ViewHolder {
		TextView itemsTitle;
		TextView itemsText;
		AbSlidingButton itemsCheck;
	}
}