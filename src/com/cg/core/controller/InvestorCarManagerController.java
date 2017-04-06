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
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgInvestor;
import com.cg.common.entity.CgInvestorCarMapping;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/investorCar")
public class InvestorCarManagerController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/investorCarList")
	public String investorList(HttpServletRequest request,
			PageUtil<CgInvestorCarMapping> pageUtil, String text,  String text2) {
		String hql = "from CgInvestorCarMapping o where 1=1";
		List<Object> params = new ArrayList<Object>();
		if (!isEmpty(text)) {
			hql += " and o.carInfo.shopFront.name LIKE ? ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		if (!isEmpty(text2)) {
			hql += " and o.carInfo.plateNumber LIKE ? ";
			params.add("%" + text2 + "%");
			request.setAttribute("text2", text2);
		}
		// 参数
		hql += " order by o.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "investorCarManage/investorCarList";
	}

	/**
	 * 跳转新增车辆投资记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addInvestorCar")
	public String goto_addInvestorCar(HttpServletRequest request,
			HttpSession session) throws Exception {
		List<CgInvestor> investorList = baseService.find("from CgInvestor where 1=1 ");
		request.setAttribute("investorList", investorList);
		
		String hql = " from CgCarInfo where 1=1 ";
		CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
		List<Object> params = new ArrayList<Object>();
		if(loginStaff!=null){//如果是员工登录，只显示员工所在的门店
			hql+=" and shopFrontId=? ";
			params.add(loginStaff.getShopFrontId());
		}
		List<CgCarInfo> carInfoList = baseService.find(hql,params);
		request.setAttribute("carList", carInfoList);
		return "investorCarManage/addInvestorCar";
	}

	/**
	 * 跳转修改车辆投资记录
	 * 
	 * @return
	 */
	@RequestMapping("goto/editInvestorCarView")
	public String editInvestorCarView(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgInvestorCarMapping investorCar = (CgInvestorCarMapping) baseService.findEntity(CgInvestorCarMapping.class, id);
			request.setAttribute("investorCar", investorCar);
			List<CgInvestor> investorList = baseService.find("from CgInvestor where 1=1 ");
			request.setAttribute("investorList", investorList);
			
			String hql = " from CgCarInfo where 1=1 ";
			CgStaff loginStaff = (CgStaff) request.getSession().getAttribute("staff");
			List<Object> params = new ArrayList<Object>();
			if(loginStaff!=null){//如果是员工登录，只显示员工所在的门店
				hql+=" and shopFrontId=? ";
				params.add(loginStaff.getShopFrontId());
			}
			List<CgCarInfo> carInfoList = baseService.find(hql,params);
			request.setAttribute("carList", carInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "investorCarManage/addInvestorCar";
	}


	/**
	 * 新增/编辑车辆投资记录
	 * 
	 * @return
	 */
	@RequestMapping("add/addInvestorCar")
	@ResponseBody
	public String addInvestorCar(HttpServletRequest request,HttpSession session, CgInvestorCarMapping investorCar) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			CgInvestorCarMapping cicm = (CgInvestorCarMapping) baseService.findEntity("from CgInvestorCarMapping where investorId=? and carInfoId=? ",
					new Object[]{investorCar.getInvestorId(),investorCar.getCarInfoId()});
			if(isEmpty(investorCar.getId())&&cicm==null){//添加时，重复关联车辆投资人，直接跳过，返回页面
				
				String id = "cicm-" + UUID.randomUUID();
				investorCar.setId(id);
				investorCar.setAddtime(new Timestamp(System.currentTimeMillis()));
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加车辆投资记录");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，添加车辆投资记录：" + investorCar.getId() + ":"
							+ investorCar.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				baseService.save(investorCar);
			}else{
				CgInvestorCarMapping fInvestorCar = (CgInvestorCarMapping) baseService.findEntity(CgInvestorCarMapping.class, investorCar.getId());
				fInvestorCar.setCarInfoId(investorCar.getCarInfoId());
				fInvestorCar.setInvestorId(investorCar.getInvestorId());
				/*fInvestorCar.setType(investorCar.getType());
				fInvestorCar.setStockRight(investorCar.getStockRight());*/
				fInvestorCar.setMoney(investorCar.getMoney());
				fInvestorCar.setBonus(investorCar.getBonus());
				baseService.update(fInvestorCar);
				
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改车辆投资记录");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，修改车辆投资记录：" 
							+ fInvestorCar.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/investorCar/select/investorCarList";
		return "1";
	}

	/**
	 * 删除投资人
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("check/checkInvestorCar")
	@ResponseBody
	public String checkInvestorCar(HttpServletRequest request, String investorId,String carInfoId) throws Exception {
		CgInvestorCarMapping cicm = (CgInvestorCarMapping) baseService.findEntity("from CgInvestorCarMapping where investorId=? and carInfoId=? ",
				new Object[]{investorId,carInfoId});
		if(cicm!=null){
			return "1";
		}else{
			return "0";
		}
		
	}
	
	/**
	 * 删除投资人
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteInvestorCar")
	@ResponseBody
	public String deleteInvestorCar(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgInvestorCarMapping investor = (CgInvestorCarMapping) baseService.findEntity(CgInvestorCarMapping.class, id);
						if (investor != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除车辆投资记录");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除id："
									+ investor.getId());
							adminLog.setAdminId(admin.getId());
							adminLog.setAdmin(admin);
							adminLog.setId("cal-"
									+ UUID.randomUUID().toString());
							baseService.deleteByAdmin(investor, adminLog);
						}
					}
				}
			} else {
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgInvestorCarMapping investor = (CgInvestorCarMapping) baseService.findEntity(CgInvestorCarMapping.class, id);
						if (investor != null) {
							baseService.delete(investor);
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
}
