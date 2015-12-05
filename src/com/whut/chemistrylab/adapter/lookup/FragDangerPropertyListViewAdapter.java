package com.whut.chemistrylab.adapter.lookup;

import java.util.List;
import java.util.Map;

import com.ab.activity.AbActivity;
import com.ab.cache.AbImageCache;
import com.ab.global.AbConstant;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.ab.net.AbImageDownloadCallback;
import com.ab.net.AbImageDownloadItem;
import com.ab.net.AbImageDownloadQueue;
import com.ab.util.AbStrUtil;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.dao.DangerDescDao;
import com.whut.chemistrylab.dao.PhyChmAttrDao;
import com.whut.chemistrylab.dao.PicturesDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.DangerDesc;
import com.whut.chemistrylab.model.FireMeans;
import com.whut.chemistrylab.model.LeakEmMeans;
import com.whut.chemistrylab.model.PhyChmAttr;
import com.whut.chemistrylab.model.Pictures;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Copyright (c) 2011 All rights reserved 名称：MyAdvancedListViewAdapter
 * 描述：ListView自定义Adapter例子
 * 
 * @author zhaoqp
 * @date 2011-11-8
 * @version
 */
public class FragDangerPropertyListViewAdapter extends BaseAdapter {

	private Context mContext;
	// 单行的布局
	private int mResource;
	// 列表展现的数据
	private List<Map<String, Object>> mData;
	// Map中的key
	private String[] mFrom;
	// view的id
	private int[] mTo;

	private AbHttpQueue queue;
	ChmIdentity chmIdentity=null;
	PhyChmAttr phyChmAttr=null;
	DangerDesc dangerDesc = null;
	PhyChmAttrDao phyChmAttrDao=null;
	DangerDescDao dangerDescDao=null;
	
	private AbImageDownloadQueue mAbImageDownloadQueue = null;
	Pictures mainDangerTypePic=null;
	Pictures viceDangerTypePic=null;
	PicturesDao picturesDao=null;
	
	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param data
	 *            列表展现的数据
	 * @param resource
	 *            单行的布局
	 * @param from
	 *            Map中的key
	 * @param to
	 *            view的id
	 */
	public FragDangerPropertyListViewAdapter(Context context,
			List<Map<String, Object>> data, int resource, String[] from,
			int[] to) {
		mContext = context;
		mData = data;
		mResource = resource;
		mFrom = from;
		mTo = to;
		queue=AbHttpQueue.getInstance();
		
		phyChmAttrDao=new PhyChmAttrDao(context);
		dangerDescDao=new DangerDescDao(context);
		
		mAbImageDownloadQueue = AbImageDownloadQueue.getInstance();
		picturesDao=new PicturesDao(context);
		
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
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			// 使用自定义的list_items作为Layout
			convertView = LayoutInflater.from(mContext).inflate(mResource,
					parent, false);
			// 使用减少findView的次数
			holder = new ViewHolder();
			holder.itemsIcon = ((ImageView) convertView.findViewById(mTo[0]));
			holder.itemsTitle = ((TextView) convertView.findViewById(mTo[1]));
			holder.itemsExpand = ((ImageView) convertView.findViewById(mTo[2]));
			holder.itemsLayoutExpand = ((RelativeLayout) convertView
					.findViewById(mTo[3]));

			// 设置标记
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置layout数据
		final Map<String, Object> dataSet = mData.get(position);
		if (dataSet == null) {
			return null;
		}
		// 获取该行数据
		final Integer imgId = (Integer) dataSet.get(mFrom[0]);
		String title = (String) dataSet.get(mFrom[1]);
		if(title==null) title="";
		final Boolean isExpand = (Boolean) dataSet.get(mFrom[2]);
		// 设置数据到View
		holder.itemsIcon.setImageResource(imgId);
		holder.itemsTitle.setText(title);
		if (!isExpand) {
			hideExpandLayout(holder);
		} else {
			showExpandLayout(holder);
			//设置expand layout数据
			switch (position) {
			case 0:
				initChmIdentityData(holder);
				break;
			case 1:
				initPhyChmAttrData(holder);
				break;
			case 2:
				initDangerDescData(holder);
				break;
			case 3:
				initDangerTypePicData(holder);
				break;
			}
		}

		holder.itemsExpand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// 更改显示设置
				dataSet.put(mFrom[2], !isExpand);
				mData.set(position, dataSet);

				FragDangerPropertyListViewAdapter.this.notifyDataSetChanged();

			}

		});

		return convertView;
	}


	private void showExpandLayout(final ViewHolder holder) {
		holder.itemsExpand
				.setBackgroundResource(R.drawable.expander_close_holo_dark);
		holder.itemsLayoutExpand.setVisibility(View.VISIBLE);
	}

	private void hideExpandLayout(final ViewHolder holder) {
		holder.itemsExpand
				.setBackgroundResource(R.drawable.expander_open_holo_dark);
		holder.itemsLayoutExpand.setVisibility(View.GONE);
	}

	private void initChmIdentityData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_prop_identity, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateChmIdentityView(view,chmIdentity);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(chmIdentity==null)
				chmIdentity=application.chmIdentity;
			}
		};
		
		queue.download(item);

	}
	
	private void updateChmIdentityView(View view, ChmIdentity chmIdentity) {
		// TODO Auto-generated method stub
		TextView tv1=(TextView) view.findViewById(R.id.prop_identity_first);
		TextView tv2=(TextView) view.findViewById(R.id.prop_identity_second);
		TextView tv3=(TextView) view.findViewById(R.id.prop_identity_third);
		TextView tv4=(TextView) view.findViewById(R.id.prop_identity_fourth);
		TextView tv5=(TextView) view.findViewById(R.id.prop_identity_fifth);
		TextView tv6=(TextView) view.findViewById(R.id.prop_identity_sixth);
		TextView tv7=(TextView) view.findViewById(R.id.prop_identity_seventh);
		TextView tv8=(TextView) view.findViewById(R.id.prop_identity_eighth);
		TextView tv9=(TextView) view.findViewById(R.id.prop_identity_ninth);
		TextView tv10=(TextView) view.findViewById(R.id.prop_identity_tenth);
		
		tv1.setText(chmIdentity.getChName());
		tv2.setText(chmIdentity.getNickChName());
		tv3.setText(chmIdentity.getEnName());
		tv4.setText(chmIdentity.getNickEnName());
		tv5.setText(chmIdentity.getCasNo());
		tv6.setText(chmIdentity.getUnNo());
		tv7.setText(chmIdentity.getEmCnNo());
		tv8.setText(chmIdentity.getRtecsNo());
		tv9.setText(chmIdentity.getIcscNo());
		tv10.setText(chmIdentity.getEcNo());
	}


	private void initPhyChmAttrData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_prop_phychmattr, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updatePhyChmAttrView(view,phyChmAttr);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(phyChmAttr==null)
//				phyChmAttr=phyChmAttrDao.getBeanByUnNo(application.chmIdentity.getUnNo());
				phyChmAttr=phyChmAttrDao.getBeanByID(application.chmIdentity.getId());
//				System.out.println(phyChmAttr);
			}
		};
		
		queue.download(item);

	}

	protected void updatePhyChmAttrView(View view, PhyChmAttr phyChmAttr) {
		// TODO Auto-generated method stub
		TextView tv1=(TextView) view.findViewById(R.id.prop_phychemattr_first);
		TextView tv2=(TextView) view.findViewById(R.id.prop_phychemattr_second);
		TextView tv3=(TextView) view.findViewById(R.id.prop_phychemattr_third);
		TextView tv4=(TextView) view.findViewById(R.id.prop_phychemattr_fourth);
		TextView tv5=(TextView) view.findViewById(R.id.prop_phychemattr_fifth);
		TextView tv6=(TextView) view.findViewById(R.id.prop_phychemattr_sixth);
		TextView tv7=(TextView) view.findViewById(R.id.prop_phychemattr_seventh);
		TextView tv8=(TextView) view.findViewById(R.id.prop_phychemattr_eighth);
		TextView tv9=(TextView) view.findViewById(R.id.prop_phychemattr_ninth);
		TextView tv10=(TextView) view.findViewById(R.id.prop_phychemattr_tenth);
		TextView tv11=(TextView) view.findViewById(R.id.prop_phychemattr_ten_first);
		TextView tv12=(TextView) view.findViewById(R.id.prop_phychemattr_ten_second);
		TextView tv13=(TextView) view.findViewById(R.id.prop_phychemattr_ten_third);
		TextView tv14=(TextView) view.findViewById(R.id.prop_phychemattr_ten_fourth);
		TextView tv15=(TextView) view.findViewById(R.id.prop_phychemattr_ten_fifth);
		TextView tv16=(TextView) view.findViewById(R.id.prop_phychemattr_ten_sixth);
		TextView tv17=(TextView) view.findViewById(R.id.prop_phychemattr_ten_seventh);
		TextView tv18=(TextView) view.findViewById(R.id.prop_phychemattr_ten_eighth);
		TextView tv19=(TextView) view.findViewById(R.id.prop_phychemattr_ten_ninth);
		TextView tv20=(TextView) view.findViewById(R.id.prop_phychemattr_twentith);
		TextView tv21=(TextView) view.findViewById(R.id.prop_phychemattr_twenty_first);
		TextView tv22=(TextView) view.findViewById(R.id.prop_phychemattr_twenty_second);
		
		tv1.setText(phyChmAttr.getAppearence());
		tv2.setText(phyChmAttr.getMolecularFormula());
		tv3.setText(phyChmAttr.getMolecularWeigth());
		tv4.setText(phyChmAttr.getPh());
		tv5.setText(phyChmAttr.getMeltPoint());
		tv6.setText(phyChmAttr.getRelativeDensity());
		tv7.setText(phyChmAttr.getBoilPoint());
		tv8.setText(phyChmAttr.getSolubility());
		tv9.setText(phyChmAttr.getSteamDensity());
		tv10.setText(phyChmAttr.getBasis());
		tv11.setText(phyChmAttr.getSaturatVaporPress());
		tv12.setText(phyChmAttr.getCombustionHeat());
		tv13.setText(phyChmAttr.getCriticalTemper());
		tv14.setText(phyChmAttr.getCriticalPress());
		tv15.setText(phyChmAttr.getAlcholWaterRadio());
		tv16.setText(phyChmAttr.getFlashPoint());
		tv17.setText(phyChmAttr.getExplodeMaxLimit());
		tv18.setText(phyChmAttr.getExplodeMinLimit());
		tv19.setText(phyChmAttr.getIgniteTemper());
		tv20.setText(phyChmAttr.getDissolveAttr());
		tv21.setText(phyChmAttr.getOtherAtrr());
		tv22.setText(phyChmAttr.getMainUse());
	}

	private void initDangerDescData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_prop_dangerdesc, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateDangerDescView(view,dangerDesc);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(dangerDesc==null)
//				dangerDesc=dangerDescDao.getBeanByUnNo(application.chmIdentity.getUnNo());
				dangerDesc=dangerDescDao.getBeanByID(application.chmIdentity.getId());
//				System.out.println(dangerDesc);
			}
		};
		
		queue.download(item);
		
	}

	protected void updateDangerDescView(View view, DangerDesc dangerDesc) {
		// TODO Auto-generated method stub
		TextView tv1=(TextView) view.findViewById(R.id.prop_dangerdesc_first);
		TextView tv2=(TextView) view.findViewById(R.id.prop_dangerdesc_second);
		TextView tv3=(TextView) view.findViewById(R.id.prop_dangerdesc_third);
		TextView tv4=(TextView) view.findViewById(R.id.prop_dangerdesc_fourth);
		TextView tv5=(TextView) view.findViewById(R.id.prop_dangerdesc_fifth);
		TextView tv6=(TextView) view.findViewById(R.id.prop_dangerdesc_sixth);
		
		tv1.setText(dangerDesc.getMainDangerType());
		tv2.setText(dangerDesc.getViceDangerType());
		tv3.setText(dangerDesc.getInvadeWay());
		tv4.setText(dangerDesc.getHealthThreat());
		tv5.setText(dangerDesc.getEnvirThreat());
		tv6.setText(dangerDesc.getExplosionDanger());
	}

	private void initDangerTypePicData(ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_prop_dangertypepic, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateDangerTypePicView(view,mainDangerTypePic,viceDangerTypePic);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//System.out.println(application.chmIdentity);
				if(mainDangerTypePic==null)
					mainDangerTypePic=picturesDao.getMainDangerTypeByUnNo(application.chmIdentity.getUnNo());
				if(viceDangerTypePic==null)
					viceDangerTypePic=picturesDao.getViceDangerTypeByUnNo(application.chmIdentity.getUnNo());
//System.out.println(dangerDesc);
			}
		};
		
		queue.download(item);
		
	}

	protected void updateDangerTypePicView(View view,
			Pictures mainDangerTypePic, Pictures viceDangerTypePic) {
		// TODO Auto-generated method stub
		ImageView iv1=(ImageView) view.findViewById(R.id.prop_dangertypepic_first);
		ImageView iv2=(ImageView) view.findViewById(R.id.prop_dangertypepic_second);
		
		updateDangerTypePicViewBackground(iv1,(mainDangerTypePic==null)?null:mainDangerTypePic.toString());
		updateDangerTypePicViewBackground(iv2,(viceDangerTypePic==null)?null:viceDangerTypePic.toString());
		
	}

	private void updateDangerTypePicViewBackground(final ImageView iv, String imageUrl) {
		// TODO Auto-generated method stub
		if(!AbStrUtil.isEmpty(imageUrl)){
	      	  //从内存缓存中获取图片，很重要否则会导致页面闪动
	      	  Bitmap bitmap = AbImageCache.getBitmapFromMemCache(imageUrl);
	      	  
//System.out.println(imageUrl+":返回的Bitmap："+bitmap);
	      	 
	      	  //内存缓存中没有，则从SD卡缓存获取，如果SD缓存没有，则根据url从网络获取
	      	  if(bitmap == null){
		        	  //设置下载项 
		              AbImageDownloadItem item = new AbImageDownloadItem(); 
		    	      //设置显示的大小
		    	      item.width = 80;
		    	      item.height = 80;
		    	      //设置为缩放
		    	      item.type = AbConstant.SCALEIMG;
		    	      item.imageUrl = imageUrl;
		              iv.setImageBitmap(AbImageCache.getBitmapFromMemCache(AbConstant.IMAGELOADINGURL));
		    	      //下载完成后更新界面
		    	      item.callback = new AbImageDownloadCallback() { 
		    	            @Override 
		    	            public void update(Bitmap bitmap, String imageUrl) { 
		    	            	if(bitmap!=null){
		    	            		iv.setImageBitmap(bitmap); 
		    	            	}else{
		    	            		iv.setImageBitmap(AbImageCache.getBitmapFromMemCache(AbConstant.IMAGEERRORURL));
		    	            	}
		    	            } 
		    	      }; 
		    	      mAbImageDownloadQueue.download(item); 
	      	  }else{
	      		  //从内存缓存中取出直接显示
	  			  iv.setImageBitmap(bitmap);
	      	  }
	        }else{
	      	  iv.setImageBitmap(AbImageCache.getBitmapFromMemCache(AbConstant.IMAGENOURL));
	        }
	}

	/**
	 * ViewHolder类
	 */
	static class ViewHolder {
		ImageView itemsIcon;
		TextView itemsTitle;
		ImageView itemsExpand;
		RelativeLayout itemsLayoutExpand;
	}
}