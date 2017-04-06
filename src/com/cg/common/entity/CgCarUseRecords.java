package com.cg.common.entity;

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

import com.google.gson.annotations.Expose;

/**
 * CgCarUseRecords entity. @author MyEclipse Persistence Tools
 * 车辆使用记录
 */
@Entity
@Table(name = "cg_car_use_records", catalog = "cg_yonggui")
public class CgCarUseRecords implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private Date addtime;
	/** 操作员工id*/
	private String satffId;
	/**借车人名字 */
	private String useUserName;
	/** 借车人联系方式*/
	private String useUserPhone;
	/** 车辆关联*/
	private String carInfoId;
	/**所属门店*/
	private String shopFrontId;
	/** 车牌*/
	private String plateNumber;
	/**当前里程数 */
	private Integer currentMileage;
	/** 归还时间*/
	private Date returnTime;
	/**归还时的里程数 */
	private Integer returnMileage;
	/** 编号*/
	private String number;
	/** 租车订单关联id*/
	private String carRentalOrderId;
	/** 还车时 员工的id*/
	private String handoverStaffId;
	/** 门店 */
	private CgShopFront shopFront;
	/** 员工表 */
	private CgStaff staff;
	private CgStaff staff2;
	/** 订单表 */
	private CgCarRentalOrder orders;
	/** 车辆信息表 */
	private CgCarInfo infos;
	// Constructors

	/** default constructor */
	public CgCarUseRecords() {
	}

	/** minimal constructor */
	public CgCarUseRecords(Integer uid, String satffId, String carInfoId,
			String shopFrontId, String plateNumber, Integer currentMileage) {
		this.uid = uid;
		this.satffId = satffId;
		this.carInfoId = carInfoId;
		this.shopFrontId = shopFrontId;
		this.plateNumber = plateNumber;
		this.currentMileage = currentMileage;
	}

	/** full constructor */
	public CgCarUseRecords(Integer uid, Date addtime, String satffId,
			String useUserName, String useUserPhone, String carInfoId,
			String shopFrontId, String plateNumber, Integer currentMileage,
			Date returnTime, Integer returnMileage, String number,
			String carRentalOrderId, String handoverStaffId) {
		this.uid = uid;
		this.addtime = addtime;
		this.satffId = satffId;
		this.useUserName = useUserName;
		this.useUserPhone = useUserPhone;
		this.carInfoId = carInfoId;
		this.shopFrontId = shopFrontId;
		this.plateNumber = plateNumber;
		this.currentMileage = currentMileage;
		this.returnTime = returnTime;
		this.returnMileage = returnMileage;
		this.number = number;
		this.carRentalOrderId = carRentalOrderId;
		this.handoverStaffId = handoverStaffId;
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

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Column(name = "satff_id", nullable = false, length = 80)
	public String getSatffId() {
		return this.satffId;
	}

	public void setSatffId(String satffId) {
		this.satffId = satffId;
	}

	@Column(name = "use_user_name", length = 80)
	public String getUseUserName() {
		return this.useUserName;
	}

	public void setUseUserName(String useUserName) {
		this.useUserName = useUserName;
	}

	@Column(name = "use_user_phone")
	public String getUseUserPhone() {
		return this.useUserPhone;
	}

	public void setUseUserPhone(String useUserPhone) {
		this.useUserPhone = useUserPhone;
	}

	@Column(name = "car_info_id", nullable = false)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}

	@Column(name = "shop_front_id", nullable = false, length = 80)
	public String getShopFrontId() {
		return this.shopFrontId;
	}

	public void setShopFrontId(String shopFrontId) {
		this.shopFrontId = shopFrontId;
	}

	@Column(name = "plate_number", nullable = false, length = 100)
	public String getPlateNumber() {
		return this.plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Column(name = "current_mileage", nullable = false)
	public Integer getCurrentMileage() {
		return this.currentMileage;
	}

	public void setCurrentMileage(Integer currentMileage) {
		this.currentMileage = currentMileage;
	}

	@Column(name = "return_time", length = 19)
	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	@Column(name = "return_mileage")
	public Integer getReturnMileage() {
		return this.returnMileage;
	}

	public void setReturnMileage(Integer returnMileage) {
		this.returnMileage = returnMileage;
	}

	@Column(name = "number")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "car_rental_order_id", length = 80)
	public String getCarRentalOrderId() {
		return this.carRentalOrderId;
	}

	public void setCarRentalOrderId(String carRentalOrderId) {
		this.carRentalOrderId = carRentalOrderId;
	}

	@Column(name = "handover_staff_id", length = 80)
	public String getHandoverStaffId() {
		return this.handoverStaffId;
	}

	public void setHandoverStaffId(String handoverStaffId) {
		this.handoverStaffId = handoverStaffId;
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
	@JoinColumn(name = "satff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getStaff() {
		return staff;
	}

	public void setStaff(CgStaff staff) {
		this.staff = staff;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "handover_staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getStaff2() {
		return staff2;
	}

	public void setStaff2(CgStaff staff2) {
		this.staff2 = staff2;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_rental_order_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarRentalOrder getOrders() {
		return orders;
	}

	public void setOrders(CgCarRentalOrder orders) {
		this.orders = orders;
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
	
}