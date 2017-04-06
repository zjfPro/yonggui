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
import com.cg.common.entity.CgCarBrand;
import com.cg.common.entity.CgSetting;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.ISettingService;
import com.google.gson.Gson;


/** 车辆库存管理 */
@Controller
@RequestMapping("control/setting")
public class SettingManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private ISettingService settingService;
	
	@RequestMapping("select/settingList")
	public String settingList(HttpServletRequest request,String text,PageUtil<CgSetting>pageUtil,HttpSession session){
		String hql = "FROM CgSetting cu ";
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		// 参数
		List<Object> params = new ArrayList<Object>();
		if(loginStaff != null){
			hql+=" where cu.shopFrontId = ? ";
			params.add(loginStaff.getShopFrontId());
			if (text != null && text.length() > 0) {
				hql += " and (cu.shopFront.name LIKE ?) ";
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}else{
			hql += " where 1=1 ";
			if (text != null && text.length() > 0) {
				hql += " and (cu.shopFront.name LIKE ?) ";
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}
		return "setting/settingList";
	}
	
	/** 跳转新增页面 */
	@RequestMapping("goto/addSettingPage")
	public String addSettingPage(HttpServletRequest request,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				request.setAttribute("fronts", fronts);
			}else{
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("fronts", fronts);
				}
			} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			}
		return "setting/addSetting";
	}
	
	/** 新增 */
	@RequestMapping("add/addSetting")
	@ResponseBody
	public String addSetting(HttpServletRequest request,HttpSession session,CgSetting cgSetting,String shopFrontId){
		try {
			CgSetting settings = (CgSetting)baseService.findEntity("from CgSetting where shopFrontId = ?", new Object[]{shopFrontId});
			if (settings != null) {
				return "该门店已经设置库存";
			}
			CgShopFront front = (CgShopFront)baseService.findEntity("from CgShopFront where id = ?", new Object[]{cgSetting.getShopFrontId()});
			if (cgSetting == null) {
				return "添加失败,数据不能为空";
			}else {
				cgSetting.setId("cs-" + UUID.randomUUID().toString());
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("新增门店库存");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，新增门店库存：" + front.getName());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
					settingService.addSetting(cgSetting,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
		return "0";
	}
	
	/** 修改页面 */
	@RequestMapping("goto/updateSettingPage")
	public String updateSettingPage(HttpServletRequest request,HttpSession session,String ccId){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				request.setAttribute("fronts", fronts);
			}else{
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("fronts", fronts);
				}
			CgSetting setting = (CgSetting)baseService.findEntity(CgSetting.class, ccId);
			request.setAttribute("setting", setting);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "setting/updateSetting";
	}
	
	/** 修改库存 */
	@RequestMapping("update/updateSetting")
	@ResponseBody
	public String updateSetting(HttpServletRequest request,HttpSession session,CgSetting setting,String ccId){
		try {
			CgShopFront front = (CgShopFront)baseService.findEntity("from CgShopFront where id = ?", new Object[]{setting.getShopFrontId()});
			CgSetting sCgSetting = (CgSetting)baseService.findEntity(CgSetting.class, ccId);
			if (setting == null) {
				return "数据不能为空";
			}else {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(2);
					adminLog.setActionDesc("修改门店库存");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改门店库存：" + front.getName());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				sCgSetting.setCarStockMinAlert(setting.getCarStockMinAlert());
				sCgSetting.setCarStockMaxAlert(setting.getCarStockMaxAlert());
				sCgSetting.setShopFrontId(setting.getShopFrontId());
				settingService.updateSetting(sCgSetting,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
		return "修改失败";
	}
	
	/** 删除 */
	@RequestMapping("delete/deleteSetting")
	@ResponseBody
	public String deleteSetting(HttpServletRequest request,HttpSession session,String ccId,String id){
		try {
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(3);
				adminLog.setActionDesc("删除车辆库存");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆库存：" + ccId );
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			if (id != null) {
				baseService.updateForHql("delete from CgSetting where id = ?", new Object[] { id });
				baseService.save(adminLog);
				return "1";
			}else{
				String[] ids = new Gson().fromJson(ccId, String[].class);
				settingService.deleteSetting(ids,adminLog);
				return "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}

}
