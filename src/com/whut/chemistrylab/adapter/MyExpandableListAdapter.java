package com.whut.chemistrylab.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.download.DownloadProgressListener;
import com.ab.download.DownloadThread;
import com.ab.download.FileDownloader;
import com.ab.model.DownFile;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpThread;
import com.ab.util.AbStrUtil;
import com.whut.chemistrylab.R;
import com.whut.chemistrylab.global.Constant;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	
	private static final String TAG = "MyExpandableListAdapter";
	private static final boolean D = Constant.DEBUG;
	private Context mContext;
	private ArrayList<ArrayList<DownFile>> mDownFileGroupList = null;
	private String[] mDownFileGroupTitle = null;
	public HashMap<String,FileDownloader> mFileDownloaders = null;
	
	
	public MyExpandableListAdapter(Context context,ArrayList<ArrayList<DownFile>> downFileGroupList,String[] downFileGroupTitle){
		this.mContext = context;
		mDownFileGroupList = downFileGroupList;
		mDownFileGroupTitle = downFileGroupTitle;
		mFileDownloaders = new HashMap<String,FileDownloader>();
	}

	/**
	 * ��ȡָ����λ�á�ָ�����б�������б�������
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mDownFileGroupList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mDownFileGroupList.get(groupPosition).size();
	}

	/**
	 * �÷�������ÿ����ѡ������
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.down_items, parent, false);
		}
		final ViewHolder holder = new ViewHolder();
  	    holder.itemsIcon = (ImageView) convertView.findViewById(R.id.down_itemsIcon);
	    holder.itemsTitle = (TextView) convertView.findViewById(R.id.down_itemsTitle);
	    holder.itemsDesc = (TextView) convertView.findViewById(R.id.down_itemsDesc);
	    holder.operateBtn = (Button) convertView.findViewById(R.id.operateBtn);
	    holder.progress = (ProgressBar) convertView.findViewById(R.id.received_progress) ;
	    holder.received_progress_percent = (TextView) convertView.findViewById(R.id.received_progress_percent);
	    holder.received_progress_number = (TextView) convertView.findViewById(R.id.received_progress_number);
	    holder.received_progressBar = (RelativeLayout) convertView.findViewById(R.id.received_progressBar) ;
	    
	    holder.itemsIcon.setFocusable(false);
	    holder.operateBtn.setFocusable(false);
	    holder.progress.setFocusable(false);
	    
	    final DownFile mDownFile = (DownFile)getChild(groupPosition,childPosition);
        if (mDownFile != null) {
	          //holder.itemsIcon.setImageResource(mDownFile.getIcon());
	          holder.itemsTitle.setText(mDownFile.getName());
	          holder.itemsDesc.setText(mDownFile.getDescription());
	          if(mDownFile.getState() == Constant.undownLoad){
	        	  holder.operateBtn.setBackgroundResource(R.drawable.down_load);
	        	  holder.received_progressBar.setVisibility(View.GONE);
	        	  holder.itemsDesc.setVisibility(View.VISIBLE);
	        	  holder.progress.setProgress(0);
	        	  holder.received_progress_percent.setText(0+"%");
	        	  holder.received_progress_number.setText("0KB/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
	          }else if(mDownFile.getState() == Constant.downInProgress){
	        	  holder.operateBtn.setBackgroundResource(R.drawable.down_pause);
	        	  if(mDownFile.getDownLength()!=0 && mDownFile.getTotalLength()!=0){
		        	  int c = mDownFile.getDownLength()*100/mDownFile.getTotalLength();
		        	  holder.itemsDesc.setVisibility(View.GONE);
		        	  holder.received_progressBar.setVisibility(View.VISIBLE);
		        	  holder.progress.setProgress(c);
		        	  holder.received_progress_percent.setText(c+"%");
		        	  holder.received_progress_number.setText(AbStrUtil.getSizeDesc(mDownFile.getDownLength())+"/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
		          }
	          }else if(mDownFile.getState() == Constant.downLoadPause){
	        	  holder.operateBtn.setBackgroundResource(R.drawable.down_load);
	        	  //�����˶���
	        	  if(mDownFile.getDownLength()!=0 && mDownFile.getTotalLength()!=0){
		        	  int c = mDownFile.getDownLength()*100/mDownFile.getTotalLength();
		        	  holder.itemsDesc.setVisibility(View.GONE);
		        	  holder.received_progressBar.setVisibility(View.VISIBLE);
		        	  holder.progress.setProgress(c);
		        	  holder.received_progress_percent.setText(c+"%");
		        	  holder.received_progress_number.setText(AbStrUtil.getSizeDesc(mDownFile.getDownLength())+"/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
		          }else{
		        	  holder.itemsDesc.setVisibility(View.VISIBLE);
		        	  holder.received_progressBar.setVisibility(View.GONE);
		        	  holder.progress.setProgress(0);
		        	  holder.received_progress_percent.setText(0+"%");
		        	  holder.received_progress_number.setText("0KB/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
		          }
	          }else if(mDownFile.getState() == Constant.downloadComplete){
	        	  holder.operateBtn.setBackgroundResource(R.drawable.down_delete);
	        	  holder.received_progressBar.setVisibility(View.GONE);
	        	  holder.itemsDesc.setVisibility(View.VISIBLE);
	          }
	          
	          final DownloadProgressListener mDownloadProgressListener = new DownloadProgressListener() {
					//ʵʱ��֪�ļ��Ѿ����ص����ݳ���
					@Override
					public void onDownloadSize(final int size) {
						final int c = size*100/mDownFile.getTotalLength();
		        		if(c!=holder.progress.getProgress()){
		        			holder.progress.post(new Runnable(){
								@Override
								public void run() {
									holder.progress.setProgress(c);
				    				holder.received_progress_percent.setText(c+"%");
				    				holder.received_progress_number.setText(AbStrUtil.getSizeDesc(size)+"/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
								}
		        			});
		        		}
						if(mDownFile.getTotalLength() == size){
							if(D)Log.d(TAG, "�������:"+size);
							mDownFile.setState(Constant.downloadComplete);
			        		//�������
							mDownFileGroupList.get(1).remove(mDownFile);
							mDownFileGroupList.get(0).add(mDownFile);
							holder.progress.post(new Runnable(){
								@Override
								public void run() {
									notifyDataSetChanged();
								}
		        			});
							
		        		}
					}
			  };
	          
	          //����ť�¼�
	          holder.operateBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
							//��sd��
							((AbActivity)mContext).showToast("û�ҵ��洢��");
							return;
						}
						
						if(mDownFile.getState() == Constant.undownLoad || mDownFile.getState() == Constant.downLoadPause){
				            //����
							holder.itemsDesc.setVisibility(View.GONE);
				        	holder.received_progressBar.setVisibility(View.VISIBLE);
				        	holder.operateBtn.setBackgroundResource(R.drawable.down_pause);
				        	mDownFile.setState(Constant.downInProgress);
				        	AbHttpThread mAbHttpThread = new AbHttpThread();
							final AbHttpItem item = new AbHttpItem();
							item.callback = new AbHttpCallback() {

								@Override
								public void update() {
								}

								@Override
								public void get() {
									try {
										//��ʼ�����ļ�
										FileDownloader loader = new FileDownloader(mContext,mDownFile,1);
										mFileDownloaders.put(mDownFile.getDownUrl(), loader);
										loader.download(mDownloadProgressListener);
									} catch (Exception e) {
										e.printStackTrace();
									}
							  };
							};
							mAbHttpThread.download(item);
							
				        	
						}else if(mDownFile.getState()==Constant.downInProgress){
							//��ͣ
							holder.operateBtn.setBackgroundResource(R.drawable.down_load);
							mDownFile.setState(Constant.undownLoad);
							FileDownloader mFileDownloader = mFileDownloaders.get(mDownFile.getDownUrl());
							//�ͷ�ԭ�����߳�
							if(mFileDownloader!=null){
								mFileDownloader.setFlag(false);
								DownloadThread mDownloadThread = mFileDownloader.getThreads();
								if(mDownloadThread!=null){
									mDownloadThread.setFlag(false);
									mFileDownloaders.remove(mDownFile.getDownUrl());
									mDownloadThread = null;
								}
								mFileDownloader = null;
							}
						}else if(mDownFile.getState()==Constant.downloadComplete){
							//ɾ��
							mDownFileGroupList.get(0).remove(mDownFile);
							mDownFile.setState(Constant.undownLoad);
							mDownFileGroupList.get(1).add(mDownFile);
							notifyDataSetChanged();
						}
						
					}
				});
	          
	          
        }
		return convertView;
	}

	/**
	 * ��ȡָ����λ�ô���������
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return mDownFileGroupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mDownFileGroupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * �÷�������ÿ����ѡ������
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.down_title, parent, false);
		}
		TextView mTextView = (TextView)convertView.findViewById(R.id.title_text);
		mTextView.setText(mDownFileGroupTitle[groupPosition]);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	/**
	 * �������ͷ��߳�
	 */
	public void releaseThread() {
		 Iterator it = mFileDownloaders.entrySet().iterator();   
		 FileDownloader mFileDownloader = null;
		 while (it.hasNext()) {
		    Map.Entry e = (Map.Entry) it.next(); 
		    mFileDownloader = (FileDownloader)e.getValue();
		    //System.out.println("Key: " + e.getKey() + "; Value: " + e.getValue());   
		    if(mFileDownloader!=null){
		    	mFileDownloader.setFlag(false);
				DownloadThread mDownloadThread = mFileDownloader.getThreads();
				if(mDownloadThread!=null){
					mDownloadThread.setFlag(false);
					mDownloadThread = null;
				}
				mFileDownloader = null;
			}
		 }   
	}
	
	public class ViewHolder {
		public ImageView itemsIcon;
		public TextView itemsTitle;
		public TextView itemsDesc;
		public Button operateBtn;
		public ProgressBar progress;
		public TextView received_progress_percent;
		public TextView received_progress_number;
		public RelativeLayout received_progressBar;
	}
}
