package com.utils.file.model;

public class SysUser {
	private String login_name;
	private String name;
	private String age;
	private String email;
	private String remark;

	public SysUser() {

	}

	public SysUser(String login_name, String name, String age, String email, String remark) {
		this.login_name = login_name;
		this.name = name;
		this.age = age;
		this.email = email;
		this.remark = remark;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
