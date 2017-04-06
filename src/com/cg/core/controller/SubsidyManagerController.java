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
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgCarTrafficAccident;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.entity.CgDriver;
import com.cg.common.entity.CgDriverSubsidy;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgUser;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/subsidy")
public class SubsidyManagerController {
	@Autowired
	private IBaseService baseService;
	/** 出车补贴 */
	@RequestMapping("select/subsidyList")
	public String subsidyList(HttpServletRequest request,PageUtil<CgDriverSubsidy>pageUtil,HttpSession session,String text){
		String hql = "from CgDriverSubsidy cu ";
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		// 参数
		List<Object> params = new ArrayList<Object>();
		if(loginStaff != null){
			hql+=" where cu.drivers.shopFrontId = ? ";
			params.add(loginStaff.getShopFrontId());
			if (text != null && text.length() > 0) {
				hql += " and (cu.plateNumber LIKE ? or cu.drivers.name LIKE ?) ";
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
				hql += " and (cu.plateNumber LIKE ? or cu.drivers.name LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
					}
				hql += " order by cu.uid desc";
				pageUtil = baseService.find(hql, params, pageUtil);
				request.setAttribute("pageUtil", pageUtil);
			}
			return "subsidy/subsidyList";
	}
	
	
	/** 跳转新增出车补贴页面 */
	@RequestMapping("goto/addSubsidyPage")
	public String addSubsidyPage(HttpServletRequest request,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgDriver> drivers = baseService.find("from CgDriver cu where 1=1 order by cu.uid desc");
				request.setAttribute("drivers", drivers);
			}else{
				List<CgDriver> drivers = baseService.find("from CgDriver where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("drivers", drivers);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "subsidy/addSubsidy";
	}
	
	/** 新增出车补贴 */
	@RequestMapping("add/addSubsidy")
	@ResponseBody
	public String addSubsidy(HttpServletRequest request,HttpSession session,CgDriverSubsidy subsidy,String payTimes){
		try {
			if (subsidy.getDriverId().equals("")) {
				return "请选择驾驶员";
			}
			if (!"".equals(payTimes)) {
				payTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(payTimes);
				subsidy.setPayTime(startTime);
			}
			subsidy.setStatus(0);//默认未审核
			subsidy.setId("cds-" + UUID.randomUUID());
			subsidy.setAddtime(new Timestamp(System.currentTimeMillis()));
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加出车补贴");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加出车补贴：" + subsidy.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseService.save(adminLog);
			}
			baseService.save(subsidy);
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "0";
	}
	
	/** 删除出车补贴 */
	@RequestMapping("delete/deleteSubsidy")
	@ResponseBody
	public String deleteSubsidy(HttpServletRequest request,HttpSession session,String ccId,String id){
		try {
			if (id != null) {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除出车补贴");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除出车补贴：" + id );
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
					String hql = "delete from CgDriverSubsidy cis where "+hqls.substring(0, hqls.length()-3);
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
					baseService.updateForHql("delete from CgDriverSubsidy where id = ?", new Object[] { ccId });
					return "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}
	
	
	/** 跳转修改页面 */
	@RequestMapping("goto/updateSubsidyPage")
	public String updateAccidentPage(HttpServletRequest request,HttpSession session,String ccId){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgDriver> drivers = baseService.find("from CgDriver cu where 1=1 order by cu.uid desc");
				request.setAttribute("drivers", drivers);
			}else{
				List<CgDriver> drivers = baseService.find("from CgDriver where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("drivers", drivers);
			}
			CgDriverSubsidy subsidy = (CgDriverSubsidy) baseService.findEntity(CgDriverSubsidy.class, ccId);
			request.setAttribute("subsidy", subsidy);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return "subsidy/updateSubsidy";
	}
	
	/** 修改 */
	@RequestMapping("update/updateSubsidy")
	@ResponseBody
	public String updateSubsidy(HttpServletRequest request,
			HttpSession session, CgDriverSubsidy subsidy,String ccId,String payTimes,String carUseRecordsId,String examineTimes) {
		CgAdmin admin = null;
		try {
			if (subsidy.getDriverId().equals("")) {
				return "请选择驾驶员";
			}
			CgDriverSubsidy driverSubsidy = (CgDriverSubsidy) baseService.findEntity(
					CgDriverSubsidy.class, subsidy.getId());
				if (!"".equals(payTimes)) {
					payTimes += " 00:00:000";
					Timestamp startTime = Timestamp.valueOf(payTimes);
					driverSubsidy.setPayTime(startTime);
				}
				driverSubsidy.setDriverId(subsidy.getDriverId());
				driverSubsidy.setPlateNumber(subsidy.getPlateNumber());
				driverSubsidy.setMoney(subsidy.getMoney());
				driverSubsidy.setReason(subsidy.getReason());
				driverSubsidy.setReply(subsidy.getReply());
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
			baseService.update(driverSubsidy);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	/** 跳转审核页面 */
	@RequestMapping("goto/checkPage")
	public String checkPage(HttpServletRequest request,String ccId){
		try {
			CgDriverSubsidy subsidy = (CgDriverSubsidy) baseService.findEntity(CgDriverSubsidy.class, ccId);
			request.setAttribute("subsidy", subsidy);
	} catch (Exception e) {
		e.printStackTrace();
	}
		return "subsidy/check";
	}
	
	@RequestMapping("update/updateCheck")
	@ResponseBody
	public String updateCheckPage(HttpServletRequest request,HttpSession session,CgDriverSubsidy subsidy,Integer status){
		CgAdmin admin = null;
		try {
			CgDriverSubsidy driver = (CgDriverSubsidy) baseService.findEntity(
					CgDriverSubsidy.class, subsidy.getId());
			
			admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
		if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("审核出车补贴");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，审核出车补贴：" + subsidy.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseService.save(adminLog);
			}
			driver.setStatus(status);
			driver.setExamineTime(new Timestamp(System.currentTimeMillis()));
			baseService.update(driver);
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
