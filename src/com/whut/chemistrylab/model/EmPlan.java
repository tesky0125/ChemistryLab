package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//应急预案
@Table(name = "EmPlan")
public class EmPlan implements Serializable{
	@Id
	@Column(name = "_id")
	private int id;
	//泄漏货种
	@Column(name = "leakChm")
	private String leakChm;
	//物质类别
	@Column(name = "chmType")
	private String chmType;
	//泄漏地点
	@Column(name = "leakAddr")
	private String leakAddr;
	//泄漏源头
	@Column(name = "leakSrc")
	private String leakSrc;
	//环境敏感目标
	@Column(name = "enSenseTarget")
	private String enSenseTarget;
	//应急设备调用
	@Column(name = "emDevCall")
	private String emDevCall;
	//船舶应急行动
	@Column(name = "shipEmAction")
	private String shipEmAction;
	//水面应急行动
	@Column(name = "waterEmAction")
	private String waterEmAction;
	//响应机制
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
