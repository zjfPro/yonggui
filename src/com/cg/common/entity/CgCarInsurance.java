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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * CgCarInsurance entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_insurance", catalog = "cg_yonggui")
public class CgCarInsurance implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String carInfoId;
	private String insured;
	private String insuranceName;
	private String insuranceCode;
	private String totalPrices;
	private Timestamp startTime;
	private Timestamp endTime;
	private String insurer;
	private String insurerAddress;
	private Timestamp addtime;
	private String insuredIdcard;
	private String insuredAddress;
	private String insuredPhone;
	private Integer isdel;
	private Timestamp deltime;
	private String compulsoryInsuranceMoney;
	private String thirdPartyLiability;
	private String driversSeatLiability;
	private String breakageOfGlass;
	private String threeLiabilityInsurance;
	private String paintDamage;
	private String vehicleDamage;
	private String robberyTheft;
	private String passengerSeatLiability;
	private String carDamage;
	private String remark;
	
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

	// Constructors

	/** default constructor */
	public CgCarInsurance() {
	}

	/** minimal constructor */
	public CgCarInsurance(Integer uid, String carInfoId) {
		this.uid = uid;
		this.carInfoId = carInfoId;
	}

	/** full constructor */
	public CgCarInsurance(Integer uid, String carInfoId, String insured,
			String insuranceName, String insuranceCode, String totalPrices,
			Timestamp startTime, Timestamp endTime, String insurer,
			String insurerAddress, Timestamp addtime, String insuredIdcard,
			String insuredAddress, String insuredPhone, Integer isdel,
			Timestamp deltime, String compulsoryInsuranceMoney,
			String thirdPartyLiability, String driversSeatLiability,
			String breakageOfGlass, String threeLiabilityInsurance,
			String paintDamage, String vehicleDamage, String robberyTheft,
			String passengerSeatLiability, String carDamage, String remark) {
		this.uid = uid;
		this.carInfoId = carInfoId;
		this.insured = insured;
		this.insuranceName = insuranceName;
		this.insuranceCode = insuranceCode;
		this.totalPrices = totalPrices;
		this.startTime = startTime;
		this.endTime = endTime;
		this.insurer = insurer;
		this.insurerAddress = insurerAddress;
		this.addtime = addtime;
		this.insuredIdcard = insuredIdcard;
		this.insuredAddress = insuredAddress;
		this.insuredPhone = insuredPhone;
		this.isdel = isdel;
		this.deltime = deltime;
		this.compulsoryInsuranceMoney = compulsoryInsuranceMoney;
		this.thirdPartyLiability = thirdPartyLiability;
		this.driversSeatLiability = driversSeatLiability;
		this.breakageOfGlass = breakageOfGlass;
		this.threeLiabilityInsurance = threeLiabilityInsurance;
		this.paintDamage = paintDamage;
		this.vehicleDamage = vehicleDamage;
		this.robberyTheft = robberyTheft;
		this.passengerSeatLiability = passengerSeatLiability;
		this.carDamage = carDamage;
		this.remark = remark;
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

	@Column(name = "car_info_id", nullable = false, length = 100)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}

	@Column(name = "insured", length = 100)
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "insurance_name", length = 100)
	public String getInsuranceName() {
		return this.insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	@Column(name = "insurance_code", length = 100)
	public String getInsuranceCode() {
		return this.insuranceCode;
	}

	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	@Column(name = "total_prices", length = 100)
	public String getTotalPrices() {
		return this.totalPrices;
	}

	public void setTotalPrices(String totalPrices) {
		this.totalPrices = totalPrices;
	}

	@Column(name = "start_time", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "insurer", length = 100)
	public String getInsurer() {
		return this.insurer;
	}

	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}

	@Column(name = "insurer_address", length = 100)
	public String getInsurerAddress() {
		return this.insurerAddress;
	}

	public void setInsurerAddress(String insurerAddress) {
		this.insurerAddress = insurerAddress;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "insured_idcard", length = 100)
	public String getInsuredIdcard() {
		return this.insuredIdcard;
	}

	public void setInsuredIdcard(String insuredIdcard) {
		this.insuredIdcard = insuredIdcard;
	}

	@Column(name = "insured_address")
	public String getInsuredAddress() {
		return this.insuredAddress;
	}

	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}

	@Column(name = "insured_phone", length = 100)
	public String getInsuredPhone() {
		return this.insuredPhone;
	}

	public void setInsuredPhone(String insuredPhone) {
		this.insuredPhone = insuredPhone;
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

	@Column(name = "compulsory_insurance_money", length = 100)
	public String getCompulsoryInsuranceMoney() {
		return this.compulsoryInsuranceMoney;
	}

	public void setCompulsoryInsuranceMoney(String compulsoryInsuranceMoney) {
		this.compulsoryInsuranceMoney = compulsoryInsuranceMoney;
	}

	@Column(name = "third_party_liability", length = 100)
	public String getThirdPartyLiability() {
		return this.thirdPartyLiability;
	}

	public void setThirdPartyLiability(String thirdPartyLiability) {
		this.thirdPartyLiability = thirdPartyLiability;
	}

	@Column(name = "drivers_seat_liability", length = 100)
	public String getDriversSeatLiability() {
		return this.driversSeatLiability;
	}

	public void setDriversSeatLiability(String driversSeatLiability) {
		this.driversSeatLiability = driversSeatLiability;
	}

	@Column(name = "breakage_of_glass", length = 100)
	public String getBreakageOfGlass() {
		return this.breakageOfGlass;
	}

	public void setBreakageOfGlass(String breakageOfGlass) {
		this.breakageOfGlass = breakageOfGlass;
	}

	@Column(name = "three_liability_insurance", length = 100)
	public String getThreeLiabilityInsurance() {
		return this.threeLiabilityInsurance;
	}

	public void setThreeLiabilityInsurance(String threeLiabilityInsurance) {
		this.threeLiabilityInsurance = threeLiabilityInsurance;
	}

	@Column(name = "paint_damage", length = 100)
	public String getPaintDamage() {
		return this.paintDamage;
	}

	public void setPaintDamage(String paintDamage) {
		this.paintDamage = paintDamage;
	}

	@Column(name = "vehicle_damage", length = 100)
	public String getVehicleDamage() {
		return this.vehicleDamage;
	}

	public void setVehicleDamage(String vehicleDamage) {
		this.vehicleDamage = vehicleDamage;
	}

	@Column(name = "robbery_theft", length = 100)
	public String getRobberyTheft() {
		return this.robberyTheft;
	}

	public void setRobberyTheft(String robberyTheft) {
		this.robberyTheft = robberyTheft;
	}

	@Column(name = "passenger_seat_liability", length = 100)
	public String getPassengerSeatLiability() {
		return this.passengerSeatLiability;
	}

	public void setPassengerSeatLiability(String passengerSeatLiability) {
		this.passengerSeatLiability = passengerSeatLiability;
	}

	@Column(name = "car_damage", length = 100)
	public String getCarDamage() {
		return this.carDamage;
	}

	public void setCarDamage(String carDamage) {
		this.carDamage = carDamage;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}