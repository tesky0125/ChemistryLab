package com.whut.chemistrylab.service;

import com.whut.chemistrylab.model.EmPlanCover;
import com.whut.chemistrylab.util.AppUtil;

public class EmPlanCoverService {
	//适合一次性加载到缓存
	public EmPlanCover getEmPlanCover() {
		EmPlanCover cover = new EmPlanCover();
		cover.setText1_1(AppUtil
				.getTextFormPath("text/emplan/1.1.txt", "GBK"));
		cover.setText1_2(AppUtil
				.getTextFormPath("text/emplan/1.2.txt", "GBK"));
		cover.setText2_1_1_1(AppUtil
				.getTextFormPath("text/emplan/2.1.1.1.txt", "GBK"));
		cover.setText2_1_1_2(AppUtil
				.getTextFormPath("text/emplan/2.1.1.2.txt", "GBK"));
		cover.setText2_1_1_3(AppUtil
				.getTextFormPath("text/emplan/2.1.1.3.txt", "GBK"));
		cover.setText2_1_1_4(AppUtil
				.getTextFormPath("text/emplan/2.1.1.4.txt", "GBK"));
		cover.setText2_1_1_5(AppUtil
				.getTextFormPath("text/emplan/2.1.1.5.txt", "GBK"));
		cover.setText2_1_2(AppUtil
				.getTextFormPath("text/emplan/2.1.2.txt", "GBK"));
		cover.setText2_1(AppUtil
				.getTextFormPath("text/emplan/2.1.txt", "GBK"));
		cover.setText2_2_1(AppUtil
				.getTextFormPath("text/emplan/2.2.1.txt", "GBK"));
		cover.setText2_2_2(AppUtil
				.getTextFormPath("text/emplan/2.2.2.txt", "GBK"));
		cover.setText2_2(AppUtil
				.getTextFormPath("text/emplan/2.2.txt", "GBK"));
		cover.setText2_3_1(AppUtil
				.getTextFormPath("text/emplan/2.3.1.txt", "GBK"));
		cover.setText2_3_2(AppUtil
				.getTextFormPath("text/emplan/2.3.2.txt", "GBK"));
		cover.setText2_3_3(AppUtil
				.getTextFormPath("text/emplan/2.3.3.txt", "GBK"));
		cover.setText2_3(AppUtil
				.getTextFormPath("text/emplan/2.3.txt", "GBK"));
		cover.setText3_1_1(AppUtil
				.getTextFormPath("text/emplan/3.1.1.txt", "GBK"));
		cover.setText3_1_2(AppUtil
				.getTextFormPath("text/emplan/3.1.2.txt", "GBK"));
		cover.setText3_1_3(AppUtil
				.getTextFormPath("text/emplan/3.1.3.txt", "GBK"));
		cover.setText3_1(AppUtil
				.getTextFormPath("text/emplan/3.1.txt", "GBK"));
		cover.setText3_2(AppUtil
				.getTextFormPath("text/emplan/3.2.txt", "GBK"));
		cover.setText3_3(AppUtil
				.getTextFormPath("text/emplan/3.3.txt", "GBK"));
		cover.setText3_4_1(AppUtil
				.getTextFormPath("text/emplan/3.4.1.txt", "GBK"));
		cover.setText3_4_2(AppUtil
				.getTextFormPath("text/emplan/3.4.2.txt", "GBK"));
		cover.setText3_4(AppUtil
				.getTextFormPath("text/emplan/3.4.txt", "GBK"));
		cover.setText3_5(AppUtil
				.getTextFormPath("text/emplan/3.5.txt", "GBK"));
		cover.setText3_6(AppUtil
				.getTextFormPath("text/emplan/3.6.txt", "GBK"));
		cover.setText3_7(AppUtil
				.getTextFormPath("text/emplan/3.7.txt", "GBK"));
		cover.setText3_8(AppUtil
				.getTextFormPath("text/emplan/3.8.txt", "GBK"));
		cover.setText3_9(AppUtil
				.getTextFormPath("text/emplan/3.9.txt", "GBK"));
		return cover;
	}

	//适合显示时再加载
	public String getEmPlanCover1_1() {
		return (AppUtil.getTextFormPath("text/emplan/1.1.txt", "GBK"));
	}

	public String getEmPlanCover1_2() {
		return (AppUtil.getTextFormPath("text/emplan/1.2.txt", "GBK"));
	}

	public String getEmPlanCover2_1_1_1() {
		return (AppUtil.getTextFormPath("text/emplan/2.1.1.1.txt", "GBK"));
	}

	public String getEmPlanCover2_1_1_2() {
		return (AppUtil.getTextFormPath("text/emplan/2.1.1.2.txt", "GBK"));
	}

	public String getEmPlanCover2_1_1_3() {
		return (AppUtil.getTextFormPath("text/emplan/2.1.1.3.txt", "GBK"));
	}

	public String getEmPlanCover2_1_1_4() {
		return (AppUtil.getTextFormPath("text/emplan/2.1.1.4.txt", "GBK"));
	}

	public String getEmPlanCover2_1_1_5() {
		return (AppUtil.getTextFormPath("text/emplan/2.1.1.5.txt", "GBK"));
	}

	public String getEmPlanCover2_1_2() {
		return (AppUtil.getTextFormPath("text/emplan/2.1.2.txt", "GBK"));
	}
	
	public String getEmPlanCover2_1() {
		return (AppUtil.getTextFormPath("text/emplan/2.1.txt", "GBK"));
	}
	
	public String getEmPlanCover2_2_1() {
		return (AppUtil.getTextFormPath("text/emplan/2.2.1.txt", "GBK"));
	}
	
	public String getEmPlanCover2_2_2() {
		return (AppUtil.getTextFormPath("text/emplan/2.2.2.txt", "GBK"));
	}
	
	public String getEmPlanCover2_2() {
		return (AppUtil.getTextFormPath("text/emplan/2.2.txt", "GBK"));
	}

	public String getEmPlanCover2_3_1() {
		return (AppUtil.getTextFormPath("text/emplan/2.3.1.txt", "GBK"));
	}

	public String getEmPlanCover2_3_2() {
		return (AppUtil.getTextFormPath("text/emplan/2.3.2.txt", "GBK"));
	}

	public String getEmPlanCover2_3_3() {
		return (AppUtil.getTextFormPath("text/emplan/2.3.3.txt", "GBK"));
	}
	
	public String getEmPlanCover2_3() {
		return (AppUtil.getTextFormPath("text/emplan/2.3.txt", "GBK"));
	}

	public String getEmPlanCover3_1_1() {
		return (AppUtil.getTextFormPath("text/emplan/3.1.1.txt", "GBK"));
	}

	public String getEmPlanCover3_1_2() {
		return (AppUtil.getTextFormPath("text/emplan/3.1.2.txt", "GBK"));
	}

	public String getEmPlanCover3_1_3() {
		return (AppUtil.getTextFormPath("text/emplan/3.1.3.txt", "GBK"));
	}
	
	public String getEmPlanCover3_1() {
		return (AppUtil.getTextFormPath("text/emplan/3.1.txt", "GBK"));
	}

	public String getEmPlanCover3_2() {
		return (AppUtil.getTextFormPath("text/emplan/3.2.txt", "GBK"));
	}

	public String getEmPlanCover3_3() {
		return (AppUtil.getTextFormPath("text/emplan/3.3.txt", "GBK"));
	}

	public String getEmPlanCover3_4_1() {
		return (AppUtil.getTextFormPath("text/emplan/3.4.1.txt", "GBK"));
	}

	public String getEmPlanCover3_4_2() {
		return (AppUtil.getTextFormPath("text/emplan/3.4.2.txt", "GBK"));
	}
	
	public String getEmPlanCover3_4() {
		return (AppUtil.getTextFormPath("text/emplan/3.4.txt", "GBK"));
	}

	public String getEmPlanCover3_5() {
		return (AppUtil.getTextFormPath("text/emplan/3.5.txt", "GBK"));
	}

	public String getEmPlanCover3_6() {
		return (AppUtil.getTextFormPath("text/emplan/3.6.txt", "GBK"));
	}

	public String getEmPlanCover3_7() {
		return (AppUtil.getTextFormPath("text/emplan/3.7.txt", "GBK"));
	}

	public String getEmPlanCover3_8() {
		return (AppUtil.getTextFormPath("text/emplan/3.8.txt", "GBK"));
	}

	public String getEmPlanCover3_9() {
		return (AppUtil.getTextFormPath("text/emplan/3.9.txt", "GBK"));
	}

}
