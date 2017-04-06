package com.cg.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * CgDriver entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_driver", catalog = "cg_yonggui")
public class CgDriver implements java.io.Serializable {

	// Fields

	private String id;
	private Integer uid;
	private String code;
	private String name;
	private String shopFrontId;
	private String deptId;
	private Integer sex;
	private String drivingLicence;
	private String phone;
	private Integer status;
	private String positionId;
	private String englishName;
	private Integer age;
	private String nation;
	private String height;
	private String bloodType;
	private String weight;
	private Date birthday;
	private String nativePlace;
	private String idcard;
	private String familyPhone;
	private String birthplace;
	private String domicilePlace;
	private String address;
	private String educationBackground;
	private String degree;
	private String schooltag;
	private String major;
	private Date timeOfGraduation;
	private String politicsStatus;
	private Date workingTime;
	private Date entryTime;
	private String resumeNumber;
	private Date dimissionTime;
	private String email;
	private Integer clockingin;
	private String hobbiesAndInterests;
	private String remarks;
	private String accessory;
	private Integer drivingYear;
	private String photo;

	private CgShopFront cgShopFront;
	private CgDept dept;
	private CgStaffPosition position;
	// Constructors

	/** default constructor */
	public CgDriver() {
	}

	/** minimal constructor */
	public CgDriver(Integer uid) {
		this.uid = uid;
	}

	/** full constructor */
	public CgDriver(Integer uid, String code, String name, String shopFrontId,
			String deptId, Integer sex, String drivingLicence, String phone,
			Integer status, String positionId, String englishName, Integer age,
			String nation, String height, String bloodType, String weight,
			Date birthday, String nativePlace, String idcard,
			String familyPhone, String birthplace, String domicilePlace,
			String address, String educationBackground, String degree,
			String schooltag, String major, Date timeOfGraduation,
			String politicsStatus, Date workingTime, Date entryTime,
			String resumeNumber, Date dimissionTime, String email,
			Integer clockingin, String hobbiesAndInterests, String remarks,
			String accessory, Integer drivingYear, String photo) {
		this.uid = uid;
		this.code = code;
		this.name = name;
		this.shopFrontId = shopFrontId;
		this.deptId = deptId;
		this.sex = sex;
		this.drivingLicence = drivingLicence;
		this.phone = phone;
		this.status = status;
		this.positionId = positionId;
		this.englishName = englishName;
		this.age = age;
		this.nation = nation;
		this.height = height;
		this.bloodType = bloodType;
		this.weight = weight;
		this.birthday = birthday;
		this.nativePlace = nativePlace;
		this.idcard = idcard;
		this.familyPhone = familyPhone;
		this.birthplace = birthplace;
		this.domicilePlace = domicilePlace;
		this.address = address;
		this.educationBackground = educationBackground;
		this.degree = degree;
		this.schooltag = schooltag;
		this.major = major;
		this.timeOfGraduation = timeOfGraduation;
		this.politicsStatus = politicsStatus;
		this.workingTime = workingTime;
		this.entryTime = entryTime;
		this.resumeNumber = resumeNumber;
		this.dimissionTime = dimissionTime;
		this.email = email;
		this.clockingin = clockingin;
		this.hobbiesAndInterests = hobbiesAndInterests;
		this.remarks = remarks;
		this.accessory = accessory;
		this.drivingYear = drivingYear;
		this.photo = photo;
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

	@Column(name = "code", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "shop_front_id", length = 100)
	public String getShopFrontId() {
		return this.shopFrontId;
	}

	public void setShopFrontId(String shopFrontId) {
		this.shopFrontId = shopFrontId;
	}

	@Column(name = "dept_id", length = 100)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "sex")
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "driving_licence", length = 100)
	public String getDrivingLicence() {
		return this.drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	@Column(name = "phone", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "position_id", length = 100)
	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	@Column(name = "english_name", length = 100)
	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "nation", length = 100)
	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "height", length = 100)
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name = "blood_type", length = 100)
	public String getBloodType() {
		return this.bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	@Column(name = "weight", length = 100)
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "native_place", length = 100)
	public String getNativePlace() {
		return this.nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	@Column(name = "idcard", length = 100)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "family_phone", length = 100)
	public String getFamilyPhone() {
		return this.familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	@Column(name = "birthplace", length = 100)
	public String getBirthplace() {
		return this.birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	@Column(name = "domicile_place", length = 100)
	public String getDomicilePlace() {
		return this.domicilePlace;
	}

	public void setDomicilePlace(String domicilePlace) {
		this.domicilePlace = domicilePlace;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "education_background", length = 100)
	public String getEducationBackground() {
		return this.educationBackground;
	}

	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}

	@Column(name = "degree", length = 100)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "schooltag", length = 100)
	public String getSchooltag() {
		return this.schooltag;
	}

	public void setSchooltag(String schooltag) {
		this.schooltag = schooltag;
	}

	@Column(name = "major", length = 100)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "time_of_graduation", length = 10)
	public Date getTimeOfGraduation() {
		return this.timeOfGraduation;
	}

	public void setTimeOfGraduation(Date timeOfGraduation) {
		this.timeOfGraduation = timeOfGraduation;
	}

	@Column(name = "politics_status", length = 100)
	public String getPoliticsStatus() {
		return this.politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "working_time", length = 10)
	public Date getWorkingTime() {
		return this.workingTime;
	}

	public void setWorkingTime(Date workingTime) {
		this.workingTime = workingTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "entry_time", length = 10)
	public Date getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	@Column(name = "resume_number", length = 100)
	public String getResumeNumber() {
		return this.resumeNumber;
	}

	public void setResumeNumber(String resumeNumber) {
		this.resumeNumber = resumeNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dimission_time", length = 10)
	public Date getDimissionTime() {
		return this.dimissionTime;
	}

	public void setDimissionTime(Date dimissionTime) {
		this.dimissionTime = dimissionTime;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "clockingin")
	public Integer getClockingin() {
		return this.clockingin;
	}

	public void setClockingin(Integer clockingin) {
		this.clockingin = clockingin;
	}

	@Column(name = "hobbies_and_interests")
	public String getHobbiesAndInterests() {
		return this.hobbiesAndInterests;
	}

	public void setHobbiesAndInterests(String hobbiesAndInterests) {
		this.hobbiesAndInterests = hobbiesAndInterests;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "accessory")
	public String getAccessory() {
		return this.accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	@Column(name = "driving_year")
	public Integer getDrivingYear() {
		return this.drivingYear;
	}

	public void setDrivingYear(Integer drivingYear) {
		this.drivingYear = drivingYear;
	}

	@Column(name = "photo", length = 100)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_front_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgShopFront getCgShopFront() {
		return cgShopFront;
	}

	public void setCgShopFront(CgShopFront cgShopFront) {
		this.cgShopFront = cgShopFront;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgDept getDept() {
		return dept;
	}

	public void setDept(CgDept dept) {
		this.dept = dept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgStaffPosition getPosition() {
		return position;
	}

	public void setPosition(CgStaffPosition position) {
		this.position = position;
	}
	
	
}