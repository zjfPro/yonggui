package com.cg.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cg.core.annotation.JsonHide;

/**
 * 管理员表
 * zjx
 * CgAdmin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_admin", catalog = "cg_yonggui")
public class CgAdmin implements java.io.Serializable {

	// Fields
	/**
	 * uuid id
	 */
	private String id; 
	/**
	 * 排序序号
	 */
	private Integer uid;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码 加密
	 */
	@JsonHide
	private String password;
	/**
	 * 创建日期
	 */
	private Timestamp createDate;
	/**
	 * 最后登录日期
	 */
	private Timestamp lastLoginDate;
	/**
	 * 最后登录时间
	 */
	private String lastLoginIp;
	/**
	 * 名字
	 */
	private String nickname;
	/**
	 * 状态 0未启用 1已启用 -1已禁用
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public CgAdmin() {
	}

	/** minimal constructor */
	public CgAdmin(Integer uid, String account) {
		this.uid = uid;
		this.account = account;
	}

	/** full constructor */
	public CgAdmin(Integer uid, String account, String password,
			Timestamp createDate, Timestamp lastLoginDate, String lastLoginIp,
			String nickname, Integer status) {
		this.uid = uid;
		this.account = account;
		this.password = password;
		this.createDate = createDate;
		this.lastLoginDate = lastLoginDate;
		this.lastLoginIp = lastLoginIp;
		this.nickname = nickname;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 60)
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

	@Column(name = "account", nullable = false)
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

	@Column(name = "create_date", length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "last_login_date", length = 19)
	public Timestamp getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@Column(name = "last_login_ip")
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(name = "nickname")
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}