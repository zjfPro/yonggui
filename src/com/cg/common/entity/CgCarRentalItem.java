package com.cg.common.entity;

import java.sql.Timestamp;
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
 * CgCarRentalItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_rental_item", catalog = "cg_yonggui")
public class CgCarRentalItem implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	@Expose
	private Integer uid;
	@Expose
	private String unitPrice;
	@Expose
	private String unitName;
	@Expose
	private Integer unitNumber;
	@Expose
	private Integer status;
	@Expose
	private Timestamp addtime;
	@Expose
	private String staffId;
	@Expose
	private String shopFrontId;
	@Expose
	private String carInfoId;
	@Expose
	private String title;
	@Expose
	private String content;
	@Expose
	private String earnestMoney;
	@Expose
	private String foregiftMoney;

	
	@Expose
	private CgStaff cgStaff;
	@Expose
	private CgShopFront cgShopFront;
	@Expose
	private CgCarInfo cgCarInfo;

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getCgStaff() {
		return cgStaff;
	}

	public void setCgStaff(CgStaff cgStaff) {
		this.cgStaff = cgStaff;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_front_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgShopFront getCgShopFront() {
		return cgShopFront;
	}

	public void setCgShopFront(CgShopFront cgShopFront) {
		this.cgShopFront = cgShopFront;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_info_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarInfo getCgCarInfo() {
		return cgCarInfo;
	}

	public void setCgCarInfo(CgCarInfo cgCarInfo) {
		this.cgCarInfo = cgCarInfo;
	}

	/** default constructor */
	public CgCarRentalItem() {
	}

	/** minimal constructor */
	public CgCarRentalItem(Integer uid, String unitPrice) {
		this.uid = uid;
		this.unitPrice = unitPrice;
	}

	/** full constructor */
	public CgCarRentalItem(Integer uid, String unitPrice, String unitName,
			Integer unitNumber, Integer status, Timestamp addtime,
			String staffId, String shopFrontId, String carInfoId, String title,
			String content, String earnestMoney, String foregiftMoney) {
		this.uid = uid;
		this.unitPrice = unitPrice;
		this.unitName = unitName;
		this.unitNumber = unitNumber;
		this.status = status;
		this.addtime = addtime;
		this.staffId = staffId;
		this.shopFrontId = shopFrontId;
		this.carInfoId = carInfoId;
		this.title = title;
		this.content = content;
		this.earnestMoney = earnestMoney;
		this.foregiftMoney = foregiftMoney;
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

	@Column(name = "unit_price", nullable = false)
	public String getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "unit_name", length = 100)
	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "unit_number")
	public Integer getUnitNumber() {
		return this.unitNumber;
	}

	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
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

	@Column(name = "staff_id", length = 80)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(name = "shop_front_id", length = 80)
	public String getShopFrontId() {
		return this.shopFrontId;
	}

	public void setShopFrontId(String shopFrontId) {
		this.shopFrontId = shopFrontId;
	}

	@Column(name = "car_info_id", length = 80)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "earnest_money")
	public String getEarnestMoney() {
		return this.earnestMoney;
	}

	public void setEarnestMoney(String earnestMoney) {
		this.earnestMoney = earnestMoney;
	}

	@Column(name = "foregift_money")
	public String getForegiftMoney() {
		return this.foregiftMoney;
	}

	public void setForegiftMoney(String foregiftMoney) {
		this.foregiftMoney = foregiftMoney;
	}

}