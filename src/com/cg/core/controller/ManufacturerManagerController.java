package com.cg.core.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;
import com.cg.common.entity.CgCarType;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffPosition;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IManufacturerManageService;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("control/manufacturer")
public class ManufacturerManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IManufacturerManageService manufacturerManageService;
	
	/** 厂商管理列表 */
	@RequestMapping("select/manufacturerList")
	public String manufacturerList(HttpServletRequest request,PageUtil<CgCarBrand>pageUtil,String text){
		String hql = "FROM CgCarBrand cu WHERE cu.series IS NULL AND cu.model IS NULL AND cu.parentId !=''";
		// 参数
		List<Object> params = new ArrayList<Object>();
		if (text != null && text.length() > 0) {
			hql += " and (cu.manufacturer LIKE ?) ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		hql += " order by cu.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "vehicleManage/manufacturer/manufacturerList";
	}
	
	/** 跳转厂商新增页面*/
	@RequestMapping("goto/addManufacturer")
	public String addManufacturer(HttpServletRequest request){
		try {
			List<CgCarBrand> brands = baseService.find("FROM CgCarBrand cu WHERE cu.parentId IS NULL OR cu.parentId = ''");
			request.setAttribute("brands", brands);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "vehicleManage/manufacturer/addManufacturer";
	}
	
	/** 新增厂商 
	 * @throws Exception */
	@RequestMapping("add/Manufacturer")
	@ResponseBody
	public String Manufacturer(HttpServletRequest request,HttpSession session,CgCarBrand carBrand) throws Exception{
		try {
			CgCarBrand cbrands = (CgCarBrand) baseService.findEntity("from CgCarBrand where manufacturer = ?", new Object[]{carBrand.getManufacturer()});
			if (cbrands != null) {
				return "该厂商已经存在";
			}
			if (carBrand.getManufacturer() == null || carBrand.getManufacturer() == "") {
				return "厂商不能为空";
			}
			if (carBrand == null) {
				return "0";
			}else {
				CgCarBrand brand2 = (CgCarBrand) baseService.findEntity(
						CgCarBrand.class, carBrand.getParentId());
				if (brand2 == null) {
					return "请选择品牌";
				}
				carBrand.setBrand(brand2.getBrand());
				carBrand.setId("ccb-" + UUID.randomUUID());
				carBrand.setRemark("2");
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("新增车辆厂商");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，新增车辆厂商：" + carBrand.getBrand());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				
				manufacturerManageService.saveManufacturer(carBrand,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return "0";
	}
	
	/** 删除厂商*/
	@RequestMapping("delete/deleteManufacturer")
	@ResponseBody
	public String deleteManufacturer(HttpServletRequest request,String ccId,HttpSession session,String id){
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
					adminLog.setActionDesc("删除车辆厂商");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆厂商：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				manufacturerManageService.deleteSeries(idarrays,adminLog);
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
				adminLog.setActionDesc("删除车辆厂商");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆厂商：" + ccId );
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
	
	/** 跳转修改厂商 */
	@RequestMapping("goto/updateManufacturerPage")
	public String updateManufacturerPage(HttpServletRequest request,String ccId){
		try {
			if (!isEmpty(ccId)) {
				CgCarBrand brand = (CgCarBrand) baseService.findEntity(CgCarBrand.class, ccId);
				request.setAttribute("brands", brand);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "vehicleManage/manufacturer/updateCarBrandPage";
	}
	
	/** 修改厂商 */
	@RequestMapping("update/updateManufacturer")
	@ResponseBody
	public String updateManufacturer(HttpSession session,HttpServletRequest request,CgCarBrand brand,String ccId){
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
					adminLog.setActionDesc("修改车辆厂商");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改车辆厂商：" + brand.getManufacturer());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				manufacturerManageService.updateManufacturer(brand,ccId,adminLog);
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
