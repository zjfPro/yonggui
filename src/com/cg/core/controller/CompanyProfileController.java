package com.cg.core.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.common.entity.CgCompanyProfile;
import com.cg.common.entity.CgNotice;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;

@Controller
@RequestMapping("companyProfile")
public class CompanyProfileController {
	@Autowired
	private IBaseService baseService;
	
	// 公司简介
	@RequestMapping("select/companyProfileList")
	public String staffList(HttpServletRequest request,PageUtil<CgStaff> pageUtil) {
		
		String hql = "from CgCompanyProfile";
		PageUtil<CgCompanyProfile> pageUtils = baseService.find(hql, null, pageUtil);
		request.setAttribute("pageUtil", pageUtils);
		return "companyProfile/companyProfileList";
	}
	
	//公司简介详细页面
	@RequestMapping("select/companyProfileDetail")
	public String companyProfileDetail(HttpServletRequest request,String cpid){
		try {
			CgCompanyProfile cp = (CgCompanyProfile) baseService.findEntity(CgCompanyProfile.class, cpid);
			request.setAttribute("cpDetail", cp);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "companyProfile/editCompanyProfile";
	}
	
	//查看内容
	@RequestMapping("select/chakanDetail")
	public String chakanDetail(HttpServletRequest request,String cpid){
		try {
			CgCompanyProfile cp = (CgCompanyProfile) baseService.findEntity(CgCompanyProfile.class, cpid);
			request.setAttribute("cpDetail", cp);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "companyProfile/chakan";
	}
	
	//修改公司简介
	@RequestMapping("update/companyProfileList")
	@ResponseBody
	public String updateCompanyActivity(HttpServletRequest request,HttpSession session, CgCompanyProfile companyProfile,
			@RequestParam("file") ArrayList<MultipartFile> file,String jjid) {
		
		try {
			
			CgCompanyProfile cp =(CgCompanyProfile) baseService.findEntity(CgCompanyProfile.class, jjid);
			
			if (!file.isEmpty()) {
				StringBuilder urls = new StringBuilder();
				for (MultipartFile files : file) {
					if(!files.isEmpty()){
						String url1 = ImageController.saveFile(session, files,
								"companyProfile/" + cp.getId() + "/" + System.currentTimeMillis() + ".jpg");
						Thread.sleep(1);
						urls.append(",").append("/image/getimg.app?url=" + url1.replaceAll("\\\\", "/"));
					}
				}
				if(urls.length()>0){
					cp.setPicture(urls.deleteCharAt(0).toString());
				}
				
			}
			cp.setContent(companyProfile.getContent());
			cp.setRemark(companyProfile.getRemark());
			cp.setTitle(companyProfile.getTitle());
			baseService.update(cp);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "修改失败";
		}
		
	}
}
