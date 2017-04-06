package com.cg.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgFeedback entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_feedback", catalog = "cg_yonggui")
public class CgFeedback implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String phone;
	private String qq;
	private String content;
	private Timestamp addtime;

	// Constructors

	/** default constructor */
	public CgFeedback() {
	}

	/** full constructor */
	public CgFeedback(String name, String phone, String qq, String content,
			Timestamp addtime) {
		this.name = name;
		this.phone = phone;
		this.qq = qq;
		this.content = content;
		this.addtime = addtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "phone", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "qq", length = 100)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

}