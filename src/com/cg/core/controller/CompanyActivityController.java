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
import com.cg.common.entity.CgNotice;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/activity")
public class CompanyActivityController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/companyActivityList")
	public String companyActivityList(HttpServletRequest request,
			PageUtil<CgNotice> pageUtil, String text, Integer type) {
		String hql = "from CgNotice where 1=1 and type = 2";
		List<Object> params = new ArrayList<Object>();
		if (!isEmpty(text)) {
			hql += " and (title LIKE ? or noticeContent LIKE ? or noticeContentBrief LIKE ?) ";
			params.add("%" + text + "%");
			params.add("%" + text + "%");
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}

		// 参数
		hql += " order by uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "companyActivity/companyActivityList";
	}

	/**
	 * 跳转新增新闻资讯
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addCompanyActivity")
	public String goto_addCompanyActivity(HttpServletRequest request,
			HttpSession session) throws Exception {
		return "companyActivity/addCompanyActivity";
	}

	/**
	 * 跳转编辑新闻资讯
	 * 
	 * @return
	 */
	@RequestMapping("goto/editCompanyActivityView")
	public String editCompanyActivityView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgNotice notice = (CgNotice) baseService.findEntity(CgNotice.class, id);
			request.setAttribute("notice", notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "companyActivity/editCompanyActivity";
	}


	/**
	 * 新增新闻资讯
	 * 
	 * @return
	 */
	@RequestMapping("add/addCompanyActivity")
	@ResponseBody
	public String addCompanyActivity(HttpServletRequest request,HttpSession session, CgNotice notice,
			@RequestParam("file") ArrayList<MultipartFile> file) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			
			String id = "cn-" + UUID.randomUUID();
			notice.setId(id);
			notice.setAddtime(new Timestamp(System.currentTimeMillis()));
			notice.setAdminId(admin.getId());
			notice.setType(2);
			if (!file.isEmpty()) {
				StringBuilder urls = new StringBuilder();
				for (MultipartFile files : file) {
					if(!files.isEmpty()){
						String url1 = ImageController.saveFile(session, files,
								"notice/" + id + "/" + System.currentTimeMillis() + ".jpg");
						Thread.sleep(1);
						urls.append(",").append("/image/getimg.app?url=" + url1.replaceAll("\\\\", "/"));
					}
				}
				if(urls.length()>0){
					notice.setPicture(urls.deleteCharAt(0).toString());
				}
			}
				
			if(admin!=null){
				CgAdminLog adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加公司活动");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，添加公司活动：" + notice.getTitle() + ":"
						+ notice.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				adminLogService.save(adminLog);
			}
			baseService.save(notice);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/activity/select/companyActivityList";
		return "1";
	}

	/**
	 * 新增新闻资讯
	 * 
	 * @return
	 */
	@RequestMapping("update/updateCompanyActivity")
	@ResponseBody
	public String updateCompanyActivity(HttpServletRequest request,HttpSession session, CgNotice notice,
			@RequestParam("file") ArrayList<MultipartFile> file) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			CgNotice fnotice = (CgNotice) baseService.findEntity(CgNotice.class, notice.getId());
			fnotice.setTitle(notice.getTitle());
			fnotice.setNoticeContentBrief(notice.getNoticeContentBrief());
			fnotice.setStatus(notice.getStatus());
			fnotice.setNoticeContent(notice.getNoticeContent());
			if (!file.isEmpty()) {
				StringBuilder urls = new StringBuilder();
				for (MultipartFile files : file) {
					if(!files.isEmpty()){
						String url1 = ImageController.saveFile(session, files,
								"notice/" + fnotice.getId() + "/" + System.currentTimeMillis() + ".jpg");
						Thread.sleep(1);
						urls.append(",").append("/image/getimg.app?url=" + url1.replaceAll("\\\\", "/"));
					}
				}
				if(urls.length()>0){
					fnotice.setPicture(urls.deleteCharAt(0).toString());
				}
				
			}
				
			if(admin!=null){
				CgAdminLog adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改公司活动");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，修改公司活动：" + notice.getTitle() + ":"
						+ notice.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				adminLogService.save(adminLog);
			}
			baseService.update(fnotice);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/activity/select/companyActivityList";
		return "1";
	}
	
	/**
	 * 删除投资人
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteCompanyActivity")
	@ResponseBody
	public String deleteCompanyActivity(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgNotice investor = (CgNotice) baseService.findEntity(CgNotice.class, id);
						if (investor != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除公司活动");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除公司活动："
									+ investor.getTitle() + ":"
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
						CgNotice investor = (CgNotice) baseService.findEntity(CgNotice.class, id);
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

	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 跳转PC公司活动
	 * 
	 * @return
	 */
	@RequestMapping("pc/companyActivity")
	public String pc_companyActivity(HttpServletRequest request,PageUtil<CgNotice> pageUtil,Integer type) {
		try {
			String hql = "from CgNotice where 1=1 and type = 2 and status = 0 ";
			List<Object> params = new ArrayList<Object>();
			
			hql += " order by uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "show/activityList";
	}
	
	/**
	 * 跳转PC公司活动
	 * 
	 * @return
	 */
	@RequestMapping("pc/companyActivityDetail")
	public String pc_companyActivityDetail(HttpServletRequest request,String id,Integer type) {
		try {
			CgNotice notice = (CgNotice) baseService.findEntity(CgNotice.class, id);
			request.setAttribute("notice", notice);
			String hql = "FROM CgNotice where type = 2 and status = 0 ORDER BY uid DESC ";
			List<Object> parmas = new ArrayList<Object>();;
			List<CgNotice> nList =  baseService.find(hql,parmas,0,10);
			request.setAttribute("nList", nList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "show/activityDetail";
	}
}
