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
 * CgCarPeccancy entity. @author MyEclipse Persistence Tools
 * 违章
 */
@Entity
@Table(name = "cg_car_peccancy", catalog = "cg_yonggui")
public class CgCarPeccancy implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private Timestamp addtime;
	private Integer payStatus;
	private Timestamp payTime;
	private String staffId;
	private String carInfoId;
	private String driverId;
	private String carUseRecordsId;
	private Timestamp peccancyTime;
	private String peccancyAddress;
	private String peccancyInfo;
	private String peccancyDescribe;
	private String punish;
	private String fineMoney;
	private Double driverProportion;
	private Double companyProportion;
	private String driverFineMoney;
	private String companyFineMoney;
	private String companyPunishmentMoney;
	private String remark;
	private String driverName;
	
	private CgStaff staff;
	
	private CgCarInfo infos;
	
	private CgCarUseRecords records;
	
	private CgDriver drivers;
	// Constructors

	/** default constructor */
	public CgCarPeccancy() {
	}

	/** minimal constructor */
	public CgCarPeccancy(Integer uid, String staffId, String carInfoId,
			String driverId) {
		this.uid = uid;
		this.staffId = staffId;
		this.carInfoId = carInfoId;
		this.driverId = driverId;
	}

	/** full constructor */
	public CgCarPeccancy(Integer uid, Timestamp addtime, Integer payStatus,
			Timestamp payTime, String staffId, String carInfoId, String driverId,
			String carUseRecordsId, Timestamp peccancyTime, String peccancyAddress,
			String peccancyInfo, String peccancyDescribe, String punish,
			String fineMoney, double driverProportion,
			double companyProportion, String driverFineMoney,
			String companyFineMoney, String companyPunishmentMoney,
			String remark) {
		this.uid = uid;
		this.addtime = addtime;
		this.payStatus = payStatus;
		this.payTime = payTime;
		this.staffId = staffId;
		this.carInfoId = carInfoId;
		this.driverId = driverId;
		this.carUseRecordsId = carUseRecordsId;
		this.peccancyTime = peccancyTime;
		this.peccancyAddress = peccancyAddress;
		this.peccancyInfo = peccancyInfo;
		this.peccancyDescribe = peccancyDescribe;
		this.punish = punish;
		this.fineMoney = fineMoney;
		this.driverProportion = driverProportion;
		this.companyProportion = companyProportion;
		this.driverFineMoney = driverFineMoney;
		this.companyFineMoney = companyFineMoney;
		this.companyPunishmentMoney = companyPunishmentMoney;
		this.remark = remark;
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

	@Column(name = "addtime", length = 19)
	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "pay_status")
	public Integer getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "pay_time", length = 19)
	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@Column(name = "staff_id", nullable = false, length = 80)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(name = "car_info_id", nullable = false, length = 80)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}

	@Column(name = "driver_id", nullable = false, length = 80)
	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	@Column(name = "car_use_records_id", length = 80)
	public String getCarUseRecordsId() {
		return this.carUseRecordsId;
	}

	public void setCarUseRecordsId(String carUseRecordsId) {
		this.carUseRecordsId = carUseRecordsId;
	}

	@Column(name = "peccancy_time", length = 19)
	public Date getPeccancyTime() {
		return this.peccancyTime;
	}

	public void setPeccancyTime(Timestamp peccancyTime) {
		this.peccancyTime = peccancyTime;
	}

	@Column(name = "peccancy_address")
	public String getPeccancyAddress() {
		return this.peccancyAddress;
	}

	public void setPeccancyAddress(String peccancyAddress) {
		this.peccancyAddress = peccancyAddress;
	}

	@Column(name = "peccancy_info")
	public String getPeccancyInfo() {
		return this.peccancyInfo;
	}

	public void setPeccancyInfo(String peccancyInfo) {
		this.peccancyInfo = peccancyInfo;
	}

	@Column(name = "peccancy_describe")
	public String getPeccancyDescribe() {
		return this.peccancyDescribe;
	}

	public void setPeccancyDescribe(String peccancyDescribe) {
		this.peccancyDescribe = peccancyDescribe;
	}

	@Column(name = "punish")
	public String getPunish() {
		return this.punish;
	}

	public void setPunish(String punish) {
		this.punish = punish;
	}

	@Column(name = "fine_money")
	public String getFineMoney() {
		return this.fineMoney;
	}

	public void setFineMoney(String fineMoney) {
		this.fineMoney = fineMoney;
	}

	@Column(name = "driver_proportion", precision = 10, scale = 3)
	public Double getDriverProportion() {
		return this.driverProportion;
	}

	public void setDriverProportion(Double driverProportion) {
		this.driverProportion = driverProportion;
	}

	@Column(name = "company_proportion", precision = 10, scale = 3)
	public Double getCompanyProportion() {
		return this.companyProportion;
	}

	public void setCompanyProportion(Double companyProportion) {
		this.companyProportion = companyProportion;
	}

	@Column(name = "driver_fine_money")
	public String getDriverFineMoney() {
		return this.driverFineMoney;
	}

	public void setDriverFineMoney(String driverFineMoney) {
		this.driverFineMoney = driverFineMoney;
	}

	@Column(name = "company_fine_money")
	public String getCompanyFineMoney() {
		return this.companyFineMoney;
	}

	public void setCompanyFineMoney(String companyFineMoney) {
		this.companyFineMoney = companyFineMoney;
	}

	@Column(name = "company_punishment_money")
	public String getCompanyPunishmentMoney() {
		return this.companyPunishmentMoney;
	}

	public void setCompanyPunishmentMoney(String companyPunishmentMoney) {
		this.companyPunishmentMoney = companyPunishmentMoney;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getStaff() {
		return staff;
	}

	public void setStaff(CgStaff staff) {
		this.staff = staff;
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
	@JoinColumn(name = "car_use_records_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarUseRecords getRecords() {
		return records;
	}

	public void setRecords(CgCarUseRecords records) {
		this.records = records;
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
	
	@Column(name = "driverName", length = 100)
	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
}