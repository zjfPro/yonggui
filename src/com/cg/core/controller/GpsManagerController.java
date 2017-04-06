package com.cg.core.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarGps;
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgDriver;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/gps")
public class GpsManagerController {
	@Autowired
	private IBaseService baseService;
	
	@RequestMapping("select/gpsList")
	public String gpsList(HttpServletRequest request,HttpSession session,PageUtil<CgCarGps>pageUtil,String text){
		String hql = "from CgCarGps cu ";
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		// 参数
		List<Object> params = new ArrayList<Object>();
		if(loginStaff != null){
			hql+=" where cu.infos.shopFrontId = ? ";
			params.add(loginStaff.getShopFrontId());
			if (text != null && text.length() > 0) {
				hql += " and (cu.carTerminal LIKE ?) ";
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}else{
			hql += " where 1=1 ";
			if (text != null && text.length() > 0) {
				hql += " and (cu.carTerminal LIKE ?) ";
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}
		return "gps/gpsList";
	}
	
	
	
	
	
	/** 跳转新增gps页面  */
	@RequestMapping("goto/addGpsPage")
	public String addGpsPage(HttpServletRequest request,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgCarInfo> infos = baseService.find("from CgCarInfo cc where 1=1 order by cc.uid desc");
				List<CgDriver> drivers = baseService.find("from CgDriver cd where 1=1 order by cd.uid desc");
				request.setAttribute("drivers", drivers);
				request.setAttribute("infos", infos);
			}else{
				List<CgDriver> drivers = baseService.find("from CgDriver where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("drivers", drivers);
				List<CgCarInfo> infos = baseService.find("from CgCarInfo where shopFrontId = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("infos", infos);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "gps/addGpsPage";
	}
	
	/** 新增gps信息 */
	@RequestMapping("add/addGps")
	@ResponseBody
	public String addGps(CgCarGps carGps,HttpServletRequest request,HttpSession session,String ownerPhone){
		try {
			if (!isMobile(ownerPhone)) {
				return "请输入正确的车主联系方式！！！";
			}
			carGps.setId("ccg-" + UUID.randomUUID());
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加车辆GPS");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆GPS：" + carGps.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseService.save(adminLog);
			}
			baseService.save(carGps);
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "-1";
	}
	
	/** 跳转修改gps页面 */
	@RequestMapping("goto/updateGpsPage")
	public String updateGpsPage(HttpServletRequest request,String ccId,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgDriver> drivers = baseService.find("from CgDriver cd where 1=1 order by cd.uid desc");
				request.setAttribute("drivers", drivers);
				List<CgCarInfo> infos = baseService.find("from CgCarInfo cc where 1=1 order by cc.uid desc");
				request.setAttribute("infos", infos);
			}else{
				List<CgCarInfo> infos = baseService.find("from CgCarInfo cu where cu.shopFrontId = ? AND cu.",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("infos", infos);
				List<CgDriver> drivers = baseService.find("from CgDriver where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("drivers", drivers);
			}
			CgCarGps gps = (CgCarGps) baseService.findEntity(
					CgCarGps.class, ccId);
			request.setAttribute("gps", gps);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "gps/updateGpsPage";
	}
	
	/** 修改 */
	@RequestMapping("update/updateGps")
	@ResponseBody
	public String updateGps(HttpServletRequest request,HttpSession session,CgCarGps gps,String ownerPhone){
		CgAdmin admin = null;
		try {
			if (!isMobile(ownerPhone)) {
				return "请输入正确的车主联系方式！！！";
			}
			CgCarGps ins = (CgCarGps) baseService.findEntity(
					CgCarGps.class, gps.getId());
			admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改车辆gps");
				adminLog.setActionContent("管理员：" + admin.getNickname()+ "，修改gps信息：" + gps.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseService.save(adminLog);
			}
			ins.setCarInfoId(gps.getCarInfoId());
			ins.setCarTerminal(gps.getCarTerminal());
			ins.setDriverId(gps.getDriverId());
			ins.setOwnerPhone(gps.getOwnerPhone());
			ins.setPhone(gps.getPhone());
			
			baseService.update(ins);
			return "1";
		} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
		}
			return "-1";
	}
	
	/** 删除gps */
	@RequestMapping("delete/deleteGps")
	@ResponseBody
	public String deleteGps(HttpServletRequest request,String ccId,HttpSession session,String id){
		try {
			if (ccId != null) {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除车辆GPS");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆GPS：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					baseService.save(adminLog);
				}
					String[] ids = new Gson().fromJson(ccId, String[].class);
					Object[] objs = new Object[ids.length-1];
					int i = 0;
					String hqls = "";
					for (String ide : ids) {
						if(ide != null){
						hqls+="  cis.id = ?  or";
						objs[i++]= String.valueOf(ide) ;
						}
					}
					String hql = "delete from CgCarGps cis where "+hqls.substring(0, hqls.length()-3);
					baseService.executeHQL(hql,objs);
					return "1";
			}else if (id != null) {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除车辆GPS");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆GPS：" + id );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					baseService.save(adminLog);
				}
					baseService.updateForHql("delete from CgCarGps where id = ?", new Object[] { id });
					return "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		if (str==null ||str.length()<1) {
			return false;
		}
		return Pattern.compile("^[1][1,2,3,4,5,6,7,8,9][0-9]{9}$").matcher(str)
				.matches();
	}
}
