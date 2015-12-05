package com.whut.chemistrylab.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

@Table(name = "user")
public class User {

	// 用户ID   @Id主键,int类型,数据库建表时此字段会设为自增长
	@Id
	@Column(name = "u_id")
	private int uId;
	
	// 登录用户名     length=20数据字段的长度是20
	@Column(name = "name", length = 20)
	private String name;
	
	// 用户密码
	@Column(name = "password")
	private String password;
	
	// 年龄一般是数值,用type = "INTEGER"规范一下.
	@Column(name = "age", type = "INTEGER")
	private int age; 
	
	// 用户邮箱  
	//假设您开始时没有此属性,程序开发中才想到此属性,也不用卸载程序.
	@Column(name = "email")
	private String email;
	
	// 头像地址
	@Column(name = "photo_url")
	private String photoUrl;
	
	// 创建时间
	@Column(name = "create_time")
	private String createTime;
	
	// 有些字段您可能不希望保存到数据库中,不用@Column注释就不会映射到数据库.
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
