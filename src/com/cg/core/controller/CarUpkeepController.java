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
import com.cg.common.entity.CgCarUpkeep;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.ICarUpkeepServer;

/**
 * 车辆保养记录
 *
 */
@Controller
@RequestMapping("control/carUpkeep")
public class CarUpkeepController {
	
	@Autowired
	private IBaseService baseService;
	@Autowired
	private ICarUpkeepServer carUpkeepServer;
	
	// 车辆保养记录列表
	@RequestMapping("select/cgCarUpkeepList")
	public String cgCarUpkeepList(HttpServletRequest request,PageUtil<CgCarUpkeep> pageUtil,String searchText) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgCarUpkeep cu where isdel=? ";
		List<Object> parmas = new ArrayList<Object>();
		parmas.add(0);		
		
		if(loginStaff!=null){
			if(isEmpty(searchText)){
				hql += " cu.cgCarInfo.shopFrontId=? ";
				parmas.add(loginStaff.getShopFrontId());
			}else{
				hql += " and (cu.upkeepPeople like ? or cu.cgCarInfo.plateNumber like ? or cu.uodateEngineOilType like ?) " +
						"and cu.cgCarInfo.shopFrontId=? ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add(loginStaff.getShopFrontId());
			}
		}else{
			if(isEmpty(searchText)){
			}else{
				hql += " and (cu.upkeepPeople like ? or cu.cgCarInfo.plateNumber like ? or cu.uodateEngineOilType like ?) ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
			}
		}
		
		hql +=" order by uid desc";
		PageUtil<CgCarUpkeep> pageUtils = baseService.find(hql, parmas,pageUtil);

		request.setAttribute("pageUtil", pageUtils);
		request.setAttribute("searchText", searchText);
		return "cgCarUpkeep/cgCarUpkeepList";
	}
	
	//跳转增加车辆保养页面
	@RequestMapping("goto/addAarUpkeepPage")
	public String addAarUpkeepPage() {
		return "cgCarUpkeep/addAarUpkeep";
	}
	
	//添加车辆保养记录
	@RequestMapping("add/gotoAddCgCarUpkeep")
	@ResponseBody
	public String gotoAddCgCarUpkeep(HttpServletRequest request,HttpSession session,CgCarUpkeep ccu,
			String theUpkeepTime,String theLastUpkeepTime) {
		
		try {
			ccu.setId("cci-" + UUID.randomUUID().toString());
			
			if (!"".equals(theUpkeepTime)) {
				theUpkeepTime += " 00:00:000";
				Timestamp upkeepTime = Timestamp.valueOf(theUpkeepTime);
				ccu.setUpkeepTime(upkeepTime);
			}
			if (!"".equals(theLastUpkeepTime)) {
				theLastUpkeepTime += " 00:00:000";
				Timestamp lastUpkeepTime = Timestamp.valueOf(theLastUpkeepTime);
				ccu.setLastUpkeepTime(lastUpkeepTime);
			}
			if(isEmpty(ccu.getUpkeepPeople())){
				return "请填写车辆保养人员";
			}
			if(isEmpty(ccu.getCarInfoId())){
				return "请选择车辆";
			}
			ccu.setIsdel(0);
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加车辆保养记录");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆保养记录：" + ccu.getUpkeepPeople()+":"+ccu.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
		
			baseService.saveByAdmin(ccu, adminLog);
			
			
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "添加失败";
		}
	}
	
//	//获取所有车辆信息
//	@RequestMapping("select/getAllCgCarInfo")
//	@ResponseBody
//	public String getAllCgCarInfo(HttpServletRequest request) {
//		
//		try {
//			HttpSession session = request.getSession(true);
//			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
//			
//			String hql = "FROM CgCarInfo ";
//			List<Object> parmas = new ArrayList<Object>();
//			List<CgCarInfo> cgCarInfoList;
//			
//			if(loginStaff!=null){
//				hql+=" where shopFrontId = ? ";
//				parmas.add(loginStaff.getShopFrontId());
//				cgCarInfoList = baseService.find(hql, parmas);
//			}else{
//				cgCarInfoList = baseService.find(hql);
//			}
//			
////			for(CgCarInfo c : cgCarInfoList){
////				c.getCarType().getCarBrand().getInfo();
////			}
//			if(cgCarInfoList.size()>0){
//				return new GsonUtil().toJson(cgCarInfoList);
//			}else{
//				return new GsonUtil().toJson("暂无车辆,请先移步车辆信息管理添加车辆");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new GsonUtil().toJson("服务器获取车辆数据错误");
//		}
//		
//	}
	
	//车保养信息详细页面
	@RequestMapping("select/cgCarUpkeepDetail")
	public String cgCarUpkeepDetail(HttpServletRequest request,String ccuID){
		
		try {
			CgCarUpkeep findEntity = (CgCarUpkeep) baseService.findEntity(CgCarUpkeep.class, ccuID);
			request.setAttribute("ccu", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "cgCarUpkeep/cgCarUpkeepDetail";
	}
	
	//修改车保养记录
	@RequestMapping("update/gotoUpdateCgCarUpkeep")
	@ResponseBody
	public String gotoUpdateCgCarUpkeep(HttpServletRequest request,HttpSession session,CgCarUpkeep ccu,String ccuID,
			String theUpkeepTime,String theLastUpkeepTime) {
		
		try{
			CgCarUpkeep findCsmm = (CgCarUpkeep) baseService.findEntity(CgCarUpkeep.class, ccuID);
			
			if (!"".equals(theUpkeepTime)) {
				theUpkeepTime += " 00:00:000";
				Timestamp upkeepTime = Timestamp.valueOf(theUpkeepTime);
				ccu.setUpkeepTime(upkeepTime);
			}
			if (!"".equals(theLastUpkeepTime)) {
				theLastUpkeepTime += " 00:00:000";
				Timestamp lastUpkeepTime = Timestamp.valueOf(theLastUpkeepTime);
				ccu.setLastUpkeepTime(lastUpkeepTime);
			}
			if(isEmpty(ccu.getUpkeepPeople())){
				return "请填写车辆保养人员";
			}
			if(isEmpty(ccu.getCarInfoId())){
				return "请选择车辆";
			}
			
			findCsmm.setCarInfoId(ccu.getCarInfoId());
			findCsmm.setLastMileage(ccu.getLastMileage());
			findCsmm.setLastUpkeepTime(ccu.getLastUpkeepTime());
			findCsmm.setMileage(ccu.getMileage());
			findCsmm.setUodateEngineOilType(ccu.getUodateEngineOilType());
			findCsmm.setUpkeepCost(ccu.getUpkeepCost());
			findCsmm.setUpkeepPeople(ccu.getUpkeepPeople());
			findCsmm.setUpkeepProject(ccu.getUpkeepProject());
			findCsmm.setUpkeepTime(ccu.getUpkeepTime());
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改车保养");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改车保养："+findCsmm.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				
				baseService.updateByAdmin(findCsmm, adminLog);
			}else{
				baseService.update(findCsmm);
			}
			
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "修改失败";
		}
	}
	
	//删除车辆保养
	@RequestMapping("delete/deleteCgCarUpkeep")
	@ResponseBody
	public String deleteCgCarUpkeep(HttpServletRequest request,HttpSession session,String strs) {
		
		String ids = strs.substring(0, strs.length()-1);
		
		try {
			carUpkeepServer.deleteCarUpkeep(ids, session, request);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "删除失败";
		}
		
		
	}
	//删除车辆保养
	@RequestMapping("delete/deleteUpkeep")
	@ResponseBody
	public String deleteUpkeep(HttpServletRequest request,HttpSession session,String idc) {
		try {
			carUpkeepServer.deleteUpkeep(session,request,idc);
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
