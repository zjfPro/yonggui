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
import com.cg.common.entity.CgInvestor;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/investor")
public class InvestorManagerController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/investorList")
	public String investorList(HttpServletRequest request,
			PageUtil<CgInvestor> pageUtil, String text, Integer type) {
		String hql = "from CgInvestor o where 1=1";
		List<Object> params = new ArrayList<Object>();
		if (!isEmpty(text)) {
			hql += " and (o.name LIKE ? or o.idcard LIKE ? or o.phone LIKE ?) ";
			params.add("%" + text + "%");
			params.add("%" + text + "%");
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}

		// 参数
		hql += " order by o.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "investorManage/investorList";
	}

	/**
	 * 跳转新增投资人
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addInvestor")
	public String goto_addInvestor(HttpServletRequest request,
			HttpSession session) throws Exception {
		return "investorManage/addInvestor";
	}

	/**
	 * 跳转修改投资人
	 * 
	 * @return
	 */
	@RequestMapping("goto/editInvestorView")
	public String editInvestorView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgInvestor investor = (CgInvestor) baseService.findEntity(CgInvestor.class, id);
			request.setAttribute("investor", investor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "investorManage/addInvestor";
	}


	/**
	 * 新增/编辑投资人
	 * 
	 * @return
	 */
	@RequestMapping("add/addInvestor")
	@ResponseBody
	public String addInvestor(HttpServletRequest request,HttpSession session, CgInvestor investor,
			@RequestParam("file") MultipartFile file) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			
			if(isEmpty(investor.getId())){
				String id = "ci-" + UUID.randomUUID();
				investor.setId(id);
				String url = ImageController.saveFile(session, file, "investorPictrue/"
						+ id + ".jpg");
				if (url != null) {
					investor.setPicture("image/getimg.app?url="
							+ url.replaceAll("\\\\", "/"));
				}
				String password = investor.getPassword();
				investor.setPassword(CustomMixedEncryptionUtil.encrypt(password,
						password.substring(0, 6)));
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加投资人");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，添加投资人：" + investor.getName() + ":"
							+ investor.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				baseService.save(investor);
			}else{
				CgInvestor fInvestor = (CgInvestor) baseService.findEntity(CgInvestor.class, investor.getId());
				if (file != null && file.getSize() > 0) {
					String url = ImageController.saveFile(session, file, "investorPictrue/"
							+ investor.getId() + ".jpg");
					if (url != null) {
						fInvestor.setPicture("image/getimg.app?url="
								+ url.replaceAll("\\\\", "/"));
					}
				}
				fInvestor.setName(investor.getName());
				fInvestor.setIdcard(investor.getIdcard());
				fInvestor.setAccount(investor.getAccount());
				fInvestor.setPhone(investor.getPhone());
				baseService.update(fInvestor);
				
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改投资人");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，修改投资人：" + fInvestor.getName() + ":"
							+ investor.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/investor/goto/addInvestor";
		return "1";
	}

	/**
	 * 删除投资人
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteInvestor")
	@ResponseBody
	public String deleteInvestor(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgInvestor investor = (CgInvestor) baseService.findEntity(CgInvestor.class, id);
						if (investor != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除投资人");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除投资人："
									+ investor.getName() + ":"
									+ investor.getId());
							adminLog.setAdminId(admin.getId());
							adminLog.setAdmin(admin);
							adminLog.setId("cal-"
									+ UUID.randomUUID().toString());
							baseService.deleteByAdmin(investor, adminLog);
							ImageController.deleteObjImage(investor,session);
						}
					}
				}
			} else {
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgInvestor investor = (CgInvestor) baseService.findEntity(CgInvestor.class, id);
						if (investor != null) {
							baseService.delete(investor);
							ImageController.deleteObjImage(investor,session);
						}
					}
				}
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

	/** 校验账号 */
	@RequestMapping("check/checkAccount")
	@ResponseBody
	public String checkAccount(String account, HttpServletRequest request) throws Exception {
		CgInvestor fuser = (CgInvestor) baseService.findEntity("from CgInvestor where account=? ", new Object[]{account});
		if(fuser!=null){
			return "1";
		}else{
			return "ok";
		}
		
	}
	
	@RequestMapping("update/updatePassWord")
	@ResponseBody
	public String updatePassWord(String id,String password, HttpServletRequest request) throws Exception {
		CgInvestor fuser = (CgInvestor) baseService.findEntity(CgInvestor.class,id);
		if(fuser!=null){
			fuser.setPassword(CustomMixedEncryptionUtil.encrypt(password,
					password.substring(0, 6)));
			baseService.update(fuser);
			return "ok";
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
