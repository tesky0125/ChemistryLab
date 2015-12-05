package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//Ӧ��Ԥ��
@Table(name = "EmPlan")
public class EmPlan implements Serializable{
	@Id
	@Column(name = "_id")
	private int id;
	//й©����
	@Column(name = "leakChm")
	private String leakChm;
	//�������
	@Column(name = "chmType")
	private String chmType;
	//й©�ص�
	@Column(name = "leakAddr")
	private String leakAddr;
	//й©Դͷ
	@Column(name = "leakSrc")
	private String leakSrc;
	//��������Ŀ��
	@Column(name = "enSenseTarget")
	private String enSenseTarget;
	//Ӧ���豸����
	@Column(name = "emDevCall")
	private String emDevCall;
	//����Ӧ���ж�
	@Column(name = "shipEmAction")
	private String shipEmAction;
	//ˮ��Ӧ���ж�
	@Column(name = "waterEmAction")
	private String waterEmAction;
	//��Ӧ����
	@Column(name = "respMach")
	private String respMach;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLeakChm() {
		return leakChm;
	}
	public void setLeakChm(String leakChm) {
		this.leakChm = leakChm;
	}
	public String getChmType() {
		return chmType;
	}
	public void setChmType(String chmType) {
		this.chmType = chmType;
	}
	public String getLeakAddr() {
		return leakAddr;
	}
	public void setLeakAddr(String leakAddr) {
		this.leakAddr = leakAddr;
	}
	public String getLeakSrc() {
		return leakSrc;
	}
	public void setLeakSrc(String leakSrc) {
		this.leakSrc = leakSrc;
	}
	public String getEnSenseTarget() {
		return enSenseTarget;
	}
	public void setEnSenseTarget(String enSenseTarget) {
		this.enSenseTarget = enSenseTarget;
	}
	public String getEmDevCall() {
		return emDevCall;
	}
	public void setEmDevCall(String emDevCall) {
		this.emDevCall = emDevCall;
	}
	public String getShipEmAction() {
		return shipEmAction;
	}
	public void setShipEmAction(String shipEmAction) {
		this.shipEmAction = shipEmAction;
	}
	public String getWaterEmAction() {
		return waterEmAction;
	}
	public void setWaterEmAction(String waterEmAction) {
		this.waterEmAction = waterEmAction;
	}
	public String getRespMach() {
		return respMach;
	}
	public void setRespMach(String respMach) {
		this.respMach = respMach;
	}
	
}
