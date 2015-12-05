package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

@Table(name="Relations")
public class Relations implements Serializable {
	@Id
	@Column(name="_id")
	private int id;
	@Column(name="unNo")
	private String unNo;
	//主要危险类别，类别名称，图片名称与类别名称一致
	@Column(name="mainDangerType")
	private String mainDangerType;
	//副危险类别
	@Column(name="viceDangerType")
	private String viceDangerType;
	//(应急救援指南)指南编号
	@Column(name="guideNo")
	private String guideNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUnNo() {
		return unNo;
	}
	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}
	public String getMainDangerType() {
		return mainDangerType;
	}
	public void setMainDangerType(String mainDangerType) {
		this.mainDangerType = mainDangerType;
	}
	public String getViceDangerType() {
		return viceDangerType;
	}
	public void setViceDangerType(String viceDangerType) {
		this.viceDangerType = viceDangerType;
	}
	public String getGuideNo() {
		return guideNo;
	}
	public void setGuideNo(String guideNo) {
		this.guideNo = guideNo;
	}
	
	
}
