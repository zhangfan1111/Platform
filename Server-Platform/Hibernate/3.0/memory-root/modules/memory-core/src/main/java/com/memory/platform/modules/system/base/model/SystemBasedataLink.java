package com.memory.platform.modules.system.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


/**
 * 类说明：用于基础数据间的多对多关系，如用户与部门，用户与角色，用户于职位，部门与职位等。之所以用一个表来存这些多对多关系，是为了方便扩展。如果业务需要，可以不用该表。重新构建。
 * 创建人： 
 * 创建时间：2014-06-17 15:12:52
 */
@Entity
@Table(name = "system_basedata_link", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SystemBasedataLink implements java.io.Serializable {

	private static final long serialVersionUID = -5615780117725229612L;

	/**id - */
	private String id;
		
	/**基础数据类型 - 对应数据字典中的，数据库表字典*/
	private String basedataType;
		
	/**基础数据对应主键值 - */
	private String basedataValue;
		
	/**关联数据类型 - 对应数据字典中的，数据库表字典*/
	private String linkdataType;
		
	/**关联数据对应主键值 - */
	private String linkdataValue;
		
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
	
	@Column(name = "basedata_type")
	public String getBasedataType() {
		return this.basedataType;
	}
	
	public void setBasedataType(String basedataType) {
		this.basedataType = basedataType;
	}
	
	@Column(name = "basedata_value")
	public String getBasedataValue() {
		return this.basedataValue;
	}
	
	public void setBasedataValue(String basedataValue) {
		this.basedataValue = basedataValue;
	}
	
	@Column(name = "linkdata_type")
	public String getLinkdataType() {
		return this.linkdataType;
	}
	
	public void setLinkdataType(String linkdataType) {
		this.linkdataType = linkdataType;
	}
	
	@Column(name = "linkdata_value")
	public String getLinkdataValue() {
		return this.linkdataValue;
	}
	
	public void setLinkdataValue(String linkdataValue) {
		this.linkdataValue = linkdataValue;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
