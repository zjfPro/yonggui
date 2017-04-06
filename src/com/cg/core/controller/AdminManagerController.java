package com.cg.core.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgAdminManageModel;
import com.cg.common.entity.CgAdminManageRole;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.MD5Util;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminManagerService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

 

/**
 * 管理员管理控制器
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("control/amc")
public class AdminManagerController {

	@Resource(name = "adminManagerService")
	private IAdminManagerService adminManagerService;
	@Autowired
	private IBaseService baseService;

	@RequestMapping("adminlist")
	public String getAdminList(Integer pageNo, Integer rows,
			HttpServletRequest request) {
		PageUtil<CgAdmin> pageUtil = new PageUtil<CgAdmin>();
		pageUtil.setPage(pageNo == null || pageNo == 0 ? 1 : pageNo);
		pageUtil.setRows(rows == null || rows == 0 ? 20 : rows);

		String findname = request.getParameter("findname");
		try {
			pageUtil = this.adminManagerService
					.getAdminList(pageUtil, findname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("pageUtil", pageUtil);
		request.setAttribute("findname", findname);
		return "adminManager/adminlist";
	}

	@RequestMapping("select/adminloglist")
	public String getAdminLogList(HttpServletRequest request,PageUtil<CgAdminLog>pageUtil,String text) {
		String hql = "from CgAdminLog cu where 1=1";
		// 参数
		List<Object> params = new ArrayList<Object>();
		if (text != null && text.length() > 0) {
			hql += " and (cu.admin.nickname LIKE ?) ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		hql += " order by cu.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "adminManager/adminloglist";
	}

	/** 删除操作日志 */
	@RequestMapping("delete/deleteAdminlog")
	@ResponseBody
	public String deleteAdminlog(HttpServletRequest request,
			HttpSession session, String ccId) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ccId, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgAdminLog adminLog = (CgAdminLog) baseService
								.findEntity(CgAdminLog.class, id);
							baseService.delete(adminLog);
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
	
	
	
	@RequestMapping("addadmin")
	public String addadmin(HttpServletResponse response,
			HttpServletRequest request, CgAdmin admin) {
		response.setContentType("text/json;charset=utf-8");
		try {

			if (admin.getAccount() == null || admin.getAccount().length() < 1) {
				printWriter(
						"<script>alert('账号不可为空！');history.back();</script>",
						response);
			}

			if (admin.getPassword() == null) {
				printWriter(
						"<script>alert('密码不可为空！');history.back();</script>",
						response);
			}

			if (admin.getPassword().length() < 6) {
				printWriter(
						"<script>alert('密码不能小于六位！');history.back();</script>",
						response);
			}

			if (admin.getNickname() == null) {
				admin.setNickname(admin.getAccount());
			}

			// 加密密码
			admin.setPassword(MD5Util.string2MD5(CustomMixedEncryptionUtil
					.encrypt(admin.getPassword(), admin.getPassword()
							.substring(0, 6))));
				 
			admin.setId("ca-" + UUID.randomUUID());
			admin.setCreateDate(new Timestamp(System.currentTimeMillis()));
			admin.setStatus(0);
			baseService.save(admin);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:../amc/adminlist";
	}

	@RequestMapping("updateAdmin")
	public String updateAdmin(HttpServletResponse response,
			HttpServletRequest request, CgAdmin admin) {
		response.setContentType("text/json;charset=utf-8");
		try {
			CgAdmin cgAdmin = (CgAdmin) baseService.findEntity(CgAdmin.class,
					admin.getId());
			if (admin.getStatus() == 1 || admin.getStatus() == -1) {
				cgAdmin.setStatus(admin.getStatus());
				baseService.update(cgAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:../amc/adminlist";
	}

	@RequestMapping("gotoSetRole")
	public String gotoSetRole(HttpServletResponse response,
			HttpServletRequest request, CgAdmin admin) {
		response.setContentType("text/json;charset=utf-8");
		try {
			List<CgAdminManageModel> adminManageModel = baseService
					.find("from CgAdminManageModel order by uid asc");
			request.setAttribute("list", adminManageModel);
			
			List<CgAdminManageRole> adminManageRoles = baseService
					.find("from CgAdminManageRole where userId=?  ",new Object[]{admin.getId()});
			StringBuffer buffer = new StringBuffer();
			for (CgAdminManageRole cgAdminManageRole : adminManageRoles) {
				buffer.append(cgAdminManageRole.getAdminManageModel().getUrl());
			}
			request.setAttribute("myrole", buffer.toString());
			request.setAttribute("admin", admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "adminManager/setRole";
	}

	
	
	
	@RequestMapping("setRole")
	@ResponseBody
	public String setRole(HttpServletResponse response,
			HttpServletRequest request, CgAdmin admin,String[] roleIds) {
		response.setContentType("text/json;charset=utf-8");
		try {
			//清空该管理员原本的所有相关权限，然后重新设置
			if (adminManagerService.addAdminRole(admin.getId(),roleIds)) {
				return "1";
			}else{
				return "-1";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "1";
	}
	
	@RequestMapping("removeAdmin")
	public String removeAdmin(HttpServletResponse response,
			HttpServletRequest request, CgAdmin admin) {
		response.setContentType("text/json;charset=utf-8");
		try {
			adminManagerService.deleteAdmin(admin.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:../amc/adminlist";
	}

	@RequestMapping("gotoaddadmin")
	public String gotoaddadmin() {
		return "adminManager/addAdmin";
	}

	/*
	 * 输出页面
	 */
	public void printWriter(String msgs, HttpServletResponse response)
			throws Exception {
		// 指定输出内容类型和编码
		response.setContentType("text/html;charset=utf-8");
		// 获取输出流，然后使用
		PrintWriter out = response.getWriter();
		try {
			// 直接进行文本操作
			out.print(msgs);
			out.flush();
			out.close();
		} catch (Exception ex) {
			out.println(ex.toString());
		}
	}
	
	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param request
	 * @return -1旧密码不正确
	 */
	@RequestMapping("upwd")
	@ResponseBody
	public String updatePwd(String oldPwd,String newPwd,HttpServletRequest request){
		CgAdmin admin = (CgAdmin) request.getSession().getAttribute("user");
		
		try {
			admin = adminManagerService.getAdmin(admin.getId());
			//加密密码
			String en_old_pwd = MD5Util.string2MD5(CustomMixedEncryptionUtil.encrypt(oldPwd, oldPwd.substring(0, 6)));
			if(!admin.getPassword().equals(en_old_pwd)){
				//旧的密码不正确
				return "-1";
			}
			System.out.println(MD5Util.string2MD5(CustomMixedEncryptionUtil.encrypt(newPwd, newPwd.substring(0, 6))));
			admin.setPassword(MD5Util.string2MD5(CustomMixedEncryptionUtil.encrypt(newPwd, newPwd.substring(0, 6))));
			CgAdminLog adminLog = null;
			if(admin != null){
				adminLog  = new CgAdminLog();
				adminLog.setId("cal-"+UUID.randomUUID().toString());
				adminLog.setAdminId(admin.getId());
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(6);
				adminLog.setActionDesc("修改管理员密码");
				adminLog.setActionContent("管理员："+admin.getNickname()+"修改密码");
			}
			System.out.println(admin.getPassword());
			this.adminManagerService.updatePwd(admin, adminLog);
			request.getSession().invalidate();
			return "1";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "-2";
		} catch (Exception e) {
			e.printStackTrace();
			return "-2";
		}
	}
	
	@RequestMapping("gotoupwd")
	public String gotoUpwd(){
		return "systemManager/upwd";
	}
}
