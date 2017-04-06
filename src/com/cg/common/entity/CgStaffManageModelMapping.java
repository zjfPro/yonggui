package com.cg.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgStaffManageRoleMapping entity. @author MyEclipse Persistence Tools 角色员工映射表
 */
@Entity
@Table(name = "cg_staff_manage_model_mapping", catalog = "cg_yonggui")
public class CgStaffManageModelMapping implements java.io.Serializable {

	// Fields

	private Integer id;
	private String staffManageRoleContainerId;
	private String staffId;

	// Constructors

	/** default constructor */
	public CgStaffManageModelMapping() {
	}

	/** full constructor */
	public CgStaffManageModelMapping(String staffManageRoleContainerId,
			String staffId) {
		this.staffManageRoleContainerId = staffManageRoleContainerId;
		this.staffId = staffId;
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

	@Column(name = "staff_manage_role_container_id", nullable = false, length = 80)
	public String getStaffManageRoleContainerId() {
		return this.staffManageRoleContainerId;
	}

	public void setStaffManageRoleContainerId(String staffManageRoleContainerId) {
		this.staffManageRoleContainerId = staffManageRoleContainerId;
	}

	@Column(name = "staff_id", nullable = false, length = 80)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

}