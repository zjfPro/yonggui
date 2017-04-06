package com.cg.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgCarUpkeep;
import com.cg.common.entity.CgFeedback;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;

@Controller
@RequestMapping("control/feedback")
public class FeedbackController {
	@Autowired
	private IBaseService baseService;
	
	// 反馈列表
	@RequestMapping("select/feedbackList")
	public String cgCarUpkeepList(HttpServletRequest request,PageUtil<CgCarUpkeep> pageUtil) {
		
		String hql = "FROM CgFeedback  order by id desc";
		PageUtil<CgFeedback> pageUtils = baseService.find(hql, null,pageUtil);

		request.setAttribute("pageUtil", pageUtils);

		return "feedback/feedback";
	}
	
	//删除反馈
	@RequestMapping("delete/deleteFeedback")
	@ResponseBody
	public String deleteFeedback(HttpServletRequest request,HttpSession session,String strs) {
		String ids = strs.substring(0, strs.length()-1);
		String[] split = ids.split(",");
		try{
			for (String id : split) {
				String hql = "delete from CgFeedback where id = ?";
				baseService.executeHQL(hql,  new Object[]{Integer.valueOf(id)});
			}
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "删除失败";
		}
		
		
	}
}
