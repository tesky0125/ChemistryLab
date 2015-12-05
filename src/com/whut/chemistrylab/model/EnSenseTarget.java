package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//应急预案：环境敏感目标
@Table(name = "EnSenseTarget")
public class EnSenseTarget implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//环境敏感目标ID//注意：这里多了一个字母t
	@Column(name = "enSensetNo")
	private String enSensetNo;
	//敏感目标类别ID
	@Column(name = "senseTargetNo")
	private String senseTargetNo;
	//预计污染物抵达最短时间
	@Column(name = "arrMinTime")
	private String arrMinTime;
	//预计污染物抵达最长时间
	@Column(name = "arrMaxTime")
	private String arrMaxTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEnSensetNo() {
		return enSensetNo;
	}
	public void setEnSensetNo(String enSensetNo) {
		this.enSensetNo = enSensetNo;
	}
	public String getSenseTargetNo() {
		return senseTargetNo;
	}
	public void setSenseTargetNo(String senseTargetNo) {
		this.senseTargetNo = senseTargetNo;
	}
	public String getArrMinTime() {
		return arrMinTime;
	}
	public void setArrMinTime(String arrMinTime) {
		this.arrMinTime = arrMinTime;
	}
	public String getArrMaxTime() {
		return arrMaxTime;
	}
	public void setArrMaxTime(String arrMaxTime) {
		this.arrMaxTime = arrMaxTime;
	}
	
}
