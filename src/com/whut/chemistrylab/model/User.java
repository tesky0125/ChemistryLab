package com.whut.chemistrylab.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

@Table(name = "user")
public class User {

	// �û�ID   @Id����,int����,���ݿ⽨��ʱ���ֶλ���Ϊ������
	@Id
	@Column(name = "u_id")
	private int uId;
	
	// ��¼�û���     length=20�����ֶεĳ�����20
	@Column(name = "name", length = 20)
	private String name;
	
	// �û�����
	@Column(name = "password")
	private String password;
	
	// ����һ������ֵ,��type = "INTEGER"�淶һ��.
	@Column(name = "age", type = "INTEGER")
	private int age; 
	
	// �û�����  
	//��������ʼʱû�д�����,���򿪷��в��뵽������,Ҳ����ж�س���.
	@Column(name = "email")
	private String email;
	
	// ͷ���ַ
	@Column(name = "photo_url")
	private String photoUrl;
	
	// ����ʱ��
	@Column(name = "create_time")
	private String createTime;
	
	// ��Щ�ֶ������ܲ�ϣ�����浽���ݿ���,����@Columnע�;Ͳ���ӳ�䵽���ݿ�.
	private String remark;
	
	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
