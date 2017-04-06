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
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgDept;
import com.cg.common.entity.CgInvestor;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

/**
 * 车辆年审控制器
 * */
@Controller
@RequestMapping("control/avmc")
public class AnnualVerificationManagerController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/annualVerificationList")
	public String annualVerificationList(HttpServletRequest request,
			PageUtil<CgAnnualVerification> pageUtil, String text, Integer type) {
		String hql = "from CgAnnualVerification o where 1=1";
		List<Object> params = new ArrayList<Object>();
		if (!isEmpty(text)) {
			hql += " and o.carInfo.shopFront.name LIKE ? ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		
		CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
		if(loginStaff!=null){
			hql+=" and o.carInfo.shopFrontId=? ";
			params.add(loginStaff.getShopFrontId());
		}
		
		// 参数
		hql += " order by o.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "annualVerificationManage/annualVerificationList";
	}

	/**
	 * 跳转新增年审
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addAnnualVerification")
	public String goto_addInvestor(HttpServletRequest request,HttpSession session) throws Exception {
		String hql = " from CgCarInfo where 1=1 ";
		CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
		List<Object> params = new ArrayList<Object>();
		if(loginStaff!=null){//如果是员工登录，只显示员工所在的门店
			hql+=" and shopFrontId=? ";
			params.add(loginStaff.getShopFrontId());
		}
		List<CgCarInfo> carInfoList = baseService.find(hql,params);
		request.setAttribute("carInfoList", carInfoList);
		return "annualVerificationManage/addAnnualVerification";
	}

	/**
	 * 跳转修改年审
	 * 
	 * @return
	 */
	@RequestMapping("goto/editAnnualVerificationView")
	public String editAnnualVerificationView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgAnnualVerification cav = (CgAnnualVerification) baseService.findEntity(CgAnnualVerification.class, id);
			request.setAttribute("cav", cav);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "annualVerificationManage/editAnnualVerification";
	}


	/**
	 * 新增年审
	 * 
	 * @return
	 */
	@RequestMapping("add/addAnnualVerification")
	@ResponseBody
	public String addAnnualVerification(HttpServletRequest request,HttpSession session, CgAnnualVerification cav) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			String id = "ci-" + UUID.randomUUID();
			cav.setId(id);
			if(admin!=null){
				CgAdminLog adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加车辆年审信息");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，添加年审的车辆Id：" + cav.getCarInfoId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				adminLogService.save(adminLog);
			}
			baseService.save(cav);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/avmc/select/annualVerificationList";
		return "1";
	}

	/**
	 * 修改年审记录
	 * 
	 * @return
	 */
	@RequestMapping("update/editAnnualVerification")
	@ResponseBody
	public String editAnnualVerification(HttpServletRequest request,HttpSession session, CgAnnualVerification cav) {
		CgAdmin admin = null;
		try{
			CgAnnualVerification fcav = (CgAnnualVerification) baseService.findEntity(CgAnnualVerification.class, cav.getId());
			fcav.setOpinion(cav.getOpinion());
			fcav.setIspass(cav.getIspass());
			baseService.update(fcav);
			admin = (CgAdmin) session.getAttribute("admin");
			if(admin!=null){
				CgAdminLog adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改车辆年审信息");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，修改年审的车辆Id：" + cav.getCarInfoId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				adminLogService.save(adminLog);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/avmc/select/annualVerificationList";
		return "1";
	}
	
	/**
	 * 删除年审
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteAnnualVerification")
	@ResponseBody
	public String deleteInvestor(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgAnnualVerification investor = (CgAnnualVerification) baseService.findEntity(CgAnnualVerification.class, id);
						if (investor != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除车辆年审");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除车辆年审信息："
									+ investor.getCarInfoId());
							adminLog.setAdminId(admin.getId());
							adminLog.setAdmin(admin);
							adminLog.setId("cal-"
									+ UUID.randomUUID().toString());
							baseService.deleteByAdmin(investor, adminLog);
						}
					}
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 没有权限
				return "没有操作权限!";
			} catch (Exception e2) {
				// 身份识别失败！ 跳转重新登录
				return "没有操作权限!";
			}
		}
		return "1";
	}

	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
