package com.whut.chemistrylab.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.ab.titlebar.AbTitleBar;
import com.ab.view.listener.AbOnListViewListener;
import com.ab.view.pullview.AbPullListView;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.R.drawable;
import com.whut.chemistrylab.R.id;
import com.whut.chemistrylab.R.layout;
import com.whut.chemistrylab.R.string;
import com.whut.chemistrylab.adapter.ImageListAdapter;
import com.whut.chemistrylab.adapter.MyListViewAdapter;
import com.whut.chemistrylab.global.ChmApplication;

public class PullToRefreshListActivity extends AbActivity {
	
	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
	
	private List<Map<String, Object>> list = null;
	private List<Map<String, Object>> newList = null;
	private AbPullListView mAbPullListView = null;
	private int currentPage = 1;
	private MyListViewAdapter myListViewAdapter;
	
	private AbHttpQueue mAbHttpQueue = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_chm_lookup_list);
        application = (ChmApplication)abApplication;
        
        AbTitleBar mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.main_first_btn);
        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
        
        mAbHttpQueue = AbHttpQueue.getInstance();
        
	    //获取ListView对象
        mAbPullListView = (AbPullListView)this.findViewById(R.id.act_chm_lookup_listview);
        mAbPullListView.setPullLoadEnable(true);
        mAbPullListView.setPullRefreshEnable(true); 
        //ListView数据
    	list = new ArrayList<Map<String, Object>>();
    	
    	//使用自定义的Adapter
    	myListViewAdapter = new MyListViewAdapter(this, list,R.layout.list_items_lookup_frag,
				new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.act_itemsIcon,
						R.id.act_itemsTitle,R.id.act_itemsText });
    	mAbPullListView.setAdapter(myListViewAdapter);
    	//item被点击事件
    	mAbPullListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
    	});
    	
    	showProgressDialog();

    	//定义两种查询的事件
    	final AbHttpItem item1 = new AbHttpItem();
		item1.callback = new AbHttpCallback() {

			@Override
			public void update() {
				removeProgressDialog();
				list.clear();
				if(newList!=null && newList.size()>0){
	                list.addAll(newList);
	                myListViewAdapter.notifyDataSetChanged();
	                newList.clear();
   		    	}
				mAbPullListView.stopRefresh();
			}

			@Override
			public void get() {
	   		    try {
	   		    	Thread.sleep(1000);
	   		    	currentPage = 1;
	   		    	newList = new ArrayList<Map<String, Object>>();
	   		    	Map<String, Object> map = null;
	   		    	
	   		    	for (int i = 0; i < 10; i++) {
	   		    		map = new HashMap<String, Object>();
	   					map.put("itemsIcon", R.drawable.image_bg);
		   		    	map.put("itemsTitle", "化学品UN编号"+i);
		   		    	map.put("itemsText", "化学品名称..."+i);
		   		    	newList.add(map);
	   				}
	   		    } catch (Exception e) {
	   		    }
		  };
		};
		
		final AbHttpItem item2 = new AbHttpItem();
		item2.callback = new AbHttpCallback() {

			@Override
			public void update() {
				if(newList!=null && newList.size()>0){
					list.addAll(newList);
					myListViewAdapter.notifyDataSetChanged();
					newList.clear();
					mAbPullListView.stopLoadMore(true);
                }else{
                	//没有新数据了
                	mAbPullListView.stopLoadMore(false);
                }
				
			}

			@Override
			public void get() {
	   		    try {
	   		    	currentPage++;
	   		    	Thread.sleep(1000);
	   		    	newList = new ArrayList<Map<String, Object>>();
	   		    	Map<String, Object> map = null;
	   		    	map = new HashMap<String, Object>();
	   		    	map.put("itemsIcon", R.drawable.image_bg);
	   		    	map.put("itemsTitle", "化学品UN编号-上拉");
	   		    	map.put("itemsText", "化学品名称-上拉...");
	   		    	newList.add(map);
	   		    } catch (Exception e) {
	   		    	currentPage--;
	   		    	newList.clear();
	   		    	showToastInThread(e.getMessage());
	   		    }
		  };
		};
		
		mAbPullListView.setAbOnListViewListener(new AbOnListViewListener(){

			@Override
			public void onRefresh() {
				mAbHttpQueue.download(item1);
			}

			@Override
			public void onLoadMore() {
				mAbHttpQueue.download(item2);
			}
			
		});
		
    	//第一次下载数据
		mAbHttpQueue.downloadBeforeClean(item1);
	    
    }
    
   
}


