package com.memory.platform.modules.system.base.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memory.platform.core.entity.IdEntity;



/**
 * 系统日志
 */
@Entity
@Table(name = "system_log")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","systemUser"})
public class SystemLog extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -8786157171097238661L;
	
	private SystemUser systemUser;
	private String systemUserId;
	private int logLevel;
	private Timestamp operateTime;
	private int operateType;
	private String logContent;
	private String broswer;//用户浏览器类型
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "system_user_id")
	public SystemUser getSystemUser() {
		return this.systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	
	@Column(name = "system_user_id",insertable=false,updatable=false)
	public String getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(String systemUserId) {
		this.systemUserId = systemUserId;
	}

	@Column(name = "log_level")
	public int getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(int loglevel) {
		this.logLevel = loglevel;
	}

	@Column(name = "operate_time", nullable = false, length = 35)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "operate_type")
	public int getOperateType() {
		return this.operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	@Column(name = "log_content", nullable = false, length = 2000)
	public String getLogContent() {
		return this.logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@Column(name = "broswer", length = 100)
	public String getBroswer() {
		return broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}
}