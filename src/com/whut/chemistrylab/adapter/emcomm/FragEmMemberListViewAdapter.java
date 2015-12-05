package com.whut.chemistrylab.adapter.emcomm;

import java.util.List;
import java.util.Map;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.dao.DangerDescDao;
import com.whut.chemistrylab.dao.EmMemberDao;
import com.whut.chemistrylab.dao.PhyChmAttrDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.ChmIdentity;
import com.whut.chemistrylab.model.DangerDesc;
import com.whut.chemistrylab.model.EmMember;
import com.whut.chemistrylab.model.FireMeans;
import com.whut.chemistrylab.model.LeakEmMeans;
import com.whut.chemistrylab.model.PhyChmAttr;

import android.app.Activity;
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
public class FragEmMemberListViewAdapter extends BaseAdapter {

	private Context mContext;
	// ���еĲ���
	private int mResource;
	// �б�չ�ֵ�����
	private List<Map<String, Object>> mData;
	// Map�е�key
	private String[] mFrom;
	// view��id
	private int[] mTo;
	
	private boolean isCheckMode;

	private AbHttpQueue queue;
	EmMember experts=null;
	EmMemberDao expertsDao=null;
	
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
	 * @param isCheckMode TODO
	 */
	public FragEmMemberListViewAdapter(Context context,
			List<Map<String, Object>> data, int resource, String[] from,
			int[] to, boolean isCheckMode) {
		mContext = context;
		mData = data;
		mResource = resource;
		mFrom = from;
		mTo = to;
		
		this.isCheckMode=isCheckMode;
		
		queue=AbHttpQueue.getInstance();
		expertsDao=new EmMemberDao(context);
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
			holder.itemsText = ((TextView) convertView.findViewById(mTo[2]));
			holder.itemsExpand = ((ImageView) convertView.findViewById(mTo[3]));
			holder.itemsLayoutExpand = ((RelativeLayout) convertView
					.findViewById(mTo[4]));
			holder.itemsCheck=((ImageView) convertView.findViewById(mTo[5]));

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
		final Integer _id = (Integer) dataSet.get(mFrom[0]);
		final Integer imgId = (Integer) dataSet.get(mFrom[1]);
		String title = (String) dataSet.get(mFrom[2]);
		if(title==null) title="";
		String text = (String) dataSet.get(mFrom[3]);
		if(text==null) text="";
		final Boolean isExpand = (Boolean) dataSet.get(mFrom[4]);
		final Boolean isCheck = (Boolean) dataSet.get(mFrom[5]);
		// �������ݵ�View
		holder.itemsIcon.setImageResource(imgId);
		holder.itemsTitle.setText(title);
		holder.itemsText.setText(text);
		
		if(!isCheckMode){
			holder.itemsExpand.setVisibility(View.VISIBLE);
			holder.itemsCheck.setVisibility(View.INVISIBLE);
		}else{
			holder.itemsExpand.setVisibility(View.INVISIBLE);
			holder.itemsCheck.setVisibility(View.VISIBLE);
		}
		
		if (!isExpand) {
			hideExpandLayout(holder);
		} else {
			showExpandLayout(holder);
			//����expand layout����
			initEmMemberData(holder,_id);
		}
		
		if (!isCheck) {
			setUnCheckLayout(holder);
		} else {
			setCheckLayout(holder);
		}

		holder.itemsExpand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ������ʾ����
				dataSet.put(mFrom[4], !isExpand);
				mData.set(position, dataSet);

				FragEmMemberListViewAdapter.this.notifyDataSetChanged();

			}

		});
		
		holder.itemsCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ������ʾ����
				dataSet.put(mFrom[5], !isCheck);
				mData.set(position, dataSet);

				notifyDataSetChanged();

			}

		});

		return convertView;
	}
	
	public void setCheckMode(boolean isCheckMode){
		this.isCheckMode=isCheckMode;
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
	
	private void setCheckLayout(final ViewHolder holder) {
		holder.itemsCheck
				.setBackgroundResource(R.drawable.btn_check_on);
	}

	private void setUnCheckLayout(final ViewHolder holder) {
		holder.itemsCheck
				.setBackgroundResource(R.drawable.btn_check_off);
	}

	private void initEmMemberData(final ViewHolder holder,final Integer id) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) holder.itemsLayoutExpand
				.findViewById(R.id.act_items_expand);
		((ViewGroup) layout).removeAllViews();
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_expert, null, false);
		
		layout.addView(view);
		
		//��������
		AbHttpItem item=new AbHttpItem();
		item.callback=new AbHttpCallback() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				updateinitEmMemberView(view,experts);
			}
			
			@Override
			public void get() {
				// TODO Auto-generated method stub
				experts=expertsDao.getBeanByID(id);
			}
		};
		
		queue.download(item);

	}
	
	private void updateinitEmMemberView(View view, EmMember experts) {
		// TODO Auto-generated method stub
		TextView tv1=(TextView) view.findViewById(R.id.expert_first);
		TextView tv2=(TextView) view.findViewById(R.id.expert_second);
		TextView tv3=(TextView) view.findViewById(R.id.expert_third);
		TextView tv4=(TextView) view.findViewById(R.id.expert_fourth);
		TextView tv5=(TextView) view.findViewById(R.id.expert_fifth);
		TextView tv6=(TextView) view.findViewById(R.id.expert_sixth);
		TextView tv7=(TextView) view.findViewById(R.id.expert_seventh);
		TextView tv8=(TextView) view.findViewById(R.id.expert_eighth);
		TextView tv9=(TextView) view.findViewById(R.id.expert_ninth);
		TextView tv10=(TextView) view.findViewById(R.id.expert_tenth);
		TextView tv11=(TextView) view.findViewById(R.id.expert_ten_first);
		
		tv1.setText(experts.getName());
		tv2.setText(experts.getGender());
		tv3.setText(experts.getWorkplace());
		tv4.setText(experts.getAge());
		tv5.setText(experts.getMajor());
		tv6.setText(experts.getTitle());
		tv7.setText(experts.getGradSchool());
		tv8.setText(experts.getDegree());
		tv9.setText(experts.getSpecialty());
		tv10.setText(experts.getContact());
		tv11.setText(experts.getBelongto());
	}

	/**
	 * ViewHolder��
	 */
	static class ViewHolder {
		ImageView itemsIcon;
		TextView itemsTitle;
		TextView itemsText;
		ImageView itemsExpand;
		ImageView itemsCheck;
		RelativeLayout itemsLayoutExpand;
	}
}