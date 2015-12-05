package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//Ó¦¼±Ö¸ÄÏ
@Table(name = "EmGuide")
public class EmGuide implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//Ó¦¼±¾ÈÔ®Ö¸ÄÏ±àºÅ
	@Column(name = "guideNo")
	private String guideNo;
	//½¡¿µÍþÐ²
	@Column(name = "healthThreat")
	private String healthThreat;
	//»ðÔÖ»ò±¬Õ¨
	@Column(name = "fireExplosion")
	private String fireExplosion;
	//Ó¦¼±·´Ó¦
	@Column(name = "emAction")
	private String emAction;
	//·À»¤
	@Column(name = "protection")
	private String protection;
	//ÏÖ³¡ÊèÉ¢
	@Column(name = "sceneEvacuate")
	private String sceneEvacuate;
	//»ðÔÖ´ëÊ©
	@Column(name = "fireMeans")
	private String fireMeans;
	//Ð¹Â¶
	@Column(name = "leak")
	private String leak;
	//¼±¾È
	@Column(name = "emergency")
	private String emergency;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGuideNo() {
		return guideNo;
	}
	public void setGuideNo(String guideNo) {
		this.guideNo = guideNo;
	}
	public String getHealthThreat() {
		return healthThreat;
	}
	public void setHealthThreat(String healthThreat) {
		this.healthThreat = healthThreat;
	}
	public String getFireExplosion() {
		return fireExplosion;
	}
	public void setFireExplosion(String fireExplosion) {
		this.fireExplosion = fireExplosion;
	}
	public String getEmAction() {
		return emAction;
	}
	public void setEmAction(String emAction) {
		this.emAction = emAction;
	}
	public String getProtection() {
		return protection;
	}
	public void setProtection(String protection) {
		this.protection = protection;
	}
	public String getSceneEvacuate() {
		return sceneEvacuate;
	}
	public void setSceneEvacuate(String sceneEvacuate) {
		this.sceneEvacuate = sceneEvacuate;
	}
	public String getFireMeans() {
		return fireMeans;
	}
	public void setFireMeans(String fireMeans) {
		this.fireMeans = fireMeans;
	}
	public String getLeak() {
		return leak;
	}
	public void setLeak(String leak) {
		this.leak = leak;
	}
	public String getEmergency() {
		return emergency;
	}
	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}
	@Override
	public String toString() {
		return "EmGuide [id=" + id + ", guideNo=" + guideNo + ", healthThreat="
				+ healthThreat + ", fireExplosion=" + fireExplosion
				+ ", emAction=" + emAction + ", protection=" + protection
				+ ", sceneEvacuate=" + sceneEvacuate + ", fireMeans="
				+ fireMeans + ", leak=" + leak + ", emergency=" + emergency
				+ "]";
	}
	
	
}
