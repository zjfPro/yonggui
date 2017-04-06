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
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarPeccancy;
import com.cg.common.entity.CgCarTrafficAccident;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.entity.CgDriver;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/accident")
public class AccidentsManagerController {
	@Autowired
	private IBaseService baseService;
	
	/** 车辆事故列表 */
	@RequestMapping("select/accidentList")
	public String accidentList(HttpServletRequest request,PageUtil<CgCarTrafficAccident> pageUtil,String text,HttpSession session){
		String hql = "from CgCarTrafficAccident cu ";
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		// 参数
		List<Object> params = new ArrayList<Object>();
		if(loginStaff != null){
			hql+=" where cu.shopFrontId = ? ";
			params.add(loginStaff.getShopFrontId());
			if (text != null && text.length() > 0) {
				hql += " and (cu.infos.plateNumber LIKE ? or cu.drivers.name LIKE ? or cu.driverName LIKE ?) ";
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
				hql += " and (cu.infos.plateNumber LIKE ? or cu.driverId LIKE ? or cu.driverName LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}
		return "accident/accidentList";
	}
	
	/** 跳转新增事故页面 */
	@RequestMapping("goto/addAccidentPage")
	public String addPeccancyPage(HttpServletRequest request,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgCarUseRecords> records = baseService.find("from CgCarUseRecords cu where 1=1 order by cu.uid desc");
				request.setAttribute("records", records);
				List<CgCarInfo> infos = baseService.find("from CgCarInfo cu where 1=1 order by cu.uid desc");
				request.setAttribute("infos", infos);
				List<CgStaff> staffs = baseService.find("from CgStaff cu where 1=1 order by cu.uid desc");
				request.setAttribute("staffs", staffs);
				List<CgDriver> drivers = baseService.find("from CgDriver cu where 1=1 order by cu.uid desc");
				request.setAttribute("drivers", drivers);
			}else{
				List<CgCarUseRecords> records = baseService.find("from CgCarUseRecords where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("records", records);
				List<CgDriver> drivers = baseService.find("from CgDriver where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("drivers", drivers);
				List<CgStaff> staffs = baseService.find("from CgStaff cu where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("staffs", staffs);
				List<CgCarInfo> infos = baseService.find("from CgCarInfo where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("infos", infos);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "accident/addAccident";
	}
	
	/** 新增车辆事故 */
	@RequestMapping("add/addAccident")
	@ResponseBody
	public String addAccident(HttpServletRequest request,HttpSession session,CgCarTrafficAccident trafficAccident,
			String payTimes,String trafficAccidentTimes,String driverName){
		try {
			if (isEmpty(driverName) || driverName.equals("")) {
				return "请填写借车人驾驶员";
			}
			if(isEmpty(trafficAccident.getStaffId())){
				return "请选择操作员工";
			}
			if(isEmpty(trafficAccident.getCarInfoId())){
				return "请选择车牌";
			}
			if(isEmpty(trafficAccident.getTrafficAccidentAddress())){
				return "请填写事故地点";
			}
			if(isEmpty(trafficAccident.getCompensateMoney())){
				return "请填写赔偿金额";
			}
			
			if (!"".equals(trafficAccidentTimes)) {
				trafficAccidentTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(trafficAccidentTimes);
				trafficAccident.setTrafficAccidentTime(startTime);
			}
			if (!"".equals(payTimes)) {
				payTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(payTimes);
				trafficAccident.setPayTime(startTime);
			}
			trafficAccident.setId("amc-" + UUID.randomUUID());
			trafficAccident.setAddtime(new Timestamp(System.currentTimeMillis()));
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加车辆事故记录");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆事故记录：" + trafficAccident.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseService.save(adminLog);
			}
			baseService.save(trafficAccident);
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "0";
	}
	
	@RequestMapping("account/driverId")
	@ResponseBody
	public String driverId(String carUseRecordsId, HttpServletRequest request) throws Exception {
		CgCarUseRecords records = (CgCarUseRecords) baseService.findEntity("from CgCarUseRecords where useUserName = ? ", new Object[]{carUseRecordsId});
		if(records!=null){
			return "1";
		}else{
			return "ok";
		}
	}
	
	/** 删除车辆事故 */
	@RequestMapping("delete/deleteAccident")
	@ResponseBody
	public String deleteAccident(HttpServletRequest request,HttpSession session,String ccId,String id){
		try {
			if (id != null) {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除车辆事故记录");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆事故记录：" + id );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					baseService.save(adminLog);
				}
					String[] ids = new Gson().fromJson(id, String[].class);
					Object[] objs = new Object[ids.length-1];
					int i = 0;
					String hqls = "";
					for (String ide : ids) {
						if(ide != null){
						hqls+="  cis.id = ?  or";
						objs[i++]= String.valueOf(ide);
						}
					}
					String hql = "delete from CgCarTrafficAccident cis where "+hqls.substring(0, hqls.length()-3);
					baseService.executeHQL(hql,objs);
					return "1";
			}else if (ccId != null) {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除车辆事故记录");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆事故记录：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					baseService.save(adminLog);
				}
					baseService.updateForHql("delete from CgCarTrafficAccident where id = ?", new Object[] { ccId });
					return "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}
	
	
	/** 跳转修改页面 */
	@RequestMapping("goto/updateAccidentPage")
	public String updateAccidentPage(HttpServletRequest request,HttpSession session,String ccId){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgCarUseRecords> records = baseService.find("from CgCarUseRecords cu where 1=1 order by cu.uid desc");
				request.setAttribute("records", records);
				List<CgCarInfo> infos = baseService.find("from CgCarInfo cu where 1=1 order by cu.uid desc");
				request.setAttribute("infos", infos);
				List<CgStaff> staffs = baseService.find("from CgStaff cu where 1=1 order by cu.uid desc");
				request.setAttribute("staffs", staffs);
				List<CgDriver> drivers = baseService.find("from CgDriver cu where 1=1 order by cu.uid desc");
				request.setAttribute("drivers", drivers);
			}else{
				List<CgDriver> drivers = baseService.find("from CgDriver where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("drivers", drivers);
				List<CgStaff> staffs = baseService.find("from CgStaff cu where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("staffs", staffs);
				List<CgCarInfo> infos = baseService.find("from CgCarInfo where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("infos", infos);
				List<CgCarUseRecords> records = baseService.find("from CgCarUseRecords where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("records", records);
			}
			CgCarTrafficAccident peccanc = (CgCarTrafficAccident) baseService.findEntity(CgCarTrafficAccident.class, ccId);
			request.setAttribute("data", peccanc);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return "accident/updateAccident";
	}
	
	/** 修改 */
	@RequestMapping("update/updateAccident")
	@ResponseBody
	public String updateAccident(HttpServletRequest request,
			HttpSession session, CgCarTrafficAccident peccancy,String ccId,String trafficAccidentTimes,String payTimes,String driverName) {
		CgAdmin admin = null;
		try {
			if (isEmpty(driverName) || driverName.equals("")) {
				return "请填写借车人驾驶员";
			}
			if(isEmpty(peccancy.getStaffId())){
				return "请选择操作员工";
			}
			if(isEmpty(peccancy.getCarInfoId())){
				return "请选择车牌";
			}
			if(isEmpty(peccancy.getTrafficAccidentAddress())){
				return "请填写事故地点";
			}
			if(isEmpty(peccancy.getCompensateMoney())){
				return "请填写赔偿金额";
			}
			CgCarTrafficAccident carPeccancy = (CgCarTrafficAccident) baseService.findEntity(
					CgCarTrafficAccident.class, peccancy.getId());
				if (!"".equals(trafficAccidentTimes)) {
					trafficAccidentTimes += " 00:00:000";
					Timestamp startTime = Timestamp.valueOf(trafficAccidentTimes);
					carPeccancy.setTrafficAccidentTime(startTime);
				}
				if (!"".equals(payTimes)) {
					payTimes += " 00:00:000";
					Timestamp startTime = Timestamp.valueOf(payTimes);
					carPeccancy.setPayTime(startTime);
				}
				carPeccancy.setDriverName(peccancy.getDriverName());
				carPeccancy.setStaffId(peccancy.getStaffId());
				carPeccancy.setCarInfoId(peccancy.getCarInfoId());
				carPeccancy.setDriverId(peccancy.getDriverId());
				carPeccancy.setTrafficAccidentAddress(peccancy.getTrafficAccidentAddress());
				carPeccancy.setTrafficAccidentInfo(peccancy.getTrafficAccidentInfo());
				carPeccancy.setResponsibility(peccancy.getResponsibility());
				carPeccancy.setCompensateMoney(peccancy.getCompensateMoney());
				carPeccancy.setCompanyFineMoney(peccancy.getCompanyFineMoney());
				carPeccancy.setPeccancyDescribe(peccancy.getPeccancyDescribe());
				carPeccancy.setCarUseRecordsId(peccancy.getCarUseRecordsId());
				carPeccancy.setPayStatus(peccancy.getPayStatus());
				carPeccancy.setResponsibility(peccancy.getResponsibility());
				carPeccancy.setCompensateMoney(peccancy.getCompensateMoney());
				carPeccancy.setDriverProportion(peccancy.getDriverProportion());
				carPeccancy.setCompanyProportion(peccancy.getCompanyProportion());
				carPeccancy.setDriverFineMoney(peccancy.getDriverFineMoney());
				carPeccancy.setCompanyFineMoney(peccancy.getCompanyFineMoney());
				carPeccancy.setCompanyPunishmentMoney(peccancy.getCompanyPunishmentMoney());
				carPeccancy.setRemark(peccancy.getRemark());
				
				admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
			if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改车辆事故记录");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，修改车辆事故记录：" + ccId);
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					baseService.save(adminLog);
				}
			baseService.update(carPeccancy);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	
}
