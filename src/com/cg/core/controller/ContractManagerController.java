package com.cg.core.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgContract;
import com.cg.common.entity.CgInvestor;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffPosition;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.GsonUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IContractManageService;
import com.google.gson.Gson;

@Controller
@RequestMapping("control/contract")
public class ContractManagerController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IContractManageService contractManageService;
	
	/** 合同列表 
	 * @throws Exception */
	@RequestMapping("select/contractList")
	public String carInfoList(HttpServletRequest request,HttpSession session,PageUtil<CgContract> pageUtil,String text,Integer ccId) throws Exception{
		String hql = "from CgContract cc";
		CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
		// 参数
		List<Object> params = new ArrayList<Object>();
		if(loginStaff != null){
			if (ccId == null) {
				hql+=" where cc.shopFrontId = ? AND cc.type = 0";
				params.add(loginStaff.getShopFrontId());
			}else{
				hql+=" where cc.shopFrontId = ? AND cc.type = ?";
				params.add(loginStaff.getShopFrontId());
				params.add(ccId);
			}
			
			if (text != null && text.length() > 0) {
				hql += " and ( cc.front.name LIKE ? or cc.investor.name LIKE ? or cc.plateNumber LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cc.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}else{
			if (ccId == null) {
				hql += " where cc.type = 0 ";
			}else{
				hql += " where cc.type = ? ";
				params.add(ccId);
			}
			
			if (text != null && text.length() > 0) {
				hql += " and ( cc.front.name LIKE ? or cc.investor.name LIKE ? or cc.plateNumber LIKE ?) ";
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				params.add("%" + text + "%");
				request.setAttribute("text", text);
			}
			hql += " order by cc.uid desc";
			pageUtil = baseService.find(hql, params, pageUtil);
			request.setAttribute("pageUtil", pageUtil);
		}
		return "contractManage/contractList";
	}

	/** 删除合同 */
	@RequestMapping("delete/deleteContract")
	@ResponseBody
	public String deleteContract(HttpServletRequest request,String ccId,HttpSession session,String id){
		try {
		CgAdmin admin = (CgAdmin) session.getAttribute("admin");
		CgAdminLog adminLog = null;
		if (admin != null) {
			adminLog = new CgAdminLog();
			adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
			adminLog.setActionIp(GetIpUtil.getIpAddr(request));
			adminLog.setActionType(3);
			adminLog.setActionDesc("删除合同");
			adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除合同：" + ccId );
			adminLog.setAdminId(admin.getId());
			adminLog.setAdmin(admin);
			adminLog.setId("cal-" + UUID.randomUUID().toString());
		}
		if (id != null) {
			baseService.updateForHql("delete from CgContract where id = ?", new Object[] { id });
			baseService.save(adminLog);
			return "1";
		}else{
			String[] ids = new Gson().fromJson(ccId, String[].class);
			contractManageService.deleteContract(ids,adminLog);
			return "1";
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "删除失败";
	}
	
	/** 跳转新增合同页面 */
	@RequestMapping("goto/addContractPage")
	public String addContractPage(HttpServletRequest request,HttpSession session){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				request.setAttribute("fronts", fronts);
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder ");
				request.setAttribute("orders", orders);
			}else{
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("fronts", fronts);
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder where shopFrontId = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("orders", orders);
			}
			List<CgInvestor> investors = baseService.find("from CgInvestor ");
			request.setAttribute("investors", investors);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "contractManage/addContractPage";
	}
	
	/** 查询员工 */
	@RequestMapping("select/carBrandList")
	@ResponseBody
	public String carBrandList(HttpServletRequest request,String pid){
			List<CgStaff> staffs = baseService.find("from CgStaff where shopFrontId = ?", new Object[]{pid});
			return new Gson().toJson(staffs);
	}
	
	/** 新增合同 
	 * @throws Exception */
	@RequestMapping("add/addContract")
	@ResponseBody
	public String addContract(HttpServletRequest request,CgContract contract,
			@RequestParam("file") ArrayList<MultipartFile> file,HttpSession session,
			String contractTimes,String startTimes,String endTimes,String investorId,String orderId,String tzrPhone) throws Exception{
		CgAdmin admin = null;
		String id = "cc-" + UUID.randomUUID();
		CgInvestor investor = (CgInvestor) baseService.findEntity("from CgInvestor where id = ? AND phone = ?",new Object[]{investorId,tzrPhone});
		CgCarRentalOrder orders = (CgCarRentalOrder) baseService.findEntity("from CgCarRentalOrder where id = ? ", new Object[]{orderId});
		try {
			String error="";
			if (investor == null || investor.equals("")) {
				error="添加失败,请检查投资人名字或电话是否正确";
				request.setAttribute("error", error);
				return "contractManage/addContractPage";
			}
			if (orders == null || orders.equals("")) {
				error="请输入正确的订单编号";
				request.setAttribute("error", error);
				return "contractManage/addContractPage";
			}
			contract.setId("cc-" + UUID.randomUUID());
			contract.setInvestorId(investor.getId());
			contract.setOrderId(orders.getId());
			if (!"".equals(contractTimes)) {
				contractTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(contractTimes);
				contract.setContractTime(startTime);
			}
			if (!"".equals(startTimes)) {
				startTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(startTimes);
				contract.setStartTime(startTime);
			}
			if (!"".equals(endTimes)) {
				endTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(endTimes);
				contract.setEndTime(startTime);
			}
			if (!file.isEmpty()) {
				StringBuilder urls = new StringBuilder();
				for (MultipartFile files : file) {
					String url1 = ImageController.saveFile(session, files,
							"contract/" + id + "/" + System.currentTimeMillis() + ".jpg");
					Thread.sleep(1);
					urls.append(",").append("/image/getimg.app?url=" + url1.replaceAll("\\\\", "/"));
				}
				contract.setContent(urls.deleteCharAt(0).toString());
			}

			admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				// 管理员新增 要创建日志
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("新增合同信息");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，合同信息："
						+ contract.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			String sql2 = "SELECT max(uid) FROM cg_contract where 1 = ?";
			List<Object[]>  list = baseService.findSQL(sql2, new Object[]{1} );
			if(list!=null){
				if(list.get(0)!=null){
					contract.setNumber(System.currentTimeMillis() + ""+list.get(0));
				}
			}
			contractManageService.saveContent(contract, adminLog);
//			return ("<script type='text/javascript'>alert('添加成功');</script >");
		return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "添加失败";
	}
	
	/** 跳转修改合同页面 */
	@RequestMapping("goto/updateContractPage")
	public String updateContractPage(HttpServletRequest request,HttpSession session,String ccId){
		try {
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			if (loginStaff == null) {
				List<CgShopFront> fronts = baseService.find("from CgShopFront ");
				request.setAttribute("fronts", fronts);
				List<CgStaff> staffs = baseService.find("from CgStaff ");
				request.setAttribute("staffs", staffs);
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder ");
				request.setAttribute("orders", orders);
			}else{
				List<CgShopFront> fronts = baseService.find("from CgShopFront where id = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("fronts", fronts);
				List<CgStaff> staffs = baseService.find("from CgStaff where shopFrontId = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("staffs", staffs);
				List<CgCarRentalOrder> orders = baseService.find("from CgCarRentalOrder where shopFrontId = ?",new Object[]{loginStaff.getShopFrontId()});
				request.setAttribute("orders", orders);
			}
			CgContract infos = (CgContract) baseService.findEntity(
					CgContract.class, ccId);
			List<CgInvestor> investors = baseService.find("from CgInvestor ");
			request.setAttribute("infos", infos);
			request.setAttribute("investors", investors);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "contractManage/updateContractPage";
	}
	
	/** 修改合同 
	 * @throws Exception */
	@RequestMapping("update/updateContract")
	@ResponseBody
	public String updateContract(HttpServletRequest request,HttpSession session,
			@RequestParam("file") ArrayList<MultipartFile> file,CgContract contract,String investorId,
			String contractTimes,String startTimes,String endTimes,String id,String plateNumber,String tzrPhone) throws Exception{
		CgAdmin admin = null;
		CgInvestor investor = (CgInvestor) baseService.findEntity("from CgInvestor where id = ? AND phone = ?",new Object[]{investorId,tzrPhone});
		try {
			String error="";
			if (investor == null || investor.equals("")) {
				error="请检查该投资人与该电话是否匹配";
				request.setAttribute("error", error);
				return "contractManage/updateContractPage";
			}
			CgContract infos = (CgContract) baseService.findEntity(
					CgContract.class, id);
			if (!file.isEmpty()) {
				StringBuilder urls = new StringBuilder();
				boolean b = false;
				for (MultipartFile f : file) {
					if (f.getSize() > 0) {
						b = true;
						String url1 = ImageController.saveFile(session, f,
								"contract/" + id + "/" + System.currentTimeMillis() + ".jpg");
						Thread.sleep(1);
						urls.append(",").append("/image/getimg.app?url=" + url1.replaceAll("\\\\", "/"));
					}
				}
				if(b == true){
					infos.setContent(urls.deleteCharAt(0).toString());
				}
				
			}
			if (!"".equals(contractTimes)) {
				contractTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(contractTimes);
				infos.setContractTime(startTime);
			}
			if (!"".equals(startTimes)) {
				startTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(startTimes);
				infos.setStartTime(startTime);
			}
			if (!"".equals(endTimes)) {
				endTimes += " 00:00:000";
				Timestamp startTime = Timestamp.valueOf(endTimes);
				infos.setEndTime(startTime);
			}
			
			// 数据
			infos.setStatus(contract.getStatus());
			infos.setClearingForm(contract.getClearingForm());
			infos.setName(contract.getName());
			infos.setPrincipal(contract.getPrincipal());
			infos.setContractMoney(contract.getContractMoney());
			infos.setRemark(contract.getRemark());
			infos.setShopFrontId(contract.getShopFrontId());
			infos.setStaffId(contract.getStaffId());
			infos.setInvestorId(investor.getId());
			infos.setPlateNumber(contract.getPlateNumber());
			infos.setType(contract.getType());
			admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("修改车辆信息");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，修改休息：" + infos.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			} 
			contractManageService.updateContent(infos, adminLog);
//			return ("<script type='text/javascript'>alert('修改成功');</script >");
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
			return "修改失败";
	}
	
	@RequestMapping("examine/investorId")
	@ResponseBody
	public String investorId(String investorId, HttpServletRequest request) throws Exception {
		CgInvestor investor = (CgInvestor) baseService.findEntity("from CgInvestor where name = ? ", new Object[]{investorId});
		if(investor!=null){
			return "1";
		}else{
			return "ok";
		}
	}
	
	@RequestMapping("account/OrderId")
	@ResponseBody
	public String OrderId(String orderId, HttpServletRequest request) throws Exception {
		CgCarRentalOrder orders = (CgCarRentalOrder) baseService.findEntity("from CgCarRentalOrder where number = ? ", new Object[]{orderId});
		if(orders!=null){
			return "1";
		}else{
			return "ok";
		}
	}
	
	
}