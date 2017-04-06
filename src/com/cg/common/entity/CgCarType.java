package com.cg.common.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.gson.annotations.Expose;

/**
 * CgCarType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cg_car_type", catalog = "cg_yonggui")
public class CgCarType implements java.io.Serializable {

	// Fields
	@Expose
	private String id;
	private Integer uid;
	private String typeName;
	private String info;
	private String engine;
	private String transmissionCase;
	private Integer sizeLength;
	private Integer sizeWidth;
	private Integer sizeHeight;
	private Integer speedMax;
	private Double accelerateTime;
	private Double gasolineConsumption;
	private Integer groundDistance;
	private Integer weight;
	private String carStructureId;
	private Integer carDoorCount;
	private Integer seatCount;
	private Double fuelTank;
	private Double cargoVolume;
	private Integer displacement;
	private String inletType;
	private String fuelForm;
	private String fuelLabel;
	@Expose
	private String carBrandId;
	@Expose
	private CgCarBrand carBrand;
	@Expose
	private CgCarStructure structure;
	// Constructors

	/** default constructor */
	public CgCarType() {
	}

	/** minimal constructor */
	public CgCarType(Integer uid, String typeName) {
		this.uid = uid;
		this.typeName = typeName;
	}

	/** full constructor */
	public CgCarType(Integer uid, String typeName, String info, String engine,
			String transmissionCase, Integer sizeLength, Integer sizeWidth,
			Integer sizeHeight, Integer speedMax, Double accelerateTime,
			Double gasolineConsumption, Integer groundDistance, Integer weight,
			String carStructureId, Integer carDoorCount, Integer seatCount,
			Double fuelTank, Double cargoVolume, String engineType,
			Integer displacement, String inletType, String fuelForm,
			String fuelLabel, String carBrandId) {
		this.uid = uid;
		this.typeName = typeName;
		this.info = info;
		this.engine = engine;
		this.transmissionCase = transmissionCase;
		this.sizeLength = sizeLength;
		this.sizeWidth = sizeWidth;
		this.sizeHeight = sizeHeight;
		this.speedMax = speedMax;
		this.accelerateTime = accelerateTime;
		this.gasolineConsumption = gasolineConsumption;
		this.groundDistance = groundDistance;
		this.weight = weight;
		this.carStructureId = carStructureId;
		this.carDoorCount = carDoorCount;
		this.seatCount = seatCount;
		this.fuelTank = fuelTank;
		this.cargoVolume = cargoVolume;
		this.displacement = displacement;
		this.inletType = inletType;
		this.fuelForm = fuelForm;
		this.fuelLabel = fuelLabel;
		this.carBrandId = carBrandId;
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

	@Column(name = "type_name", nullable = false, length = 100)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "info", length = 100)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "engine")
	public String getEngine() {
		return this.engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	@Column(name = "transmission_case")
	public String getTransmissionCase() {
		return this.transmissionCase;
	}

	public void setTransmissionCase(String transmissionCase) {
		this.transmissionCase = transmissionCase;
	}

	@Column(name = "size_length")
	public Integer getSizeLength() {
		return this.sizeLength;
	}

	public void setSizeLength(Integer sizeLength) {
		this.sizeLength = sizeLength;
	}

	@Column(name = "size_width")
	public Integer getSizeWidth() {
		return this.sizeWidth;
	}

	public void setSizeWidth(Integer sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	@Column(name = "size_height")
	public Integer getSizeHeight() {
		return this.sizeHeight;
	}

	public void setSizeHeight(Integer sizeHeight) {
		this.sizeHeight = sizeHeight;
	}

	@Column(name = "speed_max")
	public Integer getSpeedMax() {
		return this.speedMax;
	}

	public void setSpeedMax(Integer speedMax) {
		this.speedMax = speedMax;
	}

	@Column(name = "accelerate_time", precision = 10, scale = 1)
	public Double getAccelerateTime() {
		return this.accelerateTime;
	}

	public void setAccelerateTime(Double accelerateTime) {
		this.accelerateTime = accelerateTime;
	}

	@Column(name = "gasoline_consumption", precision = 10, scale = 1)
	public Double getGasolineConsumption() {
		return this.gasolineConsumption;
	}

	public void setGasolineConsumption(Double gasolineConsumption) {
		this.gasolineConsumption = gasolineConsumption;
	}

	@Column(name = "ground_distance")
	public Integer getGroundDistance() {
		return this.groundDistance;
	}

	public void setGroundDistance(Integer groundDistance) {
		this.groundDistance = groundDistance;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name = "car_structure_id", length = 80)
	public String getCarStructureId() {
		return this.carStructureId;
	}

	public void setCarStructureId(String carStructureId) {
		this.carStructureId = carStructureId;
	}

	@Column(name = "car_door_count")
	public Integer getCarDoorCount() {
		return this.carDoorCount;
	}

	public void setCarDoorCount(Integer carDoorCount) {
		this.carDoorCount = carDoorCount;
	}

	@Column(name = "seat_count")
	public Integer getSeatCount() {
		return this.seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	@Column(name = "fuel_tank", precision = 10, scale = 1)
	public Double getFuelTank() {
		return this.fuelTank;
	}

	public void setFuelTank(Double fuelTank) {
		this.fuelTank = fuelTank;
	}

	@Column(name = "cargo_volume", precision = 10, scale = 1)
	public Double getCargoVolume() {
		return this.cargoVolume;
	}

	public void setCargoVolume(Double cargoVolume) {
		this.cargoVolume = cargoVolume;
	}

	@Column(name = "displacement")
	public Integer getDisplacement() {
		return this.displacement;
	}

	public void setDisplacement(Integer displacement) {
		this.displacement = displacement;
	}

	@Column(name = "inlet_type")
	public String getInletType() {
		return this.inletType;
	}

	public void setInletType(String inletType) {
		this.inletType = inletType;
	}

	@Column(name = "fuel_form", length = 80)
	public String getFuelForm() {
		return this.fuelForm;
	}

	public void setFuelForm(String fuelForm) {
		this.fuelForm = fuelForm;
	}

	@Column(name = "fuel_label", length = 80)
	public String getFuelLabel() {
		return this.fuelLabel;
	}

	public void setFuelLabel(String fuelLabel) {
		this.fuelLabel = fuelLabel;
	}

	@Column(name = "car_brand_id", length = 80)
	public String getCarBrandId() {
		return this.carBrandId;
	}

	public void setCarBrandId(String carBrandId) {
		this.carBrandId = carBrandId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_brand_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarBrand getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(CgCarBrand carBrand) {
		this.carBrand = carBrand;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_structure_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CgCarStructure getStructure() {
		return structure;
	}

	public void setStructure(CgCarStructure structure) {
		this.structure = structure;
	}
	
}