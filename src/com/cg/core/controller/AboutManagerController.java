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

import com.cg.common.entity.CgAbout;
import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgHelp;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;

@Controller
@RequestMapping("control/about")
public class AboutManagerController {
	@Autowired
	private IBaseService baseService;
	@RequestMapping("select/aboutList")
	public String investorList(HttpServletRequest request,PageUtil<CgAbout> pageUtil, String text) {
		String hql = "from CgAbout ";
		// 参数
		List<Object> params = new ArrayList<Object>();
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "about/aboutList";
	}
	
	/** 跳转修改页面 */
	@RequestMapping("goto/updateAbout")
	public String updateAbout(HttpServletRequest request,Integer ccId,HttpSession session){
		try {
			CgAbout helps = (CgAbout) baseService.findEntity("from CgAbout where id = ?", new Object[]{ccId});
			request.setAttribute("helps", helps);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "about/updateAbout";
	}
	
	/** 修改类型 */
	@RequestMapping("update/updateAbout")
	@ResponseBody
	public String updateAbout(HttpServletRequest request,HttpSession session,CgAbout helps){
		CgAdmin admin = null;
		try {
			CgAbout ins = (CgAbout) baseService.findEntity(
					CgAbout.class, helps.getId());
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
			ins.setContent(helps.getContent());
			ins.setTitle(helps.getTitle());
			ins.setAddress(helps.getAddress());
			ins.setPhone(helps.getPhone());
			ins.setOftenPhones(helps.getOftenPhones());
			ins.setPostcode(helps.getPostcode());
			ins.setWx(helps.getWx());
			baseService.update(ins);
			return "1";
		} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
		}
			return "-1";
	}
	
	
	
}
