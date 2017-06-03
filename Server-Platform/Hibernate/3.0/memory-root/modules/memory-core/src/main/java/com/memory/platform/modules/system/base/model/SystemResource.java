package com.memory.platform.modules.system.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


/**
 * 类说明：
 * 创建人： 
 * 创建时间：2014-06-17 15:12:52
 */
@Entity
@Table(name = "system_resource", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SystemResource implements java.io.Serializable {

	private static final long serialVersionUID = 7519851718998155138L;

	/**id - */
	private String id;
		
	/**资源名称 - */
	private String name;
		
	/**资源英文名称 - */
	private String enName;
		
	/**资源分组名称 - 冗余字段，使用场景可以如下，如果一个页面上有多个增加，列表功能，但显示名称都一样，为了便于区分，可以增加该字段的值*/
	private String groupName;
		
	/**资源编码 - */
	private String code;
		
	/**资源类型 - 模块，菜单，功能等，从字典里选取*/
	private String type;
		
	/**图标 - */
	private String icon;
		
	/**图标坐标 - */
	private String iconCoordinate;
		
	/**url - */
	private String url;
		
	/**target - */
	private String position;
		
	/**描述 - */
	private String description;
		
	/**是否删除 - */
	private String deleted;
		
	/**是否隐藏（停用） - */
	private String isHide;
		
	/**排序码 - */
	private Integer orderCode;
		
	/**父节点id - */
	private String parentId;
	
	/**父节点编码 - */
	private String parentCode;
		
	/**节点路径 - */
	private String nodePath;
		
	/**是否叶子节点 - */
	private String isLeaf;
		
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
	
	@Column(name = "group_name")
	public String getGroupName() {
		return this.groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	
	@Column(name = "icon")
	public String getIcon() {
		return this.icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(name = "icon_coordinate")
	public String getIconCoordinate() {
		return this.iconCoordinate;
	}
	
	public void setIconCoordinate(String iconCoordinate) {
		this.iconCoordinate = iconCoordinate;
	}
	
	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "position")
	public String getPosition() {
		return this.position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	@Column(name = "order_code")
	public Integer getOrderCode() {
		return this.orderCode;
	}
	
	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
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

	@Transient
	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
