package com.whut.chemistrylab.adapter.lookup;

import java.util.List;
import java.util.Map;

import android.content.Context;
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

import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.dao.EmMeansDao;
import com.whut.chemistrylab.dao.FireMeansDao;
import com.whut.chemistrylab.dao.LeakDistanceDao;
import com.whut.chemistrylab.dao.LeakEmMeansDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.EmMeans;
import com.whut.chemistrylab.model.FireMeans;
import com.whut.chemistrylab.model.LeakDistance;
import com.whut.chemistrylab.model.LeakEmMeans;

/**
 * Copyright (c) 2011 All rights reserved 名称：MyAdvancedListViewAdapter
 * 描述：ListView自定义Adapter例子
 * 
 * @author zhaoqp
 * @date 2011-11-8
 * @version
 */
public class FragSceneEmMeansListViewAdapter extends BaseAdapter {

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
	FireMeans fireMeans=null;
	LeakEmMeans leakEmMeans=null;
	LeakDistance leakDistance=null;
	EmMeans emMeans=null;
	FireMeansDao fireMeansDao=null;
	LeakEmMeansDao leakEmMeansDao=null;
	LeakDistanceDao leakDistanceDao=null;
	EmMeansDao emMeansDao=null;
	
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
	public FragSceneEmMeansListViewAdapter(Context context,
			List<Map<String, Object>> data, int resource, String[] from,
			int[] to) {
		mContext = context;
		mData = data;
		mResource = resource;
		mFrom = from;
		mTo = to;
		
		queue=AbHttpQueue.getInstance();
		fireMeansDao=new FireMeansDao(context);
		leakEmMeansDao=new LeakEmMeansDao(context);
		leakDistanceDao=new LeakDistanceDao(context);
		emMeansDao=new EmMeansDao(context);
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
				initFireMeansData(holder);
				break;
			case 1:
				initLeakEmMeansData(holder);
				break;
			case 2:
				initLeakDistanceData(holder);
				break;
			case 3:
				initEmMeansData(holder);
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

				FragSceneEmMeansListViewAdapter.this.notifyDataSetChanged();

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

	private void initFireMeansData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_prop_firemeans, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateFireMeansView(view,fireMeans);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//						System.out.println(application.chmIdentity);
				if(fireMeans==null)
//				fireMeans=fireMeansDao.getBeanByUnNo(application.chmIdentity.getUnNo());
				fireMeans=fireMeansDao.getBeanByID(application.chmIdentity.getId());
			}
		};
		
		queue.download(item);
	}

	protected void updateFireMeansView(View view, FireMeans fireMeans) {
		// TODO Auto-generated method stub
		TextView tv1=(TextView) view.findViewById(R.id.prop_firemeans_first);
		TextView tv2=(TextView) view.findViewById(R.id.prop_firemeans_second);
		TextView tv3=(TextView) view.findViewById(R.id.prop_firemeans_third);
		
		tv1.setText(fireMeans.getDangerAttr());
		tv2.setText(fireMeans.getPestCombustion());
		tv3.setText(fireMeans.getPutFireMean());
	}

	private void initLeakEmMeansData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LeakEmMeans chmIdentity = new LeakEmMeans();
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emmeans, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateLeakEmMeansView(view,leakEmMeans);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//								System.out.println(application.chmIdentity);
				if(leakEmMeans==null)
//					leakEmMeans=leakEmMeansDao.getBeanByUnNo(application.chmIdentity.getUnNo());
				leakEmMeans=leakEmMeansDao.getBeanByID(application.chmIdentity.getId());
			}
		};
		
		queue.download(item);
	}

	protected void updateLeakEmMeansView(View view, LeakEmMeans leakEmMeans) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(leakEmMeans.getEmMeans());
	}

	private void initLeakDistanceData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LeakEmMeans chmIdentity = new LeakEmMeans();
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_prop_leakdistance, null, false);
		
		layout.addView(view);
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateLeakDistanceView(view,leakDistance);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//										System.out.println(application.chmIdentity);
				if(leakDistance==null)
					leakDistance=leakDistanceDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);
	}

	protected void updateLeakDistanceView(View view, LeakDistance leakDistance) {
		// TODO Auto-generated method stub
		if(leakDistance==null) return;
		
		TextView tv1=(TextView) view.findViewById(R.id.prop_leakemmeans_third);
		TextView tv2=(TextView) view.findViewById(R.id.prop_leakemmeans_fourth);
		TextView tv3=(TextView) view.findViewById(R.id.prop_leakemmeans_fifth);
		TextView tv4=(TextView) view.findViewById(R.id.prop_leakemmeans_seventh);
		TextView tv5=(TextView) view.findViewById(R.id.prop_leakemmeans_eighth);
		TextView tv6=(TextView) view.findViewById(R.id.prop_leakemmeans_ninth);
		
		tv1.setText(leakDistance.getLittleInitLeakDist());
		tv2.setText(leakDistance.getLittleDefendDistDay());
		tv3.setText(leakDistance.getLittleDefendDistNight());
		tv4.setText(leakDistance.getMuchInitLeakDist());
		tv5.setText(leakDistance.getMuchDefendDistDay());
		tv6.setText(leakDistance.getMuchDefendDistNight());
	}

	private void initEmMeansData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LeakEmMeans chmIdentity = new LeakEmMeans();
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_prop_emmeans, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmMeansView(view,emMeans);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//										System.out.println(application.chmIdentity);
				if(emMeans==null)
//					emMeans=emMeansDao.getBeanByUnNo(application.chmIdentity.getUnNo());
				emMeans=emMeansDao.getBeanByID(application.chmIdentity.getId());
			}
		};
		
		queue.download(item);
		
	}

	protected void updateEmMeansView(View view, EmMeans emMeans) {
		// TODO Auto-generated method stub
		TextView tv1=(TextView) view.findViewById(R.id.prop_emmeans_first);
		TextView tv2=(TextView) view.findViewById(R.id.prop_emmeans_second);
		TextView tv3=(TextView) view.findViewById(R.id.prop_emmeans_third);
		TextView tv4=(TextView) view.findViewById(R.id.prop_emmeans_fourth);
		
		tv1.setText(emMeans.getSkinTouch());
		tv2.setText(emMeans.getEyeTouch());
		tv3.setText(emMeans.getSuction());
		tv4.setText(emMeans.getIngestion());
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