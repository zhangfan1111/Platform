package com.memory.platform.modules.system.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "system_user", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SystemUser implements java.io.Serializable {

	private static final long serialVersionUID = -3145096279963380719L;

	private String id;
	private String loginName;
	private String pwd;
	private String name;
	private String sex;
	private Integer age;
	private String photo;
	private String email;
	private Date createTime;
	private Date updateTime;
	private Date lastLoginTime;
	private String ip;
	private String deleted;
	private String remark;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", unique = true,  nullable = false)
	public String getId() {
		return id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateTime() {
		if (this.createTime != null)
			return this.createTime;
		return new Date();
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getUpdateTime() {
		if (this.updateTime != null)
			return this.updateTime;
		return new Date();
	}

	@Column(name = "login_name", nullable = false, length = 100)
	public String getLoginName() {
		return this.loginName;
	}
//	@Column(name = "last_login_time")
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	@Column(name = "pwd", length = 100)
	public String getPwd() {
		return this.pwd;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	@Column(name = "sex", length = 50)
	public String getSex() {
		return this.sex;
	}

	@Column(name = "age", precision = 8, scale = 0)
	public Integer getAge() {
		return this.age;
	}

	@Column(name = "photo", length = 200)
	public String getPhoto() {
		return this.photo;
	}
	
	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}
	
	@Column(name = "ip", length = 30)
	public String getIp() {
		return ip;
	}
	
	@Column(name = "deleted", length = 50)
	public String getDeleted() {
		return deleted;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
