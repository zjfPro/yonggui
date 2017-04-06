package com.cg.common.entity;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CgApplicationUseCar entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_application_use_car", catalog = "cg_yonggui")
public class CgApplicationUseCar implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String oddNumbers;
	private Integer type;
	private String deptId;
	private String person;
	private String usecarType;
	private String usecarReason;
	private Timestamp startTime;
	private Timestamp endtime;
	private String pointOfDeparture;
	private String destination;
	private Integer numberOfPeope;
	private String rideHead;
	private String requestNoteType;
	private Timestamp addtime;
	private String accessory;

	// Constructors

	/** default constructor */
	public CgApplicationUseCar() {
	}

	/** minimal constructor */
	public CgApplicationUseCar(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgApplicationUseCar(Integer uid, String oddNumbers, Integer type,
			String deptId, String person, String usecarType,
			String usecarReason, Timestamp startTime, Timestamp endtime,
			String pointOfDeparture, String destination, Integer numberOfPeope,
			String rideHead, String requestNoteType, Timestamp addtime,
			String accessory) {
		this.uid = uid;
		this.oddNumbers = oddNumbers;
		this.type = type;
		this.deptId = deptId;
		this.person = person;
		this.usecarType = usecarType;
		this.usecarReason = usecarReason;
		this.startTime = startTime;
		this.endtime = endtime;
		this.pointOfDeparture = pointOfDeparture;
		this.destination = destination;
		this.numberOfPeope = numberOfPeope;
		this.rideHead = rideHead;
		this.requestNoteType = requestNoteType;
		this.addtime = addtime;
		this.accessory = accessory;
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

	@Column(name = "odd_numbers", length = 80)
	public String getOddNumbers() {
		return this.oddNumbers;
	}

	public void setOddNumbers(String oddNumbers) {
		this.oddNumbers = oddNumbers;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "deptId", length = 100)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "person", length = 100)
	public String getPerson() {
		return this.person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	@Column(name = "usecar_type", length = 100)
	public String getUsecarType() {
		return this.usecarType;
	}

	public void setUsecarType(String usecarType) {
		this.usecarType = usecarType;
	}

	@Column(name = "usecar_reason")
	public String getUsecarReason() {
		return this.usecarReason;
	}

	public void setUsecarReason(String usecarReason) {
		this.usecarReason = usecarReason;
	}

	@Column(name = "start_time", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endtime", length = 19)
	public Timestamp getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	@Column(name = "point_of_departure", length = 100)
	public String getPointOfDeparture() {
		return this.pointOfDeparture;
	}

	public void setPointOfDeparture(String pointOfDeparture) {
		this.pointOfDeparture = pointOfDeparture;
	}

	@Column(name = "destination", length = 100)
	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Column(name = "number_of_peope")
	public Integer getNumberOfPeope() {
		return this.numberOfPeope;
	}

	public void setNumberOfPeope(Integer numberOfPeope) {
		this.numberOfPeope = numberOfPeope;
	}

	@Column(name = "ride_head", length = 100)
	public String getRideHead() {
		return this.rideHead;
	}

	public void setRideHead(String rideHead) {
		this.rideHead = rideHead;
	}

	@Column(name = "request_note_type", length = 100)
	public String getRequestNoteType() {
		return this.requestNoteType;
	}

	public void setRequestNoteType(String requestNoteType) {
		this.requestNoteType = requestNoteType;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "accessory")
	public String getAccessory() {
		return this.accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

}