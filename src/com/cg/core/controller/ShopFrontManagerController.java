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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgShopFront;
import com.cg.common.entity.CgStaff;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("shopFront")
public class ShopFrontManagerController {

	@Autowired
	private IBaseService baseService;

	/**
	 * 获取门店列表 text=搜索关键字 条件：门店名 或 地址 type=-1 所有 0总店 1分店
	 * 
	 * @return
	 */
	@RequestMapping("select/shopFrontList")
	public String shopFrontList(HttpServletRequest request,
			PageUtil<CgShopFront> pageUtil, String text, Integer type,HttpSession session) {
		CgStaff staff = null;
		if (session.getAttribute("staff")!=null) {
			staff = (CgStaff) session.getAttribute("staff");
		}
		String hql = "from CgShopFront o where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (staff!=null){
			hql+=" and o.id=? ";
			params.add(staff.getShopFrontId());
		}
	
		
		if (text != null && text.length() > 0) {
			hql += " and (o.name LIKE ? or o.address LIKE ?) ";
			params.add("%" + text + "%");
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}
		if (type != null && type != -1) {
			hql += " and o.level=? ";
			params.add(type);
			request.setAttribute("type", type);
		}

		// 参数
		hql += " order by o.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "shopFrontManage/shopFrontList";
	}

	/**
	 * 跳转新增门店
	 * 
	 * @return
	 */
	@RequestMapping("goto/addshopFront")
	public String goto_addshopFront(HttpServletRequest request,
			HttpSession session) {
		//return "shopFrontManage/addshopFront";
		return "shopFrontManage/addshopFront_new";
	}

	/**
	 * 跳转修改门店
	 * 
	 * @return
	 */
	@RequestMapping("goto/modifyshopFront")
	public String goto_modifyshopFront(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgShopFront shopFront = (CgShopFront) baseService.findEntity(
					CgShopFront.class, id);
			request.setAttribute("data", shopFront);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "shopFrontManage/modifyshopFront";
		return "shopFrontManage/modifyshopFront_new";
	}

	/**
	 * 修改门店信息
	 * 
	 * @return
	 */
	@RequestMapping("update/modifyshopFront")
	@ResponseBody
	public String modifyshopFront(HttpServletRequest request,
			HttpSession session, CgShopFront shopFront,
			@RequestParam("file") MultipartFile file) {
		CgAdmin admin = null;
		try {
			CgShopFront cgShopFront = (CgShopFront) baseService.findEntity(
					CgShopFront.class, shopFront.getId());
			if (file != null && file.getSize() > 0) {
				// 重新上传图片
				String url = ImageController.saveFile(session, file,
						"shopFront/" + shopFront.getId() + ".jpg");
				if (url != null) {
					shopFront.setLogo("image/getimg.app?url="
							+ url.replaceAll("\\\\", "/"));
				}
			}

			// 数据
			cgShopFront.setName(shopFront.getName());
			cgShopFront.setAddress(shopFront.getAddress());
			cgShopFront.setLatitude(shopFront.getLatitude());
			cgShopFront.setLongitude(shopFront.getLongitude());
			cgShopFront.setIntroduce(shopFront.getIntroduce());
			cgShopFront.setManageStaffId(shopFront.getManageStaffId());
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				CgAdminLog adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("编辑门店");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，编辑门店：" + shopFront.getName() + ":"
						+ shopFront.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseService.updateByAdmin(cgShopFront, adminLog);
			} else {
				// 身份校验
				CgStaff staff = (CgStaff) session.getAttribute("staff");
				if (staff.getId().equals(cgShopFront.getManageStaffId())) {
					baseService.update(cgShopFront);
				}
			}

			request.setAttribute("data", shopFront);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}

	/**
	 * 新增门店
	 * 
	 * @return
	 */
	@RequestMapping("add/addshopFront")
	@ResponseBody
	public String add_addshopFront(HttpServletRequest request,
			HttpSession session, CgShopFront shopFront,
			@RequestParam("file") MultipartFile file) {
		CgAdmin admin = null;
		String id = "csf-" + UUID.randomUUID();
		try {
			if (shopFront.getName()==null) {
				return "请填写门店名称";
			}
			if (shopFront.getManageStaffId()==null){
				shopFront.setManageStaffId("");
			}
			if (shopFront.getAddress()==null) {
				shopFront.setAddress("");
			}
			
			String url = ImageController.saveFile(session, file, "shopFront/"
					+ id + ".jpg");
			if (url != null) {
				shopFront.setLogo("image/getimg.app?url="
						+ url.replaceAll("\\\\", "/"));
			}

			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员新增 要创建日志
				shopFront.setId(id);
				shopFront.setAddtime(new Timestamp(System.currentTimeMillis()));
				shopFront.setLevel(0);

				CgAdminLog adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("添加门店");
				adminLog.setActionContent("管理员：" + admin.getNickname()
						+ "，添加门店：" + shopFront.getName() + ":"
						+ shopFront.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());

				baseService.saveByAdmin(shopFront, adminLog);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			try {
				// 非管理员新增 只能增加分店
				CgStaff staff = (CgStaff) session.getAttribute("staff");
				shopFront.setId(id);
				shopFront.setAddtime(new Timestamp(System.currentTimeMillis()));
				shopFront.setLevel(1);
				shopFront.setManageStaffId(staff.getId());
				baseService.save(shopFront);

			} catch (Exception e2) {
				// 身份识别失败！ 跳转重新登录
				return "登录数据已过期，请重新登录";
			}

		}
		return "1";
	}

	/**
	 * 删除门店
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteshopFront")
	@ResponseBody
	public String add_addshopFront(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgShopFront shopFront = (CgShopFront) baseService
								.findEntity(CgShopFront.class, id);
						if (shopFront != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除门店");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除门店："
									+ shopFront.getName() + ":"
									+ shopFront.getId());
							adminLog.setAdminId(admin.getId());
							adminLog.setAdmin(admin);
							adminLog.setId("cal-"
									+ UUID.randomUUID().toString());
							baseService.deleteByAdmin(shopFront, adminLog);
						}
					}
				}
			} else {
				throw new Exception();
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

}
