package com.cg.common.entity;

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

import com.google.gson.annotations.Expose;

/**
 * CgContract entity. @author MyEclipse Persistence Tools
 * 合同表
 */
@Entity
@Table(name = "cg_contract", catalog = "cg_yonggui")
public class CgContract implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	private Integer uid;
	/** 合同内容 */
	private String content;
	/** 门店 */
	private String shopFrontId;
	/** 投资人 */
	private String investorId;
	/** 员工 */
	private String staffId;
	/** 车牌号 */
	private String plateNumber;
	/** 合同编号 */
	@Expose
	private String number;
	/** 订单 */
	private String orderId;
	/** 合同类型 */
	private Integer type;
	
	private String name;
	private String principal;
	private Timestamp contractTime;
	private Integer status;
	private Double contractMoney;
	private Integer clearingForm;
	private Timestamp startTime;
	private Timestamp endTime;
	private String remark;
	
	
	
	
	/** 注入门店 */
	private CgShopFront front;
	/** 投资人 */
	private CgInvestor investor;
	/** 员工 */
	private CgStaff staff;
	/** 订单 */
	private CgCarRentalOrder infos;
	// Constructors

	/** default constructor */
	public CgContract() {
	}

	/** minimal constructor */
	public CgContract(Integer uid, String content) {
		this.uid = uid;
		this.content = content;
	}

	/** full constructor */
	public CgContract(Integer uid, String content, String shopFrontId,
			String investorId, String staffId) {
		this.uid = uid;
		this.content = content;
		this.shopFrontId = shopFrontId;
		this.investorId = investorId;
		this.staffId = staffId;
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

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "shop_front_id", length = 100)
	public String getShopFrontId() {
		return this.shopFrontId;
	}

	public void setShopFrontId(String shopFrontId) {
		this.shopFrontId = shopFrontId;
	}

	@Column(name = "investor_id", length = 100)
	public String getInvestorId() {
		return this.investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	@Column(name = "staff_id", length = 100)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_front_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgShopFront getFront() {
		return front;
	}
	
	public void setFront(CgShopFront front) {
		this.front = front;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "investor_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgInvestor getInvestor() {
		return investor;
	}
	
	public void setInvestor(CgInvestor investor) {
		this.investor = investor;
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
	
	@Column(name = "plate_number", length = 100)
	public String getPlateNumber() {
		return this.plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	@Column(name = "number", length = 100)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "order_id", length = 100)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarRentalOrder getInfos() {
		return infos;
	}
	
	public void setInfos(CgCarRentalOrder infos) {
		this.infos = infos;
	}
	
	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "principal", length = 100)
	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	@Column(name = "contract_time", length = 19)
	public Timestamp getContractTime() {
		return this.contractTime;
	}

	public void setContractTime(Timestamp contractTime) {
		this.contractTime = contractTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "contract_money", precision = 10, scale = 3)
	public Double getContractMoney() {
		return this.contractMoney;
	}

	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	@Column(name = "clearing_form")
	public Integer getClearingForm() {
		return this.clearingForm;
	}

	public void setClearingForm(Integer clearingForm) {
		this.clearingForm = clearingForm;
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

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}