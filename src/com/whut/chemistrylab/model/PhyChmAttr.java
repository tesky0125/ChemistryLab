package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

//危险品属性：理化特性
@Table(name = "PhyChmAttr")
public class PhyChmAttr implements Serializable {
	@Id
	@Column(name = "_id")
	private int id;
	//UN编号
	@Column(name = "unNo")
	private String unNo;
	//外观与形状
	@Column(name = "appearence")
	private String appearence;
	//分子式
	@Column(name = "molecularFormula")
	private String molecularFormula;
	//分子量
	@Column(name = "molecularWeigth")
	private String molecularWeigth;
	//PH值
	@Column(name = "ph")
	private String ph;
	//熔点
	@Column(name = "meltPoint")
	private String meltPoint;
	//相对密度(水=1)
	@Column(name = "relativeDensity")
	private String relativeDensity;
	//沸点
	@Column(name = "boilPoint")
	private String boilPoint;
	//溶解度
	@Column(name = "solubility")
	private String solubility;
	//蒸汽密度
	@Column(name = "steamDensity")
	private String steamDensity;
	//主要成分
	@Column(name = "basis")
	private String basis;
	//饱和蒸汽压
	@Column(name = "saturatVaporPress")
	private String saturatVaporPress;
	//燃烧热
	@Column(name = "combustionHeat")
	private String combustionHeat;
	//临界温度
	@Column(name = "criticalTemper")
	private String criticalTemper;
	//临界压力
	@Column(name = "criticalPress")
	private String criticalPress;
	//辛醇/水分配比系数的对数值
	@Column(name = "alcholWaterRadio")
	private String alcholWaterRadio;
	//闪点
	@Column(name = "flashPoint")
	private String flashPoint;
	//爆炸上限
	@Column(name = "explodeMaxLimit")
	private String explodeMaxLimit;
	//爆炸下限
	@Column(name = "explodeMinLimit")
	private String explodeMinLimit;
	//引燃温度
	@Column(name = "igniteTemper")
	private String igniteTemper;
	//溶解性
	@Column(name = "dissolveAttr")
	private String dissolveAttr;
	//其他特性
	@Column(name = "otherAtrr")
	private String otherAtrr;
	//主要用途
	@Column(name = "mainUse")
	private String mainUse;
	
	
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
	public String getAppearence() {
		return appearence;
	}
	public void setAppearence(String appearence) {
		this.appearence = appearence;
	}
	public String getMolecularFormula() {
		return molecularFormula;
	}
	public void setMolecularFormula(String molecularFormula) {
		this.molecularFormula = molecularFormula;
	}
	public String getMolecularWeigth() {
		return molecularWeigth;
	}
	public void setMolecularWeigth(String molecularWeigth) {
		this.molecularWeigth = molecularWeigth;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getMeltPoint() {
		return meltPoint;
	}
	public void setMeltPoint(String meltPoint) {
		this.meltPoint = meltPoint;
	}
	public String getRelativeDensity() {
		return relativeDensity;
	}
	public void setRelativeDensity(String relativeDensity) {
		this.relativeDensity = relativeDensity;
	}
	public String getBoilPoint() {
		return boilPoint;
	}
	public void setBoilPoint(String boilPoint) {
		this.boilPoint = boilPoint;
	}
	public String getSolubility() {
		return solubility;
	}
	public void setSolubility(String solubility) {
		this.solubility = solubility;
	}
	public String getSteamDensity() {
		return steamDensity;
	}
	public void setSteamDensity(String steamDensity) {
		this.steamDensity = steamDensity;
	}
	public String getBasis() {
		return basis;
	}
	public void setBasis(String basis) {
		this.basis = basis;
	}
	public String getSaturatVaporPress() {
		return saturatVaporPress;
	}
	public void setSaturatVaporPress(String saturatVaporPress) {
		this.saturatVaporPress = saturatVaporPress;
	}
	public String getCombustionHeat() {
		return combustionHeat;
	}
	public void setCombustionHeat(String combustionHeat) {
		this.combustionHeat = combustionHeat;
	}
	public String getCriticalTemper() {
		return criticalTemper;
	}
	public void setCriticalTemper(String criticalTemper) {
		this.criticalTemper = criticalTemper;
	}
	public String getCriticalPress() {
		return criticalPress;
	}
	public void setCriticalPress(String criticalPress) {
		this.criticalPress = criticalPress;
	}
	public String getAlcholWaterRadio() {
		return alcholWaterRadio;
	}
	public void setAlcholWaterRadio(String alcholWaterRadio) {
		this.alcholWaterRadio = alcholWaterRadio;
	}
	public String getFlashPoint() {
		return flashPoint;
	}
	public void setFlashPoint(String flashPoint) {
		this.flashPoint = flashPoint;
	}
	public String getExplodeMaxLimit() {
		return explodeMaxLimit;
	}
	public void setExplodeMaxLimit(String explodeMaxLimit) {
		this.explodeMaxLimit = explodeMaxLimit;
	}
	public String getExplodeMinLimit() {
		return explodeMinLimit;
	}
	public void setExplodeMinLimit(String explodeMinLimit) {
		this.explodeMinLimit = explodeMinLimit;
	}
	public String getIgniteTemper() {
		return igniteTemper;
	}
	public void setIgniteTemper(String igniteTemper) {
		this.igniteTemper = igniteTemper;
	}
	public String getDissolveAttr() {
		return dissolveAttr;
	}
	public void setDissolveAttr(String dissolveAttr) {
		this.dissolveAttr = dissolveAttr;
	}
	public String getOtherAtrr() {
		return otherAtrr;
	}
	public void setOtherAtrr(String otherAtrr) {
		this.otherAtrr = otherAtrr;
	}
	public String getMainUse() {
		return mainUse;
	}
	public void setMainUse(String mainUse) {
		this.mainUse = mainUse;
	}
	@Override
	public String toString() {
		return "PhyChmAttr [id=" + id + ", unNo=" + unNo + ", appearence="
				+ appearence + ", molecularFormula=" + molecularFormula
				+ ", molecularWeigth=" + molecularWeigth + ", ph=" + ph
				+ ", meltPoint=" + meltPoint + ", relativeDensity="
				+ relativeDensity + ", boilPoint=" + boilPoint
				+ ", solubility=" + solubility + ", steamDensity="
				+ steamDensity + ", basis=" + basis + ", saturatVaporPress="
				+ saturatVaporPress + ", combustionHeat=" + combustionHeat
				+ ", criticalTemper=" + criticalTemper + ", criticalPress="
				+ criticalPress + ", alcholWaterRadio=" + alcholWaterRadio
				+ ", flashPoint=" + flashPoint + ", explodeMaxLimit="
				+ explodeMaxLimit + ", explodeMinLimit=" + explodeMinLimit
				+ ", igniteTemper=" + igniteTemper + ", dissolveAttr="
				+ dissolveAttr + ", otherAtrr=" + otherAtrr + ", mainUse="
				+ mainUse + "]";
	}
	
	
}
