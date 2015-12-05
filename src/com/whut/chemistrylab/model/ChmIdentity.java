package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//Σ��Ʒ���ԣ���ѧƷ��ʶ
@Table(name = "ChmIdentity")
public class ChmIdentity implements Serializable{
	@Id
	@Column(name = "_id")
	private int id;
	//��������
	@Column(name = "chName")
	private String chName;
	//���ı���
	@Column(name = "nickChName")
	private String nickChName;
	//Ӣ������
	@Column(name = "enName")
	private String enName;
	//Ӣ�ı���
	@Column(name = "nickEnName")
	private String nickEnName;
	//CAS���
	@Column(name = "casNo")
	private String casNo;
	//UN���
	@Column(name = "unNo")
	private String unNo;
	//Σ��CN��
	@Column(name = "emCnNo")
	private String emCnNo;
	//RTECS���
	@Column(name = "rtecsNo")
	private String rtecsNo;
	//ICSC���
	@Column(name = "icscNo")
	private String icscNo;
	//EC���
	@Column(name = "ecNo")
	private String ecNo;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getNickChName() {
		return nickChName;
	}
	public void setNickChName(String nickChName) {
		this.nickChName = nickChName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getNickEnName() {
		return nickEnName;
	}
	public void setNickEnName(String nickEnName) {
		this.nickEnName = nickEnName;
	}
	public String getCasNo() {
		return casNo;
	}
	public void setCasNo(String casNo) {
		this.casNo = casNo;
	}
	public String getUnNo() {
		return unNo;
	}
	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}
	public String getEmCnNo() {
		return emCnNo;
	}
	public void setEmCnNo(String emCnNo) {
		this.emCnNo = emCnNo;
	}
	public String getRtecsNo() {
		return rtecsNo;
	}
	public void setRtecsNo(String rtecsNo) {
		this.rtecsNo = rtecsNo;
	}
	public String getIcscNo() {
		return icscNo;
	}
	public void setIcscNo(String icscNo) {
		this.icscNo = icscNo;
	}
	public String getEcNo() {
		return ecNo;
	}
	public void setEcNo(String ecNo) {
		this.ecNo = ecNo;
	}
	@Override
	public String toString() {
		return "ChmIdentity [id=" + id + ", chName=" + chName + ", nickChName="
				+ nickChName + ", enName=" + enName + ", nickEnName="
				+ nickEnName + ", casNo=" + casNo + ", unNo=" + unNo
				+ ", emCnNo=" + emCnNo + ", rtecsNo=" + rtecsNo + ", icscNo="
				+ icscNo + ", ecNo=" + ecNo + "]";
	}
	
}
