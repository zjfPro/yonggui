package com.cg.core.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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
import com.cg.common.entity.CgCarBrand;
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarPeccancy;
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgCarTrafficAccident;
import com.cg.common.entity.CgCarType;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.entity.CgSetting;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IUserRecordService;
import com.google.gson.Gson;

/**
 * 车辆使用记录
 * @author Administrator
 *
 */
@Controller
@RequestMapping("control/record")
public class UserRecordManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IUserRecordService userRecordService;
	
	@RequestMapping("select/userRecordList")
	public String userRecordList(HttpServletRequest request,HttpSession session,PageUtil<CgCarUseRecords>pageUtil,String text){
		String hql = "from CgCarUseRecords cu ";
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		// 参数
		List<Object> params = new ArrayList<Object>();
		if(loginStaff != null){
			hql+=" where cu.shopFrontId = ? ";
			params.add(loginStaff.getShopFrontId());
			if (text != null && text.length() > 0) {
				hql += " and (cu.useUserName LIKE ? or cu.shopFront.name LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
					}
				hql += " order by cu.uid desc";
				pageUtil = baseService.find(hql, params, pageUtil);
				request.setAttribute("pageUtil", pageUtil);
			}else{
			hql += " where 1=1 ";
			if (text != null && text.length() > 0) {
				hql += " and (cu.useUserName LIKE ? or cu.shopFront.name LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
					}
				hql += " order by cu.uid desc";
				pageUtil = baseService.find(hql, params, pageUtil);
				request.setAttribute("pageUtil", pageUtil);
			}
		return "userRecordManage/userRecordList";
	}
	
	/** 跳转新增使用记录页面*/
	@RequestMapping("goto/addUserRecordPage")
	public String addUserRecordPage(HttpServletRequest request,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder cu where 1=1");
				List<CgCarInfo> infos = baseService.find("from CgCarInfo cu where 1=1");
				request.setAttribute("infos", infos);
				request.setAttribute("fronts", fronts);
				request.setAttribute("orders", orders);
			}else{
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				List<CgCarInfo> infos = baseService.find("from CgCarInfo where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("infos", infos);
				request.setAttribute("orders", orders);
				request.setAttribute("fronts", fronts);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "userRecordManage/addUserRecordPage";
	}
	
	/** 查询员工 */
	@RequestMapping("select/carBrandList")
	@ResponseBody
	public String carBrandList(HttpServletRequest request,String pid){
			List<CgStaff> staffs = baseService.find("from CgStaff where shopFrontId = ?", new Object[]{pid});
			return new Gson().toJson(staffs);
	}
	
	
	/** 新增使用记录 */
	@RequestMapping("add/addUserRecord")
	@ResponseBody
	public String addUserRecord(HttpServletRequest request,HttpSession session,CgCarUseRecords useRecords,
			String shopFrontId,String carInfoId,String returnTimes,String carRentalOrderId,String currentMileage,String useUserPhone){
		try {
			if (!isMobile(useUserPhone)) {
				return "请输入正确的联系方式！！！";
			}
			if (carRentalOrderId == null || carRentalOrderId == "") {
				return "租车订单不能为空";
			}
			if (carInfoId == null || carInfoId == "") {
				return "车牌不能为空";
			}
			if (shopFrontId == null || shopFrontId == "") {
				return "门店不能为空";
			}
			if (currentMileage == null || currentMileage == "") {
				return "当前里程数不能为空";
			}
			if (useRecords == null) {
				return "0";
			}else {
				if(!"".equals(returnTimes)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date returnTime = sdf.parse(returnTimes);
					useRecords.setReturnTime(returnTime);
				}
				useRecords.setId("cct-" + UUID.randomUUID());
				useRecords.setAddtime(new Timestamp(System.currentTimeMillis()));
				CgAdmin admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
				if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加车辆使用记录");
					adminLog.setActionContent("管理员：" + admin.getNickname() + "，添加车辆使用记录：" + useRecords.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
				userRecordService.addUserRecord(useRecords,adminLog);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return "0";
	}
	
	/** 删除车辆使用记录*/
	@RequestMapping("delete/deleteshopFront")
	@ResponseBody
	public String deleteshopFront(HttpServletRequest request,
			HttpSession session, String ccId,String id) {
		try {
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(3);
				adminLog.setActionDesc("删除车辆使用记录");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆使用记录：" + ccId );
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			if (id != null) {
				CgCarPeccancy peccancy = (CgCarPeccancy)baseService.findEntity("from CgCarPeccancy where carUseRecordsId = ?", new Object[]{id});
				if (peccancy != null) {
					return "0";
				}
				CgCarTrafficAccident accident = (CgCarTrafficAccident)baseService.findEntity("from CgCarTrafficAccident where carUseRecordsId = ?", new Object[]{id});
				if (accident != null) {
					return "2";
				}
				baseService.updateForHql("delete from CgCarUseRecords where id = ?", new Object[] { id });
				baseService.save(adminLog);
				return "1";
			}else{
				String[] ids = new Gson().fromJson(ccId, String[].class);
				for (String idss : ids) {
					if (idss != null) {
						CgCarPeccancy peccancy = (CgCarPeccancy)baseService.findEntity("from CgCarPeccancy where carUseRecordsId = ?", new Object[]{idss});
						if (peccancy != null) {
							return "0";
						}
						CgCarTrafficAccident accident = (CgCarTrafficAccident)baseService.findEntity("from CgCarTrafficAccident where carUseRecordsId = ?", new Object[]{idss});
						if (accident != null) {
							return "2";
						}
					}
				}
				userRecordService.deleteshopFront(ids,adminLog);
				return "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}
	
	/** 跳转修改使用记录页面*/
	@RequestMapping("goto/updateUserRecordPage")
	public String updateUserRecordPage(HttpServletRequest request,HttpSession session,String ccId){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				CgCarUseRecords useRecords = (CgCarUseRecords) baseService.findEntity(CgCarUseRecords.class, ccId);

				List<CgStaff> yuangong = baseService.find("from CgStaff where shopFrontId = ?",new Object[]{useRecords.getShopFrontId()});
				request.setAttribute("yuangong", yuangong);
				
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder cu where 1=1");
				List<CgCarInfo> infos = baseService.find("from CgCarInfo cu where 1=1");
				request.setAttribute("infos", infos);
				request.setAttribute("fronts", fronts);
				request.setAttribute("orders", orders);
				request.setAttribute("records", useRecords);
			}else{
				CgCarUseRecords useRecords = (CgCarUseRecords) baseService.findEntity(CgCarUseRecords.class, ccId);
				List<CgStaff> yuangong = baseService.find("from CgStaff where shopFrontId = ?",new Object[]{useRecords.getShopFrontId()});
				request.setAttribute("yuangong", yuangong);
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				List<CgCarInfo> infos = baseService.find("from CgCarInfo where shopFrontId = ?", new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("infos", infos);
				request.setAttribute("orders", orders);
				request.setAttribute("fronts", fronts);
				request.setAttribute("records", useRecords);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userRecordManage/updateUserRecordPage";
	}
	
	/** 修改车辆使用记录*/
	@RequestMapping("update/updateUserRecord")
	@ResponseBody
	public String modifyshopFront(HttpServletRequest request,
			HttpSession session, CgCarUseRecords records,String ccId,String returnTimes,
			String carRentalOrderId,String carInfoId,String shopFrontId,String currentMileage,String useUserPhone) {
		CgAdmin admin = null;
		try {
			if (!isMobile(useUserPhone)) {
				return "请输入正确的联系方式！！！";
			}
			if (carRentalOrderId == null || carRentalOrderId == "") {
				return "租车订单不能为空";
			}
			if (carInfoId == null || carInfoId == "") {
				return "车牌不能为空";
			}
			if (shopFrontId == null || shopFrontId == "") {
				return "门店不能为空";
			}
			if (currentMileage == null || currentMileage == "") {
				return "当前里程数不能为空";
			}
				CgCarUseRecords useRecordst = (CgCarUseRecords) baseService.findEntity(
						CgCarUseRecords.class, ccId);
				useRecordst.setSatffId(records.getSatffId());
				useRecordst.setUseUserName(records.getUseUserName());
				useRecordst.setUseUserPhone(records.getUseUserPhone());
				useRecordst.setCarInfoId(records.getCarInfoId());
				useRecordst.setShopFrontId(records.getShopFrontId());
				useRecordst.setPlateNumber(records.getPlateNumber());
				useRecordst.setCurrentMileage(records.getCurrentMileage());
				if(!"".equals(returnTimes)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date returnTime = sdf.parse(returnTimes);
					useRecordst.setReturnTime(returnTime);
				}
				useRecordst.setReturnMileage(records.getReturnMileage());
				useRecordst.setNumber(records.getNumber());
				useRecordst.setCarRentalOrderId(records.getCarRentalOrderId());
				useRecordst.setHandoverStaffId(records.getHandoverStaffId());
				admin = (CgAdmin) session.getAttribute("admin");
				CgAdminLog adminLog = null;
			if (admin != null) {
					adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改车辆使用记录");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，修改车辆使用记录：" + ccId);
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
				}
			userRecordService.updateModifyshopFront(useRecordst, adminLog);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		if (str==null ||str.length()<1) {
			return false;
		}
		return Pattern.compile("^[1][1,2,3,4,5,6,7,8,9][0-9]{9}$").matcher(str)
				.matches();
	}
}
