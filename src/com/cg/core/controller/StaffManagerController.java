package com.cg.core.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.cgcheck.CgCheckException;
import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgDept;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.entity.CgStaffManageRoleContainerMapping;
import com.cg.common.entity.CgStaffModelMapping;
import com.cg.common.entity.CgStaffPosition;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IStaffManagerService;

@Controller
@RequestMapping("control/staffManager")
public class StaffManagerController {
	
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IStaffManagerService staffManagerService;
	
	
	// 员工信息列表
	@RequestMapping("select/staffList")
	public String staffList(HttpServletRequest request,
			PageUtil<CgStaff> pageUtil,String searchText) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgStaff cs ";
		List<Object> parmas = null;
		
		if(loginStaff!=null){
			if(isEmpty(searchText)){
				parmas = new ArrayList<Object>();
				hql += " where shopFrontId=? ";
				parmas.add(loginStaff.getShopFrontId());
			}else{
				parmas = new ArrayList<Object>();
				hql += " where (cs.name like ? or cs.number like ? or cs.cgShopFront.name like ?) and shopFrontId=? ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add(loginStaff.getShopFrontId());
			}
		}else{
			if(isEmpty(searchText)){
				
			}else{
				parmas = new ArrayList<Object>();
				hql += " where (cs.name like ? or cs.number like ? or cs.cgShopFront.name like ?) ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
			}
		}
		
		hql +=" order by uid desc";

		PageUtil<CgStaff> pageUtils = baseService.find(hql, parmas, pageUtil);

		request.setAttribute("pageUtil", pageUtils);
		request.setAttribute("searchText", searchText);
		return "staffManage/staffList";
	}

	// 跳转新增页面
	@RequestMapping("goto/addStaffPage")
	public String addStaffPage() {
		return "staffManage/addStaffB";
	}

	// 新增员工
	@RequestMapping("add/addStaff")
	@ResponseBody
	public String addUser(HttpServletRequest request, HttpSession session,
			CgStaff staff, String theBirthDate, String theEntryTime,
			String theQuitTime,String stafModel,
//			String models,
			@RequestParam("file") MultipartFile file) {
		//角色id：stafModel,模块id：models
		
		try {
			
			staff.setId("cs-" + UUID.randomUUID().toString());
			if (file != null && file.getSize() > 0) {
				String url = ImageController.saveFile(session, file, "staff/"
						+ staff.getId() + ".jpg");
				if (url != null) {
					staff.setLogo("image/getimg.app?url="
							+ url.replaceAll("\\\\", "/"));
				}
			}
			if(isEmpty(staff.getName())){
				return "请填写：员工姓名";
			}
			if(isEmpty(staff.getShopFrontId())){
				return "请填写：所属门店";		
			}
			if(isEmpty(staff.getNumber())){
				return "请填写：编号";
			}
			if(isEmpty(staff.getStaffPositionId())){
				return "请填写：职位";
			}
			if(isEmpty(staff.getDecpId())){
				return "请填写：部门";
			}
			if(isEmpty(staff.getAccount())){
				return "请填写：帐号";
			}
			if(isEmpty(staff.getPassword())){
				return "请填写：密码";
			}
			if(staff.getPassword().length()<6){
				return "密码请至少输入6位";
			}
			if(!staff.getPassword().matches("[A-Za-z0-9]+")){
				return "密码请不要输入中文,只能是数字和字母组合，区分大小写";
			}
//			if(isEmpty(models)){
//				return "请给员工赋予系统权限";
//			}
			
//			staff.setName(new String(staff.getName().getBytes("ISO-8859-1"),"UTF-8"));
//			staff.setRoleName(new String(staff.getRoleName().getBytes("ISO-8859-1"),"UTF-8"));
//			staff.setAddress(new String(staff.getAddress().getBytes("ISO-8859-1"),"UTF-8"));
//			staff.setRemarks(new String(staff.getRemarks().getBytes("ISO-8859-1"),"UTF-8"));
			
			String ids="";
			
			CgStaffPosition cp = (CgStaffPosition) baseService.findEntity(CgStaffPosition.class,staff.getStaffPositionId());
			
			if(cp.getStaffManageRoleContainer()!=null){
				List<CgStaffManageRoleContainerMapping> csmrcms
				=baseService.find("from CgStaffManageRoleContainerMapping where staffManageRoleContainerId=?", cp.getStaffManageRoleContainerId());
				for(CgStaffManageRoleContainerMapping aCsmrcm:csmrcms){
					ids+=aCsmrcm.getStaffManageModelid()+",";
				}
			}else{
				CgDept cd =(CgDept)baseService.findEntity(CgDept.class, staff.getDecpId());
				if(cd.getStaffManageRoleContainer()!=null){
					List<CgStaffManageRoleContainerMapping> csmrcms
					=baseService.find("from CgStaffManageRoleContainerMapping where staffManageRoleContainerId=?", cd.getStaffManageRoleContainerId());
					for(CgStaffManageRoleContainerMapping aCsmrcm:csmrcms){
						ids+=aCsmrcm.getStaffManageModelid()+",";
					}
				}
			}
//			String ids = models.substring(0, models.length()-1);
			String password = staff.getPassword();
			
			Object obj = staffManagerService.checkStaffLogin(staff.getAccount(), password);
			if (obj instanceof String) {
				
			}else{
				return "帐号已存在,添加失败";
			}
			
			staff.setPassword(CustomMixedEncryptionUtil.encrypt(password,
					password.substring(0, 6)));
			
			staff.setAddtime(new Timestamp(System.currentTimeMillis()));
			staff.setStatus(1);
			if (!"".equals(theBirthDate)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthdayDate = sdf.parse(theBirthDate);
				staff.setBirthDate(birthdayDate);
			}
			if (!"".equals(theEntryTime)) {
				theEntryTime += " 00:00:000";
				Timestamp entryTime = Timestamp.valueOf(theEntryTime);
				staff.setEntryTime(entryTime);
			}
			if (!"".equals(theQuitTime)) {
				theQuitTime += " 00:00:000";
				Timestamp quitTime = Timestamp.valueOf(theQuitTime);
				staff.setQuitTime(quitTime);
			}
			

			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加员工");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，添加员工：" + staff.getName() + ":" + staff.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			staffManagerService.addStaff(staff, adminLog, ids);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "增加失败";
		}

	}

	/**
	 * 搜索员工 根据编号
	 * 查询 text=? 返回字符串数组,下标0是该员工的id，下标1是该员工的名字,下表2是员工编号 或 错误信息
	 */
	@RequestMapping("select/searchStaffByNumber")
	@ResponseBody
	public String searchStaffByNumber(HttpServletRequest request, String text) {
		try {
			Object obj = baseService
					.findEntity(
							"select s.id,s.name,s.number from CgStaff s where  s.number like ?   ",
							new Object[] {  text   });
			if (obj==null) {
				return "没有结果";
			}
			return new GsonUtil()
					.toJson(obj);
		} catch (Exception e) {
		}
		return "没有结果";
	}
	
	//获取所有角色
	@RequestMapping("select/getAllRoleContainer")
	@ResponseBody
	public String getAllRoleContainer(HttpSession session) {
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgStaffManageRoleContainer ";
		List<CgStaffManageRoleContainer> cgStaffManageRoleContainerList;
		JSONObject jsonObject = new JSONObject();
		try {
			
			if (loginStaff!=null) {
				cgStaffManageRoleContainerList = baseService.find(hql+" where shopFrontId=? ",new Object[]{loginStaff.getShopFrontId()});
				if(cgStaffManageRoleContainerList.size()>0){
					jsonObject.put("csmrcList", cgStaffManageRoleContainerList);
					return jsonObject.toString();
				}else{
					return new GsonUtil().toJson("暂无角色,请先移步角色模块添加角色");
				}
			}else{
				
			cgStaffManageRoleContainerList = baseService.find(hql);
			if(cgStaffManageRoleContainerList.size()>0){
				jsonObject.put("csmrcList", cgStaffManageRoleContainerList);
				return jsonObject.toString();
			}else{
				return new GsonUtil().toJson("暂无角色,请先移步角色模块添加角色");
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new GsonUtil().toJson("服务器获取角色数据错误");
		}
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
			e.printStackTrace();
			return new GsonUtil().toJson("服务器获取数据错误");
		}
		
	}
	
	
	//员工信息详细页面
	@RequestMapping("select/cgStaffDetail")
	public String cgStaffDetail(HttpServletRequest request,String staffid){
		try {
			CgStaff cg = (CgStaff) baseService.findEntity(CgStaff.class, staffid);
			request.setAttribute("cgStaffDetail", cg);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "staffManage/editStaff";
	}
	
	//获取员工拥有的模块
	@RequestMapping("select/getModelOfStaff")
	@ResponseBody
	public String getModelOfStaff(HttpServletRequest request,String theStaffID) {
		String hql = "FROM CgStaffModelMapping where staffId = ? ";
		List<Object> parmas = new ArrayList<Object>();
		parmas.add(theStaffID);
		List<CgStaffModelMapping> mapings;
		JSONObject jsonObject = new JSONObject();
		try {
			mapings = baseService.find(hql,parmas);
			if(mapings.size()>0){
				jsonObject.put("mapingsofStaff", mapings);
				return jsonObject.toString();
			}else{
				return new GsonUtil().toJson("此员工暂无权限");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new GsonUtil().toJson("服务器获取数据错误");
		}
		
	}
	
	//员工信息修改
//	@RequestMapping(value = "update/gotoUpdateStaff", method = RequestMethod.POST)
	@RequestMapping("update/gotoUpdateStaff")
	@ResponseBody
	public String gotoUpdateStaff(HttpServletRequest request,HttpSession session,CgStaff staff, String theBirthDate, String theEntryTime,
			String theQuitTime,String stafModel,String models,String theStaffID,@RequestParam("file") MultipartFile file) {
		//角色id：stafModel,模块id：models 
		try{
			
			CgStaff findStaff = (CgStaff) baseService.findEntity(CgStaff.class, theStaffID);
			
			if (file != null && file.getSize() > 0) {
				ImageController.deleteObjImage(findStaff, session);
				String url = ImageController.saveFile(session, file, "staff/"
						+ findStaff.getId() + ".jpg");
				if (url != null) {
					findStaff.setLogo("image/getimg.app?url="
							+ url.replaceAll("\\\\", "/"));
				}
			}
			
			if(isEmpty(staff.getName())){
				return "请填写：员工姓名";
			}
			if(isEmpty(staff.getShopFrontId())){
				return "请填写：所属门店";		
			}
			if(isEmpty(staff.getNumber())){
				return "请填写：编号";
			}
			if(isEmpty(staff.getStaffPositionId())){
				return "请填写：职位";
			}
			if(isEmpty(staff.getDecpId())){
				return "请填写：部门";
			}
			
			if(!isEmpty(staff.getPassword())){
				if(staff.getPassword().length()<6){
					return "密码请至少输入6位";
				}
				
				if(!staff.getPassword().matches("[A-Za-z0-9]+")){
					return "密码请不要输入中文,只能是数字和字母组合，区分大小写";
				}
			}
			
			
			if(isEmpty(models)){
				return "请给员工赋予系统权限";
			}
			
			if (!"".equals(theBirthDate)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthdayDate = sdf.parse(theBirthDate);
				staff.setBirthDate(birthdayDate);
			}
			if (!"".equals(theEntryTime)) {
				theEntryTime += " 00:00:000";
				Timestamp entryTime = Timestamp.valueOf(theEntryTime);
				staff.setEntryTime(entryTime);
			}
			if (!"".equals(theQuitTime)) {
				theQuitTime += " 00:00:000";
				Timestamp quitTime = Timestamp.valueOf(theQuitTime);
				staff.setQuitTime(quitTime);
			}
			
			String ids = models.substring(0, models.length()-1);
			String returnInfo = "1";
			if(!isEmpty(staff.getPassword())){
				String password = staff.getPassword();
				staff.setPassword(CustomMixedEncryptionUtil.encrypt(password,password.substring(0, 6)));
				findStaff.setPassword(staff.getPassword());
				returnInfo="2";
			}
			
			findStaff.setAddress(staff.getAddress());
			findStaff.setBirthDate(staff.getBirthDate());
			findStaff.setDecpId(staff.getDecpId());
			findStaff.setEntryTime(staff.getEntryTime());
			findStaff.setIdcard(staff.getIdcard());
			findStaff.setName(staff.getName());
			findStaff.setNumber(staff.getNumber());
			findStaff.setPhone(staff.getPhone());
			findStaff.setQuitTime(staff.getQuitTime());
			findStaff.setRemarks(staff.getRemarks());
			findStaff.setRoleName(staff.getRoleName());
			findStaff.setShopFrontId(staff.getShopFrontId());
			findStaff.setStatus(staff.getStatus());
			findStaff.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			findStaff.setStaffPositionId(staff.getStaffPositionId());
			findStaff.setSparePhone(staff.getSparePhone());
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改员工");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改员工：" + findStaff.getName()+":"+findStaff.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			
			staffManagerService.updateStaff(findStaff, adminLog, ids);
			
			return returnInfo;
		}catch (Exception e) {
			e.printStackTrace();
			return "服务器异常,修改失败";
		}
		
	}
	
	//删除员工
	@RequestMapping("delete/deleteStaff")
	@ResponseBody
	public String deleteStaff(HttpServletRequest request,HttpSession session,String strs) {
		
		String ids = "";
		if(strs.indexOf(",")>0){
			ids= strs.substring(0, strs.length()-1);
		}else{
			ids=strs;
		}
		try {
			staffManagerService.deleteStaff(ids,session,request);
			return "1";
		}catch(CgCheckException c){ 
			c.printStackTrace();
			return "不能删除自己";
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
