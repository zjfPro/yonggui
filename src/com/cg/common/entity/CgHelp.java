package com.cg.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgHelp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_help", catalog = "cg_yonggui")
public class CgHelp implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String helpType;
	private String content;
	private String title;


	// Constructors

	/** default constructor */
	public CgHelp() {
	}

	/** minimal constructor */
	public CgHelp(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgHelp(Integer uid, String helpType, String content) {
		this.uid = uid;
		this.helpType = helpType;
		this.content = content;
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
	
	@GeneratedValue
	@Column(name = "uid", nullable = false)
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "help_type")
	public String getHelpType() {
		return this.helpType;
	}

	public void setHelpType(String helpType) {
		this.helpType = helpType;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}