package com.memory.platform.modules.system.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 类说明：权限可以简单描述为：
某某主体 在 某某领域 有 某某权限 
1，主体可以是用户，可以是角色，也可以是一个部门
2， 领域可以是一个模块，可以是一个页面，也可以是页面上的按钮
3， 权限可以是“可见”，可以是“只读”，也可以是“可用”(如按钮可以点击)
其实就是Who、What、How的问题
 * 创建人： 
 * 创建时间：2014-06-17 15:12:52
 */
@Entity
@Table(name = "system_privilege", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SystemPrivilege implements java.io.Serializable {

	private static final long serialVersionUID = 2703946566736403534L;

	/**id - */
	private String id;
		
	/**主体类型 - 对应数据字典中的，数据库表字典*/
	private String master;
		
	/**主体对应主键值 - */
	private String masterValue;
		
	/**授权领域类型 - 对应数据字典中的，数据库表字典*/
	private String access;
		
	/**授权领域主键值 - */
	private String accessValue;
		
	/**授权类型 - 可用enable,禁用disable，不仅仅这两种，分配权限、授权权限，
都可以用这个字段定义。只是难度增大了。*/
	private String operation;
		
	/**remark - */
	private String remark;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", unique = true,  nullable = false)
		
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "master")
	public String getMaster() {
		return this.master;
	}
	
	public void setMaster(String master) {
		this.master = master;
	}
	
	@Column(name = "master_value")
	public String getMasterValue() {
		return this.masterValue;
	}
	
	public void setMasterValue(String masterValue) {
		this.masterValue = masterValue;
	}
	
	@Column(name = "access_c")
	public String getAccess() {
		return this.access;
	}
	
	public void setAccess(String access) {
		this.access = access;
	}
	
	@Column(name = "access_value")
	public String getAccessValue() {
		return this.accessValue;
	}
	
	public void setAccessValue(String accessValue) {
		this.accessValue = accessValue;
	}
	
	@Column(name = "operation")
	public String getOperation() {
		return this.operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
