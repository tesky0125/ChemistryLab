package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//Σ��Ʒ���ԣ�Σ��������
@Table(name = "DangerDesc")
public class DangerDesc implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//UN���
	@Column(name = "unNo")
	private String unNo;
	//��ҪΣ�������
	@Column(name = "mainDangerType")
	private String mainDangerType;
	//��Σ�������
	@Column(name = "viceDangerType")
	private String viceDangerType;
	//����;��
	@Column(name = "invadeWay")
	private String invadeWay;
	//����Σ��
	@Column(name = "healthThreat")
	private String healthThreat;
	//����Σ��
	@Column(name = "envirThreat")
	private String envirThreat;
	//ȼ��Σ��
	@Column(name = "explosionDanger")
	private String explosionDanger;
	
	
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
	public String getMainDangerType() {
		return mainDangerType;
	}
	public void setMainDangerType(String mainDangerType) {
		this.mainDangerType = mainDangerType;
	}
	public String getViceDangerType() {
		return viceDangerType;
	}
	public void setViceDangerType(String viceDangerType) {
		this.viceDangerType = viceDangerType;
	}
	public String getInvadeWay() {
		return invadeWay;
	}
	public void setInvadeWay(String invadeWay) {
		this.invadeWay = invadeWay;
	}
	public String getHealthThreat() {
		return healthThreat;
	}
	public void setHealthThreat(String healthThreat) {
		this.healthThreat = healthThreat;
	}
	public String getEnvirThreat() {
		return envirThreat;
	}
	public void setEnvirThreat(String envirThreat) {
		this.envirThreat = envirThreat;
	}
	public String getExplosionDanger() {
		return explosionDanger;
	}
	public void setExplosionDanger(String explosionDanger) {
		this.explosionDanger = explosionDanger;
	}
	@Override
	public String toString() {
		return "DangerDesc [id=" + id + ", unNo=" + unNo + ", mainDangerType="
				+ mainDangerType + ", viceDangerType=" + viceDangerType
				+ ", invadeWay=" + invadeWay + ", healthThreat=" + healthThreat
				+ ", envirThreat=" + envirThreat + ", explosionDanger="
				+ explosionDanger + "]";
	}
	
	
}
