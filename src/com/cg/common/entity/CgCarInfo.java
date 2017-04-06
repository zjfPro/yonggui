package com.cg.common.entity;
// default package

import java.sql.Timestamp;
import java.util.Date;

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

import com.cg.common.entity.CgCarType;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.google.gson.annotations.Expose;

/**
 * CgCarInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_info", catalog = "cg_yonggui")
public class CgCarInfo implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	private Integer uid;
	@Expose
	private String carTypeId;
	@Expose
	private String plateNumber;
	private String annualVerificationId;
	private String carAge;
	private String mileage;
	private String owner;
	@Expose
	private String shopFrontId;
	@Expose
	private String staffId;
	@Expose
	private String picture;
	private Timestamp addtime;
	private Timestamp startTimeOfContract;
	private Timestamp endTimeOfContract;
	private String unitName;
	private Integer unitNumber;
	private Timestamp updateTime;
	private String updateStaffId;
	private String remark;
	private String contractId;
	private Integer status;
	private String engineNumber;
	private String gpsNumber;
	private String nature;
	private String loads;
	private Timestamp buyTime;
	private String scope;
	private String frameNumber;
	private String park;
	/** 车类型 */
	@Expose
	private CgCarType carType;
	/** 门店 */
	private CgShopFront shopFront;
	/** 员工表 */
	private CgStaff staff;
	/** 合同*/
	private CgContract contract;
	/** 车品牌*/
	private CgCarBrand brands;
	/** 管理员*/
	private CgAdmin admin;
	// Constructors

	/** default constructor */
	public CgCarInfo() {
	}

	/** minimal constructor */
	public CgCarInfo(Integer uid, String carTypeId, String plateNumber,
			String annualVerificationId, String mileage, String owner,
			String shopFrontId, Timestamp addtime) {
		this.uid = uid;
		this.carTypeId = carTypeId;
		this.plateNumber = plateNumber;
		this.annualVerificationId = annualVerificationId;
		this.mileage = mileage;
		this.owner = owner;
		this.shopFrontId = shopFrontId;
		this.addtime = addtime;
	}

	/** full constructor */
	public CgCarInfo(Integer uid, String carTypeId, String plateNumber,
			String annualVerificationId, String carAge, String mileage,
			String owner, String shopFrontId, String staffId, String picture,
		    Timestamp addtime, Timestamp startTimeOfContract,
			Timestamp endTimeOfContract, String unitName,
			Integer unitNumber, Timestamp updateTime,
			String updateStaffId, String remark, String contractId,
			Integer status, String engineNumber) {
		this.uid = uid;
		this.carTypeId = carTypeId;
		this.plateNumber = plateNumber;
		this.annualVerificationId = annualVerificationId;
		this.carAge = carAge;
		this.mileage = mileage;
		this.owner = owner;
		this.shopFrontId = shopFrontId;
		this.staffId = staffId;
		this.picture = picture;
		this.addtime = addtime;
		this.startTimeOfContract = startTimeOfContract;
		this.endTimeOfContract = endTimeOfContract;
		this.unitName = unitName;
		this.unitNumber = unitNumber;
		this.updateTime = updateTime;
		this.updateStaffId = updateStaffId;
		this.remark = remark;
		this.contractId = contractId;
		this.status = status;
		this.engineNumber = engineNumber;
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

	@Column(name = "car_type_id", nullable = false, length = 100)
	public String getCarTypeId() {
		return this.carTypeId;
	}

	public void setCarTypeId(String carTypeId) {
		this.carTypeId = carTypeId;
	}

	@Column(name = "plate_number", nullable = false, length = 100)
	public String getPlateNumber() {
		return this.plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Column(name = "annual_verification_id", nullable = false)
	public String getAnnualVerificationId() {
		return this.annualVerificationId;
	}

	public void setAnnualVerificationId(String annualVerificationId) {
		this.annualVerificationId = annualVerificationId;
	}

	@Column(name = "car_age", length = 10)
	public String getCarAge() {
		return this.carAge;
	}

	public void setCarAge(String carAge) {
		this.carAge = carAge;
	}

	@Column(name = "mileage", nullable = false, length = 100)
	public String getMileage() {
		return this.mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	@Column(name = "owner", nullable = false)
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "shop_front_id", nullable = false)
	public String getShopFrontId() {
		return this.shopFrontId;
	}

	public void setShopFrontId(String shopFrontId) {
		this.shopFrontId = shopFrontId;
	}

	@Column(name = "staff_id")
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(name = "picture")
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "start_time_of_contract", length = 19)
	public Timestamp getStartTimeOfContract() {
		return this.startTimeOfContract;
	}

	public void setStartTimeOfContract(Timestamp startTimeOfContract) {
		this.startTimeOfContract = startTimeOfContract;
	}

	@Column(name = "end_time_of_contract", length = 19)
	public Timestamp getEndTimeOfContract() {
		return this.endTimeOfContract;
	}

	public void setEndTimeOfContract(Timestamp endTimeOfContract) {
		this.endTimeOfContract = endTimeOfContract;
	}

	@Column(name = "unit_name")
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

	@Column(name = "update_time", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "update_staff_id", length = 80)
	public String getUpdateStaffId() {
		return this.updateStaffId;
	}

	public void setUpdateStaffId(String updateStaffId) {
		this.updateStaffId = updateStaffId;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "contract_id")
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "engine_number", length = 100)
	public String getEngineNumber() {
		return this.engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_type_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarType getCarType() {
		return carType;
	}

	public void setCarType(CgCarType carType) {
		this.carType = carType;
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
	@JoinColumn(name = "contract_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgContract getContract() {
		return contract;
	}

	public void setContract(CgContract contract) {
		this.contract = contract;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plate_number", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarBrand getBrand() {
		return brands;
	}

	public void setBrand(CgCarBrand brands) {
		this.brands = brands;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "update_staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(CgAdmin admin) {
		this.admin = admin;
	}
	
	@Column(name = "gps_number", length = 100)
	public String getGpsNumber() {
		return this.gpsNumber;
	}

	public void setGpsNumber(String gpsNumber) {
		this.gpsNumber = gpsNumber;
	}

	@Column(name = "nature", length = 100)
	public String getNature() {
		return this.nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Column(name = "loads", length = 100)
	public String getLoads() {
		return this.loads;
	}

	public void setLoads(String loads) {
		this.loads = loads;
	}

	@Column(name = "buy_time", length = 19)
	public Timestamp getBuyTime() {
		return this.buyTime;
	}

	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}

	@Column(name = "scope", length = 100)
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Column(name = "frame_number", length = 100)
	public String getFrameNumber() {
		return this.frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	@Column(name = "park", length = 100)
	public String getPark() {
		return this.park;
	}

	public void setPark(String park) {
		this.park = park;
	}
}