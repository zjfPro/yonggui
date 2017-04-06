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
import com.cg.common.entity.CgDept;
import com.cg.common.entity.CgInvestor;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/dept")
public class DeptManagerController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/deptList")
	public String deptList(HttpServletRequest request,
			PageUtil<CgDept> pageUtil, String text) {
		String hql = " from CgDept o where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (!isEmpty(text)) {
			hql += " and o.shopFront.name LIKE ? ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		
		CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
		if(loginStaff!=null){
			hql+=" and o.shopFrontId=? ";
			params.add(loginStaff.getShopFrontId());
		}
		// 参数
		hql += " order by o.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "deptManage/deptList";
	}

	/**
	 * 跳转新增部门
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addDept")
	public String goto_addDept(HttpServletRequest request,
			HttpSession session) throws Exception {
		String hql = " from CgShopFront where 1=1 ";
		CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
		List<Object> params = new ArrayList<Object>();
		if(loginStaff!=null){//如果是员工登录，只显示员工所在的门店
			hql+=" and id=? ";
			params.add(loginStaff.getShopFrontId());
		}
		List<CgShopFront> shopFrontList = baseService.find(hql,params);
		request.setAttribute("shopFrontList", shopFrontList);
		return "deptManage/addDept";
	}

	/** 根据门店获取角色 */
	@RequestMapping("show/staffManageRoleContainer")
	@ResponseBody
	public String getStaffManageRoleContainerList(String id, HttpServletRequest request) throws Exception {
		String hql = "from CgStaffManageRoleContainer where shopFrontId = ?";
		List<CgStaffManageRoleContainer> list = baseService.find(hql, id);
		if(list.size()>0){
			return new GsonUtil().toJson(list);
		}else{
			return "0";
		}
		
	}
	
	/**
	 * 跳转修改部门
	 * 
	 * @return
	 */
	@RequestMapping("goto/editDeptView")
	public String editDeptView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgDept dept = (CgDept) baseService.findEntity(CgDept.class, id);
			request.setAttribute("dept", dept);
			String hql = " from CgShopFront where 1=1 ";
			CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
			List<Object> params = new ArrayList<Object>();
			if(loginStaff!=null){//如果是员工登录，只显示员工所在的门店
				hql+=" and id=? ";
				params.add(loginStaff.getShopFrontId());
			}
			List<CgShopFront> shopFrontList = baseService.find(hql,params);
			request.setAttribute("shopFrontList", shopFrontList);
			String hql2 = "from CgStaffManageRoleContainer where shopFrontId = ?";
			List<CgStaffManageRoleContainer> list = baseService.find(hql2, dept.getShopFrontId());
			request.setAttribute("staffManageRoleContainerList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "deptManage/editDept";
	}

	
	
	/**
	 * 新增/编辑部门
	 * 
	 * @return
	 */
	@RequestMapping("add/addDept")
	@ResponseBody
	public String addDept(HttpServletRequest request,HttpSession session, CgDept dept) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			if(isEmpty(dept.getId())){
				dept.setId("cd-" + UUID.randomUUID());
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加部门");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，添加部门：" + dept.getName() + ":"
							+ dept.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				baseService.save(dept);
			}else{
				CgDept fdept = (CgDept) baseService.findEntity(CgDept.class, dept.getId());
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改部门");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，修改部门：" + fdept.getName() + ":"
							+ dept.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				fdept.setName(dept.getName());
				fdept.setShopFrontId(dept.getShopFrontId());
				fdept.setStaffManageRoleContainerId(dept.getStaffManageRoleContainerId());
				baseService.update(fdept);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "1";
	}

	/**
	 * 删除部门
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete/deleteDept")
	@ResponseBody
	public String deleteDept(HttpServletRequest request,HttpSession session, String ids) throws Exception {
		CgAdmin admin = (CgAdmin) session.getAttribute("admin");
		String ss = "";
		if (admin != null) {
			// 管理员操作 要创建日志
			String[] idarrays = new Gson().fromJson(ids, String[].class);
			for (String id : idarrays) {
				List<CgStaff> staffList = baseService.find("from CgStaff where decpId = ?",id);
				if(staffList.size()>0){
					ss="no";
					break;
				}
				if (id != null&&staffList.size()==0) {
					CgDept dept = (CgDept) baseService.findEntity(CgDept.class, id);
					if (dept != null) {
						CgAdminLog adminLog = new CgAdminLog();
						adminLog.setActionDate(new Timestamp(System
								.currentTimeMillis()));
						adminLog.setActionIp(GetIpUtil.getIpAddr(request));
						adminLog.setActionType(1);
						adminLog.setActionDesc("删除部门");
						adminLog.setActionContent("管理员："
								+ admin.getNickname() + "，删除部门："
								+ dept.getName() + ":"
								+ dept.getId());
						adminLog.setAdminId(admin.getId());
						adminLog.setAdmin(admin);
						adminLog.setId("cal-"
								+ UUID.randomUUID().toString());
						baseService.deleteByAdmin(dept, adminLog);
					}
				}
			}
		} else {
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					List<CgStaff> staffList = baseService.find("from CgStaff where decpId = ?",id);
					if(staffList.size()>0){
						ss="no";
						break;
					}
					if (id != null&&staffList.size()==0) {
						CgDept dept = (CgDept) baseService.findEntity(CgDept.class, id);
						if (dept != null) {
							baseService.delete(dept);
						}
					}
				}
		}
		if(ss=="no"){
			return "0";
		}else{
			return "1";
		}
		
	}

	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
