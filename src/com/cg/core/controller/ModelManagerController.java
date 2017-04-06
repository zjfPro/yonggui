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
import com.cg.common.entity.CgCarType;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IModelManagerService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/model")
public class ModelManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IModelManagerService modelManagerService;
	/** 型号管理列表 */
	@RequestMapping("select/modelList")
	public String modelList(HttpServletRequest request,PageUtil<CgCarBrand>pageUtil,String text){
		String hql = "FROM CgCarBrand cu WHERE cu.series !='' AND cu.model !='' AND cu.parentId !=''";
		// 参数
		List<Object> params = new ArrayList<Object>();
		if (text != null && text.length() > 0) {
			hql += " and (cu.model LIKE ?) ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		hql += " order by cu.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "vehicleManage/model/modelList";
	}
	
	/** 跳转型号新增页面*/
	@RequestMapping("goto/addModelPage")
	public String addModelPage(HttpServletRequest request){
		try {
			List<CgCarBrand> series = baseService.find("FROM CgCarBrand cu WHERE cu.parentId IS NULL or cu.parentId =''");
			request.setAttribute("seriess", series);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "vehicleManage/model/addModel";
	}
	
	/** 查询厂商 */
	@RequestMapping("select/manufacturerList")
	@ResponseBody
	public String manufacturerList(HttpServletRequest request,String pid){
			List<CgCarBrand> bCarBrands = baseService.find("from CgCarBrand where parentId = ?", new Object[]{pid});
			return new Gson().toJson(bCarBrands);
	}
	
	/** 查询系列 */
	@RequestMapping("select/seriesList")
	@ResponseBody
	public String seriesList(HttpServletRequest request,String id){
			List<CgCarBrand> carBrands = baseService.find("from CgCarBrand where parentId = ?", new Object[]{id});
			return new Gson().toJson(carBrands);
	}
	
	
	
	/** 新增型号 */
	@RequestMapping("add/addModel")
	@ResponseBody
	public String addSeries(HttpServletRequest request,HttpSession session,CgCarBrand carBrand){
		try {
			if (carBrand.getModel() == null || carBrand.getModel() == "") {
				return "型号不能为空";
			}
			CgCarBrand cbrands = (CgCarBrand) baseService.findEntity("FROM CgCarBrand WHERE parentId = ? AND model = ?", new Object[]{carBrand.getSeries(),carBrand.getModel()});
			if (cbrands != null) {
				return "该型号已经存在";
			}else {
				CgCarBrand carBrand2 = (CgCarBrand)baseService.findEntity("from CgCarBrand where id = ?", new Object[]{carBrand.getSeries()});
				if (carBrand2 == null) {
					return "请选择完整的数据";
				}
				CgCarBrand brand2 = new CgCarBrand();
				brand2.setId("ccb-" + UUID.randomUUID().toString());
				brand2.setParentId(carBrand.getSeries());
				brand2.setModel(carBrand.getModel());
				brand2.setBrand(carBrand2.getBrand());
				brand2.setManufacturer(carBrand2.getManufacturer());
				brand2.setSeries(carBrand2.getSeries());
				brand2.setRemark("4");
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加车辆型号");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆型号：" + carBrand.getModel());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				modelManagerService.saveModel(brand2,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return "0";
	}
	
	/** 删除车辆型号 */
	@RequestMapping("delete/deleteModel")
	@ResponseBody
	public String deleteModel(HttpServletRequest request,String ccId,HttpSession session,String id){
		try {
			if (ccId != null) {
				String[] ida = new Gson().fromJson(ccId, String[].class);
				for (String idss : ida) {
					if (idss != null) {
						CgCarType types = (CgCarType)baseService.findEntity("from CgCarType where carBrandId = ?", new Object[]{idss});
						if (types != null) {
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
					adminLog.setActionDesc("删除车辆型号");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆型号：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				String[] ids = new Gson().fromJson(ccId, String[].class);
				modelManagerService.deleteModel(ids,adminLog);
				return "1";
			}else{
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(3);
					adminLog.setActionDesc("删除车辆型号");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆型号：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				CgCarType types = (CgCarType)baseService.findEntity("from CgCarType where carBrandId = ?", new Object[]{id});
				if (types != null) {
					return "-1";
				}
				if (id != null) {
					baseService.updateForHql("delete from CgCarBrand where id = ?", new Object[] { id });
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
	
	/** 跳转修改型号页面 */
	@RequestMapping("goto/updateModelPage")
	public String updateManufacturerPage(HttpServletRequest request,String ccId){
		try {
			if (!isEmpty(ccId)) {
				CgCarBrand brand = (CgCarBrand) baseService.findEntity(CgCarBrand.class, ccId);
				request.setAttribute("brands", brand);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "vehicleManage/model/updateModelPage";
	}
	
	/** 修改型号 */
	@RequestMapping("update/updateModel")
	@ResponseBody
	public String updateModel(HttpSession session,HttpServletRequest request,CgCarBrand brand,String ccId){
		try {
			if (brand == null) {
				return "0";
			}else {
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(2);
					adminLog.setActionDesc("修改型号");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改型号：" + brand.getModel());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				modelManagerService.updateModel(brand,ccId,adminLog);
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
