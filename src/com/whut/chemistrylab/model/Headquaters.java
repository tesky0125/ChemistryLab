package com.whut.chemistrylab.model;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

@Table(name="Headquaters")
public class Headquaters implements Serializable {
	@Id
	@Column(name="_id")
	private int id;
	//姓名
	@Column(name="name")
	private String name;
	//性别
	@Column(name="gender")
	private String gender;
	//单位
	@Column(name="workplace")
	private String workplace;
	//年龄
	@Column(name="age")
	private String age;
	//专业
	@Column(name="major")
	private String major;
	//职务/职称
	@Column(name="title")
	private String title;
	//毕业院校
	@Column(name="gradSchool")
	private String gradSchool;
	//学历
	@Column(name="degree")
	private String degree;
	//特长
	@Column(name="specialty")
	private String specialty;
	//联系方式
	@Column(name="contact")
	private String contact;
	//隶属海事局
	@Column(name="belongto")
	private String belongto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGradSchool() {
		return gradSchool;
	}
	public void setGradSchool(String gradSchool) {
		this.gradSchool = gradSchool;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getBelongto() {
		return belongto;
	}
	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}
	
	
	
}
