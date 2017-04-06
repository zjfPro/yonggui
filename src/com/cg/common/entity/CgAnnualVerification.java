package com.cg.common.entity;
// default package

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * CgAnnualVerification entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_annual_verification", catalog = "cg_yonggui")
public class CgAnnualVerification implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String carInfoId;//车辆信息ID
	private String opinion;//年审意见
	private Integer ispass;//是否通过,0没有通过，1通过
	private CgCarInfo carInfo;
	
	// Constructors

	/** default constructor */
	public CgAnnualVerification() {
	}

	/** minimal constructor */
	public CgAnnualVerification(String id, Integer uid) {
		this.id = id;
		this.uid = uid;
	}

	/** full constructor */
	public CgAnnualVerification(String id, Integer uid, String carInfoId,
			String opinion, Integer ispass) {
		this.id = id;
		this.uid = uid;
		this.carInfoId = carInfoId;
		this.opinion = opinion;
		this.ispass = ispass;
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

	@Column(name = "car_info_id", length = 100)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}

	@Column(name = "opinion")
	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@Column(name = "ispass")
	public Integer getIspass() {
		return this.ispass;
	}

	public void setIspass(Integer ispass) {
		this.ispass = ispass;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_info_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CgCarInfo carInfo) {
		this.carInfo = carInfo;
	}

}