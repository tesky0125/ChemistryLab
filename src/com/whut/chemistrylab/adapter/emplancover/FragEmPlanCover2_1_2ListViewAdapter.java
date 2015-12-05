package com.whut.chemistrylab.adapter.emplancover;

import java.util.List;
import java.util.Map;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
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
 * Copyright (c) 2011 All rights reserved ���ƣ�MyAdvancedListViewAdapter
 * ������ListView�Զ���Adapter����
 * 
 * @author zhaoqp
 * @date 2011-11-8
 * @version
 */
public class FragEmPlanCover2_1_2ListViewAdapter extends BaseAdapter {

	private Context mContext;
	// ���еĲ���
	private int mResource;
	// �б�չ�ֵ�����
	private List<Map<String, Object>> mData;
	// Map�е�key
	private String[] mFrom;
	// view��id
	private int[] mTo;

	private AbHttpQueue queue;
	EmPlanCoverService emPlanCoverService=null;
	
	/**
	 * ���췽��
	 * 
	 * @param context
	 * @param data
	 *            �б�չ�ֵ�����
	 * @param resource
	 *            ���еĲ���
	 * @param from
	 *            Map�е�key
	 * @param to
	 *            view��id
	 */
	public FragEmPlanCover2_1_2ListViewAdapter(Context context,
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
			// ʹ���Զ����list_items��ΪLayout
			convertView = LayoutInflater.from(mContext).inflate(mResource,
					parent, false);
			// ʹ�ü���findView�Ĵ���
			holder = new ViewHolder();
			holder.itemsIcon = ((ImageView) convertView.findViewById(mTo[0]));
			holder.itemsTitle = ((TextView) convertView.findViewById(mTo[1]));
			holder.itemsExpand = ((ImageView) convertView.findViewById(mTo[2]));
			holder.itemsLayoutExpand = ((RelativeLayout) convertView
					.findViewById(mTo[3]));

			// ���ñ��
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// ����layout����
		final Map<String, Object> dataSet = mData.get(position);
		if (dataSet == null) {
			return null;
		}
		// ��ȡ��������
		final Integer imgId = (Integer) dataSet.get(mFrom[0]);
		String title = (String) dataSet.get(mFrom[1]);
		if(title==null) title="";
		final Boolean isExpand = (Boolean) dataSet.get(mFrom[2]);
		// �������ݵ�View
		holder.itemsIcon.setImageResource(imgId);
		holder.itemsTitle.setText(title);
		if (!isExpand) {
			hideExpandLayout(holder);
		} else {
			showExpandLayout(holder);
			//����expand layout����
			switch (position) {
			case 0:
				initCover2_1_2Data(holder);
				break;

			}
		}

		holder.itemsExpand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ������ʾ����
				dataSet.put(mFrom[2], !isExpand);
				mData.set(position, dataSet);

				FragEmPlanCover2_1_2ListViewAdapter.this.notifyDataSetChanged();

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

	private void initCover2_1_2Data(final ViewHolder holder) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.frag_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_supervision, null, false);
		
		layout.addView(view);
		
		//��������
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				updateCover2_1_2View(view,application.emPlanCover);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				ChmApplication application = (ChmApplication) ((FragmentActivity)mContext).getApplication();
				
				if(application.emPlanCover.getText2_1_2()==null || application.emPlanCover.getText2_1_2().equals(""))
				{
					application.emPlanCover.setText2_1_2(emPlanCoverService.getEmPlanCover2_1_2());
				}
			}
		};
		
		queue.download(item);

	}

	protected void updateCover2_1_2View(View view,
			EmPlanCover emPlanCover) {
		// TODO Auto-generated method stub
		TextView tview = (TextView) view.findViewById(R.id.tv_content);
		tview.setText(emPlanCover.getText2_1_2());
	}



	/**
	 * ViewHolder��
	 */
	static class ViewHolder {
		ImageView itemsIcon;
		TextView itemsTitle;
		ImageView itemsExpand;
		RelativeLayout itemsLayoutExpand;
	}
}