package com.whut.chemistrylab.adapter.browse;

import java.util.ArrayList;
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
import com.whut.chemistrylab.dao.EnSenseTargetDao;
import com.whut.chemistrylab.dao.PicturesDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.EmPlan;
import com.whut.chemistrylab.model.DangerDesc;
import com.whut.chemistrylab.model.FireMeans;
import com.whut.chemistrylab.model.LeakEmMeans;
import com.whut.chemistrylab.model.EnSenseTarget;
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
public class FragEmPlanListViewAdapter extends BaseAdapter {

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
	EmPlan emPlan=null;
	List<EnSenseTarget> enSenseTargetList=null;
	EnSenseTargetDao enSenseTargetDao=null;

	
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
	public FragEmPlanListViewAdapter(Context context,
			List<Map<String, Object>> data, int resource, String[] from,
			int[] to) {
		mContext = context;
		mData = data;
		mResource = resource;
		mFrom = from;
		mTo = to;
		queue=AbHttpQueue.getInstance();
		
		enSenseTargetList=new ArrayList<EnSenseTarget>();
		enSenseTargetDao=new EnSenseTargetDao(context);
		
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
				initEmPlan_1Data(holder);
				break;
			case 1:
				initEmPlan_2Data(holder);
				break;
			case 2:
				initEmPlan_3Data(holder);
				break;
			case 3:
				initEmPlan_4Data(holder);
				break;
			case 4:
				initEmPlan_5Data(holder);
				break;
			case 5:
				initEmPlan_6Data(holder);
				break;
			case 6:
				initEmPlan_7Data(holder);
				break;
			case 7:
				initEmPlan_8Data(holder);
				break;
			case 8:
				initEmPlan_9Data(holder);
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

				FragEmPlanListViewAdapter.this.notifyDataSetChanged();

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

	private void initEmPlan_1Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_1View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_1View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getLeakChm());
	}


	private void initEmPlan_2Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_2View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_2View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getChmType());
	}

	private void initEmPlan_3Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_3View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_3View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getLeakAddr());
	}
	
	private void initEmPlan_4Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_4View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_4View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getLeakSrc());
	}
	
	private void initEmPlan_5Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_ensensetarget, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_5View(view,enSenseTargetList);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
				
				if(enSenseTargetList.size()==0){
					enSenseTargetList.add(enSenseTargetDao.getBeanBySenseNoAndTargetNo(emPlan.getEnSenseTarget(), "01"));
					enSenseTargetList.add(enSenseTargetDao.getBeanBySenseNoAndTargetNo(emPlan.getEnSenseTarget(), "02"));
					enSenseTargetList.add(enSenseTargetDao.getBeanBySenseNoAndTargetNo(emPlan.getEnSenseTarget(), "03"));
					enSenseTargetList.add(enSenseTargetDao.getBeanBySenseNoAndTargetNo(emPlan.getEnSenseTarget(), "04"));
					enSenseTargetList.add(enSenseTargetDao.getBeanBySenseNoAndTargetNo(emPlan.getEnSenseTarget(), "05"));
					enSenseTargetList.add(enSenseTargetDao.getBeanBySenseNoAndTargetNo(emPlan.getEnSenseTarget(), "06"));
				}
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_5View(View view, List<EnSenseTarget> enSenseTargetList) {
		// TODO Auto-generated method stub
		TextView tv_min_1=(TextView) view.findViewById(R.id.ensensetarget_min_1);
		TextView tv_max_1=(TextView) view.findViewById(R.id.ensensetarget_max_1);
		TextView tv_min_2=(TextView) view.findViewById(R.id.ensensetarget_min_2);
		TextView tv_max_2=(TextView) view.findViewById(R.id.ensensetarget_max_2);
		TextView tv_min_3=(TextView) view.findViewById(R.id.ensensetarget_min_3);
		TextView tv_max_3=(TextView) view.findViewById(R.id.ensensetarget_max_3);
		TextView tv_min_4=(TextView) view.findViewById(R.id.ensensetarget_min_4);
		TextView tv_max_4=(TextView) view.findViewById(R.id.ensensetarget_max_4);
		TextView tv_min_5=(TextView) view.findViewById(R.id.ensensetarget_min_5);
		TextView tv_max_5=(TextView) view.findViewById(R.id.ensensetarget_max_5);
		TextView tv_min_6=(TextView) view.findViewById(R.id.ensensetarget_min_6);
		TextView tv_max_6=(TextView) view.findViewById(R.id.ensensetarget_max_6);

		tv_min_1.setText(enSenseTargetList.get(0).getArrMinTime());
		tv_min_2.setText(enSenseTargetList.get(1).getArrMinTime());
		tv_min_3.setText(enSenseTargetList.get(2).getArrMinTime());
		tv_min_4.setText(enSenseTargetList.get(3).getArrMinTime());
		tv_min_5.setText(enSenseTargetList.get(4).getArrMinTime());
		tv_min_6.setText(enSenseTargetList.get(5).getArrMinTime());
		tv_max_1.setText(enSenseTargetList.get(0).getArrMaxTime());
		tv_max_2.setText(enSenseTargetList.get(1).getArrMaxTime());
		tv_max_3.setText(enSenseTargetList.get(2).getArrMaxTime());
		tv_max_4.setText(enSenseTargetList.get(3).getArrMaxTime());
		tv_max_5.setText(enSenseTargetList.get(4).getArrMaxTime());
		tv_max_6.setText(enSenseTargetList.get(5).getArrMaxTime());
	
	}
	
	private void initEmPlan_6Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_6View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_6View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getEmDevCall());
	}
	
	private void initEmPlan_7Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_7View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_7View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getShipEmAction());
	}
	
	private void initEmPlan_8Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_8View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_8View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getWaterEmAction());
	}
	
	private void initEmPlan_9Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmPlan_9View(view,emPlan);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//				System.out.println(application.chmIdentity);
				if(emPlan==null)
				emPlan=application.emPlan;
			}
		};
		
		queue.download(item);

	}
	
	private void updateEmPlan_9View(View view, EmPlan chmIdentity) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(chmIdentity.getRespMach());
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