package com.cg.common.entity;
// default package

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

import com.google.gson.annotations.Expose;

/**
 * CgDept entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_dept", catalog = "cg_yonggui")
public class CgDept implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	private Integer uid;
	@Expose
	private String name;
	private String shopFrontId;
	private CgShopFront shopFront;
	
	private String staffManageRoleContainerId;//角色Id
	private CgStaffManageRoleContainer staffManageRoleContainer;
	//
	// Constructors

	/** default constructor */
	public CgDept() {
	}

	/** full constructor */
	public CgDept(Integer uid, String name, String shopFrontId) {
		this.uid = uid;
		this.name = name;
		this.shopFrontId = shopFrontId;
	}
	
	public CgDept(String id, Integer uid, String name, String shopFrontId,
			CgShopFront shopFront, String staffManageRoleContainerId,
			CgStaffManageRoleContainer staffManageRoleContainer) {
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.shopFrontId = shopFrontId;
		this.shopFront = shopFront;
		this.staffManageRoleContainerId = staffManageRoleContainerId;
		this.staffManageRoleContainer = staffManageRoleContainer;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 100)
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

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "shop_front_id", nullable = false, length = 100)
	public String getShopFrontId() {
		return this.shopFrontId;
	}

	public void setShopFrontId(String shopFrontId) {
		this.shopFrontId = shopFrontId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_front_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgShopFront getShopFront() {
		return shopFront;
	}

	public void setShopFront(CgShopFront shopFront) {
		this.shopFront = shopFront;
	}

	@Column(name = "staff_manage_role_container_id", nullable = false, length = 100)
	public String getStaffManageRoleContainerId() {
		return staffManageRoleContainerId;
	}

	public void setStaffManageRoleContainerId(String staffManageRoleContainerId) {
		this.staffManageRoleContainerId = staffManageRoleContainerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_manage_role_container_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaffManageRoleContainer getStaffManageRoleContainer() {
		return staffManageRoleContainer;
	}

	public void setStaffManageRoleContainer(
			CgStaffManageRoleContainer staffManageRoleContainer) {
		this.staffManageRoleContainer = staffManageRoleContainer;
	}
	
	
}