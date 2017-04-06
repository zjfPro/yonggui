package com.cg.core.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgAbout;
import com.cg.common.entity.CgCarRentalItem;
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgCarStructure;
import com.cg.common.entity.CgCompanyProfile;
import com.cg.common.entity.CgFeedback;
import com.cg.common.entity.CgHelp;
import com.cg.common.entity.CgNotice;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;

/**
 * 用于控制公司形象静态页的跳转和动态显示数据
 * 
 * @author zjf
 * 
 */
@Controller
@RequestMapping("pc")
public class ShowController {

	@Autowired
	private IBaseService baseService;

	// 首页
	@RequestMapping("index")
	public String index(HttpServletRequest request,String carType) {
		try {
			List<CgCompanyProfile> find = baseService
					.find("from CgCompanyProfile order by uid");
			CgCompanyProfile cg = null;
			if (find != null && find.size() > 0) {
				cg = find.get(0);
				request.setAttribute("data", cg);
			} else {
				cg = new CgCompanyProfile();
				cg.setTitle("公司简介无数据");
				cg.setContent("公司简介无数据");
				request.setAttribute("data", cg);
			}
			
			String hql = "FROM CgCarRentalItem ci where ci.status=1 and ci.cgCarInfo.plateNumber!=null ";
			List<Object> parmas =null;
			if(carType!=null&&!carType.equals("")){
				
				carType  = new String(carType.getBytes("ISO-8859-1"),"UTF-8");
				hql+=" and ci.cgCarInfo.carType.structure.name=? ";
				parmas =new ArrayList<Object>();
				parmas.add(carType);
				request.setAttribute("carType", carType);
			}
			
			
			hql += " order by ci.uid desc";
			List<CgCarRentalItem> pageUtils = baseService.find(hql, parmas);
			
			String hql2 = "from CgCarStructure";
			List<CgCarStructure> findStructure = baseService.find(hql2);
			request.setAttribute("pageUtil", pageUtils);
			request.setAttribute("structure", findStructure);
			
			
			//首页 活动模块的数据 
			String hql3 = "from CgNotice where 1=1 and type = 2 and status = 0 order by uid desc ";//首页只显示5条
			List<Object> parmas2 = new ArrayList<Object>();;
			List<CgNotice> activityList = baseService.find(hql3,parmas2,0,5);
			request.setAttribute("activityList", activityList);
			
			//首页 新闻资讯模块数据 公司新闻
			String hql4 = "from CgNotice where status=0 and type=0 order by uid desc ";//显示3条
			List<Object> parmas4 = new ArrayList<Object>();
			List<CgNotice> companyNewsList =  baseService.find(hql4,parmas4,0,3);
			request.setAttribute("companyNewsList", companyNewsList);
			//首页 新闻资讯模块数据 行业资讯
			String hql5 = "from CgNotice where status=0 and type=1 order by uid desc ";//显示3条
			List<Object> parmas5 = new ArrayList<Object>();
			List<CgNotice> industryNewsList =  baseService.find(hql5,parmas5,0,3);
			request.setAttribute("industryNewsList", industryNewsList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "show/index";
	}
	
	@RequestMapping("getShowCarType")
	@ResponseBody
	public String getShowCarType(HttpServletRequest request,String theCarType){
		JSONObject jsonObject = null;
		try {
			
			String hql = "FROM CgCarRentalItem ci where ci.status=1 and ci.cgCarInfo.plateNumber!=null ";
			List<Object> parmas =null;
			List<CgCarRentalItem> cars = null;
			if(theCarType!=null&&!theCarType.equals("")){
				
				if(theCarType.equals("all")){
					
					hql += " order by ci.uid desc LIMIT 4 ";
					cars = baseService.find(hql);
					
				}else{
					hql+=" and ci.cgCarInfo.carType.structure.name=? ";
					parmas =new ArrayList<Object>();
					parmas.add(theCarType);
					hql += " order by ci.uid desc LIMIT 4 ";
					cars = baseService.find(hql, parmas);
				}
				
			}
			if(cars.size()>0){
				for(CgCarRentalItem cr :cars){
					cr.getCgCarInfo().getCarType().getCarBrand().getInfo();
				}
				return new GsonUtil().toJson(cars);
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("errInfo", "无上架车辆数据");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("errInfo", "获取数据失败");
			return jsonObject.toString();
		}
	}
	
	
	// 车型展示
	@RequestMapping("carList")
	public String carList(HttpServletRequest request,PageUtil<CgCarRentalItem> pageUtil,String carType) {
		pageUtil.setRows(10);
		try {
			
			String hql = "FROM CgCarRentalItem ci where ci.status=1 and ci.cgCarInfo.plateNumber!=null ";
			List<Object> parmas =null;
			if(carType!=null&&!carType.equals("")){
				
				carType  = new String(carType.getBytes("ISO-8859-1"),"UTF-8");
				hql+=" and ci.cgCarInfo.carType.structure.name=? ";
				parmas =new ArrayList<Object>();
				parmas.add(carType);
				request.setAttribute("carType", carType);
			}
			
			
			hql += " order by ci.uid desc";
			PageUtil<CgCarRentalItem> pageUtils = baseService.find(hql, parmas, pageUtil);
			
			String hql2 = "from CgCarStructure";
			List<CgCarStructure> findStructure = baseService.find(hql2);
			request.setAttribute("pageUtil", pageUtils);
			request.setAttribute("structure", findStructure);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		return "show/carList";
	}
	
	// 车型详细
	@RequestMapping("itemDetail")
	public String detail(HttpServletRequest request,String itemID) {
		try {
			CgCarRentalItem cgCarRentalItem = (CgCarRentalItem)baseService.findEntity(CgCarRentalItem.class, itemID);
			request.setAttribute("item", cgCarRentalItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "show/carDetail";
	}
	
	// 新增订单
	@RequestMapping("addItemOrder")
	@ResponseBody
	public String addItemOrder(HttpServletRequest request,String itemID,String userName,String userPhone,String userNumber){
		
		try {
			String hql = "from CgCarRentalOrder where userName=? and userPhone=? and userIdcar=? and status = 0";
			List<Object> parmas =new ArrayList<Object>();
			parmas.add(userName);
			parmas.add(userPhone);
			parmas.add(userNumber);
			List find = baseService.find(hql, parmas);
			if(find!=null){
				if(find.size()>0){
					return "请勿重复提交预定信息,与门店前台联系后此信息会审核";
				}
			}
			
			CgCarRentalItem item = (CgCarRentalItem) baseService.findEntity(CgCarRentalItem.class, itemID);
			
			if(item==null){
				return "发生错误";
			}
			CgCarRentalOrder  order = new CgCarRentalOrder();
			
			order.setAddtime(new Timestamp(System.currentTimeMillis()));
			order.setId("cu-" + UUID.randomUUID().toString());
			order.setCarRentalItemId(item.getId());
			order.setUserName(userName);
			order.setUserPhone(userPhone);
			order.setUserIdcar(userNumber);
			order.setSnapshotJson(new GsonUtil().toJson(item));
			order.setShopFrontId(item.getShopFrontId());
			order.setNumber(System.currentTimeMillis()+"");
			Thread.sleep(1);
			order.setStatus(0);
			order.setPayStatus(0);
			order.setIsdel(0);
			baseService.save(order);
			
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "服务器发生错误";
		}
	}
	
//	//关于邕桂
//	@RequestMapping("about")
//	public String about(HttpServletRequest request) {
//		return "show/about-us";
//	}
//	
	//联系方式
	@RequestMapping("contact")
	public String contact(HttpServletRequest request,Integer aboutId) {
		try {
			CgAbout abouts = (CgAbout)baseService.findEntity("from CgAbout ch where ch.id = ?", new Object[]{aboutId});
			request.setAttribute("abouts", abouts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "show/contact";
	}
	
	//关于邕桂
	@RequestMapping("about")
	public String fingerpost(HttpServletRequest request,Integer aboutId) {
		try {
			List<CgCompanyProfile> find = baseService
					.find("from CgCompanyProfile order by uid");
			CgCompanyProfile cg = null;
			if (find != null && find.size() > 0) {
				cg = find.get(0);
				request.setAttribute("data", cg);
			} else {
				cg = new CgCompanyProfile();
				cg.setTitle("公司简介无数据");
				cg.setContent("公司简介无数据");
				request.setAttribute("data", cg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "show/about-us";
	}

	//帮助中心
	@RequestMapping("help")
	public String help(HttpServletRequest request,String helpId) {
		try {
			CgHelp helps = (CgHelp)baseService.findEntity("from CgHelp ch where ch.id = ?", new Object[]{helpId});
			request.setAttribute("helps", helps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "show/help";
	}
	// 反馈
	@RequestMapping("addFankui")
	@ResponseBody
	public String addFankui(HttpServletRequest request,String userName,String userPhone,String userQQ,String userFB){
		try{
			CgFeedback cf = new CgFeedback();
			cf.setAddtime(new Timestamp(System.currentTimeMillis()));
			cf.setContent(userFB);
			cf.setName(userName);
			cf.setPhone(userPhone);
			cf.setQq(userQQ);
			baseService.save(cf);
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "反馈失败";
		}
		
		
	}
}
