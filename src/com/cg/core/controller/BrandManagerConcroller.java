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
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.ICarBrandManageService;
import com.google.gson.Gson;
@Controller
@RequestMapping("control/brand")
public class BrandManagerConcroller {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private ICarBrandManageService brandManagerService;
	
	/** 车品牌列表 */
	@RequestMapping("select/brandList")
	public String carBrandList(HttpServletRequest request,PageUtil<CgCarBrand>pageUtil,String text){
		String hql = "FROM CgCarBrand cu WHERE (cu.parentId IS NULL OR cu.parentId = '') ";
		// 参数
		List<Object> params = new ArrayList<Object>();
		if (text != null && text.length() > 0) {
			hql += " and (cu.brand LIKE ?) ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		hql += " order by cu.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "vehicleManage/carBrand/brandList";
	}
	
	/** 跳转品牌新增页面*/
	@RequestMapping("goto/addCarBrandPage")
	public String addCarBrandPage(HttpServletRequest request){
		return "vehicleManage/carBrand/addBrand";
	}

	/** 新增品牌 */
	@RequestMapping("add/addCarBrand")
	@ResponseBody
	public String addCarBrand(HttpServletRequest request,HttpSession session,CgCarBrand carBrand){
		try {
			CgCarBrand cbrands = (CgCarBrand) baseService.findEntity("from CgCarBrand where brand = ?", new Object[]{carBrand.getBrand()});
			if (cbrands != null) {
				return "该品牌已经存在";
			}
			if (carBrand.getBrand() == null || carBrand.getBrand() == "") {
				return "品牌不能为空";
			}
			if (carBrand == null) {
				return "0";
			}else {
				carBrand.setId("ccb-" + UUID.randomUUID());
				carBrand.setRemark("1");
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加车辆品牌");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆品牌：" + carBrand.getBrand());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
//				baseService.saveByAdmin(carBrand,adminLog);
				brandManagerService.addCarBrand(carBrand,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return "0";
	}
	
	/** 修改品牌页面 */
	@RequestMapping("goto/updateBrandPage")
	public String updateBrandPage(HttpServletRequest request,String ccId){
		try {
			if (!isEmpty(ccId)) {
				CgCarBrand brand = (CgCarBrand) baseService.findEntity(CgCarBrand.class, ccId);
				request.setAttribute("brand", brand);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "vehicleManage/carBrand/updateBrandPage";
	}
	
	/** 修改品牌 */
	@RequestMapping("update/updateBrand")
	@ResponseBody
	public String updateCarBrand(HttpSession session,HttpServletRequest request,CgCarBrand brand,String ccId){
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
					adminLog.setActionDesc("修改车辆品牌");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改车辆品牌：" + brand.getBrand());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				brandManagerService.updateCarBrand(brand,ccId, adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "0";
	}
	
	/** 删除品牌 */
	@RequestMapping("delete/deleteBrand")
	@ResponseBody
	public String deleteBrand(HttpServletRequest request,String ccId,HttpSession session,String id){
	try{
		if (ccId != null) {
			String[] idarrays = new Gson().fromJson(ccId, String[].class);
			for (String ids : idarrays) {
				if (ids != null) {
					CgCarBrand brands = (CgCarBrand) baseService.findEntity("from CgCarBrand where parentId = ?", new Object[]{ids});
					if (brands != null) {
						return "-1";
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
				brandManagerService.deleteCarBrand(idarrays,adminLog);
				return "1";
				}
			}
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
			CgCarBrand brands = (CgCarBrand) baseService.findEntity("from CgCarBrand where parentId = ?", new Object[]{id});
			if (brands != null) {
				return "-1";
			}
			baseService.updateForHql("delete from CgCarBrand where id = ?", new Object[] { id });
			baseService.save(adminLog);
			return "1";
			
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return "删除失败";
	}
	
	
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
