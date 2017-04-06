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
import com.cg.common.entity.CgHelp;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;

@Controller
@RequestMapping("control/help")
public class HelpManagerController {
	@Autowired
	private IBaseService baseService;
	
	@RequestMapping("select/helpList")
	public String investorList(HttpServletRequest request,
			PageUtil<CgHelp> pageUtil, String text) {
		String hql = "from CgHelp cu ";
		// 参数
		List<Object> params = new ArrayList<Object>();
		hql += " where 1=1 ";
		if (text != null && text.length() > 0) {
			hql += " and (cu.title LIKE ?) ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		hql += " order by cu.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "help/helpList";
	}
	
	/** 跳转修改页面 */
	@RequestMapping("goto/updateHelp")
	public String updateHelp(HttpServletRequest request,String ccId,HttpSession session){
		try {
			CgHelp helps = (CgHelp) baseService.findEntity(
					CgHelp.class, ccId);
			request.setAttribute("helps", helps);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "help/updateHelp";
	}
	
	/** 修改类型 */
	@RequestMapping("update/updateHelp")
	@ResponseBody
	public String updateHelp(HttpServletRequest request,HttpSession session,CgHelp helps){
		CgAdmin admin = null;
		try {
			CgHelp ins = (CgHelp) baseService.findEntity(
					CgHelp.class, helps.getId());
			admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改帮助中心");
				adminLog.setActionContent("管理员：" + admin.getNickname()+ "，修改帮助信息：" + helps.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseService.save(adminLog);
			}
			ins.setHelpType(helps.getHelpType());
			ins.setContent(helps.getContent());
			ins.setTitle(helps.getTitle());
			baseService.update(ins);
			return "1";
		} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
		}
			return "-1";
	}
	
	
}
