package com.cg.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgStaffLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_staff_log", catalog = "cg_yonggui")
public class CgStaffLog implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String staffId;
	private Integer actionType;
	private String actionDesc;
	private String actionContent;
	private String actionIp;
	private Timestamp actionDate;
	private String foreignId;

	// Constructors

	/** default constructor */
	public CgStaffLog() {
	}

	/** minimal constructor */
	public CgStaffLog(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgStaffLog(Integer uid, String staffId, Integer actionType,
			String actionDesc, String actionContent, String actionIp,
			Timestamp actionDate, String foreignId) {
		this.uid = uid;
		this.staffId = staffId;
		this.actionType = actionType;
		this.actionDesc = actionDesc;
		this.actionContent = actionContent;
		this.actionIp = actionIp;
		this.actionDate = actionDate;
		this.foreignId = foreignId;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 80)
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

	@Column(name = "staff_id", length = 80)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
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

	@Column(name = "foreign_id", length = 80)
	public String getForeignId() {
		return this.foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

}