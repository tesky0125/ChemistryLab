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
	//����
	@Column(name="name")
	private String name;
	//�Ա�
	@Column(name="gender")
	private String gender;
	//��λ
	@Column(name="workplace")
	private String workplace;
	//����
	@Column(name="age")
	private String age;
	//רҵ
	@Column(name="major")
	private String major;
	//ְ��/ְ��
	@Column(name="title")
	private String title;
	//��ҵԺУ
	@Column(name="gradSchool")
	private String gradSchool;
	//ѧ��
	@Column(name="degree")
	private String degree;
	//�س�
	@Column(name="specialty")
	private String specialty;
	//��ϵ��ʽ
	@Column(name="contact")
	private String contact;
	//�������¾�
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
