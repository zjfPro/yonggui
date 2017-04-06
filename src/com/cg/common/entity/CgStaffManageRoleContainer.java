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

import com.google.gson.annotations.Expose;

/**
 * CgStaffManageRoleContainer entity. @author MyEclipse Persistence Tools角色表
 */
@Entity
@Table(name = "cg_staff_manage_role_container", catalog = "cg_yonggui")
public class CgStaffManageRoleContainer implements java.io.Serializable {

	// Fields

	@Expose
	private String id;
	@Expose
	private String name;
	@Expose
	private String shopFrontId;
	@Expose
	private CgShopFront cgShopFront;
	
	private Integer uid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_front_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgShopFront getCgShopFront() {
		return cgShopFront;
	}

	public void setCgShopFront(CgShopFront cgShopFront) {
		this.cgShopFront = cgShopFront;
	}
	
	// Constructors

	/** default constructor */
	public CgStaffManageRoleContainer() {
	}

	/** full constructor */
	public CgStaffManageRoleContainer(String name) {
		this.name = name;
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

	@Column(name = "name")
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
	
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "uid", nullable = false)
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
}