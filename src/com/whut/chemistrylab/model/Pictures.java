package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;
import com.whut.chemistrylab.global.Constant;

//Σ������ͼƬ��Ϣ
@Table(name = "Pictures")
public class Pictures implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//Σ������-��Ӧ-ͼƬ����
	@Column(name = "dangerType")
	private String dangerType;
	//ͼƬ·��
	@Column(name = "picLoc")
	private String picLoc;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDangerType() {
		return dangerType;
	}
	public void setDangerType(String dangerType) {
		this.dangerType = dangerType;
	}
	public String getPicLoc() {
		return picLoc;
	}
	public void setPicLoc(String picLoc) {
		this.picLoc = picLoc;
	}
	@Override
	public String toString() {
		if(this==null) return null;
		return picLoc + dangerType + Constant.imageFormat;
	}
	
}
