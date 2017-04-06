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
import com.cg.common.entity.CgCarRentalItem;
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.entity.CgFinancialDetails;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffLog;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IRentalOrderService;

/**
 * 租车订单管理
 *
 */
@Controller
@RequestMapping("control/rentalOrder")
public class RentalOrderController {
	
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IRentalOrderService rentalOrderService;
	
	//租车订单列表
	@RequestMapping("select/rentalOrderList")
	public String rentalOrderList(HttpServletRequest request,PageUtil<CgCarRentalOrder> pageUtil,String searchText) {
		
		HttpSession session = request.getSession(true);
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		
		String hql = "FROM CgCarRentalOrder cs where cs.isdel=0 ";
		
		List<Object> parmas = null;
		
		if(loginStaff!=null){
			if(isEmpty(searchText)){
				parmas = new ArrayList<Object>();
				hql += " and shopFrontId=? ";
				parmas.add(loginStaff.getShopFrontId());
			}else{
				parmas = new ArrayList<Object>();
				hql += " and (cs.number like ? or cs.cgCarRentalItem.cgCarInfo.plateNumber like ? or cs.cgShopFront.name like ?) and shopFrontId=? ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add(loginStaff.getShopFrontId());
			}
		}else{
			if(isEmpty(searchText)){
				
			}else{
				parmas = new ArrayList<Object>();
				hql += " and (cs.number like ? or cs.cgCarRentalItem.cgCarInfo.plateNumber like ? or cs.cgShopFront.name like ?) ";
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
				parmas.add("%"+searchText+"%");
			}
		}

		hql +=" order by cs.uid desc";

		PageUtil<CgCarRentalOrder> pageUtils = baseService.find(hql, parmas, pageUtil);

		request.setAttribute("pageUtil", pageUtils);
		request.setAttribute("searchText", searchText);
		return "rentalOrder/rentalOrderList";
	}
	
	//审核页面
	@RequestMapping("select/shenheDetail")
	public String shenheDetail(HttpServletRequest request,String shenheorderID){
		
		try {
			CgCarRentalOrder findEntity = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, shenheorderID);
			request.setAttribute("shenhedd", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rentalOrder/shenheOrder";
	}
	
	//修改审核状态
	@RequestMapping("update/gotoUpdateOrderStatus")
	@ResponseBody
	public String gotoUpdateOrderStatus(HttpServletRequest request,HttpSession session,String shenhedd,String status){
		try {
			CgCarRentalOrder findCcri = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, shenhedd);
			
			findCcri.setStatus(Integer.valueOf(status));
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改订单");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改审核状态："+findCcri.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			CgStaffLog cgStaffLog=null;
			if(loginStaff!=null){
				cgStaffLog = new CgStaffLog();
				cgStaffLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				cgStaffLog.setActionIp(GetIpUtil.getIpAddr(request));
				cgStaffLog.setActionType(2);
				cgStaffLog.setActionDesc("审核订单");
				cgStaffLog.setActionContent("员工：" + loginStaff.getName() + "，修改订单审核状态："+findCcri.getId()+"，状态为："+findCcri.getStatus());
				cgStaffLog.setId("cal-" + UUID.randomUUID().toString());
				cgStaffLog.setStaffId(loginStaff.getId());
				cgStaffLog.setForeignId(findCcri.getId());
			}
			
			rentalOrderService.updateOrder(findCcri, adminLog, cgStaffLog,null);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误!";
		}
		
	}
	
	//完善订单页面
	@RequestMapping("select/wanshanDetail")
	public String wanshanDetail(HttpServletRequest request,String wanshanorderID){
		
		try {
			CgCarRentalOrder findEntity = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, wanshanorderID);
			request.setAttribute("wanshandd", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rentalOrder/wanshanOrder";
	}
	
	//完善订单
	@RequestMapping("update/gotoPerfectOrder")
	@ResponseBody
	public String gotoPerfectOrder(HttpServletRequest request,HttpSession session,
			String perfectdd,
			String userName,
			String userPhone,
			String carRentalItemId,
			String shopFrontId,
			String staffId,
			String contractId){
		
		if(isEmpty(userName)){
			return "请输入租车客户姓名";
		}
		if(isEmpty(userPhone)){
			return "请输入租车用户联系电话";
		}
		if(isEmpty(carRentalItemId)){
			return "请选择上架车辆";
		}
		if(isEmpty(staffId)){
			return "请选择业务员";
		}
		if(isEmpty(contractId)){
			return "请选择租车合同";
		}
		
		try {
			CgCarRentalOrder findCcri = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, perfectdd);
			
			if(findCcri.getStatus()==1){
				findCcri.setUserName(userName);
				findCcri.setUserPhone(userPhone);
				findCcri.setContractId(contractId);
				findCcri.setShopFrontId(shopFrontId);
				findCcri.setStaffId(staffId);
				findCcri.setCarRentalItemId(carRentalItemId);
				
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改订单");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改订单："+findCcri.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				
				CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
				CgStaffLog cgStaffLog=null;
				if(loginStaff!=null){
					cgStaffLog = new CgStaffLog();
					cgStaffLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					cgStaffLog.setActionIp(GetIpUtil.getIpAddr(request));
					cgStaffLog.setActionType(2);
					cgStaffLog.setActionDesc("完善订单");
					cgStaffLog.setActionContent("员工：" + loginStaff.getName() + "，完善订单："+findCcri.getId());
					cgStaffLog.setId("cal-" + UUID.randomUUID().toString());
					cgStaffLog.setStaffId(loginStaff.getId());
					cgStaffLog.setForeignId(findCcri.getId());
				}
				
				
				rentalOrderService.updateOrder(findCcri, adminLog, cgStaffLog,null);
				return "1";
			}else{
				return "该订单因未通过审核，不能补全订单资料";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误";
		}
		
	}
	
	//订单付款页面
	@RequestMapping("select/fukuanDetail")
	public String fukuanDetail(HttpServletRequest request,String fukuanorderID){
		
		try {
			CgCarRentalOrder findEntity = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, fukuanorderID);
			request.setAttribute("wanshandd", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rentalOrder/fukuanOrder";
	}
	
	
	//订单付款
	@RequestMapping("update/gotoPayment")
	@ResponseBody
	public String gotoPayment(HttpServletRequest request,HttpSession session,String fukuanorderID,String earnestMoney,String foregiftMoney,
			String buyMoney){
		
		try {
			
			if(!earnestMoney.matches("^[0-9]*$")){
				return "请输入整数";
			}
			if(!foregiftMoney.matches("^[0-9]*$")){
				return "请输入整数";
			}
			if(!buyMoney.matches("^[0-9]*$")){
				return "请输入整数";
			}
			CgCarRentalOrder findCcri = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, fukuanorderID);
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if(findCcri.getStatus()==1&&findCcri.getContractId()!=null){
				findCcri.setEarnestMoney(earnestMoney);
				findCcri.setForegiftMoney(foregiftMoney);
				findCcri.setBuyMoney(buyMoney);
				findCcri.setPayStatus(1);
				CgFinancialDetails cfd=null;
				if(!isEmpty(findCcri.getEarnestMoney())&&
						!isEmpty(findCcri.getForegiftMoney())&&!isEmpty(findCcri.getBuyMoney())){
					
					if(loginStaff!=null){
						cfd = new CgFinancialDetails();
						cfd.setId("cfd-" + UUID.randomUUID().toString());
						cfd.setAddtime(new Timestamp(System.currentTimeMillis()));
						cfd.setMoney("+"+findCcri.getBuyMoney());
						cfd.setCarRentalOrderId(findCcri.getId());
						cfd.setInfo("员工:"+loginStaff.getName()+"填入："+findCcri.getBuyMoney()+"金额");
						cfd.setStaffId(loginStaff.getId());
					}
					
				}else{
					return "请填写付款数额";
				}
				
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改订单");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，修改订单："+findCcri.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				
			
				CgStaffLog cgStaffLog=null;
				if(loginStaff!=null){
					cgStaffLog = new CgStaffLog();
					cgStaffLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					cgStaffLog.setActionIp(GetIpUtil.getIpAddr(request));
					cgStaffLog.setActionType(2);
					cgStaffLog.setActionDesc("订单付款");
					cgStaffLog.setActionContent("员工：" + loginStaff.getName() + "，订单付款："+findCcri.getId()
							+"定金:"+findCcri.getEarnestMoney()+"押金:"+findCcri.getForegiftMoney()+"支付总价:"+findCcri.getBuyMoney());
					cgStaffLog.setId("cal-" + UUID.randomUUID().toString());
					cgStaffLog.setStaffId(loginStaff.getId());
					cgStaffLog.setForeignId(findCcri.getId());
				}
				
				
				rentalOrderService.updateOrder(findCcri, adminLog, cgStaffLog,cfd);
				return "1";
			}else if(findCcri.getStatus()!=1){
				return "该订单因未通过审核，不能付款";
			}else{
				return "该订单资料未补全";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误";
		}
		
	}
	
	//订单取车页面
	@RequestMapping("select/qucheDetail")
	public String qucheDetail(HttpServletRequest request,String qucheorderID){
		
		try {
			CgCarRentalOrder findEntity = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, qucheorderID);
			request.setAttribute("wanshandd", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rentalOrder/qucheOrder";
	}
	
	//取车:1填写车辆使用记录，2车辆信息表改成出库，3订单添加取车员工，4车辆项目下架
	@RequestMapping("update/gotoQuche")
	@ResponseBody
	public String gotoQuche(HttpServletRequest request,HttpSession session,String qucheorderID,CgCarUseRecords cur,String examineRuningStaffId){
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		if(loginStaff==null){
			return "管理员不能取车,请使用员工账号";
		}
		
		if(isEmpty(cur.getUseUserName())){
			return "请输入借车人姓名";
		}
		if(isEmpty(cur.getUseUserPhone())){
			return "请输入借车人联系方式";	
		}
		if(cur.getCurrentMileage()==null){
			return "请输入当前里程数";
		}
		if(examineRuningStaffId==null||examineRuningStaffId.equals("")){
			return "请选择取车员工";
		}
		
		try{
			CgCarRentalOrder findCcri = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, qucheorderID);
			
			//审核通过并且已付款才能取车
			if(findCcri.getStatus()!=1||findCcri.getPayStatus()!=1){
				return "订单审核未通过或者未付款,不能取车";
			}
			
			//1 start
			cur.setId("cur-" + UUID.randomUUID().toString());
			cur.setAddtime(new Timestamp(System.currentTimeMillis()));
			cur.setSatffId(loginStaff.getId());
			cur.setCarInfoId(findCcri.getCgCarRentalItem().getCarInfoId());
			cur.setShopFrontId(findCcri.getShopFrontId());
			cur.setPlateNumber(findCcri.getCgCarRentalItem().getCgCarInfo().getPlateNumber());
			cur.setCarRentalOrderId(findCcri.getId());
			cur.setNumber("QC"+System.currentTimeMillis());
			//1 end
			
			//2 start
			CgCarInfo findCarInfo = (CgCarInfo) baseService.findEntity(CgCarInfo.class, findCcri.getCgCarRentalItem().getCarInfoId());
			findCarInfo.setStatus(1);
			//2 end
			
			//3 start
			findCcri.setExamineRuningStaffId(examineRuningStaffId);
			//3 end
			
			//4 start
			CgCarRentalItem findCri = (CgCarRentalItem) baseService.findEntity(CgCarRentalItem.class, findCcri.getCarRentalItemId());
			findCri.setStatus(-1);
			//4 end
			
			CgStaffLog cgStaffLog=null;
			if(loginStaff!=null){
				cgStaffLog = new CgStaffLog();
				cgStaffLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				cgStaffLog.setActionIp(GetIpUtil.getIpAddr(request));
				cgStaffLog.setActionType(2);
				cgStaffLog.setActionDesc("取车");
				cgStaffLog.setActionContent("员工：" + loginStaff.getName() + "，订单："+findCcri.getId());
				cgStaffLog.setId("cal-" + UUID.randomUUID().toString());
				cgStaffLog.setStaffId(loginStaff.getId());
				cgStaffLog.setForeignId(findCcri.getId());
			}
			
			rentalOrderService.updateOrderForQuche(findCcri, null, cgStaffLog, cur, findCarInfo, findCri);
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "发生错误";
		}
		
	}
	
	//订单还车页面
	@RequestMapping("select/huancheDetail")
	public String huancheDetail(HttpServletRequest request,String huancheorderID){
		
		try {
			CgCarRentalOrder findEntity = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, huancheorderID);
			
			List<CgCarUseRecords> find = baseService.find("from CgCarUseRecords where carRentalOrderId=?", findEntity.getId());
			if(find.size()==1){
				request.setAttribute("chejilu", find.get(0));
			}
			request.setAttribute("wanshandd", findEntity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rentalOrder/huancheOrder";
	}
	
	//还车:1更新车辆使用记录，2车辆表入库，3订单还车，4车辆上架，5计算投资人分成
	@RequestMapping("update/gotoHuanche")
	@ResponseBody
	public String gotoHuanche(HttpServletRequest request,HttpSession session,String huancheorderID,String chejiluID,String huaicheMileage){
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
//		if(loginStaff==null){
//			return "管理员不能操作还车,请使用员工账号";
//		}
		try {
			
			CgCarRentalOrder findEntity = (CgCarRentalOrder) baseService.findEntity(CgCarRentalOrder.class, huancheorderID);
			if(findEntity.getExamineRuningStaffId()==null||findEntity.getExamineRuningStaffId().length()<1){
				return "并未取车，不能操作还车";
			}
			
			if(isEmpty(huaicheMileage)){
				return "请输入还车里程数";	
			}
			if(!huaicheMileage.matches("^[0-9]*$")){
				return "请输入整数";
			}
			
			//1 start
			List<CgCarUseRecords> find = baseService.find("from CgCarUseRecords where carRentalOrderId=?", findEntity.getId());
			CgCarUseRecords ccur = find.get(0);
			ccur.setReturnTime(new Timestamp(System.currentTimeMillis()));
			ccur.setReturnMileage(Integer.valueOf(huaicheMileage));
			ccur.setHandoverStaffId(loginStaff.getId());
			//1 end
			
			//2 start
			CgCarInfo findCarInfo = (CgCarInfo) baseService.findEntity(CgCarInfo.class, findEntity.getCgCarRentalItem().getCarInfoId());
			findCarInfo.setStatus(0);
			//2 end
			
			//3 start
			findEntity.setExamineEndStaffId(loginStaff.getId());
			//3 end
			
			//4 start
			CgCarRentalItem findCri = (CgCarRentalItem) baseService.findEntity(CgCarRentalItem.class, findEntity.getCarRentalItemId());
			findCri.setStatus(1);
			//4 end
			
			//5 start
			
			//5 end
			
			return "1";
		} catch (Exception e) {
			return "操作失败";
		}
		
		
	}
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
