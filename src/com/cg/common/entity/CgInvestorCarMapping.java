package com.cg.common.entity;
// default package

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
 * CgInvestorCarMapping entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_investor_car_mapping", catalog = "cg_yonggui")
public class CgInvestorCarMapping implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private Timestamp addtime;
	private String investorId;
	private String carInfoId;
	private Integer type;
	private Double stockRight;
	private String money;
	private Integer isdel;
	private Timestamp deltime;
	private String bonus;
	
	private CgInvestor investor;
	private CgCarInfo carInfo;
	// Constructors

	/** default constructor */
	public CgInvestorCarMapping() {
	}

	/** minimal constructor */
	public CgInvestorCarMapping(Integer uid, String investorId, String carInfoId) {
		this.uid = uid;
		this.investorId = investorId;
		this.carInfoId = carInfoId;
	}

	/** full constructor */
	public CgInvestorCarMapping(Integer uid, Timestamp addtime,
			String investorId, String carInfoId, Integer type,
			Double stockRight, String money, Integer isdel, Timestamp deltime,String bonus) {
		this.uid = uid;
		this.addtime = addtime;
		this.investorId = investorId;
		this.carInfoId = carInfoId;
		this.type = type;
		this.stockRight = stockRight;
		this.money = money;
		this.isdel = isdel;
		this.deltime = deltime;
		this.bonus = bonus;
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

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "investor_id", nullable = false, length = 80)
	public String getInvestorId() {
		return this.investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	@Column(name = "car_info_id", nullable = false, length = 80)
	public String getCarInfoId() {
		return this.carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "stock_right", precision = 10)
	public Double getStockRight() {
		return this.stockRight;
	}

	public void setStockRight(Double stockRight) {
		this.stockRight = stockRight;
	}

	@Column(name = "bonus")
	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	@Column(name = "money")
	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
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
		return deltime;
	}

	public void setDeltime(Timestamp deltime) {
		this.deltime = deltime;
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
	@JoinColumn(name = "car_info_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CgCarInfo carInfo) {
		this.carInfo = carInfo;
	}
	
	
}