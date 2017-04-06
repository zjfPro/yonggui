package com.cg.common.entity;

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
 * CgCarBrand entity. @author MyEclipse Persistence Tools
 	车品牌表
 */
@Entity
@Table(name = "cg_car_brand", catalog = "cg_yonggui")
public class CgCarBrand implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	@Expose
	private Integer uid;
	/**品牌 */
	@Expose
	private String brand;
	/**系列 */
	@Expose
	private String series;
	/**型号 */
	@Expose
	private String model;
	/**备注 */
	@Expose
	private String remark;
	/**自联ID */
	@Expose
	private String parentId;
	/**厂商 */
	@Expose
	private String manufacturer;
	
	private CgCarBrand parent;
	
	/**车名称 */
	@Expose
	private String info;
	
	/**车名称 */
	@Expose
	private String infoOfSeries;
	// Constructors

	/** default constructor */
	public CgCarBrand() {
	}

	/** minimal constructor */
	public CgCarBrand(Integer uid, String brand, String series, String model) {
		this.uid = uid;
		this.brand = brand;
		this.series = series;
		this.model = model;
	}

	/** full constructor */
	public CgCarBrand(Integer uid, String brand, String series, String model,
			String remark, String parentId, String manufacturer) {
		this.uid = uid;
		this.brand = brand;
		this.series = series;
		this.model = model;
		this.remark = remark;
		this.parentId = parentId;
		this.manufacturer = manufacturer;
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

	@Column(name = "brand", nullable = false, length = 100)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name = "series", nullable = false, length = 100)
	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@Column(name = "model", nullable = false, length = 100)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "parent_id", length = 100)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "manufacturer", length = 100)
	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarBrand getParent() {
		return parent;
	}
	
	public void setParent(CgCarBrand parent) {
		this.parent = parent;
	}

	
	@Transient
	public String getInfo() {
//		return getBrand()+" "+getSeries()+" "+getModel();
		info = getBrand()+" "+getSeries()+" "+getModel();
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	@Transient
	public String getInfoOfSeries() {
		infoOfSeries=getBrand()+" "+getManufacturer()+" "+getSeries();
		return infoOfSeries;
	}

	public void setInfoOfSeries(String infoOfSeries) {
		this.infoOfSeries = infoOfSeries;
	}
	
	
	
	
}