package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//Œ£œ’∆∑ Ù–‘£∫–π¬∂”¶º±¥¶¿Ì£∫–π¬∂æ‡¿Î
@Table(name = "LeakDistance")
public class LeakDistance implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//UN±‡∫≈
	@Column(name = "unNo")
	private String unNo;
	//…Ÿ¡ø–π¬∂
	//≥ı º–π¬∂æ‡¿Î//∑¿ª§æ‡¿Î(∞◊ÃÏ)//∑¿ª§æ‡¿Î(“πÕÌ)
	@Column(name = "littleInitLeakDist")
	private String littleInitLeakDist;
	@Column(name = "littleDefendDistDay")
	private String littleDefendDistDay;
	@Column(name = "littleDefendDistNight")
	private String littleDefendDistNight;
	//¥Û¡ø–π¬∂
	//≥ı º–π¬∂æ‡¿Î//∑¿ª§æ‡¿Î(∞◊ÃÏ)//∑¿ª§æ‡¿Î(“πÕÌ)
	@Column(name = "muchInitLeakDist")
	private String muchInitLeakDist;
	@Column(name = "muchDefendDistDay")
	private String muchDefendDistDay;
	@Column(name = "muchDefendDistNight")
	private String muchDefendDistNight;
	
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
	public String getLittleInitLeakDist() {
		return littleInitLeakDist;
	}
	public void setLittleInitLeakDist(String littleInitLeakDist) {
		this.littleInitLeakDist = littleInitLeakDist;
	}
	public String getLittleDefendDistDay() {
		return littleDefendDistDay;
	}
	public void setLittleDefendDistDay(String littleDefendDistDay) {
		this.littleDefendDistDay = littleDefendDistDay;
	}
	public String getLittleDefendDistNight() {
		return littleDefendDistNight;
	}
	public void setLittleDefendDistNight(String littleDefendDistNight) {
		this.littleDefendDistNight = littleDefendDistNight;
	}
	public String getMuchInitLeakDist() {
		return muchInitLeakDist;
	}
	public void setMuchInitLeakDist(String muchInitLeakDist) {
		this.muchInitLeakDist = muchInitLeakDist;
	}
	public String getMuchDefendDistDay() {
		return muchDefendDistDay;
	}
	public void setMuchDefendDistDay(String muchDefendDistDay) {
		this.muchDefendDistDay = muchDefendDistDay;
	}
	public String getMuchDefendDistNight() {
		return muchDefendDistNight;
	}
	public void setMuchDefendDistNight(String muchDefendDistNight) {
		this.muchDefendDistNight = muchDefendDistNight;
	}
	
	
	
}
