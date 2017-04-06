package com.cg.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgStaffManageRoleContainerMapping entity. @author MyEclipse Persistence Tools 角色模块映射表
 */
@Entity
@Table(name = "cg_staff_manage_role_container_mapping", catalog = "cg_yonggui")
public class CgStaffManageRoleContainerMapping implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer staffManageModelid;
	private String staffManageRoleContainerId;

	// Constructors

	/** default constructor */
	public CgStaffManageRoleContainerMapping() {
	}

	/** full constructor */
	public CgStaffManageRoleContainerMapping(Integer staffManageModelid,
			String staffManageRoleContainerId) {
		this.staffManageModelid = staffManageModelid;
		this.staffManageRoleContainerId = staffManageRoleContainerId;
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

	@Column(name = "staff_manage_modelid", nullable = false)
	public Integer getStaffManageModelid() {
		return this.staffManageModelid;
	}

	public void setStaffManageModelid(Integer staffManageModelid) {
		this.staffManageModelid = staffManageModelid;
	}

	@Column(name = "staff_manage_role_container_id", nullable = false)
	public String getStaffManageRoleContainerId() {
		return this.staffManageRoleContainerId;
	}

	public void setStaffManageRoleContainerId(String staffManageRoleContainerId) {
		this.staffManageRoleContainerId = staffManageRoleContainerId;
	}

}