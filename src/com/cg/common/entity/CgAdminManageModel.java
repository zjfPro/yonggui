package com.cg.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限模块表
 * CgAdminManageModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_admin_manage_model", catalog = "cg_yonggui")
public class CgAdminManageModel implements java.io.Serializable {

	// Fields

	private String id;
	private String url;//路径
	private String text;//模块解释
	private Integer type;//0查看   1操作    1会生成日志
	private Integer uid;
	
	// Constructors
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "uid", nullable = false)
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/** default constructor */
	public CgAdminManageModel() {
	}

	/** full constructor */
	public CgAdminManageModel(String id, String url, String text) {
		this.id = id;
		this.url = url;
		this.text = text;
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

	@Column(name = "url", nullable = false, length = 80)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "text", nullable = false, length = 80)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Column(name = "type", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}