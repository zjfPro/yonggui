package com.cg.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
 * CgStaffManageModel entity. @author MyEclipse Persistence Tools模块表
 */
@Entity
@Table(name = "cg_staff_manage_model", catalog = "cg_yonggui")
public class CgStaffManageModel implements java.io.Serializable {

	// Fields

	@Expose
	private Integer id;
	@Expose
	private String url;
	@Expose
	private String text;
	@Expose
	private Integer fatId;
	@Expose
	private Integer type;
	
	private CgStaffManageModel csmm;
	
	

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "fat_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaffManageModel getCsmm() {
		return csmm;
	}

	public void setCsmm(CgStaffManageModel csmm) {
		this.csmm = csmm;
	}

	/** default constructor */
	public CgStaffManageModel() {
	}

	/** minimal constructor */
	public CgStaffManageModel(String url, String text) {
		this.url = url;
		this.text = text;
	}

	/** full constructor */
	public CgStaffManageModel(String url, String text, Integer fatId,
			Integer type) {
		this.url = url;
		this.text = text;
		this.fatId = fatId;
		this.type = type;
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

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "text", nullable = false)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "fat_id")
	public Integer getFatId() {
		return this.fatId;
	}

	public void setFatId(Integer fatId) {
		this.fatId = fatId;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}