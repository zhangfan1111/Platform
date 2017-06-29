package com.utils.file.model;

/**
 * <p></p>
 * @author memory 2017年6月29日 下午5:13:09
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年6月29日
 * @modify by reason:{方法名}:{原因}
 */
public class SysUser {
	/**
	 * 账号
	 */
	private String loginName;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 备注
	 */
	private String remark;

	public SysUser() {

	}

	public SysUser(String loginName, String name, String age, String email, String remark) {
		this.loginName = loginName;
		this.name = name;
		this.age = age;
		this.email = email;
		this.remark = remark;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
