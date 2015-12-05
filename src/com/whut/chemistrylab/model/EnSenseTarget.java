package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//Ӧ��Ԥ������������Ŀ��
@Table(name = "EnSenseTarget")
public class EnSenseTarget implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//��������Ŀ��ID//ע�⣺�������һ����ĸt
	@Column(name = "enSensetNo")
	private String enSensetNo;
	//����Ŀ�����ID
	@Column(name = "senseTargetNo")
	private String senseTargetNo;
	//Ԥ����Ⱦ��ִ����ʱ��
	@Column(name = "arrMinTime")
	private String arrMinTime;
	//Ԥ����Ⱦ��ִ��ʱ��
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
