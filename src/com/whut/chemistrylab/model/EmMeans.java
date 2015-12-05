package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//���ȴ�ʩ
@Table(name = "EmMeans")
public class EmMeans implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//UN���
	@Column(name = "unNo")
	private String unNo;
	//Ƥ���Ӵ�
	@Column(name = "skinTouch")
	private String skinTouch;
	//�۾��Ӵ�
	@Column(name = "eyeTouch")
	private String eyeTouch;
	//����
	@Column(name = "suction")
	private String suction;
	//ʳ��
	@Column(name = "ingestion")
	private String ingestion;
	
	
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
	public String getSkinTouch() {
		return skinTouch;
	}
	public void setSkinTouch(String skinTouch) {
		this.skinTouch = skinTouch;
	}
	public String getEyeTouch() {
		return eyeTouch;
	}
	public void setEyeTouch(String eyeTouch) {
		this.eyeTouch = eyeTouch;
	}
	public String getSuction() {
		return suction;
	}
	public void setSuction(String suction) {
		this.suction = suction;
	}
	public String getIngestion() {
		return ingestion;
	}
	public void setIngestion(String ingestion) {
		this.ingestion = ingestion;
	}
	
	
}
