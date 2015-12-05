package com.whut.chemistrylab.example;

import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.titlebar.AbTitleBar;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.R.drawable;
import com.whut.chemistrylab.R.id;
import com.whut.chemistrylab.R.layout;
import com.whut.chemistrylab.R.string;
import com.whut.chemistrylab.adapter.UserDBListAdapter;
import com.whut.chemistrylab.dao.UserDao;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.model.User;
/**
 * 名称：DBActivity
 * 描述：数据库演示
 * @author zhaoqp
 * @date 2011-12-13
 * @version
 */
public class DBActivity extends AbActivity {
	
	private ChmApplication application;
	//列表适配器
	private UserDBListAdapter myListViewAdapter = null;
	//列表数据
	private List<User> userList = null;
	private ListView mListView = null;
	//定义数据库操作实现类
	private UserDao userDao = null;
	
	//每一页显示的行数
	public int pageSize = 10;
	//当前页数
	public int pageNum = 1;
	//总条数
	public int totalCount = 0;
	//分页栏
	private LinearLayout mListViewForPage;
	//总条数和当前显示的几页
	public TextView  total, current;
	//上一页和下一页的按钮
	private Button preView,nextView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setAbContentView(R.layout.db_main);
        
        AbTitleBar mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.db_name);
        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
	    
	    application = (ChmApplication)abApplication;
	    
	    //初始化数据库操作实现类
	    userDao = new UserDao(DBActivity.this);
	    
	    //查询数据
	    userList = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +0, null);
        
	    totalCount = userDao.queryCount(null, null);
        //获取ListView对象
        mListView = (ListView)this.findViewById(R.id.mListView);
        //分页栏
        mListViewForPage = (LinearLayout) this.findViewById(R.id.mListViewForPage);
        //上一页和下一页的按钮
        preView = (Button) this.findViewById(R.id.preView);
		nextView = (Button) this.findViewById(R.id.nextView);
		//分页栏显示的文本
		total = (TextView)findViewById(R.id.total);
		current = (TextView)findViewById(R.id.current);
		
		//创建一个HeaderView用于向数据库中增加一条数据
        View headerView = mInflater.inflate(R.layout.db_list_header,null);
        //加到ListView的顶部
        mListView.addHeaderView(headerView);
        //使用自定义的Adapter
    	myListViewAdapter = new UserDBListAdapter(this,userList);
    	mListView.setAdapter(myListViewAdapter);
    	
    	if(userList == null || userList.size()==0){
			//无数据隐藏分页栏
			mListViewForPage.setVisibility(View.GONE);
		}else{
			total.setText("总条数:" +String.valueOf(totalCount));
			current.setText("当前页:" + String.valueOf(pageNum));
			checkView();
			mListViewForPage.setVisibility(View.VISIBLE);
		}
        
        //增加记录的按钮
        final Button addBtn = (Button)headerView.findViewById(R.id.addBtn);
        //增加的字段数据
        final EditText mEditText = (EditText)headerView.findViewById(R.id.add_name);
        addBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//获取用户输入的数据
				String name = mEditText.getText().toString();
				if(name!=null && !"".equals(name.trim())){
					//增加一条数据到数据库
					User u = new User();
					u.setName(name);
					long id = userDao.insert(u);
					showToast("插入数据成功,ID:"+id);
					//插入数据成功
					if(id!=-1){
						//查询数据
						
				        List<User> userListNew = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +String.valueOf((pageNum-1)*pageSize), null);
				        userList.clear();
				        userList.addAll(userListNew);
						myListViewAdapter.notifyDataSetChanged();
						totalCount = userDao.queryCount(null, null);
						total.setText("总条数:" +String.valueOf(totalCount));
						current.setText("当前页:" + String.valueOf(pageNum));
						checkView();
					}
				}else{
					showToast("请输入名称!");
				}
			}
        });
        
        //上一页事件
        preView.setOnTouchListener(new Button.OnTouchListener(){
		      @Override
		      public boolean onTouch(View arg0, MotionEvent arg1){
		        switch (arg1.getAction()) {
		          case MotionEvent.ACTION_DOWN:
		        	  preView();
		              break;
		          case MotionEvent.ACTION_MOVE:
		              break;
		          case MotionEvent.ACTION_UP:
		              break;
		          case MotionEvent.ACTION_CANCEL:
		            break;
		          default:
		              break;
		          }
		         return true;
		      } 
		 });
		
        //下一页事件
		nextView.setOnTouchListener(new Button.OnTouchListener(){
		      @Override
		      public boolean onTouch(View arg0, MotionEvent arg1){
		        switch (arg1.getAction()) {
		          case MotionEvent.ACTION_DOWN:
		        	  nextView();
		              break;
		          case MotionEvent.ACTION_MOVE:
		              break;
		          case MotionEvent.ACTION_UP:
		              break;
		          case MotionEvent.ACTION_CANCEL:
		            break;
		          default:
		              break;
		          }
		         return true;
		      } 
		});
        
      } 
    
    
    /*
     * 上一页
     */
	private void preView() {
		pageNum--;
		current.setText("当前页:" + String.valueOf(pageNum));
		userList.clear();
		List<User> userListNew = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +String.valueOf((pageNum-1)*pageSize), null);
		userList.addAll(userListNew);
		myListViewAdapter.notifyDataSetChanged();
		checkView();
	}
    /*
     * 下一页
     */
	private void nextView() {
		pageNum++;
		current.setText("当前页:" + String.valueOf(pageNum));
		userList.clear();
		List<User> userListNew = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +String.valueOf((pageNum-1)*pageSize), null);
		userList.addAll(userListNew);
		myListViewAdapter.notifyDataSetChanged();
		checkView();
	}
    
    /*
     * 文本是否可点击
     */
	public void checkView() {
		if (pageNum <= 1) {
			//上一页文本为不可点击状态
			preView.setEnabled(false);
			preView.setBackgroundResource(R.drawable.left_press);
			//总条数小于每页显示的条数
			if (totalCount <= pageSize) {
				//下一页文本为不可点击状态
				nextView.setEnabled(false);
				nextView.setBackgroundResource(R.drawable.right_press);
			}else{
				nextView.setEnabled(true);
				nextView.setBackgroundResource(R.drawable.right_normal);
			}
		}//总条数-当前页*每页显示的条数 <=每页显示的条数
		else if (totalCount - (pageNum-1) * pageSize <= pageSize){
			//下一页文本为不可点击状态,上一页变为可点击
			nextView.setEnabled(false);
			nextView.setBackgroundResource(R.drawable.right_press);
			preView.setEnabled(true);
			preView.setBackgroundResource(R.drawable.left_normal);
		}else {
			//上一页下一页文本设置为可点击状态
			preView.setEnabled(true);
			preView.setBackgroundResource(R.drawable.left_normal);
			nextView.setEnabled(true);
			nextView.setBackgroundResource(R.drawable.right_normal);
		}
	}
	
	/**
	 * 更新数据
	 * 描述：TODO
	 * @param u
	 */
	public void updateData(User u){
		userDao.update(u);
	}
	
	/**
	 * 
	 * 描述：根据ID查询数据
	 * @param id
	 * @return
	 */
	public User queryDataById(int id){
		return (User)userDao.queryOne(id);
		
	}
	
	/**
	 * 
	 * 描述：删除数据
	 * @param id
	 */
	public void delData(int id){
		userDao.delete(id);
		totalCount = userDao.queryCount(null, null);
		total.setText("总条数:" +String.valueOf(totalCount));
		current.setText("当前页:" + String.valueOf(pageNum));
		checkView();
	}
    
}