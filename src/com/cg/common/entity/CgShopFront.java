package com.cg.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.gson.annotations.Expose;

/**
 * CgShopFront entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_shop_front", catalog = "cg_yonggui")
public class CgShopFront implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	private Integer uid;
	/**
	 * 门店
	 */
	@Expose
	private String name;
	/**
	 * 添加时间
	 */
	private Timestamp addtime;
	/**
	 * 门店管理员ID
	 */
	private String manageStaffId;
	private CgStaff manageStaff;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 介绍
	 */
	private String introduce;
	/**
	 * 预览图 logo
	 */
	private String logo;
	/**
	 * 门店级别 0总店 1分店
	 */
	private Integer level;
	private String levelSTR;
	// Constructors

	/** default constructor */
	public CgShopFront() {
	}

	/** minimal constructor */
	public CgShopFront(Integer uid, String name, Timestamp addtime,
			String manageStaffId, String address) {
		this.uid = uid;
		this.name = name;
		this.addtime = addtime;
		this.manageStaffId = manageStaffId;
		this.address = address;
	}

	/** full constructor */
	public CgShopFront(Integer uid, String name, Timestamp addtime,
			String manageStaffId, String address, Double longitude,
			Double latitude, String introduce, String logo) {
		this.uid = uid;
		this.name = name;
		this.addtime = addtime;
		this.manageStaffId = manageStaffId;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.introduce = introduce;
		this.logo = logo;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "manage_staff_id", nullable = false, length = 80)
	public String getManageStaffId() {
		return this.manageStaffId;
	}

	public void setManageStaffId(String manageStaffId) {
		this.manageStaffId = manageStaffId;
	}

	@Column(name = "address", nullable = false)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "longitude", precision = 22, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", precision = 22, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "introduce")
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "logo")
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return level == null ? 0 : this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manage_staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getManageStaff() {
		return manageStaff;
	}

	public void setManageStaff(CgStaff manageStaff) {
		this.manageStaff = manageStaff;
	}

	
	@Transient
	public String getLevelSTR() {
		switch (getLevel()) {
		case 0:
			levelSTR="总店";
			break;
		default:
			levelSTR="分店";
			break;
		}
		return levelSTR;
	}

	public void setLevelSTR(String levelSTR) {
		this.levelSTR = levelSTR;
	}
	
	
	

}