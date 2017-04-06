package com.cg.core.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgAnnualVerification;
import com.cg.common.entity.CgCarBrand;
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarStructure;
import com.cg.common.entity.CgCarType;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.ICarInfoManagerService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/carInfo")
public class CarInfoManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private ICarInfoManagerService carInfoManagerService;
	
	/** 车辆信息列表 */
	@RequestMapping("select/carInfoList")
	public String carInfoList(HttpServletRequest request,PageUtil<CgCarInfo> pageUtil,String text,HttpSession session){
		String hql = "from CgCarInfo cu ";
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		// 参数
		List<Object> params = new ArrayList<Object>();
		if(loginStaff != null){
			hql+=" where cu.shopFrontId = ? ";
			params.add(loginStaff.getShopFrontId());
			if (text != null && text.length() > 0) {
				hql += " and (cu.plateNumber LIKE ? or cu.owner LIKE ? or cu.shopFront.name LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}else{
			hql += " where 1=1 ";
			if (text != null && text.length() > 0) {
				hql += " and (cu.plateNumber LIKE ? or cu.owner LIKE ? or cu.shopFront.name LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}
		return "vehicleManage/carInfo/carInfoList";
	}
	
	/** 跳转新增车辆信息页面*/
	@RequestMapping("goto/addCarInfoPage")
	public String addCarInfoPage(HttpServletRequest request,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				request.setAttribute("fronts", fronts);
			}else{
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("fronts", fronts);
			}
			List<CgCarType> types = baseService.find("from CgCarType ");
			List<CgCarBrand> brands = baseService.find("FROM CgCarBrand cu WHERE cu.parentId IS NULL OR cu.parentId = ''");
			List<CgCarBrand> cBrands = baseService.find("from CgCarBrand cc where cc.brand IS NOT NULL AND cc.series IS NOT NULL AND cc.model IS  NULL");
			List<CgCarStructure> structures = baseService.find("from CgCarStructure ");
			request.setAttribute("structures", structures);
			request.setAttribute("cBrands", cBrands);
			request.setAttribute("types", types);
			request.setAttribute("brands", brands);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "vehicleManage/carInfo/info/addInfo";
	}
	
	/** 删除车辆信息 
	 * @throws Exception */
	@RequestMapping("delete/deleteCarInfo")
	@ResponseBody
	public String deleteCarInfo(HttpServletRequest request,String id,HttpSession session,String ccId) throws Exception {
		if (id != null) {
			String d = id.substring(1, id.lastIndexOf(","));
			CgAnnualVerification fVerification = (CgAnnualVerification)baseService.findEntity("from CgAnnualVerification where carInfoId = ?", new Object[]{d});
			if (fVerification != null) {
				return "请先删除该车辆的年审信息";
			}
		}
		
		try {
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(3);
				adminLog.setActionDesc("删除车辆信息");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆信息：" + id );
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			if (ccId != null) {
				baseService.updateForHql("delete from CgCarInfo where id = ?", new Object[] { ccId });
				baseService.save(adminLog);
				return "1";
			}else{
				String[] ids = new Gson().fromJson(id, String[].class);
				carInfoManagerService.deleteCarInfo(ids,adminLog);
				return "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}

	
	/** 跳转修改页面 */
	@RequestMapping("goto/updateCarBrandPage")
	public String updateCarBrandPage(HttpServletRequest request,HttpSession session,String id){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				request.setAttribute("fronts", fronts);
				List<CgStaff> staffs = baseService.find("from CgStaff ");
				request.setAttribute("staffs", staffs);
			}else{
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("fronts", fronts);
				List<CgStaff> staffs = baseService.find("from CgStaff where shopFrontId = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("staffs", staffs);
			}
			CgCarInfo infos = (CgCarInfo) baseService.findEntity(
					CgCarInfo.class, id);
			List types = baseService.find("from CgCarType cu where 1=1");
			List brands = baseService.find("FROM CgCarBrand cu WHERE cu.parentId IS NULL OR cu.parentId = ''");
			List<CgCarStructure> structures = baseService.find("from CgCarStructure ");
			request.setAttribute("structures", structures);
			request.setAttribute("brands", brands);
			request.setAttribute("types", types);
			request.setAttribute("data", infos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "vehicleManage/carInfo/updateCarInfoPage";
	}
	
	/** 查询员工 */
	@RequestMapping("select/carBrandList")
	@ResponseBody
	public String carBrandList(HttpServletRequest request,String pid){
			List<CgStaff> staffs = baseService.find("from CgStaff where shopFrontId = ?", new Object[]{pid});
			return new Gson().toJson(staffs);
	}
	
	
	/** 修改车辆信息 */
	@RequestMapping("update/updateCarBrand")
	@ResponseBody
	public String updateCarBrand(HttpServletRequest request,HttpSession session,CgCarInfo carInfo,@RequestParam("file") MultipartFile file,
			String buyTimes,String staffId){
		CgAdmin admin = null;
		try {
			CgCarInfo infos = (CgCarInfo) baseService.findEntity(
					CgCarInfo.class, carInfo.getId());
			if (file != null && file.getSize() > 0) {
				// 重新上传图片
				String url = ImageController.saveFile(session, file,
						"carInfo/" + carInfo.getId() + ".jpg");
				if (url != null) {
					infos.setPicture("image/getimg.app?url="
							+ url.replaceAll("\\\\", "/"));
				}
			}
			
			// 数据
			infos.setMileage(carInfo.getMileage());
			infos.setFrameNumber(carInfo.getFrameNumber());
			infos.setShopFrontId(carInfo.getShopFrontId());
			infos.setPlateNumber(carInfo.getPlateNumber());
			infos.setOwner(carInfo.getOwner());
			infos.setStaffId(carInfo.getStaffId());
			infos.setGpsNumber(carInfo.getGpsNumber());
			infos.setStatus(carInfo.getStatus());
			infos.setLoads(carInfo.getLoads());
			infos.setScope(carInfo.getScope());
			infos.setCarTypeId(carInfo.getCarTypeId());
			infos.setNature(carInfo.getNature());
			if (!"".equals(buyTimes)) {
				buyTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(buyTimes);
				infos.setBuyTime(startTime);
			}
			infos.setPark(carInfo.getPark());
			infos.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			infos.setRemark(carInfo.getRemark());
			admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改车辆信息");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，修改信息：" + infos.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			} 
			carInfoManagerService.updateInfo(infos, adminLog);
		return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "修改失败";
	}
	

	@RequestMapping("examine/checkAccount")
	@ResponseBody
	public String checkAccount(String staffId, HttpServletRequest request) throws Exception {
		CgStaff staff = (CgStaff) baseService.findEntity("from CgStaff where name = ? ", new Object[]{staffId});
		if(staff!=null){
			return "1";
		}else{
			return "ok";
		}
	}
	
	/** 新增车辆信息（字段改后） */
	@RequestMapping("add/addRearCarInfo")
	@ResponseBody
	public String addRearCarInfo(HttpServletRequest request,HttpSession session,CgCarInfo carInfo,CgCarType carType,
			String carTypeId,@RequestParam("file") MultipartFile file,CgCarType carType2,
			String carBrandId,Integer displacement,Integer weight,String fuelLabel,String model,
			Double fuelTank,String engine,Double gasolineConsumption,String info,String buyTimes,String carStructureId,
			CgCarBrand bcBrand){
		CgAdmin admin = null;
		String id = "cci-" + UUID.randomUUID();
		if (carTypeId == null || carTypeId.equals("")) {
			String error="";
			if (carBrandId != "" && model.equals("")) {
				error="请选择型号";
				request.setAttribute("error", error);
				return "vehicleManage/carInfo/info/addInfo";
			}else if(carBrandId.equals("") && model != ""){
				error="请选择车品牌";
				request.setAttribute("error", error);
				return "vehicleManage/carInfo/info/addInfo";
			}else if(carBrandId !="" && model != "" && carStructureId.equals("")){
				error="请选择车结构";
				request.setAttribute("error", error);
				return "vehicleManage/carInfo/info/addInfo";
			}
			
		}
		
		try {
			if (carTypeId.equals("") || carTypeId.equals(null)) {
				CgCarBrand carBrand = (CgCarBrand)baseService.findEntity("from CgCarBrand where parentId = ? AND model = ?", new Object[]{carBrandId,model});
				
				//如果输入品牌型号不存在，新增一个插入车辆类型
			if (carBrand == null) {
				CgCarBrand car = (CgCarBrand)baseService.findEntity("from CgCarBrand where parentId = ?", new Object[]{carBrandId});
				car.setId("ccb-" + UUID.randomUUID());
				car.setModel(model);
				baseService.save(car);
				
				carType.setId("cct-" + UUID.randomUUID());
				carType.setDisplacement(displacement);
				carType.setWeight(weight);
				carType.setFuelLabel(fuelLabel);
				carType.setFuelTank(fuelTank);
				carType.setEngine(engine);
				carType.setGasolineConsumption(gasolineConsumption);
				carType.setInfo(info);
				carType.setCarBrandId(car.getId());
				carType.setCarStructureId(carStructureId);
				baseService.save(carType);
			}
			
			if (carBrand == null) {//是空，就新增一条
					carInfo.setCarTypeId(carType.getId());
				}else if (carBrand.getModel().equals(model)) {
					CgCarType tCarType = (CgCarType)baseService.findEntity("from CgCarType where carBrandId = ?", new Object[]{carBrand.getId()});
					if (tCarType == null) {
						carType.setId("cct-" + UUID.randomUUID());
						carType.setDisplacement(displacement);
						carType.setWeight(weight);
						carType.setFuelLabel(fuelLabel);
						carType.setFuelTank(fuelTank);
						carType.setEngine(engine);
						carType.setGasolineConsumption(gasolineConsumption);
						carType.setInfo(info);
						carType.setCarBrandId(carBrand.getId());
						carType.setCarStructureId(carStructureId);
						baseService.save(carType);
						
						carInfo.setCarTypeId(carType.getId());
					}else{
						carInfo.setCarTypeId(tCarType.getId());
						}
					}
				}
			carInfo.setId("cci-" + UUID.randomUUID());
			String url = ImageController.saveFile(session, file, "carInfo/"
					+ id + ".jpg");
			if (url != null) {
				carInfo.setPicture("image/getimg.app?url="
						+ url.replaceAll("\\\\", "/"));
			}
			carInfo.setAddtime(new Timestamp(System.currentTimeMillis()));
			if (!"".equals(buyTimes)) {
				buyTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(buyTimes);
				carInfo.setBuyTime(startTime);
			}
			admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				// 管理员新增 要创建日志
				carInfo.setId(id);
				carInfo.setAddtime(new Timestamp(System.currentTimeMillis()));
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("新增车辆信息");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，车辆信息："
						+ carInfo.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());

			}
			carInfoManagerService.saveInfo(carInfo, adminLog);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "添加失败";
	}
	
}