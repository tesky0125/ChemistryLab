package com.whut.chemistrylab.example;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.ab.activity.AbActivity;
import com.ab.db.DownFileDao;
import com.ab.model.DownFile;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpPool;
import com.ab.titlebar.AbTitleBar;
import com.ab.util.AbFileUtil;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.R.drawable;
import com.whut.chemistrylab.R.id;
import com.whut.chemistrylab.R.layout;
import com.whut.chemistrylab.R.string;
import com.whut.chemistrylab.adapter.MyExpandableListAdapter;
import com.whut.chemistrylab.global.Constant;
import com.whut.chemistrylab.global.ChmApplication;

public class ExpandListActivity extends AbActivity{
	
	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
	
	private DownFileDao mDownFileDao = null;
	private ArrayList<DownFile> mDownFileList1 = null;
	private ArrayList<DownFile> mDownFileList2 = null;
	private ArrayList<ArrayList<DownFile>> mGroupDownFileList = null;
	private MyExpandableListAdapter mExpandableListAdapter = null;
	private AbHttpPool mAbHttpPool = null;
    
    @ViewInject(id=R.id.mExpandableListView) ExpandableListView mExpandListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.down_list);
		application = (ChmApplication)abApplication;
		
		AbTitleBar mAbTitleBar = this.getTitleBar();
		mAbTitleBar.setTitleText(R.string.lookup_first);
		mAbTitleBar.setLogo(R.drawable.button_selector_back);
		mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
		mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
		mAbTitleBar.setLogoLine(R.drawable.line);
	    
        mDownFileDao = DownFileDao.getInstance(this);
        mDownFileList1 = new ArrayList<DownFile>();
        mDownFileList2 = new ArrayList<DownFile>();
        mGroupDownFileList = new ArrayList<ArrayList<DownFile>>();
        mGroupDownFileList.add(mDownFileList1);
        mGroupDownFileList.add(mDownFileList2);
        
        mAbHttpPool = AbHttpPool.getInstance();
        
        String[] mDownFileGroupTitle = new String[]{this.getResources().getString(R.string.download_complete_title),
        		this.getResources().getString(R.string.undownLoad_title)};
        
        //����һ��BaseExpandableListAdapter����
      	mExpandableListAdapter = new MyExpandableListAdapter(this,mGroupDownFileList,mDownFileGroupTitle);
      	
      	mExpandListView.setAdapter(mExpandableListAdapter);
      	
        //Indicator����
        int width = getWindowManager().getDefaultDisplay().getWidth();
        mExpandListView.setIndicatorBounds(width-40, width-25);
        mExpandListView.setChildIndicatorBounds(5, 53);
        
        initDownFileList();
     }

	
	
	/**
	 * ��ʼ�������ļ�
	 */
	private void initDownFileList() {

		List<DownFile> mDownFileList = new ArrayList<DownFile>();
		
		DownFile mDownFile1 = new DownFile();
		mDownFile1.setName("�ǲ���");
		mDownFile1.setDescription("������Ű���ƻ����˿���̵���Ϸ����");
		mDownFile1.setPakageName("");
		mDownFile1.setState(Constant.undownLoad);
		mDownFile1.setIcon(String.valueOf(R.drawable.image_bg));
		mDownFile1.setDownUrl("http://gdown.baidu.com/data/wisegame/8c459c47b29219a4/kabuka_201.apk");
		mDownFileList.add(mDownFile1); 
		
		DownFile mDownFile2 = new DownFile();
		mDownFile2.setName("����ˮ��");
		mDownFile2.setPakageName("");
		mDownFile2.setDescription("��Ʒ����������Ϸ���淨�׶���������");
		mDownFile2.setState(Constant.undownLoad);
		mDownFile2.setIcon(String.valueOf(R.drawable.image_bg));
		mDownFile2.setDownUrl("http://gdown.baidu.com/data/wisegame/2fe5e761daa8916b/gunbashuiguo.apk");
		mDownFileList.add(mDownFile2); 
		
		DownFile mDownFile3 = new DownFile();
		mDownFile3.setName("è����ս2");
		mDownFile3.setPakageName("");
		mDownFile3.setDescription("�Թ�������è���Ʋ�����������ĳ���");
		mDownFile3.setState(Constant.undownLoad);
		mDownFile3.setIcon(String.valueOf(R.drawable.image_bg));
		mDownFile3.setDownUrl("http://gdown.baidu.com/data/wisegame/377ac8656deb965f/CatWar2_19.apk");
		mDownFileList.add(mDownFile3); 
		
		//��ʼ���ļ��Ѿ����صĳ��ȣ����������صĽ���
		for(DownFile mDownFile:mDownFileList){
			  //��������
			  DownFile mDownFileT = mDownFileDao.getDownFile(mDownFile.getDownUrl());
	          if(mDownFileT != null){
	        	  mDownFile = mDownFileT;
	        	  if(mDownFile.getDownLength() == mDownFile.getTotalLength()){
	    	    	  mDownFile.setState(Constant.downloadComplete);
	    	    	  mDownFileList1.add(mDownFile);
	    	    	  mExpandableListAdapter.notifyDataSetChanged();
				  }else{
					  //��ʾΪ��ͣ״̬
		        	  mDownFile.setState(Constant.downLoadPause);
	        	      mDownFileList2.add(mDownFile);
	        	      mExpandableListAdapter.notifyDataSetChanged();
				  }
	          }else{
	        	    final DownFile mDownNewFile = mDownFile ;
					AbHttpItem item = new AbHttpItem();
					item.callback = new AbHttpCallback() {

						@Override
						public void update() {
			        	    mDownFileList2.add(mDownNewFile);
			        	    mExpandableListAdapter.notifyDataSetChanged();
						}

						@Override
						public void get() {
							try {
								  int totalLength = AbFileUtil.getContentLengthFormUrl(mDownNewFile.getDownUrl());
								  mDownNewFile.setTotalLength(totalLength);
								  mDownNewFile.setState(Constant.undownLoad);
							} catch (Exception e) {
								e.printStackTrace();
							}
					  };
					};
					mAbHttpPool.download(item);
	          }
	    }
	}
	
	
	@Override
	public void finish() {
		super.finish();
		//�ͷ����е������߳�
		mExpandableListAdapter.releaseThread();

		
	}
	

}