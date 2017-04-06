package com.cg.common.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgAbout entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_about", catalog = "cg_yonggui")
public class CgAbout implements java.io.Serializable {

	// Fields

	private Integer id;
	private String address;
	private String phone;
	private String qq;
	private String qqGroup;
	private String wx;
	private String oftenPhones;
	private String postcode;
	private String title;
	private String content;

	// Constructors

	/** default constructor */
	public CgAbout() {
	}

	/** full constructor */
	public CgAbout(String address, String phone, String qq, String qqGroup,
			String wx, String oftenPhones, String postcode, String title,
			String content) {
		this.address = address;
		this.phone = phone;
		this.qq = qq;
		this.qqGroup = qqGroup;
		this.wx = wx;
		this.oftenPhones = oftenPhones;
		this.postcode = postcode;
		this.title = title;
		this.content = content;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "qq")
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "qq_group")
	public String getQqGroup() {
		return this.qqGroup;
	}

	public void setQqGroup(String qqGroup) {
		this.qqGroup = qqGroup;
	}

	@Column(name = "wx")
	public String getWx() {
		return this.wx;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}

	@Column(name = "often_phones")
	public String getOftenPhones() {
		return this.oftenPhones;
	}

	public void setOftenPhones(String oftenPhones) {
		this.oftenPhones = oftenPhones;
	}

	@Column(name = "postcode")
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}