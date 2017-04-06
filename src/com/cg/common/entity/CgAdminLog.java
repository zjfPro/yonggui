package com.cg.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 管理员日志
 * CgAdminLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_admin_log", catalog = "cg_yonggui")
public class CgAdminLog implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String adminId;
	private Integer actionType;//预留
	private String actionDesc;//操作说明
	private String actionContent;//操作内容
	private String actionIp;//IP地址
	private Timestamp actionDate;//操作时间

	private CgAdmin admin;
	// Constructors

	/** default constructor */
	public CgAdminLog() {
	}

	/** minimal constructor */
	public CgAdminLog(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgAdminLog(Integer uid, String adminId, Integer actionType,
			String actionDesc, String actionContent, String actionIp,
			Timestamp actionDate) {
		this.uid = uid;
		this.adminId = adminId;
		this.actionType = actionType;
		this.actionDesc = actionDesc;
		this.actionContent = actionContent;
		this.actionIp = actionIp;
		this.actionDate = actionDate;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 60)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "uid", nullable = false)
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "admin_id", length = 60)
	public String getAdminId() {
		return this.adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Column(name = "action_type")
	public Integer getActionType() {
		return this.actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	@Column(name = "action_desc")
	public String getActionDesc() {
		return this.actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	@Column(name = "action_content")
	public String getActionContent() {
		return this.actionContent;
	}

	public void setActionContent(String actionContent) {
		this.actionContent = actionContent;
	}

	@Column(name = "action_ip")
	public String getActionIp() {
		return this.actionIp;
	}

	public void setActionIp(String actionIp) {
		this.actionIp = actionIp;
	}

	@Column(name = "action_date", length = 19)
	public Timestamp getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="admin_id",insertable=false,updatable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CgAdmin getAdmin() {
		if(admin != null)this.admin.setPassword(null);
		return admin;
	}

	public void setAdmin(CgAdmin admin) {
		this.admin = admin;
	}

}