package com.whut.chemistrylab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.gsm.SmsManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.titlebar.AbTitleBar;
import com.ab.view.sliding.AbSlidingTabView;
import com.whut.chemistrylab.emcomm.Fragment_EmMember;
import com.whut.chemistrylab.emcomm.Fragment_Experts;
import com.whut.chemistrylab.emcomm.Fragment_Headquaters;
import com.whut.chemistrylab.global.ChmApplication;
import com.whut.chemistrylab.listener.IInFragmentCallback;
import com.whut.chemistrylab.listener.IInActivityCallback;
import com.whut.chemistrylab.model.EmMember;
import com.whut.chemistrylab.model.Experts;
import com.whut.chemistrylab.model.Headquaters;
import com.whut.chemistrylab.service.EmMemberListService;
import com.whut.chemistrylab.service.ExpertsListService;
import com.whut.chemistrylab.service.HeadquatersListService;
import com.whut.chemistrylab.util.AppUtil;

public class EmCommTabActivity extends AbActivity implements IInActivityCallback{
	
	private ChmApplication application;
	private AbTitleBar mAbTitleBar = null;
	HeadquatersListService headquatersListService=null;
	ExpertsListService expertsListService=null;
	EmMemberListService emMemberListService=null;
	
	@ViewInject(id=R.id.mAbSlidingTabView) AbSlidingTabView mAbSlidingTabView;
	
	private IInFragmentCallback mInFragmentListener_1;  
	private IInFragmentCallback mInFragmentListener_2;  
	private IInFragmentCallback mInFragmentListener_3;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.frag_sliding_tab);
		FinalActivity.initInjectedView(this);
		
		application = (ChmApplication) abApplication;
		headquatersListService=new HeadquatersListService(this);
		expertsListService=new ExpertsListService(this);
		emMemberListService=new EmMemberListService(this);
		
		initTitleBarLayout();
		initTitleRightLayout();

		
		//禁止滑动
		/*mAbSlidingTabView.getViewPager().setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});*/

		Fragment_Headquaters page1 = new Fragment_Headquaters();//指挥部
		Fragment_Experts page2 = new Fragment_Experts();//专家组
		Fragment_EmMember page3 = new Fragment_EmMember();//应急人员
		
		List<Fragment> mFragments = new ArrayList<Fragment>();
		mFragments.add(page1);
		mFragments.add(page2);
		mFragments.add(page3);
		
		List<String> tabTexts = new ArrayList<String>();
		tabTexts.add("指挥部");
		tabTexts.add("专家组");
		tabTexts.add("应急人员");
		mAbSlidingTabView.addItemViews(tabTexts, mFragments);
		
		mAbSlidingTabView.setTabColor(Color.BLACK);
		mAbSlidingTabView.setTabSelectColor(Color.rgb(86, 186, 70));
		mAbSlidingTabView.setTabLayoutBackground(R.drawable.slide_top);

		
	}
	
	private void initTitleBarLayout() {
		mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.main_second_btn);
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


	private void initTitleRightLayout() {
		mAbTitleBar.clearRightView();
		View searchView=mInflater.inflate(R.layout.btn_search, null);
		final View rightViewMenu = mInflater.inflate(R.layout.btn_menu, null);
		mAbTitleBar.addRightView(rightViewMenu);
		mAbTitleBar.addRightView(searchView);
		
    	Button searchBtn=(Button) searchView.findViewById(R.id.searchBtn);
    	Button menu = (Button)rightViewMenu.findViewById(R.id.menuBtn);
    	
    	searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//System.out.println("1.mViewListener.onCallback(null, null);");
				
				processPreSearch();
				
			}

			
		});
    	
    	menu.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View v) {
 				PopupWindow window=makePopupWindow(EmCommTabActivity.this);
				//window.showAtLocation();
				window.showAsDropDown(rightViewMenu);
 			}
         });
    	
	}
	
	private void processPreSearch() {
		int index=mAbSlidingTabView.getViewPager().getCurrentItem();
		//showToast("page index:"+index);
		
		switch(index){
		case 0:
			mInFragmentListener_1.onExpand();
			break;
		case 1:
			mInFragmentListener_2.onExpand();
			break;
		case 2:
			mInFragmentListener_3.onExpand();
			break;
		}
	}
	
	private PopupWindow makePopupWindow(Context cx) {
		LayoutInflater mLayoutInflater = LayoutInflater.from(cx);
	    LinearLayout layout_window = (LinearLayout) mLayoutInflater.inflate(  
	            R.layout.layout_popwindow, null, true);  
	    
	    final PopupWindow window = new PopupWindow(cx);
	    window.setContentView(layout_window);
	    
	    ListView list_popwondow=(ListView) layout_window.findViewById(R.id.list_popwindow);
	    
	    List<Map<String,String>> listData = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("item_name", "搜索联系人");
		listData.add(map);
		map = new HashMap<String,String>();
		map.put("item_name", "群发短信");
		listData.add(map);

		
	    list_popwondow.setAdapter(new SimpleAdapter(cx, listData, R.layout.list_items_popwindow,
				new String[]{"item_name"}, new int[]{R.id.item_name}));
	    
	    list_popwondow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				switch(pos){
				case 0:
					processPreSearch();
					break;
				case 1:
					processPreSend();
					break;
				}
				
				window.dismiss();
			}
		});
	    
	    setLvFullHeight(list_popwondow);
	    
	    final Resources res = cx.getResources();
	    window.setBackgroundDrawable(new ColorDrawable(res.getColor(R.color.popwindow_background)));
	    
	    window.setWidth(res.getDimensionPixelSize(R.dimen.page_window_width));
//	    window.setHeight(res.getDimensionPixelSize(R.dimen.page_window_height));
//	    window.setWidth(LayoutParams.WRAP_CONTENT);
	    window.setHeight(LayoutParams.WRAP_CONTENT);
	    
	    window.setAnimationStyle(R.style.AnimationFade);
	    window.setOutsideTouchable(true);
	    window.setTouchable(true);
	    window.setFocusable(true); 
	    
	    return window;
	}
	
	/**
	 * 动态改变listView的高度
	 * @param lv
	 */
	private void setLvFullHeight(ListView lv){
		ListAdapter listAdapter = lv.getAdapter();  
	    if (listAdapter == null) { 
	        return; 
	    } 
		int totalHeight = 0;  
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目  
			View listItem = listAdapter.getView(i, null, lv);  
			listItem.measure(0, 0); //计算子项View 的宽高  
			totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度  
		}  
		  
		ViewGroup.LayoutParams params = lv.getLayoutParams();  
		params.height = totalHeight + (lv.getDividerHeight() * (listAdapter.getCount() - 1));  

		lv.setLayoutParams(params);  
	}


	private void processPreSend() {
		int index=mAbSlidingTabView.getViewPager().getCurrentItem();
		//showToast("page index:"+index);
		
		switch(index){
		case 0:
			mInFragmentListener_1.onPreSend();
			break;
		case 1:
			mInFragmentListener_2.onPreSend();
			break;
		case 2:
			mInFragmentListener_3.onPreSend();
			break;
		}
	}
    
    @Override  
    public void onAttachFragment(Fragment fragment) {  
        // TODO Auto-generated method stub  
          
        try {  
        	IInFragmentCallback mExpandListener=(IInFragmentCallback) fragment;
        	
        	if(mExpandListener instanceof Fragment_Headquaters)
        	{
        		mInFragmentListener_1=mExpandListener;
        	}
        	else if(mExpandListener instanceof Fragment_Experts)
        	{
        		mInFragmentListener_2=mExpandListener;
        	}
        	else if(mExpandListener instanceof Fragment_EmMember)
        	{
        		mInFragmentListener_3=mExpandListener;
        	}

        } catch (Exception e) {  
        	throw new ClassCastException(fragment.toString() + "must implements IExpandCallback,ISendSmsCallback");  
        }  
          
        super.onAttachFragment(fragment);  
    }  
    
    @Override
	public void onPopup(Bundle data) {
		// TODO Auto-generated method stub
    	this.showDialog(Dialog_Index_Handle, data);
    	
	}
    
	private static final int Dialog_Index_Handle = 1;	
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle data) {
		// TODO Auto-generated method stub	
		
		switch(id){
		case Dialog_Index_Handle:
			List<Map<String,String>> handlelist = new ArrayList<Map<String,String>>();
			Map<String,String> map = new HashMap<String,String>();
			map.put("item_name", "发送短信");//0
			handlelist.add(map);
			map = new HashMap<String,String>();
			map.put("item_name", "发送彩信");//1
			handlelist.add(map);
			map = new HashMap<String,String>();//2
			map.put("item_name", "拨打电话");
			handlelist.add(map);
			map = new HashMap<String,String>();//3
			map.put("item_name", "群发短信");
			handlelist.add(map);
			
			return createPopupDialog(Dialog_Index_Handle,handlelist,data);			

		default:
			break;
		}
		return super.onCreateDialog(id,data);
	}

	/**
	 * 生成弹出对话框
	 * @param index：指向生成哪个对话框
	 * @param list：对话框中list数据
	 * @param data：为点击list的item传送的数据
	 * @return
	 */
	private Dialog createPopupDialog(final int dlgindex,List<Map<String,String>> list,final Bundle data){
		
		LinearLayout layout=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_popup, null, false);
		ListView lv = (ListView) layout.findViewById(R.id.list_popup);
		layout.removeView(lv);
		//lv.setCacheColorHint(Color.argb(0, 0, 0, 0));//使用父背景
		lv.setAdapter(new SimpleAdapter(this, list, R.layout.list_items_popup,
				new String[]{"item_name"}, new int[]{R.id.item_name}));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> lv, View v, int position,
					long id) {
				// TODO Auto-generated method stub
//showToast("item click .position:"+position);
				processDialogItem(dlgindex,position,data);
				removeDialog(dlgindex);
			}
		});
		
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("请选择");//标题为联系人姓名		
		builder.setView(lv);
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				removeDialog(dlgindex);
			}
		});
		return  builder.create();
	}
	
	private void processDialogItem(int dlgindex, int position,Bundle data) {
		// TODO Auto-generated method stub	

		if(dlgindex == Dialog_Index_Handle){
			switch(position){
			case 0://发送短信
				processSendSMS(data);
				break;
			case 1://发送彩信
				processSendMMS(data);
				break;
			case 2://拨打电话
				processDialCall(data);
				break;
			case 3://群发短信
				//processSendSMSAll(data);//选择所有联系人并发送
				processPreSend();//转入用户选择联系人界面
				break;
			}
		}
		
	}
	
	private void processSendSMS(Bundle data){
		
		//sendSMSDirect("5556","THIS IS HELLO.Direct");
		//sendSMSIndirect("5556", "THIS IS HELLO.Indirect");
		
		int fragindex = data.getInt("index");
		
    	String phone="";
		switch(fragindex){
		case 1:
			Headquaters entity_1 = (Headquaters) data.getSerializable("entity");
			phone=entity_1.getContact();
			break;
		case 2:
			Experts entity_2 = (Experts) data.getSerializable("entity");
			phone=entity_2.getContact();
			break;
		case 3:
			EmMember entity_3 = (EmMember) data.getSerializable("entity");
			phone=entity_3.getContact();
			break;

		default:
				break;
		}
		
		if(AppUtil.isPhoneNumberValid(phone)){
			sendSMSIndirect(phone, "");
		}else{
			showToast(this.getResources().getString(R.string.err_phone));
		}
		
	}
 
	
	private void processSendMMS(Bundle data) {
		// TODO Auto-generated method stub
		//sendMMSIndirect("5556","","subject","content");
		
		int fragindex = data.getInt("index");
    	
		String phone="";
		switch(fragindex){
		case 1:
			Headquaters entity_1 = (Headquaters) data.getSerializable("entity");
			phone=entity_1.getContact();
			break;
		case 2:
			Experts entity_2 = (Experts) data.getSerializable("entity");
			phone=entity_2.getContact();
			break;
		case 3:
			EmMember entity_3 = (EmMember) data.getSerializable("entity");
			phone=entity_3.getContact();
			break;

		default:
				break;
		}
		
		if(AppUtil.isPhoneNumberValid(phone)){
			sendMMSIndirect(phone,"","","");
			//sendSMSIndirect(phone,"");
		}else{
			showToast(this.getResources().getString(R.string.err_phone));
		}
	}

	
	private void processDialCall(Bundle data) {
		// TODO Auto-generated method stub
		//dialCallIndirect("5556");
		
		int fragindex = data.getInt("index");
    	
		String phone="";
		switch(fragindex){
		case 1:
			Headquaters entity_1 = (Headquaters) data.getSerializable("entity");
			phone=entity_1.getContact();
			break;
		case 2:
			Experts entity_2 = (Experts) data.getSerializable("entity");
			phone=entity_2.getContact();
			break;
		case 3:
			EmMember entity_3 = (EmMember) data.getSerializable("entity");
			phone=entity_3.getContact();
			break;

		default:
				break;
		}
		
		if(AppUtil.isPhoneNumberValid(phone)){
			dialCallIndirect(phone);
		}else{
			showToast(this.getResources().getString(R.string.err_phone));
		}
	}

	private void processSendSMSAll(Bundle data){
		List<String> phones=null;
				
		int fragindex = data.getInt("index");
    	
		switch(fragindex){
		case 1:
			phones=headquatersListService.getHeadquatersPhoneList();
			break;
		case 2:
			phones=expertsListService.getExpertsPhoneList();
			break;
		case 3:
			phones=emMemberListService.getEmMemberPhoneList();
			break;

		default:
				break;
		}
		
		sendSMSIndirectAll(phones,"");
		
	}
	
	
	private void sendSMSDirect(String phone, String message){
		//退出时需要将广播监听器取消
		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,
		        0);
		this.registerReceiver(new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context _context, Intent _intent) {
		        switch (getResultCode()) {
		        case Activity.RESULT_OK:
		        	Toast.makeText(EmCommTabActivity.this,
		        "短信发送成功", Toast.LENGTH_SHORT)
		        .show();
		        break;
		        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
		        break;
		        case SmsManager.RESULT_ERROR_RADIO_OFF:
		        break;
		        case SmsManager.RESULT_ERROR_NULL_PDU:
		        break;
		        }
		    }
		}, new IntentFilter(SENT_SMS_ACTION));

		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,
		       deliverIntent, 0);
		this.registerReceiver(new BroadcastReceiver() {
		   @Override
		   public void onReceive(Context _context, Intent _intent) {
		       Toast.makeText(EmCommTabActivity.this,
		  "收信人已经成功接收", Toast.LENGTH_SHORT)
		  .show();
		   }
		}, new IntentFilter(DELIVERED_SMS_ACTION));

		
		SmsManager smsManager = SmsManager.getDefault();  
		List<String> divideContents = smsManager.divideMessage(message);  
		for (String text : divideContents) {    
		    smsManager.sendTextMessage(phone, null, text, sentPI, deliverPI);    
		}  
	}
	
		
	private void sendSMSIndirect(String phone, String message){
		Uri uri = Uri.parse("smsto:"+phone);            
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);            
		it.putExtra("sms_body", message);            
		this.startActivity(it);  
	}
	
	//权限出错！
	private void sendMMSIndirect(String phone,String url,String subject,String message){
		
        try {
        	Intent intent = new Intent(Intent.ACTION_SEND);
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		if(url!=null && !url.equals("")) intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
    		intent.putExtra("subject", ""); 
    		intent.putExtra("address", phone); 
    		intent.putExtra("sms_body", message); 
    		intent.putExtra(Intent.EXTRA_TEXT, "");
    		intent.setType("image/*");
    		//防止手机厂商定制，出现找不到该类抛出异常
			//Class.forName("com.android.mms.ui.ComposeMessageActivity");
			intent.setClassName("com.android.mms","com.android.mms.ui.ComposeMessageActivity");
			startActivity(intent);
		} catch (/*ClassNotFound*/Exception e) {
			showToast("该机型不支持该权限,请在短信中插入媒体文件!");
		}
		
	}
	

	private void dialCallIndirect(String phone){
		Uri uri = Uri.parse("tel:"+phone);
		Intent it = new Intent(Intent.ACTION_CALL, uri);
		//Intent it = new Intent(Intent.ACTION_DIAL, uri);   
		this.startActivity(it);  
	}


	private void sendSMSIndirectAll(List<String> phones, String message){
		
		String phone="";
		for(String p : phones)
			phone=phone+p+";";
		phone=phone.substring(0, phone.length()-2);
		
		Uri uri = Uri.parse("smsto:"+phone);            
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);            
		it.putExtra("sms_body", "");            

		this.startActivity(it);  
	}

	
	@Override
	public void onSendSms(List<String> list) {
		// TODO Auto-generated method stub
		sendSMSIndirectAll(list,"");
	}

}