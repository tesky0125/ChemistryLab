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
import com.whut.chemistrylab.dao.EmGuideDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.EmGuide;

/**
 * Copyright (c) 2011 All rights reserved 名称：MyAdvancedListViewAdapter
 * 描述：ListView自定义Adapter例子
 * 
 * @author zhaoqp
 * @date 2011-11-8
 * @version
 */
public class FragEmGuideListViewAdapter extends BaseAdapter {

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
	EmGuide emGuide=null;
	EmGuideDao emGuideDao=null;
	
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
	public FragEmGuideListViewAdapter(Context context,
			List<Map<String, Object>> data, int resource, String[] from,
			int[] to) {
		mContext = context;
		mData = data;
		mResource = resource;
		mFrom = from;
		mTo = to;
		
		queue=AbHttpQueue.getInstance();
		emGuideDao=new EmGuideDao(context);
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
				initHealthThreatData(holder);
				break;
			case 1:
				initFireExplosionData(holder);
				break;
			case 2:
				initEmActionData(holder);
				break;
			case 3:
				initProtectionData(holder);
				break;
			case 4:
				initSceneEvacuateData(holder);
				break;
			case 5:
				initFireMeansData(holder);
				break;
			case 6:
				initLeakData(holder);
				break;
			case 7:
				initEmergencyData(holder);
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

				FragEmGuideListViewAdapter.this.notifyDataSetChanged();

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

	private void initHealthThreatData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateHealthThreatView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//										System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
//System.out.println(emGuide);
			}
		};
		
		queue.download(item);

	}

	protected void updateHealthThreatView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getHealthThreat());
//System.out.println(emGuide.getHealthThreat());
	}

	private void initFireExplosionData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateFireExplosionView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//												System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);

	}

	protected void updateFireExplosionView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getFireExplosion());
	}

	private void initEmActionData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmActionView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//												System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);
	}

	protected void updateEmActionView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getEmAction());
	}

	private void initProtectionData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		layout.addView(view);
		
		//设置数据
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateProtectionView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//														System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);
	}

	protected void updateProtectionView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getProtection());
	}

	private void initSceneEvacuateData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		layout.addView(view);
		
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateSceneEvacuateView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//														System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);
	}
	
	protected void updateSceneEvacuateView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getSceneEvacuate());
//System.out.println(emGuide.getSceneEvacuate());
	}

	private void initFireMeansData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		
		layout.addView(view);
		
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateFireMeansView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//														System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);
	}
	
	protected void updateFireMeansView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getFireMeans());
//System.out.println(emGuide.getFireMeans());
	}

	private void initLeakData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		
		layout.addView(view);
		
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateLeakView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//														System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);
	}
	
	protected void updateLeakView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getLeak());
//System.out.println(emGuide.getLeak());
	}

	private void initEmergencyData(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_emguide, null, false);
		
		
		layout.addView(view);
		
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateEmergencyView(view,emGuide);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
//														System.out.println(application.chmIdentity);
				if(emGuide==null)
					emGuide=emGuideDao.getBeanByUnNo(application.chmIdentity.getUnNo());
			}
		};
		
		queue.download(item);
	}

	protected void updateEmergencyView(View view, EmGuide emGuide) {
		// TODO Auto-generated method stub
		if(emGuide==null) return;
		
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emGuide.getEmergency());
//System.out.println(emGuide.getEmergency());		
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