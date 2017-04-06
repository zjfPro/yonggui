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

import com.cg.cgcheck.CgCheckException;
import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffManageModel;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.entity.CgStaffManageRoleContainerMapping;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAuthorityManagementService;
import com.cg.core.service.IBaseService;

/**
 * 权限管理控制器
 * 
 */
@Controller
@RequestMapping("control/authority")
public class AuthorityManagementController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IAuthorityManagementService authorityManagementService;
	
	
	// 模块列表
	@RequestMapping("select/cgStaffManageModelList")
	public String cgStaffManageModelList(HttpServletRequest request,PageUtil<CgStaffManageModel> pageUtil) {
		
		String hql = "FROM CgStaffManageModel where 1 = ? order by id desc";
		List<Object> parmas = new ArrayList<Object>();
		parmas.add(1);
		
		PageUtil<CgStaffManageModel> pageUtils = baseService.find(hql, parmas,pageUtil);

		request.setAttribute("pageUtil", pageUtils);

		return "authorityManagement/cgStaffManageModelList";
	}
	
	//跳转增加模块页面
	@RequestMapping("goto/addCcgStaffManageModel")
	public String addCcgStaffManageModelPage() {
		return "authorityManagement/addCcgStaffManageModel";

	}
	
	//添加模块
	@RequestMapping("add/gotoAddCcgStaffManageModel")
	@ResponseBody
	public String gotoAddCcgStaffManageModel(HttpServletRequest request,HttpSession session,CgStaffManageModel csmm,String modelTypeData) {
		
		
		try {
			if(modelTypeData!=null&&!modelTypeData.trim().equals("")){
				csmm.setFatId(Integer.valueOf(modelTypeData));
			}else{
				csmm.setUrl("");
				csmm.setFatId(null);
			}
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加模块");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加模块：" + csmm.getText()+":"+csmm.getUrl());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			
			baseService.saveByAdmin(csmm, adminLog);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "添加发生错误";
		}
		
	}
	
	//修改模块
	@RequestMapping("update/gotoUpdateCcgStaffManageModel")
	@ResponseBody
	public String gotoUpdateCcgStaffManageModel(HttpServletRequest request,CgStaffManageModel csmm,String modelTypeData,String modelID) {
		
		try {
			CgStaffManageModel findCsmm = (CgStaffManageModel) baseService.findEntity(CgStaffManageModel.class, Integer.valueOf(modelID));
			
			if(findCsmm.getUrl().equals("")&&findCsmm.getFatId()==null&&modelTypeData!=null&&!modelTypeData.trim().equals("")){
				return "父类不能修改成子类";
			}else if(modelTypeData!=null&&!modelTypeData.trim().equals("")){
				findCsmm.setFatId(Integer.valueOf(modelTypeData));
				findCsmm.setText(csmm.getText());
				findCsmm.setUrl(csmm.getUrl());
				findCsmm.setType(csmm.getType());
			}else{
				findCsmm.setText(csmm.getText());
				findCsmm.setUrl("");
				findCsmm.setFatId(null);
				findCsmm.setType(csmm.getType());
			}
			baseService.update(findCsmm);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "修改发生错误";
		}
		
	}
	
	//删除模块
	@RequestMapping("delete/deleteCcgStaffManageModel")
	@ResponseBody
	public String deleteCcgStaffManageModel(HttpServletRequest request,String strs) {
		
		String ids = strs.substring(0, strs.length()-1);
		try {
			authorityManagementService.deleteModel(ids);
			return "1";
		}catch (CgCheckException e) {
			e.printStackTrace();
			return "父模块还含有子模块,不能删除";
		}catch (Exception e) {
			e.printStackTrace();
			return "删除发生错误";
		}
			
	}
	//获取父模块
	@RequestMapping("select/getParpenModel")
	@ResponseBody
	public String getParpenModel() {
		String hql = "FROM CgStaffManageModel where (url is null or url = '') and (fatId is null or fatId='') order by id";
		List<CgStaffManageModel> cgStaffManageModelList;
		JSONObject jsonObject = new JSONObject();
		try {
			cgStaffManageModelList = baseService.find(hql);
			if(cgStaffManageModelList.size()>0){
				jsonObject.put("cgStaffManageModelList", cgStaffManageModelList);
				return jsonObject.toString();
			}else{
				return new GsonUtil().toJson("无父类");
			}
		} catch (Exception e) {
			return new GsonUtil().toJson("服务器获取数据错误");
		}
		
	}
	

	
	//模块信息详细页面
	@RequestMapping("select/modelDetail")
	public String modelDetail(HttpServletRequest request,String modelid){
		try {
			CgStaffManageModel cg = (CgStaffManageModel) baseService.findEntity(CgStaffManageModel.class, Integer.valueOf(modelid));
			request.setAttribute("model", cg);
			
			String hql = "FROM CgStaffManageModel where (url is null or url = '') and (fatId is null or fatId='') order by id";
			List<CgStaffManageModel> cgStaffManageModelList = baseService.find(hql);
			request.setAttribute("modelPar", cgStaffManageModelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "authorityManagement/modelDetail";
	}
	//模块树形展示
	@RequestMapping("select/showModel")
	public String showModel(){
		return "authorityManagement/showModel";
	}
	
}
