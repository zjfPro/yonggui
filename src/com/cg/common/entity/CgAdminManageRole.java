package com.cg.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 权限表
 * CgAdminManageRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_admin_manage_role", catalog = "cg_yonggui")
public class CgAdminManageRole implements java.io.Serializable {

	// Fields

	private String id;
	private String adminManageModelId;
	private String userId;
	private Timestamp addtime;
	private CgAdminManageModel adminManageModel;
	
	
	// Constructors
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="admin_manage_model_id",insertable=false,updatable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CgAdminManageModel getAdminManageModel() {
		return adminManageModel;
	}

	public void setAdminManageModel(CgAdminManageModel adminManageModel) {
		this.adminManageModel = adminManageModel;
	}

	/** default constructor */
	public CgAdminManageRole() {
	}

	/** minimal constructor */
	public CgAdminManageRole(String id, String adminManageModelId, String userId) {
		this.id = id;
		this.adminManageModelId = adminManageModelId;
		this.userId = userId;
	}

	/** full constructor */
	public CgAdminManageRole(String id, String adminManageModelId,
			String userId, Timestamp addtime) {
		this.id = id;
		this.adminManageModelId = adminManageModelId;
		this.userId = userId;
		this.addtime = addtime;
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

	@Column(name = "admin_manage_model_id", nullable = false, length = 80)
	public String getAdminManageModelId() {
		return this.adminManageModelId;
	}

	public void setAdminManageModelId(String adminManageModelId) {
		this.adminManageModelId = adminManageModelId;
	}

	@Column(name = "user_id", nullable = false, length = 80)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

}