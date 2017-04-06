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
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarStructure;
import com.cg.common.entity.CgCarType;
import com.cg.common.entity.CgInvestor;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.ICarTypeManageService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/carType")
public class CarTypeManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private ICarTypeManageService carTypeManageService;
	
	
	
	/** 车型列表 */
	@RequestMapping("select/carTypeList")
	public String carTypeList(HttpServletRequest request,PageUtil<CgCarType>pageUtil,String text){
		String hql = "from CgCarType cu where 1=1";
		// 参数
		List<Object> params = new ArrayList<Object>();
		if (text != null && text.length() > 0) {
			hql += " and (cu.typeName LIKE ? or cu.fuelForm LIKE ? or cu.structure.name LIKE ?) ";
			params.add("%" + text + "%");
			params.add("%" + text + "%");
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		hql += " order by cu.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "vehicleManage/carType/carTypeList";
	}
	
	/**删除车型*/
	@RequestMapping("delete/deleteCarType")
	@ResponseBody
	public String deleteCarType(HttpServletRequest request,String ccId,HttpSession session,String id) {
		try {
			if (ccId != null) {
				String[] ida = new Gson().fromJson(ccId, String[].class);
				for (String idss : ida) {
					if (idss != null) {
						CgCarInfo infos = (CgCarInfo)baseService.findEntity("from CgCarInfo where carTypeId = ?", new Object[]{idss});
						if (infos != null) {
							return "-1";
						}
					}
				}
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除车辆类型");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆类型：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				String[] ids = new Gson().fromJson(ccId, String[].class);
				carTypeManageService.deleteCarType(ids,adminLog);
				return "1";
			}else{
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除车辆类型");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆类型：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				CgCarInfo infos = (CgCarInfo)baseService.findEntity("from CgCarInfo where carTypeId = ?", new Object[]{id});
				if (infos != null) {
					return "-1";
				}
				if (id != null) {
					baseService.updateForHql("delete from CgCarType where id = ?", new Object[] { id });
					baseService.save(adminLog);
					return "1";
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}
	
	/** 添加车型页面 */
	@RequestMapping("goto/addCarTypePage")
	public String addCarTypePage(HttpServletRequest request){
		try {
				List<CgCarBrand> brands = baseService.find("from CgCarBrand cc where cc.brand IS NOT NULL AND cc.series IS NOT NULL AND cc.model IS NOT NULL");
				request.setAttribute("brands", brands);
				List<CgCarStructure> structures = baseService.find("from CgCarStructure ");
				request.setAttribute("structures", structures);
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
		return "vehicleManage/carType/addCarTypePage";
	}
	
	/** 添加车型 */
	@RequestMapping("add/addCarType")
	@ResponseBody
	public String addCarType(HttpServletRequest request,HttpSession session,CgCarType carType,String carBrandId,String carStructureId,String displacement){
		try {
			if (carBrandId == null || carBrandId == "") {
				return "请选择车辆品牌";
			}
			if (carStructureId == null || carStructureId == "") {
				return "请选择车身结构";
			}
			if(!displacement.matches("^[0-9]*$")){
				return "排量请输入整数";
			}
			CgCarType types = (CgCarType) baseService.findEntity("FROM CgCarType WHERE carBrandId = ?", new Object[]{carType.getCarBrandId()});
			if (types != null) {
				return "该车型已经存在";
			}else {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加车辆类型");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆类型：" + carType.getTypeName());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				carTypeManageService.addCarType(carType,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return "0";
	}
	
	/** 跳转修改页面 */
	@RequestMapping("goto/updateCarTypePage")
	public String updateCarTypePage(HttpServletRequest request,String ccId){
		try {
			if (!isEmpty(ccId)) {
				CgCarType type = (CgCarType) baseService.findEntity(CgCarType.class, ccId);
				request.setAttribute("type", type);
				List<CgCarBrand> brands = baseService.find("from CgCarBrand cc where cc.brand IS NOT NULL AND cc.series IS NOT NULL AND cc.model IS NOT NULL");
				request.setAttribute("brands", brands);
				List<CgCarStructure> structures = baseService.find("from CgCarStructure ");
				request.setAttribute("structures", structures);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "vehicleManage/carType/updateCarTypePage";
	}
	
	/** 修改车辆类型 */
	@RequestMapping("update/updateCarType")
	@ResponseBody
	public String updateCarType(HttpSession session,HttpServletRequest request,CgCarType carType,String ccId,String displacement){
		try {
			if(!displacement.matches("^[0-9]*$")){
				return "排量请输入整数";
			}
			if (carType == null) {
				return "0";
			}else {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(2);
					adminLog.setActionDesc("修改车辆类型");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改车辆类型：" + carType.getTypeName());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				carTypeManageService.updateCarType(carType,adminLog,ccId);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "0";
	}

	
	
	
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
