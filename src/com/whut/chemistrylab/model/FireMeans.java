package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//危险品属性：消防措施
@Table(name = "FireMeans")
public class FireMeans implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//UN编号
	@Column(name = "unNo")
	private String unNo;
	//危险特性
	@Column(name = "dangerAttr")
	private String dangerAttr;
	//有害物燃烧产物
	@Column(name = "pestCombustion")
	private String pestCombustion;
	//灭火方法
	@Column(name = "putFireMean")
	private String putFireMean;
	
	
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
	public String getDangerAttr() {
		return dangerAttr;
	}
	public void setDangerAttr(String dangerAttr) {
		this.dangerAttr = dangerAttr;
	}
	public String getPestCombustion() {
		return pestCombustion;
	}
	public void setPestCombustion(String pestCombustion) {
		this.pestCombustion = pestCombustion;
	}
	public String getPutFireMean() {
		return putFireMean;
	}
	public void setPutFireMean(String putFireMean) {
		this.putFireMean = putFireMean;
	}
	
	
}
