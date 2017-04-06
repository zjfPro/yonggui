package com.cg.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffManageModel;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.entity.CgStaffManageRoleContainerMapping;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAuthorityManagementService;
import com.cg.core.service.IBaseService;
@Controller
@RequestMapping("control/roleContainer")
public class RoleContainerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IAuthorityManagementService authorityManagementService;
	
	// 角色列表
	@RequestMapping("select/csmrcList")
	public String csmrcList(HttpServletRequest request,PageUtil<CgStaffManageModel> pageUtil,String searchText) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgStaffManageRoleContainer cr";
		List<Object> parmas = null;
		
		if(loginStaff!=null){
			if(isEmpty(searchText)){
				parmas = new ArrayList<Object>();
				hql += " where shopFrontId=? ";
				parmas.add(loginStaff.getShopFrontId());
			}else{
				parmas = new ArrayList<Object>();
				hql += " where (cr.name like ? or cr.cgShopFront.name like ?) and shopFrontId=? ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add(loginStaff.getShopFrontId());
			}
		}else{
			if(isEmpty(searchText)){
				
			}else{
				parmas = new ArrayList<Object>();
				hql += " where (cr.name like ? or cr.cgShopFront.name like ?) ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
			}
		}
		
		hql +=" order by uid desc";
		PageUtil<CgStaffManageRoleContainer> pageUtils = baseService.find(hql, parmas,pageUtil);

		request.setAttribute("pageUtil", pageUtils);
		request.setAttribute("searchText", searchText);
		return "authorityManagement/csmrcList";
	}
	
	//跳转增加角色页面
	@RequestMapping("goto/addJuese")
	public String addJuese() {
		return "authorityManagement/addjuese";
	}
	
	//增加角色页面中获取所有模块
	@RequestMapping("select/getAllModel")
	@ResponseBody
	public String getAllModel() {
		
		String hql = "FROM CgStaffManageModel order by id";
		List<CgStaffManageModel> cgStaffManageModelList;
		try {
			cgStaffManageModelList = baseService.find(hql);
			if(cgStaffManageModelList.size()>0){
				String data =new GsonUtil().toJson(cgStaffManageModelList);
				return data;
			}else{
				return new GsonUtil().toJson("无父类");
			}
		} catch (Exception e) {
			return new GsonUtil().toJson("服务器获取数据错误");
		}
		
	}
	
	//添加角色
	@RequestMapping("add/gotoAddCsmrc")
	@ResponseBody
	public String gotoAddCsmrc(HttpServletRequest request,String nodes,String name,String shopFrontId){
		
		if(nodes==null){
			return "请勾选模块";
		}
		if(nodes.trim().equals("")){
			return "请勾选模块";
		}
		if(name==null){
			return "请输入角色名称";
		}
		if(name.trim().equals("")){
			return "请输入角色名称";
		}
		if(shopFrontId==null){
			return "请选择门店";
		}
		if(shopFrontId.trim().equals("")){
			return "请选择门店";
		}
		
		String hql = "from CgStaffManageRoleContainer where name = ? and shopFrontId = ? ";
		List find = baseService.find(hql, new Object[]{name,shopFrontId});
		if(find.size()>0){
			return "该角色已存在,请勿重复添加";
		}
		
		String ids = nodes.substring(0, nodes.length()-1);
		try {
			authorityManagementService.addCgStaffManageRoleContainerMapping(ids, name,shopFrontId);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "添加失败";
		}
		
	}
	
	//角色信息详细页面
	@RequestMapping("select/csmrcDetail")
	public String csmrcDetail(HttpServletRequest request,String jsid){
		try {
			CgStaffManageRoleContainer cg = (CgStaffManageRoleContainer) baseService.findEntity(CgStaffManageRoleContainer.class, jsid);
			request.setAttribute("csmrc", cg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "authorityManagement/editjuese";
	}
	
	//获取角色拥有的模块
	@RequestMapping("select/getModelOfRole")
	@ResponseBody
	public String getModelOfRole(HttpServletRequest request,String csmrcID) {
		String hql = "FROM CgStaffManageRoleContainerMapping where staffManageRoleContainerId = ? order by id";
		List<Object> parmas = new ArrayList<Object>();
		parmas.add(csmrcID);
		List<CgStaffManageRoleContainerMapping> mapings;
		JSONObject jsonObject = new JSONObject();
		try {
			mapings = baseService.find(hql,parmas);
			if(mapings.size()>0){
				jsonObject.put("mapings", mapings);
				return jsonObject.toString();
			}else{
				return new GsonUtil().toJson("此角色暂无模块");
			}
		} catch (Exception e) {
			return new GsonUtil().toJson("服务器获取数据错误");
		}
		
	}
	
	//修改角色
	@RequestMapping("update/gotoUpdateCsmrc")
	@ResponseBody
	public String gotoUpdateCsmrc(HttpServletRequest request,String nodes,String name,String csmrcID,String shopFrontId){
		
		if(nodes==null){
			return "请勾选模块";
		}
		if(nodes.trim().equals("")){
			return "请勾选模块";
		}
		if(name==null){
			return "请输入角色名称";
		}
		if(name.trim().equals("")){
			return "请输入角色名称";
		}
		if(shopFrontId==null||shopFrontId.trim().equals("")){
			return "请选择门店";
		}
		
		String hql = "from CgStaffManageRoleContainer where name = ? and shopFrontId = ? ";
		List find = baseService.find(hql, new Object[]{name,shopFrontId});
		if(find.size()>0){
			return "该角色已存在,请勿重复修改添加";
		}
		
		String ids = nodes.substring(0, nodes.length()-1);
		try {
			authorityManagementService.updateCgStaffManageRoleContainerMapping(ids, name, csmrcID,shopFrontId);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "修改失败";
		}
		
	}
	
	//删除角色
	@RequestMapping("delete/deleteCsmrc")
	@ResponseBody
	public String deleteCsmrc(HttpServletRequest request,HttpSession session,String strs) {
		String ids = "";
		if(strs.indexOf(",")>0){
			ids= strs.substring(0, strs.length()-1);
		}else{
			ids=strs;
		}
		 
		try {
			authorityManagementService.deleteRole(request, session, ids);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "删除失败";
		}
	}
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
