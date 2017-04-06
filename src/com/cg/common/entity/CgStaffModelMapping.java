package com.cg.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * CgStaffModelMapping entity. @author MyEclipse Persistence Tools员工模块映射表
 */
@Entity
@Table(name = "cg_staff_model_mapping", catalog = "cg_yonggui")
public class CgStaffModelMapping implements java.io.Serializable {

	// Fields

	private Integer id;
	private String staffId;
	private Integer staffManageModelId;
	
	
	private CgStaffManageModel cgStaffManageModel;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_manage_model_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaffManageModel getCgStaffManageModel() {
		return cgStaffManageModel;
	}

	public void setCgStaffManageModel(CgStaffManageModel cgStaffManageModel) {
		this.cgStaffManageModel = cgStaffManageModel;
	}

	/** default constructor */
	public CgStaffModelMapping() {
	}

	/** full constructor */
	public CgStaffModelMapping(String staffId, Integer staffManageModelId) {
		this.staffId = staffId;
		this.staffManageModelId = staffManageModelId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "staff_id", nullable = false, length = 100)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(name = "staff_manage_model_id", nullable = false)
	public Integer getStaffManageModelId() {
		return this.staffManageModelId;
	}

	public void setStaffManageModelId(Integer staffManageModelId) {
		this.staffManageModelId = staffManageModelId;
	}

}