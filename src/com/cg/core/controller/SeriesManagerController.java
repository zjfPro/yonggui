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
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.ISeriesManagerService;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("control/series")
public class SeriesManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private ISeriesManagerService seriesManagerService;
	/** 系列管理列表 */
	@RequestMapping("select/seriesList")
	public String manufacturerList(HttpServletRequest request,PageUtil<CgCarBrand>pageUtil,String text){
		String hql = "FROM CgCarBrand cu WHERE cu.series !='' AND cu.model IS NULL AND cu.parentId !=''";
		// 参数
		List<Object> params = new ArrayList<Object>();
		if (text != null && text.length() > 0) {
			hql += " and (cu.series LIKE ?) ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		hql += " order by cu.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "vehicleManage/series/seriesList";
	}
	
	/** 跳转系列新增页面*/
	@RequestMapping("goto/addSeriesPage")
	public String addSeriesPage(HttpServletRequest request){
		try {
			List<CgCarBrand> brands = baseService.find("FROM CgCarBrand cu WHERE cu.parentId IS NULL or cu.parentId =''");
			request.setAttribute("brands", brands);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "vehicleManage/series/addSeries";
	}
	
	/** 查询厂商 */
	@RequestMapping("select/manufacturerList")
	@ResponseBody
	public String manufacturerList(HttpServletRequest request,String pid){
			List<CgCarBrand> bCarBrands = baseService.find("from CgCarBrand where parentId = ?", new Object[]{pid});
			return new Gson().toJson(bCarBrands);
	}
	
	
	/** 新增系列 */
	@RequestMapping("add/addSeries")
	@ResponseBody
	public String addSeries(HttpServletRequest request,HttpSession session,CgCarBrand carBrand,String manufacturer,String series){
		try {
			if (carBrand.getSeries() == null || carBrand.getSeries() == "") {
				return "系列不能为空";
			}
			CgCarBrand cbrands = (CgCarBrand) baseService.findEntity("FROM CgCarBrand WHERE parentId = ? AND series = ?", new Object[]{carBrand.getManufacturer(),carBrand.getSeries()});
			if (cbrands != null) {
				return "该系列已经存在";
			}else {
				CgCarBrand carBrand2 = (CgCarBrand)baseService.findEntity("from CgCarBrand where id = ?", new Object[]{carBrand.getManufacturer()});
				CgCarBrand brand2 = new CgCarBrand();
				brand2.setId("ccb-" + UUID.randomUUID().toString());
				if (carBrand2 == null) {
					return "请选择品牌";
				}
				brand2.setBrand(carBrand2.getBrand());
				brand2.setSeries(carBrand.getSeries());
				brand2.setParentId(carBrand.getManufacturer());
				brand2.setManufacturer(carBrand2.getManufacturer());
				brand2.setModel(null);
				brand2.setRemark("3");
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加车辆系列");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆系列：" + carBrand.getSeries());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				seriesManagerService.saveSeries(brand2,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return "0";
	}
	
	/** 删除车辆系列 */
	@RequestMapping("delete/deleteSeries")
	@ResponseBody
	public String deleteSeries(HttpServletRequest request,String ccId,HttpSession session,String id){
		try {
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
					seriesManagerService.deleteSeries(idarrays,adminLog);
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
	/** 修改系列页面 */
	@RequestMapping("goto/updateSeriePage")
	public String updateBrandPage(HttpServletRequest request,String ccId){
		try {
			if (!isEmpty(ccId)) {
				CgCarBrand brand = (CgCarBrand) baseService.findEntity(CgCarBrand.class, ccId);
				request.setAttribute("brands", brand);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "vehicleManage/series/updateSeriePage";
	}
	
	/** 修改系列 */
	@RequestMapping("update/updateSerie")
	@ResponseBody
	public String updateSerie(HttpSession session,HttpServletRequest request,CgCarBrand brand,String ccId){
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
					adminLog.setActionDesc("修改系列");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改系列：" + brand.getSeries());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				seriesManagerService.updateSeries(brand,ccId,adminLog);
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
