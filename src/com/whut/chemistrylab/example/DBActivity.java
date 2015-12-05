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
 * ���ƣ�DBActivity
 * ���������ݿ���ʾ
 * @author zhaoqp
 * @date 2011-12-13
 * @version
 */
public class DBActivity extends AbActivity {
	
	private ChmApplication application;
	//�б�������
	private UserDBListAdapter myListViewAdapter = null;
	//�б�����
	private List<User> userList = null;
	private ListView mListView = null;
	//�������ݿ����ʵ����
	private UserDao userDao = null;
	
	//ÿһҳ��ʾ������
	public int pageSize = 10;
	//��ǰҳ��
	public int pageNum = 1;
	//������
	public int totalCount = 0;
	//��ҳ��
	private LinearLayout mListViewForPage;
	//�������͵�ǰ��ʾ�ļ�ҳ
	public TextView  total, current;
	//��һҳ����һҳ�İ�ť
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
	    
	    //��ʼ�����ݿ����ʵ����
	    userDao = new UserDao(DBActivity.this);
	    
	    //��ѯ����
	    userList = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +0, null);
        
	    totalCount = userDao.queryCount(null, null);
        //��ȡListView����
        mListView = (ListView)this.findViewById(R.id.mListView);
        //��ҳ��
        mListViewForPage = (LinearLayout) this.findViewById(R.id.mListViewForPage);
        //��һҳ����һҳ�İ�ť
        preView = (Button) this.findViewById(R.id.preView);
		nextView = (Button) this.findViewById(R.id.nextView);
		//��ҳ����ʾ���ı�
		total = (TextView)findViewById(R.id.total);
		current = (TextView)findViewById(R.id.current);
		
		//����һ��HeaderView���������ݿ�������һ������
        View headerView = mInflater.inflate(R.layout.db_list_header,null);
        //�ӵ�ListView�Ķ���
        mListView.addHeaderView(headerView);
        //ʹ���Զ����Adapter
    	myListViewAdapter = new UserDBListAdapter(this,userList);
    	mListView.setAdapter(myListViewAdapter);
    	
    	if(userList == null || userList.size()==0){
			//���������ط�ҳ��
			mListViewForPage.setVisibility(View.GONE);
		}else{
			total.setText("������:" +String.valueOf(totalCount));
			current.setText("��ǰҳ:" + String.valueOf(pageNum));
			checkView();
			mListViewForPage.setVisibility(View.VISIBLE);
		}
        
        //���Ӽ�¼�İ�ť
        final Button addBtn = (Button)headerView.findViewById(R.id.addBtn);
        //���ӵ��ֶ�����
        final EditText mEditText = (EditText)headerView.findViewById(R.id.add_name);
        addBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//��ȡ�û����������
				String name = mEditText.getText().toString();
				if(name!=null && !"".equals(name.trim())){
					//����һ�����ݵ����ݿ�
					User u = new User();
					u.setName(name);
					long id = userDao.insert(u);
					showToast("�������ݳɹ�,ID:"+id);
					//�������ݳɹ�
					if(id!=-1){
						//��ѯ����
						
				        List<User> userListNew = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +String.valueOf((pageNum-1)*pageSize), null);
				        userList.clear();
				        userList.addAll(userListNew);
						myListViewAdapter.notifyDataSetChanged();
						totalCount = userDao.queryCount(null, null);
						total.setText("������:" +String.valueOf(totalCount));
						current.setText("��ǰҳ:" + String.valueOf(pageNum));
						checkView();
					}
				}else{
					showToast("����������!");
				}
			}
        });
        
        //��һҳ�¼�
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
		
        //��һҳ�¼�
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
     * ��һҳ
     */
	private void preView() {
		pageNum--;
		current.setText("��ǰҳ:" + String.valueOf(pageNum));
		userList.clear();
		List<User> userListNew = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +String.valueOf((pageNum-1)*pageSize), null);
		userList.addAll(userListNew);
		myListViewAdapter.notifyDataSetChanged();
		checkView();
	}
    /*
     * ��һҳ
     */
	private void nextView() {
		pageNum++;
		current.setText("��ǰҳ:" + String.valueOf(pageNum));
		userList.clear();
		List<User> userListNew = userDao.queryList(null, null, null, null, null, "create_time desc limit "+String.valueOf(pageSize)+ " offset " +String.valueOf((pageNum-1)*pageSize), null);
		userList.addAll(userListNew);
		myListViewAdapter.notifyDataSetChanged();
		checkView();
	}
    
    /*
     * �ı��Ƿ�ɵ��
     */
	public void checkView() {
		if (pageNum <= 1) {
			//��һҳ�ı�Ϊ���ɵ��״̬
			preView.setEnabled(false);
			preView.setBackgroundResource(R.drawable.left_press);
			//������С��ÿҳ��ʾ������
			if (totalCount <= pageSize) {
				//��һҳ�ı�Ϊ���ɵ��״̬
				nextView.setEnabled(false);
				nextView.setBackgroundResource(R.drawable.right_press);
			}else{
				nextView.setEnabled(true);
				nextView.setBackgroundResource(R.drawable.right_normal);
			}
		}//������-��ǰҳ*ÿҳ��ʾ������ <=ÿҳ��ʾ������
		else if (totalCount - (pageNum-1) * pageSize <= pageSize){
			//��һҳ�ı�Ϊ���ɵ��״̬,��һҳ��Ϊ�ɵ��
			nextView.setEnabled(false);
			nextView.setBackgroundResource(R.drawable.right_press);
			preView.setEnabled(true);
			preView.setBackgroundResource(R.drawable.left_normal);
		}else {
			//��һҳ��һҳ�ı�����Ϊ�ɵ��״̬
			preView.setEnabled(true);
			preView.setBackgroundResource(R.drawable.left_normal);
			nextView.setEnabled(true);
			nextView.setBackgroundResource(R.drawable.right_normal);
		}
	}
	
	/**
	 * ��������
	 * ������TODO
	 * @param u
	 */
	public void updateData(User u){
		userDao.update(u);
	}
	
	/**
	 * 
	 * ����������ID��ѯ����
	 * @param id
	 * @return
	 */
	public User queryDataById(int id){
		return (User)userDao.queryOne(id);
		
	}
	
	/**
	 * 
	 * ������ɾ������
	 * @param id
	 */
	public void delData(int id){
		userDao.delete(id);
		totalCount = userDao.queryCount(null, null);
		total.setText("������:" +String.valueOf(totalCount));
		current.setText("��ǰҳ:" + String.valueOf(pageNum));
		checkView();
	}
    
}