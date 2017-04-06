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
 * CgSetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_setting", catalog = "cg_yonggui")
public class CgSetting implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private Integer carStockMinAlert;
	private Integer carStockMaxAlert;
	private String shopFrontId;
	
	/** 门店 */
	private CgShopFront shopFront;
	// Constructors

	/** default constructor */
	public CgSetting() {
	}

	/** minimal constructor */
	public CgSetting(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgSetting(Integer uid, Integer carStockMinAlert,
			Integer carStockMaxAlert, String shopFrontId) {
		this.uid = uid;
		this.carStockMinAlert = carStockMinAlert;
		this.carStockMaxAlert = carStockMaxAlert;
		this.shopFrontId = shopFrontId;
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

	@Column(name = "car_stock_min_alert")
	public Integer getCarStockMinAlert() {
		return this.carStockMinAlert;
	}

	public void setCarStockMinAlert(Integer carStockMinAlert) {
		this.carStockMinAlert = carStockMinAlert;
	}

	@Column(name = "car_stock_max_alert")
	public Integer getCarStockMaxAlert() {
		return this.carStockMaxAlert;
	}

	public void setCarStockMaxAlert(Integer carStockMaxAlert) {
		this.carStockMaxAlert = carStockMaxAlert;
	}

	@Column(name = "shop_front_id", length = 80)
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
}