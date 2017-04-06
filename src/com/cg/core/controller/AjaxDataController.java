package com.cg.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarRentalItem;
import com.cg.common.entity.CgContract;
import com.cg.common.entity.CgDept;
import com.cg.common.entity.CgSetting;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.entity.CgStaffPosition;
import com.cg.common.utils.GsonUtil;
import com.cg.core.service.IBaseService;
/**
 * 通过ajax获取数据
 */
@Controller
@RequestMapping("ajax")
public class AjaxDataController {
	
	@Autowired
	private IBaseService baseService;

	//获取所有车辆信息
	@RequestMapping("select/getAllCgCarInfo")
	@ResponseBody
	public String getAllCgCarInfo(HttpServletRequest request) {
		JSONObject jsonObject = null;
		try {
			
			HttpSession session = request.getSession(true);
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			
			String hql = "FROM CgCarInfo ";
			List<Object> parmas = new ArrayList<Object>();
			List<CgCarInfo> cgCarInfoList;
			//如果是员工登录，根据员工所在门店查询数据
			if(loginStaff!=null){
				hql+=" where shopFrontId = ? ";
				parmas.add(loginStaff.getShopFrontId());
				cgCarInfoList = baseService.find(hql, parmas);
			}else{
				cgCarInfoList = baseService.find(hql);
			}
			
			if(cgCarInfoList.size()>0){
				return new GsonUtil().toJson(cgCarInfoList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "暂无车辆,请先移步车辆信息管理添加车辆");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取车辆数据错误");
			return jsonObject.toString();
		}
		
	}
	
	
	//根据门店查找员工
	@RequestMapping("select/getStaffForFront")
	@ResponseBody
	public String getStaffForFront(HttpServletRequest request,String mendianID) {
		JSONObject jsonObject = null;
		try {
			String hql = "FROM CgStaff where shopFrontId = ? ";
			List<Object> parmas = new ArrayList<Object>();
			parmas.add(mendianID);
			List<CgStaff> staffList = baseService.find(hql, parmas);
			
			if(staffList.size()>0){
				return new GsonUtil().toJson(staffList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "此车辆所在门店暂无员工,请先添加员工");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取员工数据错误");
			return jsonObject.toString();
		}
	}
	
	@RequestMapping("select/getCarInfo")
	public String getCarInfo(HttpServletRequest request,String ccriID) {
		try {
			CgCarInfo info= (CgCarInfo) baseService.findEntity(CgCarInfo.class, ccriID);
			request.setAttribute("data", info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rentalItem/carInfo";
	}
	
	
	//获取所有职位信息
	@RequestMapping("select/getStaffPosition")
	@ResponseBody
	public String getStaffPosition(HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgStaffPosition ";
		List<CgStaffPosition> positionList;
		JSONObject jsonObject =null;
		try {
			
			//如果是员工登录，根据员工所在门店查询数据
			if(loginStaff!=null){
				List<Object> parmas = new ArrayList<Object>();
				hql+=" where shopFrontId = ? ";
				parmas.add(loginStaff.getShopFrontId());
				positionList = baseService.find(hql, parmas);
			}else{
				positionList = baseService.find(hql);
			}
			
			if(positionList.size()>0){
				return  new GsonUtil().toJson(positionList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "暂无职位,请先移步职位管理添加职位");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取职位数据错误");
			return jsonObject.toString();
		}
		
	}
	
	//获取所有部门信息
	@RequestMapping("select/getAllCgDept")
	@ResponseBody
	public String getAllCgDept(HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgDept ";
		List<CgDept> cgDeptList;
		JSONObject jsonObject = null;
		
		try {
			//如果是员工登录，根据员工所在门店查询数据
			if(loginStaff!=null){
				List<Object> parmas = new ArrayList<Object>();
				hql+=" where shopFrontId = ? ";
				parmas.add(loginStaff.getShopFrontId());
				cgDeptList = baseService.find(hql, parmas);
			}else{
				cgDeptList = baseService.find(hql);
			}
			
			
			
			if(cgDeptList.size()>0){
				return new GsonUtil().toJson(cgDeptList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "暂无部门,请先移步部门管理添加部门");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取部门数据错误");
			return jsonObject.toString();
		}
		
	}
	
	
	
	
	/**
	 * 获取指定门店职位信息
	 */
	@RequestMapping("select/getShopFrontStaffPosition")
	@ResponseBody
	public String getShopFrontStaffPosition(HttpServletRequest request,String id) {
		String hql = "FROM CgStaffPosition ";
		List<CgStaffPosition> positionList;
		JSONObject jsonObject =null;
		try {
			//如果是员工登录，根据员工所在门店查询数据
			if(id!=null){
				List<Object> parmas = new ArrayList<Object>();
				hql+=" where shopFrontId = ? ";
				parmas.add(id);
				positionList = baseService.find(hql, parmas);
			}else{
				positionList = baseService.find(hql);
			}
			
			if(positionList.size()>0){
				return  new GsonUtil().toJson(positionList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "暂无职位,请先移步职位管理添加职位");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取职位数据错误");
			return jsonObject.toString();
		}
		
	}
	
	/**
	 * 获取指定门店的部门信息
	 */
	@RequestMapping("select/getShopFrontCgDept")
	@ResponseBody
	public String getShopFrontCgDept(HttpServletRequest request,String id) {
		String hql = "FROM CgDept ";
		List<CgDept> cgDeptList;
		JSONObject jsonObject = null;
		
		try {
			//如果是员工登录，根据员工所在门店查询数据
			if(id!=null){
				List<Object> parmas = new ArrayList<Object>();
				hql+=" where shopFrontId = ? ";
				parmas.add(id);
				cgDeptList = baseService.find(hql, parmas);
			}else{
				cgDeptList = baseService.find(hql);
			}
			
			if(cgDeptList.size()>0){
				return new GsonUtil().toJson(cgDeptList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "暂无部门,请先移步部门管理添加部门");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取部门数据错误");
			return jsonObject.toString();
		}
		
	}
	
	//获取所有门店信息
	@RequestMapping("select/getAllShopFront")
	@ResponseBody
	public String getAllShopFront(HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgShopFront ";
		List<CgShopFront> cgShopFrontList;
		JSONObject jsonObject = null;
		
		try {
			
			if(loginStaff!=null){
				List<Object> parmas = new ArrayList<Object>();
				hql+=" where id = ? ";
				parmas.add(loginStaff.getShopFrontId());
				cgShopFrontList = baseService.find(hql, parmas);
			}else{
				cgShopFrontList = baseService.find(hql);
			}
			
			if(cgShopFrontList.size()>0){
				return new GsonUtil().toJson(cgShopFrontList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "暂无门店,请先移步门店管理添加门店");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取门店数据错误");
			return jsonObject.toString();
		}
		
	}
	
	
	
	/**
	 * 库存警报
	 */
	@RequestMapping("select/stockSetting")
	@ResponseBody
	public String stockSetting(HttpServletRequest request,HttpSession session) {
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			CgSetting setting = (CgSetting) baseService.findEntity("from CgSetting where shopFrontId=? ", new Object[]{loginStaff.getShopFrontId()});
			
			List<Object> params=new ArrayList<Object>();
			params.add(loginStaff.getShopFrontId());
			//获取未出库的车辆库存
			Long count1 = baseService.count("from CgCarInfo where shopFrontId=? and status=0 ", params);
			
			if (count1<setting.getCarStockMinAlert()) {
				return "库存最小值超出范围！当前库存:"+count1+"辆(警报值:"+setting.getCarStockMinAlert()+"辆)";
			}else if (count1 > setting.getCarStockMaxAlert()){
				return "库存最大值超出范围！当前库存:"+count1+"辆(警报值:"+setting.getCarStockMaxAlert()+"辆)";
			}
			
		} catch (Exception e) {
			return "1";
		}
		return "1";
		
	}
	
	//获取上架车辆
	@RequestMapping("select/getRentalItem")
	@ResponseBody
	public String getRentalItem(HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgCarRentalItem where status!=-2 and cgCarInfo.plateNumber!=null ";
		List<CgCarRentalItem> cgList;
		JSONObject jsonObject = null;
		
		try {
			//如果是员工登录，根据员工所在门店查询数据
			if(loginStaff!=null){
				List<Object> parmas = new ArrayList<Object>();
				hql+=" and shopFrontId = ? ";
				parmas.add(loginStaff.getShopFrontId());
				hql+=" order by uid desc";
				cgList = baseService.find(hql, parmas);
			}else{
				hql+=" order by uid desc";
				cgList = baseService.find(hql);
			}
			
			
			
			if(cgList.size()>0){
				return new GsonUtil().toJson(cgList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "暂无上架车辆,请先租车项目管理添加上架车辆");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取数据错误");
			return jsonObject.toString();
		}
		
	}
	
	//根据租车项目的门店查找员工
	@RequestMapping("select/getStaffByItemId")
	@ResponseBody
	public String getStaffByItemId(HttpServletRequest request,String itemID) {
		JSONObject jsonObject = null;
		try {
			
			CgCarRentalItem findEntity = (CgCarRentalItem) baseService.findEntity(CgCarRentalItem.class, itemID);
			
			String hql = "FROM CgStaff where shopFrontId = ? ";
			List<Object> parmas = new ArrayList<Object>();
			parmas.add(findEntity.getShopFrontId());
			List<CgStaff> staffList = baseService.find(hql, parmas);
			
			if(staffList.size()>0){
				return new GsonUtil().toJson(staffList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "此车辆所在门店暂无员工,请先添加员工");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取员工数据错误");
			return jsonObject.toString();
		}
	}
	
	//根据租车项目的门店查找合同
	@RequestMapping("select/getContract")
	@ResponseBody
	public String getContractId(HttpServletRequest request,String itemID) {
		JSONObject jsonObject = null;
		try {
			
			CgCarRentalItem findEntity = (CgCarRentalItem) baseService.findEntity(CgCarRentalItem.class, itemID);
			
			String hql = "FROM CgContract where shopFrontId = ? ";
			List<Object> parmas = new ArrayList<Object>();
			parmas.add(findEntity.getShopFrontId());
			List<CgContract> staffList = baseService.find(hql, parmas);
			
			if(staffList.size()>0){
				return new GsonUtil().toJson(staffList);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "此门店暂无合同,请先添加合同");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "服务器获取数据错误");
			return jsonObject.toString();
		}
	}
	
	
	
	
	//获取所有角色
	@RequestMapping("select/getShopFrontRoleContainer")
	@ResponseBody
	public String getShopFrontRoleContainer(String id) {
		String hql = "FROM CgStaffManageRoleContainer where shopFrontId=? ";
		List<CgStaffManageRoleContainer> cgStaffManageRoleContainerList;
		JSONObject jsonObject = new JSONObject();
		try {
			cgStaffManageRoleContainerList = baseService.find(hql,new Object[]{id});
			if(cgStaffManageRoleContainerList.size()>0){
				jsonObject.put("csmrcList", cgStaffManageRoleContainerList);
				return jsonObject.toString();
			}else{
				return new GsonUtil().toJson("暂无角色,请先移步角色模块添加角色");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new GsonUtil().toJson("服务器获取角色数据错误");
		}
	}
}
