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
 * CgCarRentalOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_rental_order", catalog = "cg_yonggui")
public class CgCarRentalOrder implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String carRentalItemId;
	private String userId;
	private String userName;
	private String userPhone;
	private String userAddress;
	private Timestamp addtime;
	private String snapshotJson;
	private String staffId;
	private String shopFrontId;
	private Integer orderCount;
	private String number;
	private Integer status;
	private String earnestMoney;
	private String foregiftMoney;
	private Integer isdel;
	private Timestamp deltime;
	private String buyMoney;
	private String refundMoney;
	private Timestamp refundTime;
	private Integer payStatus;
	private String payStaffId;
	private String examineRuningStaffId;
	private String examineEndStaffId;
	private String examineStopStaffId;
	private String userIdcar;
	private String contractId;
	
	private CgCarRentalItem cgCarRentalItem;
	private CgStaff cgStaff;
	private CgShopFront cgShopFront;
	
	
	private CgStaff cgStaffExamineRuningStaffId;
	private CgStaff cgStaffExamineEndStaffId;
	private CgStaff cgStaffExamineStopStaffId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examine_runing_staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getCgStaffExamineRuningStaffId() {
		return cgStaffExamineRuningStaffId;
	}

	public void setCgStaffExamineRuningStaffId(CgStaff cgStaffExamineRuningStaffId) {
		this.cgStaffExamineRuningStaffId = cgStaffExamineRuningStaffId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examine_end_staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getCgStaffExamineEndStaffId() {
		return cgStaffExamineEndStaffId;
	}

	public void setCgStaffExamineEndStaffId(CgStaff cgStaffExamineEndStaffId) {
		this.cgStaffExamineEndStaffId = cgStaffExamineEndStaffId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examine_stop_staff_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaff getCgStaffExamineStopStaffId() {
		return cgStaffExamineStopStaffId;
	}

	public void setCgStaffExamineStopStaffId(CgStaff cgStaffExamineStopStaffId) {
		this.cgStaffExamineStopStaffId = cgStaffExamineStopStaffId;
	}

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
	@JoinColumn(name = "car_rental_item_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarRentalItem getCgCarRentalItem() {
		return cgCarRentalItem;
	}

	public void setCgCarRentalItem(CgCarRentalItem cgCarRentalItem) {
		this.cgCarRentalItem = cgCarRentalItem;
	}

	/** default constructor */
	public CgCarRentalOrder() {
	}

	/** minimal constructor */
	public CgCarRentalOrder(Integer uid, String carRentalItemId) {
		this.uid = uid;
		this.carRentalItemId = carRentalItemId;
	}

	/** full constructor */
	public CgCarRentalOrder(Integer uid, String carRentalItemId, String userId,
			String userName, String userPhone, String userAddress,
			Timestamp addtime, String snapshotJson, String staffId,
			String shopFrontId, Integer orderCount, String number,
			Integer status, String earnestMoney, String foregiftMoney,
			Integer isdel, Timestamp deltime, String buyMoney,
			String refundMoney, Timestamp refundTime, Integer payStatus,
			String payStaffId, String examineRuningStaffId,
			String examineEndStaffId, String examineStopStaffId) {
		this.uid = uid;
		this.carRentalItemId = carRentalItemId;
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userAddress = userAddress;
		this.addtime = addtime;
		this.snapshotJson = snapshotJson;
		this.staffId = staffId;
		this.shopFrontId = shopFrontId;
		this.orderCount = orderCount;
		this.number = number;
		this.status = status;
		this.earnestMoney = earnestMoney;
		this.foregiftMoney = foregiftMoney;
		this.isdel = isdel;
		this.deltime = deltime;
		this.buyMoney = buyMoney;
		this.refundMoney = refundMoney;
		this.refundTime = refundTime;
		this.payStatus = payStatus;
		this.payStaffId = payStaffId;
		this.examineRuningStaffId = examineRuningStaffId;
		this.examineEndStaffId = examineEndStaffId;
		this.examineStopStaffId = examineStopStaffId;
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

	@Column(name = "car_rental_item_id", nullable = false, length = 80)
	public String getCarRentalItemId() {
		return this.carRentalItemId;
	}

	public void setCarRentalItemId(String carRentalItemId) {
		this.carRentalItemId = carRentalItemId;
	}

	@Column(name = "user_id", length = 80)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_phone")
	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Column(name = "user_address")
	public String getUserAddress() {
		return this.userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "snapshot_json")
	public String getSnapshotJson() {
		return this.snapshotJson;
	}

	public void setSnapshotJson(String snapshotJson) {
		this.snapshotJson = snapshotJson;
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

	@Column(name = "order_count")
	public Integer getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	@Column(name = "number")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Column(name = "buy_money")
	public String getBuyMoney() {
		return this.buyMoney;
	}

	public void setBuyMoney(String buyMoney) {
		this.buyMoney = buyMoney;
	}

	@Column(name = "refund_money")
	public String getRefundMoney() {
		return this.refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	@Column(name = "refund_time", length = 19)
	public Timestamp getRefundTime() {
		return this.refundTime;
	}

	public void setRefundTime(Timestamp refundTime) {
		this.refundTime = refundTime;
	}

	@Column(name = "pay_status")
	public Integer getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "pay_staff_id", length = 80)
	public String getPayStaffId() {
		return this.payStaffId;
	}

	public void setPayStaffId(String payStaffId) {
		this.payStaffId = payStaffId;
	}

	@Column(name = "examine_runing_staff_id", length = 80)
	public String getExamineRuningStaffId() {
		return this.examineRuningStaffId;
	}

	public void setExamineRuningStaffId(String examineRuningStaffId) {
		this.examineRuningStaffId = examineRuningStaffId;
	}

	@Column(name = "examine_end_staff_id", length = 80)
	public String getExamineEndStaffId() {
		return this.examineEndStaffId;
	}

	public void setExamineEndStaffId(String examineEndStaffId) {
		this.examineEndStaffId = examineEndStaffId;
	}

	@Column(name = "examine_stop_staff_id", length = 80)
	public String getExamineStopStaffId() {
		return this.examineStopStaffId;
	}

	public void setExamineStopStaffId(String examineStopStaffId) {
		this.examineStopStaffId = examineStopStaffId;
	}
	@Column(name = "user_idcar", length = 100)
	public String getUserIdcar() {
		return userIdcar;
	}

	public void setUserIdcar(String userIdcar) {
		this.userIdcar = userIdcar;
	}
	@Column(name = "contract_id", length = 80)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

}