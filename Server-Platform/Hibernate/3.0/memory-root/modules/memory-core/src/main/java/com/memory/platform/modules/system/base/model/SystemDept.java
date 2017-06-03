package com.memory.platform.modules.system.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 类说明：
 * 创建人： 
 * 创建时间：2014-06-17 15:12:52
 */
@Entity
@Table(name = "system_dept", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SystemDept implements java.io.Serializable {

	private static final long serialVersionUID = 3743979999068126058L;

	/**id - */
	private String id;
		
	/**名称 - */
	private String name;
		
	/**英文名称 - */
	private String enName;
		
	/**编号 - */
	private String code;
		
	/**描述 - */
	private String description;
		
	/**父部门 - */
	private String parentId;
		
	/**是否叶子节点 - */
	private String isLeaf;
		
	/**节点路径 - */
	private String nodePath;
		
	/**是否停用 - */
	private String isStop;
		
	/**是否删除 - */
	private String deleted;
		
	/**创建人 - */
	private String createUserId;
		
	/**创建时间 - */
	private Date createTime;
		
	/**创建方式 - 录入还是导入（冗余字段）*/
	private String createType;
		
	/**remark - */
	private String remark;
	
	private boolean isChecked;
	
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
	
	@Column(name = "name")
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "en_name")
	public String getEnName() {
		return this.enName;
	}
	
	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	@Column(name = "code")
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "parent_id")
	public String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "is_leaf")
	public String getIsLeaf() {
		return this.isLeaf;
	}
	
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	@Column(name = "node_path")
	public String getNodePath() {
		return this.nodePath;
	}
	
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	
	@Column(name = "is_stop")
	public String getIsStop() {
		return this.isStop;
	}
	
	public void setIsStop(String isStop) {
		this.isStop = isStop;
	}
	
	@Column(name = "deleted")
	public String getDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	@Column(name = "create_user_id")
	public String getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	@Column(name = "create_time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "create_type")
	public String getCreateType() {
		return this.createType;
	}
	
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
