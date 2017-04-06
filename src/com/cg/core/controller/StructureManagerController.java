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
import com.cg.common.entity.CgCarStructure;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IStructureService;
import com.google.gson.Gson;

/** 车结构 */
@Controller
@RequestMapping("control/structure")
public class StructureManagerController {

	@Autowired
	private IBaseService baseService;
	@Autowired
	private IStructureService structureService;
	@RequestMapping("select/structureList")
	public String structureList(HttpServletRequest request,String text,PageUtil<CgCarStructure>pageUtil,HttpSession session){
			String hql = "FROM CgCarStructure cu where 1=1";
			// 参数
			List<Object> params = new ArrayList<Object>();
			if (text != null && text.length() > 0) {
				hql += " and (cu.name LIKE ?) ";
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cu.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
			return "structureManage/structureList";
		}
	
	
	
	/** 跳转新增车结构页面 */
	@RequestMapping("goto/addStructurePage")
	public String addStructurePage(){
		
		return "structureManage/addStructure";
	}
	
	/** 新增车结构 */
	@RequestMapping("add/addStructure")
	@ResponseBody
	public String addStructure(HttpServletRequest request,HttpSession session,CgCarStructure structure){
		try {
			CgCarStructure cbrands = (CgCarStructure) baseService.findEntity("from CgCarStructure where name = ?", new Object[]{structure.getName()});
			if (cbrands != null) {
				return "该结构已经存在";
			}
			if (structure.getName() == null || structure.getName() == "") {
				return "车结构不能为空";
			}
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加车辆结构");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆结构：" + structure.getName());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			structureService.addStructure(structure,adminLog);
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "添加失败";
	}
	
	/** 跳转修改车结构页面 */
	@RequestMapping("goto/updateStructurePage")
	public String updateStructurePage(HttpServletRequest request,String ccId){
		try {
			CgCarStructure structure = (CgCarStructure)baseService.findEntity(CgCarStructure.class, ccId);
			request.setAttribute("structure", structure);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "structureManage/updateStructure";
	}
	
	/** 修改车结构 */
	@RequestMapping("update/updateStructure")
	@ResponseBody
	public String updateStructure(HttpServletRequest request,HttpSession session,CgCarStructure structure,String ccId){
		try {
			CgCarStructure cbrands = (CgCarStructure) baseService.findEntity("from CgCarStructure where id = ?", new Object[]{ccId});
				if (cbrands.getName() == null || structure.getName() == "") {
					return "车结构不能为空";
				}
			
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(2);
					adminLog.setActionDesc("修改车结构");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改车结构：" + structure.getName());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				
				structureService.updateStructure(structure, adminLog,ccId);
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "0";
	}
	
	/** 删除车结构 */
	@RequestMapping("delete/deleteStructure")
	@ResponseBody
	public String deleteStructure(HttpSession session,HttpServletRequest request,String ccId,String id){
		try{
			if (ccId != null) {
				String[] idarrays = new Gson().fromJson(ccId, String[].class);
				for (String ids : idarrays) {
					if (ids != null) {
					CgAdmin admin = (CgAdmin) session.getAttribute("admin");
					CgAdminLog adminLog = null;
					if (admin != null) {
						adminLog = new CgAdminLog();
						adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
						adminLog.setActionIp(GetIpUtil.getIpAddr(request));
						adminLog.setActionType(3);
						adminLog.setActionDesc("删除车结构");
						adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车结构：" + ccId );
						adminLog.setAdminId(admin.getId());
						adminLog.setAdmin(admin);
						adminLog.setId("cal-" + UUID.randomUUID().toString());
					}
					structureService.deleteStructure(idarrays,adminLog);
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
					adminLog.setActionDesc("删除车结构");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车结构：" + ccId );
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				baseService.updateForHql("delete from CgCarStructure where id = ?", new Object[] { id });
				baseService.save(adminLog);
				return "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}
	
	
}
