package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//Ӧ��ָ��
@Table(name = "EmGuide")
public class EmGuide implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//Ӧ����Ԯָ�ϱ��
	@Column(name = "guideNo")
	private String guideNo;
	//������в
	@Column(name = "healthThreat")
	private String healthThreat;
	//���ֻ�ը
	@Column(name = "fireExplosion")
	private String fireExplosion;
	//Ӧ����Ӧ
	@Column(name = "emAction")
	private String emAction;
	//����
	@Column(name = "protection")
	private String protection;
	//�ֳ���ɢ
	@Column(name = "sceneEvacuate")
	private String sceneEvacuate;
	//���ִ�ʩ
	@Column(name = "fireMeans")
	private String fireMeans;
	//й¶
	@Column(name = "leak")
	private String leak;
	//����
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
