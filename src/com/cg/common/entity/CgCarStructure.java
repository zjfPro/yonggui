package com.cg.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgCarStructure entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_structure", catalog = "cg_yonggui")
public class CgCarStructure implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String name;

	// Constructors

	/** default constructor */
	public CgCarStructure() {
	}

	/** minimal constructor */
	public CgCarStructure(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgCarStructure(Integer uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
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

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}