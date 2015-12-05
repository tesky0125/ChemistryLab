package com.whut.chemistrylab.emcomm;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.ab.view.listener.AbOnListViewListener;
import com.ab.view.pullview.AbPullListView;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.adapter.emcomm.FragEmMemberListViewAdapter;
import com.whut.chemistrylab.adapter.emcomm.FragHeadquatersListViewAdapter;
import com.whut.chemistrylab.adapter.lookup.FragDangerPropertyListViewAdapter;
import com.whut.chemistrylab.adapter.lookup.FragEmGuideListViewAdapter;
import com.whut.chemistrylab.dao.EmMemberDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.global.Constant;
import com.whut.chemistrylab.listener.IInFragmentCallback;
import com.whut.chemistrylab.listener.IInActivityCallback;
import com.whut.chemistrylab.model.EmMember;
import com.whut.chemistrylab.service.EmMemberListService;

public class Fragment_EmMember extends Fragment implements IInFragmentCallback {

	private ChmApplication application;
	private AbActivity mActivity = null;

	private List<Map<String, Object>> listdata = null;
	private List<Map<String, Object>> newListdata = null;
	private FragEmMemberListViewAdapter myListViewAdapter;
	private EmMemberListService expertsListService;
	
	private AbHttpItem itemRefresh = null;
	private AbHttpItem itemLoadMore = null;
	private int currentPageNum = 1;
	public int totalCount = 0;
	private int searchWhereType=0;//
	private String searchWhereClause=null;
	private List<Map<String,String>> listSearchByData=null;
	boolean isLayoutSearchVisible=false;
	boolean isSearchByListVisible=false;
	boolean isLayoutSendSmsVisible=false;
	
	private EmMember experts=null;
	private EmMemberDao emMemberDao=null;
	
	@ViewInject(id=R.id.act_chm_lookup_listview) AbPullListView mAbPullListView;
	@ViewInject(id=R.id.layout_search) RelativeLayout layout_search;
	@ViewInject(id=R.id.edit_search) EditText edit_search;
	@ViewInject(id=R.id.btn_search) ImageButton btn_search;
	@ViewInject(id=R.id.tv_search_by) TextView tv_search_by;
	@ViewInject(id=R.id.btn_search_by) ImageButton btn_search_by;
	@ViewInject(id=R.id.list_search_by) ListView list_search_by;
	@ViewInject(id=R.id.layout_sendsms) RelativeLayout layout_sendsms;
	@ViewInject(id=R.id.btn_checkall) Button btn_checkall;
	@ViewInject(id=R.id.btn_send) Button btn_send;
	@ViewInject(id=R.id.btn_cancelsms) Button btn_cancelsms;
	
	private AbHttpQueue mAbHttpQueue = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = (AbActivity) this.getActivity();
		application = (ChmApplication) mActivity.getApplication();

		View view = inflater.inflate(R.layout.activity_chm_experts_list, null);
		FinalActivity.initInjectedView(this, view);

		expertsListService=new EmMemberListService(mActivity);
		emMemberDao = new EmMemberDao(mActivity);
		mAbHttpQueue = AbHttpQueue.getInstance();

		
		initSearchListener();
		initSearchByListData();
		initSendSmsListener();
		
		mAbPullListView.setPullLoadEnable(true);
        mAbPullListView.setPullRefreshEnable(false); 
		
        listdata = new ArrayList<Map<String, Object>>();

		// 使用自定义的Adapter
        myListViewAdapter = new FragEmMemberListViewAdapter(mActivity, listdata,
				R.layout.list_items_lookup_act_ex, new String[] { "_id","itemsIcon",
						"itemsTitle","itemsText","itemsExpand","itemsCheck" }, new int[] { R.id.act_itemsIcon,
						R.id.act_itemsTitle,R.id.act_itemsText,R.id.act_btn_expand,R.id.act_items_complex,
						R.id.act_btn_check}, false);
    	mAbPullListView.setAdapter(myListViewAdapter);
    	mAbPullListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0) return;
//System.out.println("click.");
				//
				Map<String, Object> map = listdata.get(position-1);
				 
				Integer _id=(Integer) map.get("_id");
//System.out.println(_id);
				experts = emMemberDao.getBeanByID(_id);
				//application.experts=experts;
				
//				Intent it=new Intent(ExpertsListActivity.this,ExpertsActivity.class);
//				ExpertsListActivity.this.startActivity(it);
			}
		});
    	
    	mAbPullListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//System.out.println("long click.");
				if(position==0) return true;
				//
				Map<String, Object> map = listdata.get(position-1);
				 
				Integer _id=(Integer) map.get("_id");
//System.out.println(_id);
				experts = emMemberDao.getBeanByID(_id);
				
				Bundle data=new Bundle();
				data.putInt("index", 3);
				data.putSerializable("entity", experts);
				
				mInActivityListener.onPopup(data);
				
				return true;
			}
			
			
		});
    	
    	
    	mActivity.showProgressDialog();
    	
    	initLoadCallback();
    	
    	mAbHttpQueue.download(itemRefresh);

		return view;
	}

	private void initSearchByListData() {
		tv_search_by.setText(this.getResources().getString(R.string.search_by_name));

		listSearchByData = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("item_name", this.getResources().getString(R.string.search_by_name));
		listSearchByData.add(map);
		map = new HashMap<String,String>();
		map.put("item_name", this.getResources().getString(R.string.search_by_workplace));
		listSearchByData.add(map);
		list_search_by.setAdapter(new SimpleAdapter(mActivity, listSearchByData, R.layout.list_items_search,
				new String[]{"item_name"}, new int[]{R.id.item_name}));
		
		list_search_by.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> lv, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				switch(position)
				{
				case 0:
					searchWhereType=0;
					tv_search_by.setText(mActivity.getResources().getString(R.string.search_by_name));
					break;
				case 1:
					searchWhereType=1;
					tv_search_by.setText(mActivity.getResources().getString(R.string.search_by_workplace));
					break;
				default:
					break;
				}
				isSearchByListVisible=false;
				lv.setVisibility(View.GONE);
			}
		});
	}

	private void initSearchListener() {
		// TODO Auto-generated method stub
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_search_by:
					if(!isSearchByListVisible){
						enableSearchListLayout();
					}else{
						disableSearchListLayout();
					}
					
					break;
				case R.id.btn_search:
					disableSearchListLayout();
					searchWhereClause=edit_search.getText().toString();
					mAbHttpQueue.download(itemRefresh);
					edit_search.clearFocus();//隐藏键盘
					mActivity.showProgressDialog();
					break;
				default:
					break;
				}
				
			}

			
		};
		
		edit_search.clearFocus();//隐藏键盘
		
		btn_search_by.setOnClickListener(listener);
		btn_search.setOnClickListener(listener);
	}
	
	private void disableSearchListLayout() {
		isSearchByListVisible=false;
		list_search_by.setVisibility(View.GONE);
		mAbPullListView.setVisibility(View.VISIBLE);
	}

	private void enableSearchListLayout() {
		isSearchByListVisible=true;
		list_search_by.setVisibility(View.VISIBLE);
		mAbPullListView.setVisibility(View.GONE);
	}
	
	private void initSendSmsListener() {
		// TODO Auto-generated method stub
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_checkall:
					Button btn=(Button) v;
					if(btn.getText().equals(getResources().getString(R.string.sms_checkall))){
						setAllChecked();
						btn.setText(getResources().getString(R.string.sms_uncheckall));
					}else{
						setAllUnChecked();
						btn.setText(getResources().getString(R.string.sms_checkall));
					}
					myListViewAdapter.notifyDataSetChanged();
					break;
				
				case R.id.btn_send:
					//getCheckedPhones
					mInActivityListener.onSendSms(getCheckedPhones());
					break;
					
				case R.id.btn_cancelsms:
					disableSendSmsLayout();
					myListViewAdapter.setCheckMode(false);
					myListViewAdapter.notifyDataSetChanged();
					break;
				default:
					break;
				}
				
			}

			
		};
		
		
		btn_checkall.setOnClickListener(listener);
		btn_send.setOnClickListener(listener);
		btn_cancelsms.setOnClickListener(listener);
	}

	private void disableSendSmsLayout() {
		isLayoutSendSmsVisible=false;
		layout_sendsms.setVisibility(View.GONE);
	}

	private void enableSendSmsLayout() {
		isLayoutSendSmsVisible=true;
		layout_sendsms.setVisibility(View.VISIBLE);
	}
	
	private List<String> getCheckedPhones()
	{
		List<String> list=new ArrayList<String>();
		
		for(Map<String,Object> map : listdata)
		{
			boolean isCheck=(Boolean) map.get("itemsCheck");
			Integer _id=(Integer) map.get("_id");
			EmMember experts = emMemberDao.getBeanByID(_id);
			if(isCheck) 
				list.add(experts.getContact());
		}
		
		return list;
	}
	
	private void setAllChecked()
	{
		for(int i=0;i<listdata.size();i++)
		{
			Map<String,Object> map=listdata.get(i);
			map.put("itemsCheck",true);
			listdata.set(i, map);
		}
		
	}
	
	private void setAllUnChecked()
	{
		for(int i=0;i<listdata.size();i++)
		{
			Map<String,Object> map=listdata.get(i);
			map.put("itemsCheck",false);
			listdata.set(i, map);
		}
		
	}
	
	private void initLoadCallback() {
		//定义两种加载的事件
    	itemRefresh = new AbHttpItem();
		itemRefresh.callback = new AbHttpCallback() {

			@Override
			public void update() {
				mActivity.removeProgressDialog();
				listdata.clear();
				if(newListdata!=null && newListdata.size()>0){
	                listdata.addAll(newListdata);
	                myListViewAdapter.notifyDataSetChanged();
	                newListdata.clear();
   		    	}
				mAbPullListView.stopRefresh();
				
			}

			@Override
			public void get() {
	   		    try {
	   		    	Thread.sleep(1000);
	   		    	currentPageNum = 1;
	   		    	//始终加载最新的30条
	   		    	
	   		    	newListdata=expertsListService.getEmMemberList(searchWhereType, currentPageNum, Constant.pageSize, searchWhereClause);
//System.out.println("emmemverListService--newListdata"+newListdata.size());	   		    	
	   		    	totalCount=expertsListService.getEmMemberCount(searchWhereType, searchWhereClause);
	   		    	
	   		    } catch (Exception e) {
	   		    	e.printStackTrace();
	   		    }
		  };
		};
		
		itemLoadMore = new AbHttpItem();
		itemLoadMore.callback = new AbHttpCallback() {

			@Override
			public void update() {
				if(newListdata!=null && newListdata.size()>0){
					listdata.addAll(newListdata);
					myListViewAdapter.notifyDataSetChanged();
					newListdata.clear();
					mAbPullListView.stopLoadMore(true);
                }else{
                	//没有新数据了
                	mAbPullListView.stopLoadMore(false);
                }
				
			}

			@Override
			public void get() {
	   		    try {
	   		    	currentPageNum++;
	   		    	Thread.sleep(1000);
	   		    	
	   		    	List<Map<String, Object>> loadMoreList=expertsListService.getEmMemberList(searchWhereType, currentPageNum, Constant.pageSize, searchWhereClause);
	   		    	newListdata.addAll(loadMoreList);
	   		    	
	   		    } catch (Exception e) {
	   		    	currentPageNum--;
	   		    	if(newListdata!=null && newListdata.size()>0){
	   		    		newListdata.clear();
	   		    	}
	   		    	mActivity.showToastInThread(e.getMessage());
	   		    }
		  };
		};
    	
    	mAbPullListView.setAbOnListViewListener(new AbOnListViewListener(){

			@Override
			public void onRefresh() {
//				System.out.println("onRefresh");
				mAbHttpQueue.download(itemRefresh);
			}

			@Override
			public void onLoadMore() {
//				System.out.println("onLoadMore");
				mAbHttpQueue.download(itemLoadMore);
			}
			
		});
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onExpand() {
		// TODO Auto-generated method stub
		//System.out.println("2.mViewListener.onCallback(null, null);");
		if(!isLayoutSearchVisible){
			isLayoutSearchVisible=true;
			layout_search.setVisibility(View.VISIBLE);
		}else {
			isLayoutSearchVisible=false;
			layout_search.setVisibility(View.GONE);
		}
	}
	
	private IInActivityCallback mInActivityListener;  
	
	@Override  
    public void onAttach(Activity activity) {  
        // TODO Auto-generated method stub  
          
        try {         
        	mInActivityListener=(IInActivityCallback) activity;  
        } catch (Exception e) {  
            // TODO: handle exception  
            throw new ClassCastException(activity.toString() + "must implement IPopupCallback");  
        }  
          
        super.onAttach(activity);  
    }

	@Override
	public void onPreSend() {
		// TODO Auto-generated method stub
		if(!isLayoutSendSmsVisible){
			enableSendSmsLayout();
			myListViewAdapter.setCheckMode(true);
		}else {
			disableSendSmsLayout();
			myListViewAdapter.setCheckMode(false);
		}
		
		myListViewAdapter.notifyDataSetChanged();
	}  
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}  
	
}
