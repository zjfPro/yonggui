package com.cg.core.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.select.Evaluator.IsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgCarInsurance;
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgCarUpkeep;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.entity.CgDept;
import com.cg.common.entity.CgInvestor;
import com.cg.common.entity.CgInvestorCarMapping;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IinvestorService;

/**
 * 用于控制员工(staff)登录过程
 * 
 */
@Controller
@RequestMapping("pc/investorPc")
public class InvestorPcController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IinvestorService investorService;

	// 登录页
	@RequestMapping("go_to/investorLogin")
	public String goto_investorLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession(true);
		if (session.getAttribute("investor") != null) {
			printWriter("<script>alert('您已登录,如果想切换帐号,请先注销登录(右上角按钮)');history.back();</script>", response);
			return "pc/index";
		}
		return "show/personCenter/login";
	}

	// 点击验证登录
	@RequestMapping("login/investorLogin")
	@ResponseBody
	public String investorLogin(HttpServletRequest req, String account,String password) throws Exception {
		Object obj = investorService.checkInvestorLogin(account, password);
		if (obj instanceof String) {// 登录失败
			return obj.toString();
		}
		CgInvestor investor = (CgInvestor) obj;
		HttpSession session = req.getSession();
		
		session.setAttribute("investor", investor);
		session.setMaxInactiveInterval(3600 * 24);

		return "1";
	}
	
	@RequestMapping("updateInfo")
	@ResponseBody
	public String updateInfo(HttpServletRequest req, CgInvestor cgInvestor) throws Exception {
		CgInvestor fInvestor = (CgInvestor) baseService.findEntity(CgInvestor.class, cgInvestor.getId());
		if(fInvestor!=null){
			fInvestor.setPhone(cgInvestor.getPhone());
			fInvestor.setSex(cgInvestor.getSex());
			fInvestor.setAddress(cgInvestor.getAddress());
			baseService.update(fInvestor);
			return "ok";
		}else{
			return "no";
		}
	}
	
	// 登录成功后进入后台主页
	@RequestMapping("index")
	public String gotoIndex(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.getAttribute("staff") == null&&session.getAttribute("admin") == null) {
			return "redirect:login";
		}
		return "index/index";
	}

	@RequestMapping("go_to/personCenter")
	public String go_toPersonCenter(HttpSession session,HttpServletRequest req,PageUtil<CgCarRentalOrder> pageUtil,
			Integer page,String click) {//点击个人中心，跳转订单管理页面
		
		if(page!=null&&click!=null||click!=""){
			if("next".equals(click)){
				pageUtil.setPage(page+1);
			}else if("back".equals(click)){
				int p = page-1<=0?1:page-1;
				pageUtil.setPage(p);
			}
		}
		CgInvestor investor = (CgInvestor) session.getAttribute("investor");
		List<CgInvestorCarMapping> cicmList =  baseService.find("from CgInvestorCarMapping where investorId = ?",investor.getId());
		if(cicmList.size()>0){
			StringBuffer hql = new StringBuffer("");
			List<Object> params = new ArrayList<Object>();
			hql.append(" FROM CgCarRentalOrder where isdel=0 ");
			hql.append(" and cgCarRentalItem.carInfoId in ( ? ");
			for(CgInvestorCarMapping cicm : cicmList){
				hql.append(", ? ");
				params.add(cicm.getCarInfoId());
			}
			String hqlString = hql.substring(0, hql.length() - 4);
			hqlString += ")";
			hqlString += " order by uid desc ";
			System.out.println(hqlString);
			PageUtil<CgCarRentalOrder> ccroList = baseService.find(hqlString, params,pageUtil);
			req.setAttribute("pageUtil", ccroList);
		}
		
		return "show/personCenter/orderManage";

	}
	
	@RequestMapping("orderDetails")
	public String orderDetails(HttpServletRequest req,String id) throws Exception {//跳转订单详情页面
		CgCarRentalOrder ccro = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, id);
		req.setAttribute("ccro", ccro);
		
		return "show/personCenter/orderDetails";

	}
	//注销登录
	@RequestMapping("out")
	public String out(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		//清除所有session
		session.removeAttribute("investor");
		
		return "show/index";

	}
	
	//个人信息
	@RequestMapping("investorInfo")
	public String investorInfo(HttpServletRequest request, HttpSession session) throws Exception {
		CgInvestor investor = (CgInvestor) session.getAttribute("investor");
		CgInvestor findIn = (CgInvestor) baseService.findEntity(CgInvestor.class, investor.getId());
		request.setAttribute("investor", findIn);
		
		return "show/personCenter/investorInfo";

	}
	
	//车辆信息
	@RequestMapping("carInfo")
	public String carInfo(HttpServletRequest request, HttpSession session,PageUtil<CgInvestorCarMapping> pageUtil,
			Integer page,String click) {
		if(page!=null&&click!=null||click!=""){
			if("next".equals(click)){
				pageUtil.setPage(page+1);
			}else if("back".equals(click)){
				int p = page-1<=0?1:page-1;
				pageUtil.setPage(p);
			}
		}
		
		CgInvestor investor = (CgInvestor) session.getAttribute("investor");
		String hql ="from CgInvestorCarMapping where 1=1 and investorId = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(investor.getId());
		PageUtil<CgInvestorCarMapping> pageList = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageList);
		
		return "show/personCenter/carInfo";

	}
	//车辆使用
	@RequestMapping("carUseRecord")
	public String carUseRecord(HttpServletRequest request, HttpSession session,PageUtil<CgCarUseRecords> pageUtil,
			Integer page,String click) {
		if(page!=null&&click!=null||click!=""){
			if("next".equals(click)){
				pageUtil.setPage(page+1);
			}else if("back".equals(click)){
				int p = page-1<=0?1:page-1;
				pageUtil.setPage(p);
			}
		}
		
		CgInvestor investor = (CgInvestor) session.getAttribute("investor");
		List<CgInvestorCarMapping> cicmList =  baseService.find("from CgInvestorCarMapping where investorId = ?",investor.getId());
		if(cicmList.size()>0){
			StringBuffer hql = new StringBuffer("");
			List<Object> params = new ArrayList<Object>();
			hql.append(" from CgCarUseRecords where 1=1 ");
			hql.append(" and carInfoId in ( ? ");
			for(CgInvestorCarMapping cicm : cicmList){
				hql.append(", ? ");
				params.add(cicm.getCarInfoId());
			}
			String hqlString = hql.substring(0, hql.length() - 4);
			hqlString += ")";
			hqlString += " order by uid desc ";
			System.out.println(hqlString);
			PageUtil<CgCarUseRecords> ccurList = baseService.find(hqlString, params,pageUtil);
			request.setAttribute("pageUtil", ccurList);
			
		}
		 
		return "show/personCenter/carUseRecord";

	}
	
	//车辆保养
	@RequestMapping("carUpkeep")
	public String carUpkeep(HttpServletRequest request, HttpSession session,PageUtil<CgCarUpkeep> pageUtil,
			Integer page,String click) {
		if(page!=null&&click!=null||click!=""){
			if("next".equals(click)){
				pageUtil.setPage(page+1);
			}else if("back".equals(click)){
				int p = page-1<=0?1:page-1;
				pageUtil.setPage(p);
			}
		}
		CgInvestor investor = (CgInvestor) session.getAttribute("investor");
		List<CgInvestorCarMapping> cicmList =  baseService.find("from CgInvestorCarMapping where investorId = ?",investor.getId());
		if(cicmList.size()>0){
			StringBuffer hql = new StringBuffer("");
			List<Object> params = new ArrayList<Object>();
			hql.append(" from CgCarUpkeep where 1=1 ");
			hql.append(" and carInfoId in ( ? ");
			for(CgInvestorCarMapping cicm : cicmList){
				hql.append(", ? ");
				params.add(cicm.getCarInfoId());
			}
			String hqlString = hql.substring(0, hql.length() - 4);
			hqlString += ")";
			hqlString += " order by uid desc ";
			System.out.println(hqlString);
			PageUtil<CgCarUpkeep> ccurList = baseService.find(hqlString, params,pageUtil);
			request.setAttribute("pageUtil", ccurList);
			
		}
		 
		return "show/personCenter/carUpkeep";

	}
		
	//车辆保险
	@RequestMapping("carInsurance")
	public String carInsurance(HttpServletRequest request, HttpSession session,PageUtil<CgCarInsurance> pageUtil,
			Integer page,String click) {
		if(page!=null&&click!=null||click!=""){
			if("next".equals(click)){
				pageUtil.setPage(page+1);
			}else if("back".equals(click)){
				int p = page-1<=0?1:page-1;
				pageUtil.setPage(p);
			}
		}
		
		CgInvestor investor = (CgInvestor) session.getAttribute("investor");
		List<CgInvestorCarMapping> cicmList =  baseService.find("from CgInvestorCarMapping where investorId = ?",investor.getId());
		if(cicmList.size()>0){
			StringBuffer hql = new StringBuffer("");
			List<Object> params = new ArrayList<Object>();
			hql.append(" from CgCarInsurance where 1=1 ");
			hql.append(" and carInfoId in ( ? ");
			for(CgInvestorCarMapping cicm : cicmList){
				hql.append(", ? ");
				params.add(cicm.getCarInfoId());
			}
			String hqlString = hql.substring(0, hql.length() - 4);
			hqlString += ")";
			hqlString += " order by uid desc ";
			System.out.println(hqlString);
			PageUtil<CgCarInsurance> ccurList = baseService.find(hqlString, params,pageUtil);
			request.setAttribute("pageUtil", ccurList);
			
		}
		 
		return "show/personCenter/carInsurance";

	}
	
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
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
