package com.cg.common.entity;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgInvestor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_investor", catalog = "cg_yonggui")
public class CgInvestor implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String name;
	private String idcard;
	private String picture;
	private Integer isdel;
	private Timestamp deltime;
	private String balance;
	private String account;
	private String password;
	private String phone;
	private String number;
	private Integer sex;
	private String address;
	// Constructors

	/** default constructor */
	public CgInvestor() {
	}

	/** minimal constructor */
	public CgInvestor(String id, Integer uid, String name, String idcard) {
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.idcard = idcard;
	}

	/** full constructor */
	public CgInvestor(String id, Integer uid, String name, String idcard,
			String picture, Integer isdel, Timestamp deltime, String balance,
			String account, String password, String phone, String number,Integer sex,String address) {
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.idcard = idcard;
		this.picture = picture;
		this.isdel = isdel;
		this.deltime = deltime;
		this.balance = balance;
		this.account = account;
		this.password = password;
		this.phone = phone;
		this.number = number;
		this.sex = sex;
		this.address = address;
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

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "idcard", nullable = false, length = 100)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "picture")
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	@Column(name = "balance")
	public String getBalance() {
		return this.balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Column(name = "account")
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "sex")
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}