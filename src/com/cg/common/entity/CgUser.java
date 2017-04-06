package com.cg.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

/**
 * CgUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_user", catalog = "cg_mdcpb")
public class CgUser implements java.io.Serializable {

	// Fields

	// Fields
	/**
	 * uuid
	 */
	@Expose
	private String id;
	/**
	 * 排序序号
	 */
	@Expose
	private Integer uid;
	/**
	 * 用户头像
	 */
	@Expose
	private String headImageUrl;
	/**
	 * 手机号码
	 */
	@Expose
	private String phone;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 添加时间
	 */
	@Expose
	private Timestamp addtime;
	/**
	 * 名字
	 */
	@Expose
	private String name;
	/**
	 * 个性签名
	 */
	@Expose
	private String signature;
	/**
	 * 类型 0学生 1教师 其他待定
	 */
	@Expose
	private Integer type;
	/**
	 * 性别 默认0 0男 1女
	 */
	@Expose
	private Integer sex;
	/**
	 * 家乡
	 */
	@Expose
	private String hometown;
	/**
	 * 生日
	 */
	@Expose
	private Date birthday;

	/**
	 * 学校名称
	 */
	@Expose
	private String schoolName;
	/**
	 * 入学时间
	 */
	@Expose
	private String schoolTime;
	/**
	 * 学院名称
	 */
	@Expose
	private String schoolCollege;
	/**
	 * 专业
	 */
	@Expose
	private String schoolMajor;
	/**
	 * 年级
	 */
	@Expose
	private String schoolGrade;
	/**
	 * 状态 -1禁用 其他正常
	 */
	@Expose
	private Integer status;
	/**
	 * 星座
	 */
	@Expose
	private String constellation;
	/**
	 * 隐藏设置 0隐藏 1显示 星座
	 */
	@Expose
	private Integer hideBirthday;
	/**
	 * 隐藏设置 0隐藏 1显示 性别
	 */
	@Expose
	private Integer hideSex;
	/**
	 * 隐藏设置 0隐藏 1显示 高校名称
	 */
	@Expose
	private Integer hideSchoolName;
	/**
	 * 隐藏设置 0隐藏 1显示 年级
	 */
	@Expose
	private Integer hideSchoolGrade;
	/**
	 * 隐藏设置 0隐藏 1显示 专业
	 */
	@Expose
	private Integer hideSchoolMajor;
	/**
	 * 隐藏设置 0隐藏 1显示 家乡
	 */
	@Expose
	private Integer hideHometown;

	/**
	 * 学号 或 职工号
	 */
	@Expose
	private String schoolNumber;

	/**
	 * 昵称
	 */
	@Expose
	private String nickName;
	/**
	 * 民族
	 */
	@Expose
	private String multiracial;
	/**
	 * 校方管理 0 不是校方管理 10 院级管理 20 本校管理
	 */
	@Expose
	private Integer schoolManager;
	/**
	 * 部门
	 */
	@Expose
	private String communitySector;
	// Constructors

	/** default constructor */
	public CgUser() {
	}

	/** minimal constructor */
	public CgUser(Integer uid, String phone, String password, Timestamp addtime) {
		this.uid = uid;
		this.phone = phone;
		this.password = password;
		this.addtime = addtime;
	}

	/** full constructor */
	public CgUser(Integer uid, String headImageUrl, String phone,
			String password, Timestamp addtime, String name, String signature,
			Integer type, Integer sex, String hometown, Date birthday,
			String schoolName, String schoolTime, String schoolCollege,
			String schoolMajor, String schoolGrade, Integer status,
			String constellation, Integer hideBirthday, Integer hideSex,
			Integer hideSchoolName, Integer hideSchoolGrade,
			Integer hideSchoolMajor, Integer hideHometown) {
		this.uid = uid;
		this.headImageUrl = headImageUrl;
		this.phone = phone;
		this.password = password;
		this.addtime = addtime;
		this.name = name;
		this.signature = signature;
		this.type = type;
		this.sex = sex;
		this.hometown = hometown;
		this.birthday = birthday;
		this.schoolName = schoolName;
		this.schoolTime = schoolTime;
		this.schoolCollege = schoolCollege;
		this.schoolMajor = schoolMajor;
		this.schoolGrade = schoolGrade;
		this.status = status;
		this.constellation = constellation;
		this.hideBirthday = hideBirthday;
		this.hideSex = hideSex;
		this.hideSchoolName = hideSchoolName;
		this.hideSchoolGrade = hideSchoolGrade;
		this.hideSchoolMajor = hideSchoolMajor;
		this.hideHometown = hideHometown;
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

	@Column(name = "head_image_url", length = 80)
	public String getHeadImageUrl() {
		return this.headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	@Column(name = "phone", nullable = false, length = 80)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "password", nullable = false, length = 80)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "name", length = 80)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "signature")
	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "sex")
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "hometown")
	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "school_name")
	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@Column(name = "school_time")
	public String getSchoolTime() {
		return this.schoolTime;
	}

	public void setSchoolTime(String schoolTime) {
		this.schoolTime = schoolTime;
	}

	@Column(name = "school_college")
	public String getSchoolCollege() {
		return this.schoolCollege;
	}

	public void setSchoolCollege(String schoolCollege) {
		this.schoolCollege = schoolCollege;
	}

	@Column(name = "school_major")
	public String getSchoolMajor() {
		return this.schoolMajor;
	}

	public void setSchoolMajor(String schoolMajor) {
		this.schoolMajor = schoolMajor;
	}

	@Column(name = "school_grade")
	public String getSchoolGrade() {
		return this.schoolGrade;
	}

	public void setSchoolGrade(String schoolGrade) {
		this.schoolGrade = schoolGrade;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "constellation")
	public String getConstellation() {
		return this.constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	@Column(name = "hide_birthday")
	public Integer getHideBirthday() {
		return this.hideBirthday;
	}

	public void setHideBirthday(Integer hideBirthday) {
		this.hideBirthday = hideBirthday;
	}

	@Column(name = "hide_sex")
	public Integer getHideSex() {
		return this.hideSex;
	}

	public void setHideSex(Integer hideSex) {
		this.hideSex = hideSex;
	}

	@Column(name = "hide_school_name")
	public Integer getHideSchoolName() {
		return this.hideSchoolName;
	}

	public void setHideSchoolName(Integer hideSchoolName) {
		this.hideSchoolName = hideSchoolName;
	}

	@Column(name = "hide_school_grade")
	public Integer getHideSchoolGrade() {
		return this.hideSchoolGrade;
	}

	public void setHideSchoolGrade(Integer hideSchoolGrade) {
		this.hideSchoolGrade = hideSchoolGrade;
	}

	@Column(name = "hide_school_major")
	public Integer getHideSchoolMajor() {
		return this.hideSchoolMajor;
	}

	public void setHideSchoolMajor(Integer hideSchoolMajor) {
		this.hideSchoolMajor = hideSchoolMajor;
	}

	@Column(name = "hide_hometown")
	public Integer getHideHometown() {
		return this.hideHometown;
	}

	public void setHideHometown(Integer hideHometown) {
		this.hideHometown = hideHometown;
	}

	@Column(name = "school_number")
	public String getSchoolNumber() {
		return schoolNumber;
	}

	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}

	@Column(name = "nick_name")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "multiracial")
	public String getMultiracial() {
		return multiracial;
	}

	public void setMultiracial(String multiracial) {
		this.multiracial = multiracial;
	}

	@Column(name = "school_manager")
	public Integer getSchoolManager() {
		return schoolManager;
	}

	public void setSchoolManager(Integer schoolManager) {
		this.schoolManager = schoolManager;
	}
	
	
	@Column(name = "community_sector")
	public String getCommunitySector() {
		return communitySector;
	}

	public void setCommunitySector(String communitySector) {
		this.communitySector = communitySector;
	}

}