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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarInsurance;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.ICarInsuranceManagerService;
/**
 * 车辆保险管理
 *
 */
@Controller
@RequestMapping("control/carInsurance")
public class CarInsuranceManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private ICarInsuranceManagerService  carInsuranceManagerService;
	
	// 车辆保险列表
	@RequestMapping("select/cgCarInsuranceList")
	public String cgCarInsuranceList(HttpServletRequest request,PageUtil<CgCarInsurance> pageUtil,String searchText) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgCarInsurance ci where ci.isdel=? ";
		List<Object> parmas = null;
		
		if(loginStaff!=null){
			if(isEmpty(searchText)){
				parmas = new ArrayList<Object>();
				hql += " ci.cgCarInfo.shopFrontId=? ";
				parmas.add(0);
				parmas.add(loginStaff.getShopFrontId());
			}else{
				parmas = new ArrayList<Object>();
				hql += " and (ci.insured like ? or ci.insurer like ? or ci.insuranceCode like ?) " +
						"and ci.cgCarInfo.shopFrontId=? ";
				parmas.add(0);
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add(loginStaff.getShopFrontId());
			}
		}else{
			if(isEmpty(searchText)){
				parmas = new ArrayList<Object>();
				parmas.add(0);
			}else{
				parmas = new ArrayList<Object>();
				hql += " and (ci.insured like ? or ci.insurer like ? or ci.insuranceCode like ? or ci.cgCarInfo.shopFront.name like ?) ";
				parmas.add(0);
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
			}
		}
		
		hql +=" order by uid desc";

		PageUtil<CgCarInsurance> pageUtils = baseService.find(hql, parmas, pageUtil);

		request.setAttribute("pageUtil", pageUtils);
		request.setAttribute("searchText", searchText);

		return "carInsuranceManager/cgCarInsuranceList";
	}
	
	//跳转增加车险页面
	@RequestMapping("goto/addCgCarInsurance")
	public String addCgCarInsurance() {
		return "carInsuranceManager/addCgCarInsurance";

	}
	
	//添加车险
	@RequestMapping("add/gotoAddCgCarInsurance")
	@ResponseBody
	public String gotoAddCgCarInsurance(HttpServletRequest request,HttpSession session,CgCarInsurance cci,
			String theStartTime,String theEndTime) {
		
		try {
			cci.setId("cci-" + UUID.randomUUID().toString());
			
			if (!"".equals(theStartTime)) {
				theStartTime += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(theStartTime);
				cci.setStartTime(startTime);
			}
			if (!"".equals(theEndTime)) {
				theEndTime += " 00:00:000";
				Timestamp endTime = Timestamp.valueOf(theEndTime);
				cci.setEndTime(endTime);
			}
			
			if(isEmpty(cci.getCarInfoId())){
				return "请选择车辆";
			}
			if(isEmpty(cci.getInsured())){
				return "请填写：经办人";
			}
			if(isEmpty(cci.getInsuranceCode())){
				return "请填写：保险单号";	
			}
			if(isEmpty(cci.getInsurer())){
				return "请填写：承保公司";	
			}
			if(isEmpty(cci.getTotalPrices())){
				return "请填写：保险费合计";
			}
			if(!cci.getTotalPrices().matches("^[0-9]*$")){
				return "缴费总计请输入整数";
			}
			
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加车险");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车险：" + cci.getInsuranceName()+":"+cci.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			cci.setIsdel(0);
			baseService.saveByAdmin(cci, adminLog);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "添加过程发生错误";
		}
		
	}
	
	//车险信息详细页面
	@RequestMapping("select/cgCarInsuranceDetail")
	public String cgCarInsuranceDetail(HttpServletRequest request,String cciID){
		
		try {
			CgCarInsurance findEntity = (CgCarInsurance) baseService.findEntity(CgCarInsurance.class, cciID);
			request.setAttribute("cci", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "carInsuranceManager/cgCarInsuranceDetail";
	}
	
	//修改车险
	@RequestMapping("update/gotoUpdateCgCarInsurance")
	@ResponseBody
	public String gotoUpdateCgCarInsurance(HttpServletRequest request,CgCarInsurance cci,HttpSession session,String cciID,
			String theStartTime,String theEndTime) {
		
		try{
			CgCarInsurance findCsmm = (CgCarInsurance) baseService.findEntity(CgCarInsurance.class, cciID);
			
			if (!"".equals(theStartTime)) {
				theStartTime += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(theStartTime);
				cci.setStartTime(startTime);
			}
			if (!"".equals(theEndTime)) {
				theEndTime += " 00:00:000";
				Timestamp endTime = Timestamp.valueOf(theEndTime);
				cci.setEndTime(endTime);
			}
			if(isEmpty(cci.getCarInfoId())){
				return "请选择车辆";
			}
			if(isEmpty(cci.getInsured())){
				return "请填写：经办人";
			}
			if(isEmpty(cci.getInsuranceCode())){
				return "请填写：保险单号";	
			}
			if(isEmpty(cci.getInsurer())){
				return "请填写：承保公司";	
			}
			if(isEmpty(cci.getTotalPrices())){
				return "请填写：保险费合计";
			}
			if(!cci.getTotalPrices().matches("^[0-9]*$")){
				return "缴费总计请输入整数";
			}
			
			findCsmm.setAddtime(cci.getAddtime());
			findCsmm.setCarInfoId(cci.getCarInfoId());
			findCsmm.setEndTime(cci.getEndTime());
			findCsmm.setInsuranceCode(cci.getInsuranceCode());
			findCsmm.setInsuranceName(cci.getInsuranceName());
			findCsmm.setInsured(cci.getInsured());
			findCsmm.setInsuredAddress(cci.getInsuredAddress());
			findCsmm.setInsuredIdcard(cci.getInsuredIdcard());
			findCsmm.setInsuredPhone(cci.getInsuredPhone());
			findCsmm.setInsurer(cci.getInsurer());
			findCsmm.setInsurerAddress(cci.getInsurerAddress());
			findCsmm.setRemark(cci.getRemark());
			findCsmm.setStartTime(cci.getStartTime());
			findCsmm.setTotalPrices(cci.getTotalPrices());
			
			findCsmm.setCompulsoryInsuranceMoney(cci.getCompulsoryInsuranceMoney());
			findCsmm.setThirdPartyLiability(cci.getThirdPartyLiability());
			findCsmm.setDriversSeatLiability(cci.getDriversSeatLiability());
			findCsmm.setBreakageOfGlass(cci.getBreakageOfGlass());
			findCsmm.setThreeLiabilityInsurance(cci.getThreeLiabilityInsurance());
			findCsmm.setPaintDamage(cci.getPaintDamage());
			findCsmm.setVehicleDamage(cci.getVehicleDamage());
			findCsmm.setRobberyTheft(cci.getRobberyTheft());
			findCsmm.setPassengerSeatLiability(cci.getPassengerSeatLiability());
			findCsmm.setCarDamage(cci.getCarDamage());
			findCsmm.setRemark(cci.getRemark());
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改车险");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改车险：" + findCsmm.getInsuranceName()+":"+findCsmm.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			
			baseService.updateByAdmin(findCsmm, adminLog);
			
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "修改失败";
		}
	}
	
	//删除车辆保险
	@RequestMapping("delete/deleteCarInsurance")
	@ResponseBody
	public String deleteCarInsurance(HttpServletRequest request,HttpSession session,String strs) {
		
		String ids = strs.substring(0, strs.length()-1);
		
		try {
			carInsuranceManagerService.deleteCarInsurance(ids, session, request);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "删除失败";
		}
		
		
	}
	
	//删除车辆保险
	@RequestMapping("delete/deleteInsurance")
	@ResponseBody
	public String deleteInsurance(HttpServletRequest request,HttpSession session,String idc) {
		try {
			carInsuranceManagerService.deleteInsurance(session,request,idc);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "删除失败";
		}
		
		
	}
	
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
