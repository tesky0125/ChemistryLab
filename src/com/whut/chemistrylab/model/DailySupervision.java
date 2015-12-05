package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//日常监管
@Table(name = "DailySupervision")
public class DailySupervision implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//UN编号
	@Column(name = "unNo")
	private String unNo;
	//存储注意事项
	@Column(name = "storeAttention")
	private String storeAttention;
	//操作注意事项
	@Column(name = "operateAttention")
	private String operateAttention;
	
	
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
	public String getStoreAttention() {
		return storeAttention;
	}
	public void setStoreAttention(String storeAttention) {
		this.storeAttention = storeAttention;
	}
	public String getOperateAttention() {
		return operateAttention;
	}
	public void setOperateAttention(String operateAttention) {
		this.operateAttention = operateAttention;
	}
	
	
}
