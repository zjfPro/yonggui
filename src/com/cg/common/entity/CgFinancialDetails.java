package com.cg.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgFinancialDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_financial_details", catalog = "cg_yonggui")
public class CgFinancialDetails implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private Timestamp addtime;
	private String investorId;
	private String staffId;
	private String money;
	private String info;
	private String type;
	private String carRentalOrderId;
	private String investorWithdrawCashId;

	// Constructors

	/** default constructor */
	public CgFinancialDetails() {
	}

	/** minimal constructor */
	public CgFinancialDetails(Integer uid, Timestamp addtime, String money) {
		this.uid = uid;
		this.addtime = addtime;
		this.money = money;
	}

	/** full constructor */
	public CgFinancialDetails(Integer uid, Timestamp addtime,
			String investorId, String staffId, String money, String info,
			String type, String carRentalOrderId, String investorWithdrawCashId) {
		this.uid = uid;
		this.addtime = addtime;
		this.investorId = investorId;
		this.staffId = staffId;
		this.money = money;
		this.info = info;
		this.type = type;
		this.carRentalOrderId = carRentalOrderId;
		this.investorWithdrawCashId = investorWithdrawCashId;
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

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "investor_id", length = 80)
	public String getInvestorId() {
		return this.investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	@Column(name = "staff_id", length = 80)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(name = "money", nullable = false)
	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Column(name = "info")
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "car_rental_order_id", length = 80)
	public String getCarRentalOrderId() {
		return this.carRentalOrderId;
	}

	public void setCarRentalOrderId(String carRentalOrderId) {
		this.carRentalOrderId = carRentalOrderId;
	}

	@Column(name = "investor_withdraw_cash_id", length = 80)
	public String getInvestorWithdrawCashId() {
		return this.investorWithdrawCashId;
	}

	public void setInvestorWithdrawCashId(String investorWithdrawCashId) {
		this.investorWithdrawCashId = investorWithdrawCashId;
	}

}