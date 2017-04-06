package com.cg.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * CgNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_notice", catalog = "cg_yonggui")
public class CgNotice implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String title;
	private String noticeContent;
	private String adminId;
	private Timestamp addtime;
	private Integer status;
	private Integer type;
	private String noticeContentBrief;
	private Timestamp endTime;
	private Timestamp startTime;
	private String picture;
	private CgAdmin admin;
	
	
	// Constructors

	/** default constructor */
	public CgNotice() {
	}

	/** minimal constructor */
	public CgNotice(Integer uid, String title, String noticeContent) {
		this.uid = uid;
		this.title = title;
		this.noticeContent = noticeContent;
	}

	/** full constructor */
	public CgNotice(Integer uid, String title, String noticeContent,
			String adminId, Timestamp addtime, Integer status, Integer type,
			String noticeContentBrief, Timestamp endTime, Timestamp startTime,String picture) {
		this.uid = uid;
		this.title = title;
		this.noticeContent = noticeContent;
		this.adminId = adminId;
		this.addtime = addtime;
		this.status = status;
		this.type = type;
		this.noticeContentBrief = noticeContentBrief;
		this.endTime = endTime;
		this.startTime = startTime;
		this.picture = picture;
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

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "notice_content", nullable = false)
	public String getNoticeContent() {
		return this.noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	@Column(name = "picture")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Column(name = "admin_id", length = 80)
	public String getAdminId() {
		return this.adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Column(name = "addtime", length = 19)
	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "notice_content_brief")
	public String getNoticeContentBrief() {
		return this.noticeContentBrief;
	}

	public void setNoticeContentBrief(String noticeContentBrief) {
		this.noticeContentBrief = noticeContentBrief;
	}

	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="admin_id",insertable=false,updatable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CgAdmin getAdmin() {
		if(admin != null)this.admin.setPassword(null);
		return admin;
	}

	public void setAdmin(CgAdmin admin) {
		this.admin = admin;
	}
}