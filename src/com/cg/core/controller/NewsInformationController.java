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
@RequestMapping("control/news")
public class NewsInformationController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/newsInformationList")
	public String newsInformationList(HttpServletRequest request,
			PageUtil<CgNotice> pageUtil, String text, Integer type) {
		String hql = "from CgNotice where 1=1 and (type = 0 or type = 1) ";
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
		return "newsInformation/newsInformationList";
	}

	/**
	 * 跳转新增新闻资讯
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addNewsInformation")
	public String goto_addNewsInformation(HttpServletRequest request,
			HttpSession session) throws Exception {
		return "newsInformation/addNewsInformation";
	}

	/**
	 * 跳转编辑新闻资讯
	 * 
	 * @return
	 */
	@RequestMapping("goto/editNewsInformationView")
	public String editNewsInformationView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgNotice notice = (CgNotice) baseService.findEntity(CgNotice.class, id);
			request.setAttribute("notice", notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "newsInformation/editNewsInformation";
	}


	/**
	 * 新增新闻资讯
	 * 
	 * @return
	 */
	@RequestMapping("add/addNewsInformation")
	@ResponseBody
	public String addNewsInformation(HttpServletRequest request,HttpSession session, CgNotice notice,
			@RequestParam("file") ArrayList<MultipartFile> file) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			
			String id = "cn-" + UUID.randomUUID();
			notice.setId(id);
			notice.setAddtime(new Timestamp(System.currentTimeMillis()));
			notice.setAdminId(admin.getId());
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
				adminLog.setActionDesc("添加新闻资讯");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，添加新闻资讯：" + notice.getTitle() + ":"
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
		//return "redirect:/control/news/select/newsInformationList";
		return "1";
	}

	/**
	 * 新增新闻资讯
	 * 
	 * @return
	 */
	@RequestMapping("update/updateNewsInformation")
	@ResponseBody
	public String updateNewsInformation(HttpServletRequest request,HttpSession session, CgNotice notice,
			@RequestParam("file") ArrayList<MultipartFile> file) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			CgNotice fnotice = (CgNotice) baseService.findEntity(CgNotice.class, notice.getId());
			fnotice.setTitle(notice.getTitle());
			fnotice.setNoticeContentBrief(notice.getNoticeContentBrief());
			fnotice.setType(notice.getType());
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
				adminLog.setActionDesc("修改新闻资讯");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，修改新闻资讯：" + notice.getTitle() + ":"
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
		//return "redirect:/control/news/select/newsInformationList";
		return "1";
	}
	
	/**
	 * 删除投资人
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteNewsInformation")
	@ResponseBody
	public String deleteNewsInformation(HttpServletRequest request,
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
							adminLog.setActionDesc("删除新闻资讯");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除新闻资讯："
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
	 * 跳转PC新闻资讯列表
	 * 
	 * @return
	 */
	@RequestMapping("pc/newsInformationList")
	public String pc_newsInformationList(HttpServletRequest request,
			PageUtil<CgNotice> pageUtil, String text, Integer type,Integer page,String click) {
		try {
			if(page!=null&&click!=null||click!=""){
				if("next".equals(click)){
					pageUtil.setPage(page+1);
				}else if("back".equals(click)){
					int p = page-1<=0?1:page-1;
					pageUtil.setPage(p);
				}
			}
			
			String hql = "from CgNotice where status=0 and type=?";
			List<Object> params = new ArrayList<Object>();
			params.add(type);
			hql += " order by uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
			request.setAttribute("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(type==0){
			return "show/newsInformation/companyNewsList";
		}else{
			return "show/newsInformation/industryInformationList";
		}
	}
	
	//
	/**
	 * 跳转PC新闻资讯详情
	 * 
	 * @return
	 */
	@RequestMapping("pc/newsInformationDetail")
	public String pc_newsInformationDetail(HttpServletRequest request,String id,Integer type) {
		try {
			CgNotice fnotice = (CgNotice) baseService.findEntity(CgNotice.class, id);
			request.setAttribute("fnotice", fnotice);
			request.setAttribute("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "show/newsInformation/newsDetail";
	}
	
}
