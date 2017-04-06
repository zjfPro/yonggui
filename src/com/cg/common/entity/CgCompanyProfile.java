package com.cg.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgCompanyProfile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_public_company_profile", catalog = "cg_yonggui")
public class CgCompanyProfile implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String title;
	private String content;
	private String remark;
	private Timestamp addtime;
	private String picture;

	// Constructors

	/** default constructor */
	public CgCompanyProfile() {
	}

	/** minimal constructor */
	public CgCompanyProfile(Integer uid, String title, String content,
			Timestamp addtime) {
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.addtime = addtime;
	}

	/** full constructor */
	public CgCompanyProfile(Integer uid, String title, String content,
			String remark, Timestamp addtime, String picture) {
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.remark = remark;
		this.addtime = addtime;
		this.picture = picture;
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

	@Column(name = "title", nullable = false, length = 20)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "picture", length = 100)
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}