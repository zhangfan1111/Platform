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
 * 类说明：系统字典表
 * 创建人： 
 * 创建时间：2014-06-17 15:12:52
 */
@Entity
@Table(name = "system_dict", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SystemDict implements java.io.Serializable {

	private static final long serialVersionUID = 4458864418238192119L;

	/**主键 - */
	private String id;
		
	/**名称 - */
	private String name;
		
	/**英文名称 - */
	private String enName;
		
	/**编码 - */
	private String code;
		
	/**字典类型 - 字典类型（通用字典，数据库表字典，数据库字段字典）COMMON,DBTABLE,DBFIELD*/
	private String type;
		
	/**数据库表字典，表名 - 数据库表字典，表名*/
	private String dbTableName;
		
	/**数据库字段字典，字段名 - 数据库字段字典，字段名*/
	private String dbFieldName;
		
	/**排序码 - */
	private String orderCode;
		
	/**是否删除 - */
	private String deleted;
		
	/**是否隐藏（停用） - */
	private String isHide;
	
	/**父节点ID - */
	private String parentId;
		
	/**父节点编码 - */
	private String parentCode;
		
	/**节点路径 - */
	private String nodePath;
		
	/**是否叶子节点 - */
	private String isLeaf;
		
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
	
	@Column(name = "type")
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "db_table_name")
	public String getDbTableName() {
		return this.dbTableName;
	}
	
	public void setDbTableName(String dbTableName) {
		this.dbTableName = dbTableName;
	}
	
	@Column(name = "db_field_name")
	public String getDbFieldName() {
		return this.dbFieldName;
	}
	
	public void setDbFieldName(String dbFieldName) {
		this.dbFieldName = dbFieldName;
	}
	
	@Column(name = "order_code")
	public String getOrderCode() {
		return this.orderCode;
	}
	
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@Column(name = "deleted")
	public String getDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	@Column(name = "is_hide")
	public String getIsHide() {
		return this.isHide;
	}
	
	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	
	@Column(name = "parent_id")
	public String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "parent_code")
	public String getParentCode() {
		return this.parentCode;
	}
	
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	@Column(name = "node_path")
	public String getNodePath() {
		return this.nodePath;
	}
	
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	
	@Column(name = "is_leaf")
	public String getIsLeaf() {
		return this.isLeaf;
	}
	
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
