package com.cg.core.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.cg.common.entity.CgDept;
import com.cg.common.entity.CgDriver;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.entity.CgStaffPosition;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/driver")
public class DriverManagerController {
	@Autowired
	private IBaseService baseService;
	
	@Autowired
	private IAdminLogService adminLogService;
	
	@RequestMapping("select/driverList")
	public String driverList(HttpServletRequest request,PageUtil<CgDriver> pageUtil,String text, String text2) {
		String hql = " FROM CgDriver where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (!isEmpty(text)) {
			hql += " and cgShopFront.name LIKE ? ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		if (!isEmpty(text2)) {
			hql += " and ( name LIKE ? or phone LIKE ? )";
			params.add("%" + text2 + "%");
			params.add("%" + text2 + "%");
			request.setAttribute("text2", text2);
		}
		
		hql+=" order by uid desc ";
		PageUtil<CgDriver> pageUtils = baseService.find(hql, params,pageUtil);
		request.setAttribute("pageUtil", pageUtils);
		return "driver/driverList";
	}
	
	/**
	 * 跳转新增驾驶员
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addCgDriver")
	public String goto_addCgDriver(HttpServletRequest request,
			HttpSession session) throws Exception {
		String hql = " from CgShopFront where 1=1 ";
		CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
		List<Object> params = new ArrayList<Object>();
		if(loginStaff!=null){//如果是员工登录，只显示员工所在的门店
			hql+=" and id=? ";
			params.add(loginStaff.getShopFrontId());
		}
		List<CgShopFront> shopFrontList = baseService.find(hql,params);
		request.setAttribute("shopFrontList", shopFrontList);
		return "driver/driverDetail";
	}
	/**
	 * 跳转修改投资人
	 * 
	 * @return
	 */
	@RequestMapping("goto/editDriverView")
	public String editDriverView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgDriver driver = (CgDriver) baseService.findEntity(CgDriver.class, id);
			request.setAttribute("driver", driver);
			
			String hql = " from CgShopFront where 1=1 ";
			CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
			List<Object> params = new ArrayList<Object>();
			if(loginStaff!=null){//如果是员工登录，只显示员工所在的门店
				hql+=" and id=? ";
				params.add(loginStaff.getShopFrontId());
			}
			List<CgShopFront> shopFrontList = baseService.find(hql,params);
			request.setAttribute("shopFrontList", shopFrontList);
			
			String hql2 = " from CgDept where shopFrontId = ?";
			List<CgDept> deptList = baseService.find(hql2, driver.getShopFrontId());
			request.setAttribute("deptList", deptList);
			String hql3 = " from CgStaffPosition where shopFrontId = ? ";
			List<CgStaffPosition> positionList = baseService.find(hql3, driver.getShopFrontId());
			request.setAttribute("positionList", positionList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "driver/driverDetail";
	}
	
	/**
	 * 新增/编辑驾驶员
	 * 
	 * @return
	 */
	@RequestMapping("add/addDriver")
	@ResponseBody
	public String addDriver(HttpServletRequest request,HttpSession session, CgDriver driver,
			String birthdays,String entryTimes,String dimissionTimes,String workingTimes,
			String timeOfGraduations,@RequestParam("file") MultipartFile file) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			if(isEmpty(driver.getId())){
				String id = "ci-" + UUID.randomUUID();
				driver.setId(id);
				if(!file.isEmpty()){
					String url = ImageController.saveFile(session, file, "driverPictrue/"
							+ id + ".jpg");
					if (url != null) {
						driver.setPhoto("image/getimg.app?url="
								+ url.replaceAll("\\\\", "/"));
					}
				}
				
				if(!isEmpty(birthdays)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					driver.setBirthday(sdf.parse(birthdays));
				}
				if(!isEmpty(entryTimes)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					driver.setEntryTime(sdf.parse(entryTimes));
				}
				if(!isEmpty(dimissionTimes)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					driver.setDimissionTime(sdf.parse(dimissionTimes));
				}
				if(!isEmpty(workingTimes)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					driver.setWorkingTime(sdf.parse(workingTimes));
				}
				if(!isEmpty(timeOfGraduations)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					driver.setTimeOfGraduation(sdf.parse(timeOfGraduations));
				}
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加驾驶员");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，添加驾驶员：" + driver.getName() + ":"
							+ driver.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				baseService.save(driver);
			}else{
				CgDriver fdriver = (CgDriver) baseService.findEntity(CgDriver.class, driver.getId());
				if (file != null && file.getSize() > 0) {
					String url = ImageController.saveFile(session, file, "driverPictrue/"
							+ fdriver.getId() + ".jpg");
					if (url != null) {
						fdriver.setPhoto("image/getimg.app?url="
								+ url.replaceAll("\\\\", "/"));
					}
				}
				fdriver.setCode(driver.getCode());
				fdriver.setName(driver.getName());
				fdriver.setShopFrontId(driver.getShopFrontId());
				fdriver.setDeptId(driver.getDeptId());
				fdriver.setSex(driver.getSex());
				fdriver.setDrivingLicence(driver.getDrivingLicence());
				fdriver.setPhone(driver.getPhone());
				fdriver.setStatus(driver.getStatus());
				fdriver.setPositionId(driver.getPositionId());
				fdriver.setEnglishName(driver.getEnglishName());
				fdriver.setAge(driver.getAge());
				fdriver.setNation(driver.getNation());
				fdriver.setHeight(driver.getHeight());
				fdriver.setBloodType(driver.getBloodType());
				fdriver.setWeight(driver.getWeight());
				fdriver.setNativePlace(driver.getNativePlace());
				fdriver.setIdcard(driver.getIdcard());
				fdriver.setFamilyPhone(driver.getFamilyPhone());
				fdriver.setBirthplace(driver.getBirthplace());
				fdriver.setDomicilePlace(driver.getDomicilePlace());
				fdriver.setAddress(driver.getAddress());
				fdriver.setEducationBackground(driver.getEducationBackground());
				fdriver.setDegree(driver.getDegree());
				fdriver.setSchooltag(driver.getSchooltag());
				fdriver.setMajor(driver.getMajor());
				fdriver.setPoliticsStatus(driver.getPoliticsStatus());
				fdriver.setResumeNumber(driver.getResumeNumber());
				fdriver.setEmail(driver.getEmail());
				fdriver.setClockingin(driver.getClockingin());
				fdriver.setHobbiesAndInterests(driver.getHobbiesAndInterests());
				fdriver.setRemarks(driver.getRemarks());
				
				if(!isEmpty(birthdays)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					fdriver.setBirthday(sdf.parse(birthdays));
				}else{
					fdriver.setBirthday(null);
				}
				if(!isEmpty(entryTimes)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					fdriver.setEntryTime(sdf.parse(entryTimes));
				}else{
					fdriver.setEntryTime(null);
				}
				if(!isEmpty(dimissionTimes)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					fdriver.setDimissionTime(sdf.parse(dimissionTimes));
				}else{
					fdriver.setDimissionTime(null);
				}
				if(!isEmpty(workingTimes)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					fdriver.setWorkingTime(sdf.parse(workingTimes));
				}else{
					fdriver.setWorkingTime(null);
				}
				if(!isEmpty(timeOfGraduations)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					fdriver.setTimeOfGraduation(sdf.parse(timeOfGraduations));
				}else{
					fdriver.setTimeOfGraduation(null);
				}
				
				baseService.update(fdriver);
				
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改驾驶员信息");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，修改驾驶员信息：" + fdriver.getName() + ":"
							+ fdriver.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/driver/select/driverList";
		return "1";
	}
	
	/**
	 * 删除投资人
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteDriver")
	@ResponseBody
	public String deleteDriver(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgDriver investor = (CgDriver) baseService.findEntity(CgDriver.class, id);
						if (investor != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除驾驶员");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除id："
									+ investor.getId());
							adminLog.setAdminId(admin.getId());
							adminLog.setAdmin(admin);
							adminLog.setId("cal-"
									+ UUID.randomUUID().toString());
							baseService.deleteByAdmin(investor, adminLog);
							ImageController.deleteObjImage(investor,session);
						}
					}
				}
			} else {
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgDriver investor = (CgDriver) baseService.findEntity(CgDriver.class, id);
						if (investor != null) {
							baseService.delete(investor);
							ImageController.deleteObjImage(investor,session);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	/** 根据门店获取部门 */
	@RequestMapping("get/depts")
	@ResponseBody
	public String getDeptList(String id, HttpServletRequest request) throws Exception {
		String hql = " from CgDept where shopFrontId = ?";
		List<CgDept> list = baseService.find(hql, id);
		if(list.size()>0){
			return new GsonUtil().toJson(list);
		}else{
			return "0";
		}
		
	}
	
	/** 根据门店获取职位 */
	@RequestMapping("get/positions")
	@ResponseBody
	public String getPositionsList(String id, HttpServletRequest request) throws Exception {
		String hql = " from CgStaffPosition where shopFrontId = ? ";
		List<CgStaffPosition> list = baseService.find(hql, id);
		if(list.size()>0){
			return new GsonUtil().toJson(list);
		}else{
			return "0";
		}
		
	}
	
}
