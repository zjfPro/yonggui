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
import com.cg.common.entity.CgCarRentalItem;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IRentalItemServer;
/**
 * 租车项目管理
 *
 */
@Controller
@RequestMapping("control/rentalItem")
public class RentalItemController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IRentalItemServer rentalItemServer;
	
	//租车项目列表
	@RequestMapping("select/rentalItemList")
	public String rentalItemList(HttpServletRequest request,PageUtil<CgCarRentalItem> pageUtil,String searchText) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgCarRentalItem where status!=-2 and cgCarInfo.plateNumber!=null ";
		List<Object> parmas = null;
		if(loginStaff!=null){
			if(isEmpty(searchText)){
				parmas = new ArrayList<Object>();
				hql += " and shopFrontId=? ";
				parmas.add(loginStaff.getShopFrontId());
			}else{
				parmas = new ArrayList<Object>();
				hql += " and (cgCarInfo.plateNumber like ? or cgShopFront.name like ?) and shopFrontId=? ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add(loginStaff.getShopFrontId());
			}
		}else{
			if(isEmpty(searchText)){
				
			}else{
				parmas = new ArrayList<Object>();
				hql += " and (cgCarInfo.plateNumber like ? or cgShopFront.name like ?) ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
			}
		}
		
		hql +=" order by uid desc";
		PageUtil<CgCarRentalItem> pageUtils = baseService.find(hql, parmas, pageUtil);

		request.setAttribute("pageUtil", pageUtils);
		request.setAttribute("searchText", searchText);
		return "rentalItem/rentalItemList";
	}
	
	// 跳转新增页面
	@RequestMapping("goto/addRentalItem")
	public String addStaffPage() {
		return "rentalItem/addRentalItem_new";
	}
	
	//添加租车项目
	@RequestMapping("add/gotoAddCgCarRentalItem")
	@ResponseBody
	public String gotoAddCgCarRentalItem(HttpServletRequest request,HttpSession session, CgCarRentalItem ccri){
		try {
			
			
			if(isEmpty(ccri.getCarInfoId())){
				return "请选择车辆";
			}
			if(isEmpty(ccri.getStaffId())){
				return "请选择操作人";
			}
			if(isEmpty(ccri.getShopFrontId())){
				return "请选择门店";
			}
			if(isEmpty(ccri.getUnitPrice())){
				return "请输入价格";
			}
			if(isEmpty(ccri.getUnitName())){
				return "请输入销售单位";
			}
			if(ccri.getUnitNumber()==null){
				return "请输入销售数量";
			}
			
			ccri.setId("cci-" + UUID.randomUUID().toString());
			ccri.setAddtime(new Timestamp(System.currentTimeMillis()));
			
			String ids = ccri.getCarInfoId();
			String[] split = ids.split(",");
			ccri.setCarInfoId(split[0]);
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加车辆项目");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆项目：" + ccri.getCarInfoId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				
				baseService.saveByAdmin(ccri, adminLog);
			}else{
				baseService.save(ccri);
			}
			
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "添加数据失败";
		}
	}
	
	//租车项目信息详细页面
	@RequestMapping("select/cgCarRentalItemDetail")
	public String cgCarRentalItemDetail(HttpServletRequest request,String ccriID){
		
		try {
			CgCarRentalItem findEntity = (CgCarRentalItem) baseService.findEntity(CgCarRentalItem.class, ccriID);
			request.setAttribute("ccri", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rentalItem/editRentalItem";
	}
	
	//修改租车项目
	@RequestMapping("update/gotoUpdateCgCarRentalItem")
	@ResponseBody
	public String gotoUpdateCgCarRentalItem(HttpServletRequest request,HttpSession session, CgCarRentalItem ccri,String ccriID){
		
		try {
			
			CgCarRentalItem findCcri = (CgCarRentalItem) baseService.findEntity(CgCarRentalItem.class, ccriID);
			
			if(isEmpty(ccri.getCarInfoId())){
				return "请选择车辆";
			}
			if(isEmpty(ccri.getStaffId())){
				return "请选择操作人";
			}
			if(isEmpty(ccri.getShopFrontId())){
				return "请选择门店";
			}
			if(isEmpty(ccri.getUnitPrice())){
				return "请输入价格";
			}
			if(isEmpty(ccri.getUnitName())){
				return "请输入销售单位";
			}
			if(ccri.getUnitNumber()==null){
				return "请输入销售数量";
			}
			
			String ids = ccri.getCarInfoId();
			String[] split = ids.split(",");
			ccri.setCarInfoId(split[0]);
			
			findCcri.setCarInfoId(ccri.getCarInfoId());
			findCcri.setContent(ccri.getContent());
			findCcri.setEarnestMoney(ccri.getEarnestMoney());
			findCcri.setShopFrontId(ccri.getShopFrontId());
			findCcri.setStaffId(ccri.getStaffId());
			findCcri.setStatus(ccri.getStatus());
			findCcri.setTitle(ccri.getTitle());
			findCcri.setUnitName(ccri.getUnitName());
			findCcri.setUnitNumber(ccri.getUnitNumber());
			findCcri.setUnitPrice(ccri.getUnitPrice());
			findCcri.setForegiftMoney(ccri.getForegiftMoney());
			
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改租车项目");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改租车项目："+findCcri.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				
				baseService.updateByAdmin(findCcri, adminLog);
			}else{
				baseService.update(findCcri);
			}
			
			
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "修改失败";
		}
		
	}
	
	//删除租车项目
	@RequestMapping("delete/deleteCgCarRentalItem")
	@ResponseBody
	public String deleteCgCarRentalItem(HttpServletRequest request,HttpSession session,String strs) {
		
		String ids = "";
		if(strs.indexOf(",")>0){
			ids= strs.substring(0, strs.length()-1);
		}else{
			ids=strs;
		}
		
		try {
			rentalItemServer.deleteCarRentalItem(ids, session, request);
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
