package com.whut.chemistrylab.adapter.emplancover;

import java.util.List;
import java.util.Map;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.whut.chemistrylab.EmPlanCover2_1TabActivity;
import com.whut.chemistrylab.EmPlanCover3_1TabActivity;
import com.whut.chemistrylab.EmPlanCover3_4TabActivity;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.dao.DailySupervisionDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.DailySupervision;
import com.whut.chemistrylab.model.DangerDesc;
import com.whut.chemistrylab.model.EmPlanCover;
import com.whut.chemistrylab.model.FireMeans;
import com.whut.chemistrylab.model.LeakEmMeans;
import com.whut.chemistrylab.model.PhyChmAttr;
import com.whut.chemistrylab.service.EmPlanCoverService;

import android.content.Context;
import android.content.Intent;
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
public class FragEmPlanCover3ListViewAdapter extends BaseAdapter {

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
	EmPlanCoverService emPlanCoverService=null;
	
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
	public FragEmPlanCover3ListViewAdapter(Context context,
			List<Map<String, Object>> data, int resource, String[] from,
			int[] to) {
		mContext = context;
		mData = data;
		mResource = resource;
		mFrom = from;
		mTo = to;
		
		queue=AbHttpQueue.getInstance();
		emPlanCoverService=new EmPlanCoverService();
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
				initCover3_1Data(holder);
				break;
			case 1:
				initCover3_2Data(holder);
				break;
			case 2:
				initCover3_3Data(holder);
				break;
			case 3:
				initCover3_4Data(holder);
				break;
			case 4:
				initCover3_5Data(holder);
				break;
			case 5:
				initCover3_6Data(holder);
				break;
			case 6:
				initCover3_7Data(holder);
				break;
			case 7:
				initCover3_8Data(holder);
				break;
			case 8:
				initCover3_9Data(holder);
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

				FragEmPlanCover3ListViewAdapter.this.notifyDataSetChanged();

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

	private void initCover3_1Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_1View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				
				if(application.emPlanCover.getText3_1()==null || application.emPlanCover.getText3_1().equals(""))
				{
					application.emPlanCover.setText3_1(emPlanCoverService.getEmPlanCover3_1());
				}
			}
		};
		
		queue.download(item);

	}

	protected void updateCover3_1View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_1());
		tview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(mContext,EmPlanCover3_1TabActivity.class);
				mContext.startActivity(it);	
			}
		});
		
	}

	private void initCover3_2Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_2View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();

				if(application.emPlanCover.getText3_2()==null || application.emPlanCover.getText3_2().equals(""))
				{
					application.emPlanCover.setText3_2(emPlanCoverService.getEmPlanCover3_2());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_2View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_2());
	}

	private void initCover3_3Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_3View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();

				if(application.emPlanCover.getText3_3()==null || application.emPlanCover.getText3_3().equals(""))
				{
					application.emPlanCover.setText3_3(emPlanCoverService.getEmPlanCover3_3());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_3View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_3());
	}

	private void initCover3_4Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_4View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				
				if(application.emPlanCover.getText3_4()==null || application.emPlanCover.getText3_4().equals(""))
				{
					application.emPlanCover.setText3_4(emPlanCoverService.getEmPlanCover3_4());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_4View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_4());
		tview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(mContext,EmPlanCover3_4TabActivity.class);
				mContext.startActivity(it);
			}
		});
	}

	private void initCover3_5Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_5View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();

				if(application.emPlanCover.getText3_5()==null || application.emPlanCover.getText3_5().equals(""))
				{
					application.emPlanCover.setText3_5(emPlanCoverService.getEmPlanCover3_5());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_5View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_5());
	}

	private void initCover3_6Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_6View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();

				if(application.emPlanCover.getText3_6()==null || application.emPlanCover.getText3_6().equals(""))
				{
					application.emPlanCover.setText3_6(emPlanCoverService.getEmPlanCover3_6());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_6View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_6());
	}

	private void initCover3_7Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_7View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();

				if(application.emPlanCover.getText3_7()==null || application.emPlanCover.getText3_7().equals(""))
				{
					application.emPlanCover.setText3_7(emPlanCoverService.getEmPlanCover3_7());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_7View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_7());
	}

	private void initCover3_8Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_8View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();

				if(application.emPlanCover.getText3_8()==null || application.emPlanCover.getText3_8().equals(""))
				{
					application.emPlanCover.setText3_8(emPlanCoverService.getEmPlanCover3_8());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_8View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_8());
	}

	private void initCover3_9Data(final ViewHolder holder) {
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
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover3_9View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();

				if(application.emPlanCover.getText3_9()==null || application.emPlanCover.getText3_9().equals(""))
				{
					application.emPlanCover.setText3_9(emPlanCoverService.getEmPlanCover3_9());
				}
			}
		};
		
		queue.download(item);

	}


	protected void updateCover3_9View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText3_9());
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