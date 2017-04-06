package com.cg.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * CgCarGps entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_gps", catalog = "cg_yonggui")
public class CgCarGps implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String carInfoId;
	private String driverId;
	private String phone;
	private String ownerPhone;
	private String carTerminal;
	
	/** 车辆信息 */
	private CgCarInfo infos;
	/** 驾驶员 */
	private CgDriver drivers;
	// Constructors

	/** default constructor */
	public CgCarGps() {
	}

	/** minimal constructor */
	public CgCarGps(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgCarGps(Integer uid, String carInfoId, String driverId,
			String phone, String ownerPhone, String carTerminal) {
		this.uid = uid;
		this.carInfoId = carInfoId;
		this.driverId = driverId;
		this.phone = phone;
		this.ownerPhone = ownerPhone;
		this.carTerminal = carTerminal;
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

	@GeneratedValue
	@Column(name = "uid", nullable = false)
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "car_info_id", length = 100)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}

	@Column(name = "driver_id", length = 100)
	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	@Column(name = "phone", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "owner_phone", length = 100)
	public String getOwnerPhone() {
		return this.ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	@Column(name = "car_terminal", length = 100)
	public String getCarTerminal() {
		return this.carTerminal;
	}

	public void setCarTerminal(String carTerminal) {
		this.carTerminal = carTerminal;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_info_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarInfo getInfos() {
		return infos;
	}

	public void setInfos(CgCarInfo infos) {
		this.infos = infos;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgDriver getDrivers() {
		return drivers;
	}

	public void setDrivers(CgDriver driver) {
		this.drivers = driver;
	}
}