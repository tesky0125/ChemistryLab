package com.whut.chemistrylab.adapter;

import java.util.List;

import com.whut.chemistrylab.R;
import com.whut.chemistrylab.example.DBActivity;
import com.whut.chemistrylab.model.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserDBListAdapter extends BaseAdapter {

	private Context mContext;
	// xmlתView����
	private LayoutInflater mInflater;
	// �б�չ�ֵ�����
	private List<User> mUserlist;

	/**
	 * ���췽��
	 * @param context
	 * @param data �б�չ�ֵ�����
	 * @param resource ���еĲ���
	 * @param from Map�е�key
	 * @param to view��id
	 */
	public UserDBListAdapter(Context context, List<User> userlist) {
		mContext = context;
		mUserlist = userlist;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mUserlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mUserlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// ��ǰ����
		final int index = position;
		
		if (convertView == null) {
			// ʹ���Զ����list_items��ΪLayout
			convertView = mInflater.inflate(R.layout.db_list_items, parent, false);
			// ����findView�Ĵ���
			holder = new ViewHolder();
			// ��ʼ�������е�Ԫ��
			holder.itemsTitle = ((TextView) convertView.findViewById(R.id.db_itemsTitle));
			holder.itemsText = ((EditText) convertView.findViewById(R.id.db_itemsText));
			holder.modifyBtn = ((Button) convertView.findViewById(R.id.db_modBtn));
			holder.delBtn = ((Button) convertView.findViewById(R.id.db_delBtn));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// ��ȡ���е�����
		final User user = mUserlist.get(position);
		// �������ݵ�View
		holder.itemsTitle.setText(String.valueOf(user.getuId()));
		holder.itemsText.setText(user.getName());
		// �޸ĺ�ɾ����ť
		// �ͷŽ���
		holder.modifyBtn.setFocusable(false);
		holder.delBtn.setFocusable(false);
		// �޸İ�ť�¼�
		holder.modifyBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				 //�޸�����
				 user.setName(holder.itemsText.getText().toString().trim());
				 ((DBActivity)mContext).updateData(user);
				 //��ѯ
				 User u = ((DBActivity)mContext).queryDataById(Integer.parseInt(holder.itemsTitle.getText().toString()));
				 mUserlist.remove(index);
				 addItem(0,u);
			}
		});
		
		// ɾ����ť�¼�
		holder.delBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				 //ɾ��
				 ((DBActivity)mContext).delData(Integer.parseInt(holder.itemsTitle.getText().toString()));
				 mUserlist.remove(index);
				 //������ͼ
				 notifyDataSetChanged();
			}
		});
		return convertView;
	}

	/**
	 * ����һ�����ı���ͼ
	 * @param item
	 */
	public void addItem(int position,User user) {
		mUserlist.add(position,user);
		notifyDataSetChanged();
	}
	
	/**
	 * ����һ�����ı���ͼ
	 * @param item
	 */
	public void addItem(User user) {
		mUserlist.add(0,user);
		notifyDataSetChanged();
	}

	/**
	 * ViewԪ��
	 */
	static class ViewHolder {
		TextView itemsTitle;
		EditText itemsText;
		Button modifyBtn;
		Button delBtn;
	}

}