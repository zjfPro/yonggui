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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgDept;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.entity.CgStaffPosition;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/position")
public class PositionManagerController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/positionList")
	public String positionList(HttpServletRequest request,
			PageUtil<CgStaffPosition> pageUtil, String text, Integer type) {
		String hql = " from CgStaffPosition o where 1=1 ";
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
		//hql += " order by o.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "positionManage/positionList";
	}

	/**
	 * 跳转新增部门
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addPosition")
	public String goto_addPosition(HttpServletRequest request,
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
		
		return "positionManage/addPosition";
	}

	/**
	 * 跳转修改部门
	 * 
	 * @return
	 */
	@RequestMapping("goto/editPositionView")
	public String editPositionView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgStaffPosition position = (CgStaffPosition) baseService.findEntity(CgStaffPosition.class, id);
			request.setAttribute("position", position);
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
			List<CgStaffManageRoleContainer> list = baseService.find(hql2, position.getShopFrontId());
			request.setAttribute("staffManageRoleContainerList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "positionManage/editPosition";
	}


	/**
	 * 新增/编辑部门
	 * 
	 * @return
	 */
	@RequestMapping("add/addPosition")
	@ResponseBody
	public String addPosition(HttpServletRequest request,HttpSession session, CgStaffPosition position) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			if(isEmpty(position.getId())){
				position.setId("csp-" + UUID.randomUUID());
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加职位");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，添加职位：" + position.getName() + ":"
							+ position.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				baseService.save(position);
			}else{
				CgStaffPosition fposition = (CgStaffPosition) baseService.findEntity(CgStaffPosition.class, position.getId());
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改职位");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，修改部门：" + fposition.getName() + ":"
							+ fposition.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				fposition.setName(position.getName());
				fposition.setShopFrontId(position.getShopFrontId());
				fposition.setType(position.getType());
				fposition.setStaffManageRoleContainerId(position.getStaffManageRoleContainerId());
				baseService.update(fposition);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/position/select/positionList";
		return "1";
	}

	/**
	 * 删除部门
	 * 
	 * @return
	 */
	@RequestMapping("delete/deletePosition")
	@ResponseBody
	public String deletePosition(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						//CgDept dept = (CgDept) baseService.findEntity(CgDept.class, id);
						CgStaffPosition fposition = (CgStaffPosition) baseService.findEntity(CgStaffPosition.class, id);
						if (fposition != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除职位");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除职位："
									+ fposition.getName() + ":"
									+ fposition.getId());
							adminLog.setAdminId(admin.getId());
							adminLog.setAdmin(admin);
							adminLog.setId("cal-"
									+ UUID.randomUUID().toString());
							baseService.deleteByAdmin(fposition, adminLog);
						}
					}
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 没有权限
				return "没有操作权限!";
			} catch (Exception e2) {
				// 身份识别失败！ 跳转重新登录
				return "没有操作权限!";
			}
		}
		return "1";
	}

	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
