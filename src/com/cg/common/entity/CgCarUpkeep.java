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

/**
 * CgCarUpkeep entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_upkeep", catalog = "cg_yonggui")
public class CgCarUpkeep implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private Timestamp upkeepTime;
	private String mileage;
	private String upkeepPeople;
	private String upkeepProject;
	private String uodateEngineOilType;
	private String lastMileage;
	private Timestamp lastUpkeepTime;
	private String carInfoId;
	private String upkeepCost;
	private Integer isdel;
	private Timestamp deltime;

	private CgCarInfo cgCarInfo;

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
	public CgCarUpkeep() {
	}

	/** minimal constructor */
	public CgCarUpkeep(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgCarUpkeep(Integer uid, Timestamp upkeepTime, String mileage,
			String upkeepPeople, String upkeepProject,
			String uodateEngineOilType, String lastMileage,
			Timestamp lastUpkeepTime, String carInfoId) {
		this.uid = uid;
		this.upkeepTime = upkeepTime;
		this.mileage = mileage;
		this.upkeepPeople = upkeepPeople;
		this.upkeepProject = upkeepProject;
		this.uodateEngineOilType = uodateEngineOilType;
		this.lastMileage = lastMileage;
		this.lastUpkeepTime = lastUpkeepTime;
		this.carInfoId = carInfoId;
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

	@Column(name = "upkeep_time", length = 19)
	public Timestamp getUpkeepTime() {
		return this.upkeepTime;
	}

	public void setUpkeepTime(Timestamp upkeepTime) {
		this.upkeepTime = upkeepTime;
	}

	@Column(name = "mileage", length = 100)
	public String getMileage() {
		return this.mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	@Column(name = "upkeep_people", length = 100)
	public String getUpkeepPeople() {
		return this.upkeepPeople;
	}

	public void setUpkeepPeople(String upkeepPeople) {
		this.upkeepPeople = upkeepPeople;
	}

	@Column(name = "upkeep_project", length = 100)
	public String getUpkeepProject() {
		return this.upkeepProject;
	}

	public void setUpkeepProject(String upkeepProject) {
		this.upkeepProject = upkeepProject;
	}

	@Column(name = "uodate_engine_oil_type", length = 100)
	public String getUodateEngineOilType() {
		return this.uodateEngineOilType;
	}

	public void setUodateEngineOilType(String uodateEngineOilType) {
		this.uodateEngineOilType = uodateEngineOilType;
	}

	@Column(name = "last_mileage", length = 100)
	public String getLastMileage() {
		return this.lastMileage;
	}

	public void setLastMileage(String lastMileage) {
		this.lastMileage = lastMileage;
	}

	@Column(name = "last_upkeep_time", length = 19)
	public Timestamp getLastUpkeepTime() {
		return this.lastUpkeepTime;
	}

	public void setLastUpkeepTime(Timestamp lastUpkeepTime) {
		this.lastUpkeepTime = lastUpkeepTime;
	}

	@Column(name = "car_info_id", length = 100)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}
	
	@Column(name = "upkeep_cost", length = 100)
	public String getUpkeepCost() {
		return this.upkeepCost;
	}

	public void setUpkeepCost(String upkeepCost) {
		this.upkeepCost = upkeepCost;
	}

	@Column(name = "isdel")
	public Integer getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	@Column(name = "deltime", length = 19)
	public Timestamp getDeltime() {
		return this.deltime;
	}

	public void setDeltime(Timestamp deltime) {
		this.deltime = deltime;
	}
}