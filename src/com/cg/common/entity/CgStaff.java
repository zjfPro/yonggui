package com.cg.common.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.gson.annotations.Expose;

/**
 * CgStaff entity. @author MyEclipse Persistence Tools员工表
 */
@Entity
@Table(name = "cg_staff", catalog = "cg_yonggui")
public class CgStaff implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	private Integer uid;
	private Timestamp addtime;
	@Expose
	private String name;
	private String logo;
	private String remarks;
	@Expose
	private String shopFrontId;
	private String roleName;
	private Timestamp updateTime;
	private String number;
	private String idcard;
	private Date birthDate;
	private Integer status;
	private String staffPositionId;
	private String decpId;
	private String account;
	private String password;
	private Timestamp entryTime;
	private Timestamp quitTime;
	private String phone;
	private String sparePhone;
	private String address;
	@Expose
	private CgShopFront cgShopFront;
	private CgStaffPosition cgStaffPosition;
	private CgDept cgDept;
	// Constructors

	/** default constructor */
	public CgStaff() {
	}

	/** minimal constructor */
	public CgStaff(Integer uid, Timestamp addtime, String name,
			String shopFrontId,
			String staffPositionId, String decpId, String account,
			String password) {
		this.uid = uid;
		this.addtime = addtime;
		this.name = name;
		this.shopFrontId = shopFrontId;
		this.staffPositionId = staffPositionId;
		this.decpId = decpId;
		this.account = account;
		this.password = password;
	}

	/** full constructor */
	public CgStaff(Integer uid, Timestamp addtime, String name, String logo,
			String remarks, String shopFrontId, String roleName,
			Timestamp updateTime, String number, String idcard, Date birthDate,
			Integer status, String staffPositionId, String decpId,
			String account, String password) {
		this.uid = uid;
		this.addtime = addtime;
		this.name = name;
		this.logo = logo;
		this.remarks = remarks;
		this.shopFrontId = shopFrontId;
		this.roleName = roleName;
		this.updateTime = updateTime;
		this.number = number;
		this.idcard = idcard;
		this.birthDate = birthDate;
		this.status = status;
		this.staffPositionId = staffPositionId;
		this.decpId = decpId;
		this.account = account;
		this.password = password;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "logo")
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "shop_front_id", nullable = false, length = 80)
	public String getShopFrontId() {
		return this.shopFrontId;
	}

	public void setShopFrontId(String shopFrontId) {
		this.shopFrontId = shopFrontId;
	}

	@Column(name = "role_name")
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "update_time", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "number")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "idcard")
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", length = 10)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "staff_position_id", nullable = false, length = 100)
	public String getStaffPositionId() {
		return this.staffPositionId;
	}

	public void setStaffPositionId(String staffPositionId) {
		this.staffPositionId = staffPositionId;
	}

	@Column(name = "decp_id", nullable = false, length = 100)
	public String getDecpId() {
		return this.decpId;
	}

	public void setDecpId(String decpId) {
		this.decpId = decpId;
	}

	@Column(name = "account", nullable = false, length = 100)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "entry_time", length = 19)
	public Timestamp getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}

	@Column(name = "quit_time", length = 19)
	public Timestamp getQuitTime() {
		return this.quitTime;
	}

	public void setQuitTime(Timestamp quitTime) {
		this.quitTime = quitTime;
	}
	@Column(name = "phone", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "spare_phone", length = 100)
	public String getSparePhone() {
		return this.sparePhone;
	}

	public void setSparePhone(String sparePhone) {
		this.sparePhone = sparePhone;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	@JoinColumn(name = "staff_position_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaffPosition getCgStaffPosition() {
		return cgStaffPosition;
	}

	public void setCgStaffPosition(CgStaffPosition cgStaffPosition) {
		this.cgStaffPosition = cgStaffPosition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "decp_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgDept getCgDept() {
		return cgDept;
	}

	public void setCgDept(CgDept cgDept) {
		this.cgDept = cgDept;
	}
	
	
}