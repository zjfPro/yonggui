package com.cg.common.entity;

import java.sql.Timestamp;
import java.util.Date;
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
 * CgDriverSubsidy entity. @author MyEclipse Persistence Tools
 *  出车补贴
 */
@Entity
@Table(name = "cg_driver_subsidy", catalog = "cg_yonggui")
public class CgDriverSubsidy implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String driverId;
	private String plateNumber;
	private String money;
	private Integer status;
	private Timestamp addtime;
	private Timestamp examineTime;
	private Timestamp payTime;
	private String reason;
	private String reply;
	
	private CgDriver drivers;
	// Constructors

	/** default constructor */
	public CgDriverSubsidy() {
	}

	/** minimal constructor */
	public CgDriverSubsidy(Integer uid, String driverId) {
		this.uid = uid;
		this.driverId = driverId;
	}

	/** full constructor */
	public CgDriverSubsidy(Integer uid, String driverId, String plateNumber,
			String money, Integer status, Timestamp addtime, Timestamp examineTime,
			Timestamp payTime, String reason, String reply) {
		this.uid = uid;
		this.driverId = driverId;
		this.plateNumber = plateNumber;
		this.money = money;
		this.status = status;
		this.addtime = addtime;
		this.examineTime = examineTime;
		this.payTime = payTime;
		this.reason = reason;
		this.reply = reply;
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

	@GeneratedValue
	@Column(name = "uid", nullable = false)
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "driver_id", nullable = false, length = 80)
	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	@Column(name = "plate_number", length = 20)
	public String getPlateNumber() {
		return this.plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Column(name = "money", length = 80)
	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "examine_time", length = 19)
	public Timestamp getExamineTime() {
		return this.examineTime;
	}

	public void setExamineTime(Timestamp examineTime) {
		this.examineTime = examineTime;
	}

	@Column(name = "pay_time", length = 19)
	public Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@Column(name = "reason")
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "reply")
	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
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