package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//危险品属性：泄露应急处理
@Table(name = "LeakEmMeans")
public class LeakEmMeans implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//UN编号
	@Column(name = "unNo")
	private String unNo;
	//应急处理
	@Column(name = "emMeans")
	private String emMeans;
	
	
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
	public String getEmMeans() {
		return emMeans;
	}
	public void setEmMeans(String emMeans) {
		this.emMeans = emMeans;
	}
	
	
}
