package com.whut.chemistrylab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.ab.titlebar.AbTitleBar;
import com.ab.view.listener.AbOnListViewListener;
import com.ab.view.pullview.AbPullListView;
import com.whut.chemistrylab.adapter.ImageListAdapter;
import com.whut.chemistrylab.adapter.MyListViewAdapter;
import com.whut.chemistrylab.dao.EmPlanDao;
import com.whut.chemistrylab.example.SlidingTabActivity;
import com.whut.chemistrylab.global.Constant;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.EmPlan;
import com.whut.chemistrylab.service.EmPlanBrowseListService;

public class EmPlanBrowseListActivity extends AbActivity {
	
	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
	
	private List<Map<String, Object>> listdata = null;
	private List<Map<String, Object>> newListdata = null;
	private ImageListAdapter myListViewAdapter;
	private EmPlanBrowseListService lookupListService;
	
	AbHttpItem itemRefresh = null;
	AbHttpItem itemLoadMore = null;
	private int currentPageNum = 1;
	public int totalCount = 0;
	TextView tvCnt=null;
	int searchWhereType=0;
	String searchWhereClause=null;
	List<Map<String,String>> listSearchByData=null;
	boolean isLayoutSearchVisible=false;
	boolean isSearchByListVisible=false;
	
	EmPlan emPlan=null;
	EmPlanDao emPlanDao=null;
	
	@ViewInject(id=R.id.act_chm_lookup_listview) AbPullListView mAbPullListView;
	@ViewInject(id=R.id.layout_search) RelativeLayout layout_search;
	@ViewInject(id=R.id.edit_search) EditText edit_search;
	@ViewInject(id=R.id.btn_search) ImageButton btn_search;
	@ViewInject(id=R.id.tv_search_by) TextView tv_search_by;
	@ViewInject(id=R.id.btn_search_by) ImageButton btn_search_by;
	@ViewInject(id=R.id.list_search_by) ListView list_search_by;
	
	private AbHttpQueue mAbHttpQueue = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_chm_lookup_list);
		FinalActivity.initInjectedView(this);
		
		application = (ChmApplication) abApplication;
		lookupListService=new EmPlanBrowseListService(this);
		emPlanDao = new EmPlanDao(this);
		mAbHttpQueue = AbHttpQueue.getInstance();

		initTitleBarLayout();
		initTitleRightLayout();
		
		initSearchListener();
		initSearchByListData();
		
		mAbPullListView.setPullLoadEnable(true);
        mAbPullListView.setPullRefreshEnable(false); 
        
        //ListView数据
        listdata = new ArrayList<Map<String, Object>>();
    	
    	//使用自定义的Adapter
    	myListViewAdapter = new ImageListAdapter(this, listdata,R.layout.list_items_lookup_act,
				new String[] { "_id", "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.act_itemsIcon,
						R.id.act_itemsTitle,R.id.act_itemsText });
    	mAbPullListView.setAdapter(myListViewAdapter);
    	mAbPullListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0) return;
				//
				Map<String, Object> map = listdata.get(position-1);
				 
//				String unNo=(String) map.get("itemsTitle");
//System.out.println(unNo);
//				chmIdentity = chmIdentityDao.getBeanByUnNo(unNo);
				
				Integer _id=(Integer)map.get("_id");
				emPlan = emPlanDao.getBeanByID(_id);
//System.out.println(chmIdentity);
				
				application.emPlan=emPlan;
				
				Intent it=new Intent(EmPlanBrowseListActivity.this,EmPlanBrowseTabActivity.class);
				EmPlanBrowseListActivity.this.startActivity(it);
			}
		});
    	
    	mAbPullListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
System.out.println("long click.");				
				return true;
			}
		});
    	
    	
    	showProgressDialog();
    	
    	initLoadCallback();
    	
    	mAbHttpQueue.downloadBeforeClean(itemRefresh);
    	
	}
	
	private void initSearchByListData() {
		tv_search_by.setText(this.getResources().getString(R.string.search_by_leakname));

		listSearchByData = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("item_name", this.getResources().getString(R.string.search_by_leakname));
		listSearchByData.add(map);
		map = new HashMap<String,String>();
		map.put("item_name", this.getResources().getString(R.string.search_by_type));
		listSearchByData.add(map);
		map = new HashMap<String,String>();
		map.put("item_name", this.getResources().getString(R.string.search_by_addr));
		listSearchByData.add(map);
		map = new HashMap<String,String>();
		map.put("item_name", this.getResources().getString(R.string.search_by_src));
		listSearchByData.add(map);
		
		list_search_by.setAdapter(new SimpleAdapter(EmPlanBrowseListActivity.this, listSearchByData, R.layout.list_items_search,
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
					tv_search_by.setText(EmPlanBrowseListActivity.this.getResources().getString(R.string.search_by_name));
					break;
				case 1:
					searchWhereType=1;
					tv_search_by.setText(EmPlanBrowseListActivity.this.getResources().getString(R.string.search_by_type));
					break;
				case 2:
					searchWhereType=2;
					tv_search_by.setText(EmPlanBrowseListActivity.this.getResources().getString(R.string.search_by_addr));
					break;
				case 3:
					searchWhereType=3;
					tv_search_by.setText(EmPlanBrowseListActivity.this.getResources().getString(R.string.search_by_src));
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
						enableSearchLayout();
					}else{
						disableSearchLayout();
					}
					
					break;
				case R.id.btn_search:
					disableSearchLayout();
					searchWhereClause=edit_search.getText().toString();
					mAbHttpQueue.download(itemRefresh);
					edit_search.clearFocus();//隐藏键盘
					showProgressDialog();
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
	
	private void disableSearchLayout() {
		isSearchByListVisible=false;
		list_search_by.setVisibility(View.GONE);
		mAbPullListView.setVisibility(View.VISIBLE);
	}

	private void enableSearchLayout() {
		isSearchByListVisible=true;
		list_search_by.setVisibility(View.VISIBLE);
		mAbPullListView.setVisibility(View.GONE);
	}

	private void initLoadCallback() {
		//定义两种加载的事件
    	itemRefresh = new AbHttpItem();
		itemRefresh.callback = new AbHttpCallback() {

			@Override
			public void update() {
				removeProgressDialog();
				listdata.clear();
				if(newListdata!=null && newListdata.size()>0){
	                listdata.addAll(newListdata);
	                myListViewAdapter.notifyDataSetChanged();
	                newListdata.clear();
   		    	}
				mAbPullListView.stopRefresh();
				
				tvCnt.setText("共"+totalCount+"条");
			}

			@Override
			public void get() {
	   		    try {
	   		    	Thread.sleep(1000);
	   		    	currentPageNum = 1;
	   		    	//始终加载最新的30条
	   		    	
	   		    	newListdata=lookupListService.getLookupList(searchWhereType, currentPageNum, Constant.pageSize, searchWhereClause);
	   		    	
	   		    	totalCount=lookupListService.getLookupCount(searchWhereType, searchWhereClause);
	   		    	
	   		    } catch (Exception e) {
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
	   		    	
	   		    	List<Map<String, Object>> loadMoreList=lookupListService.getLookupList(searchWhereType, currentPageNum, Constant.pageSize, searchWhereClause);
	   		    	newListdata.addAll(loadMoreList);
	   		    	
	   		    } catch (Exception e) {
	   		    	currentPageNum--;
	   		    	newListdata.clear();
	   		    	showToastInThread(e.getMessage());
	   		    }
		  };
		};
    	
    	mAbPullListView.setAbOnListViewListener(new AbOnListViewListener(){

			@Override
			public void onRefresh() {
System.out.println("onRefresh");
				mAbHttpQueue.download(itemRefresh);
			}

			@Override
			public void onLoadMore() {
System.out.println("onLoadMore");
				mAbHttpQueue.download(itemLoadMore);
			}
			
		});
	}

	private void initTitleBarLayout() {
		mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.main_third_btn);
        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
        
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void initTitleRightLayout(){
		mAbTitleBar.clearRightView();
		View cntView=mInflater.inflate(R.layout.tv_chmcount, null);
		View searchView=mInflater.inflate(R.layout.btn_search, null);
		
		mAbTitleBar.addRightView(cntView);
		mAbTitleBar.addRightView(searchView);
		
    	tvCnt=(TextView) cntView.findViewById(R.id.tv_count);
    	Button searchBtn=(Button) searchView.findViewById(R.id.searchBtn);
    	searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isLayoutSearchVisible){
					isLayoutSearchVisible=true;
					layout_search.setVisibility(View.VISIBLE);
				}else {
					isLayoutSearchVisible=false;
					layout_search.setVisibility(View.GONE);
				}
			}
		});
    	
    }
}
