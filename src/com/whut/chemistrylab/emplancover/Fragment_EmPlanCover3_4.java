package com.whut.chemistrylab.emplancover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.whut.chemistrylab.R;
import com.whut.chemistrylab.adapter.emplancover.FragEmPlanCover3_4ListViewAdapter;
import com.whut.chemistrylab.global.ChmApplication;

public class Fragment_EmPlanCover3_4 extends Fragment {

	private ChmApplication application;
	private Activity mActivity = null;

	private List<Map<String, Object>> listdata = null;
	@ViewInject(id = R.id.frag_chm_lookup_listview)
	ListView mListView = null;

	private FragEmPlanCover3_4ListViewAdapter myListViewAdapter = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = this.getActivity();
		application = (ChmApplication) mActivity.getApplication();

		View view = inflater.inflate(R.layout.frag_chm_lookup_list, null);
		FinalActivity.initInjectedView(this, view);

		initListData();

		// 使用自定义的Adapter
		myListViewAdapter = new FragEmPlanCover3_4ListViewAdapter(mActivity, listdata,
				R.layout.list_items_lookup_frag, new String[] { "itemsIcon",
						"itemsTitle","itemsExpand" }, new int[] { R.id.frag_itemsIcon,
						R.id.frag_itemsTitle,R.id.frag_btn_expand,R.id.frag_items_complex });
		mListView.setAdapter(myListViewAdapter);
		// item被点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//((AbActivity)mActivity).showToast("item long click.");
				
				return true;
			}
			
			
		});
		return view;
	}

	private void initListData() {
		// ListView数据
		listdata = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		map.put("itemsIcon", R.drawable.lable);
		map.put("itemsTitle", "3.4.1危险品泄露监视");
		map.put("itemsExpand", false);
		listdata.add(map);

		map = new HashMap<String, Object>();
		map.put("itemsIcon", R.drawable.lable);
		map.put("itemsTitle", "3.4.2危险品泄露监测");
		map.put("itemsExpand", false);
		listdata.add(map);

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
