package com.cg.core.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgApplicationUseCar;
import com.cg.common.entity.CgInvestor;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.ImageCompressUtil;
import com.cg.common.utils.PageUtil;
import com.cg.core.service.IAdminLogService;
import com.cg.core.service.IBaseService;
import com.google.gson.Gson;

/**
 * 用车申请控制器
 * 
 * */
@Controller
@RequestMapping("control/applicationUseCar")
public class ApplicationUseCarManagerController {
	@Autowired
	private IAdminLogService adminLogService;
	
	@Autowired
	private IBaseService baseService;

	/**
	 * @return
	 */
	@RequestMapping("select/applicationUseCarList")
	public String applicationUseCarList(HttpServletRequest request,
			PageUtil<CgApplicationUseCar> pageUtil, String text, Integer type) {
		String hql = "from CgApplicationUseCar o where 1=1";
		List<Object> params = new ArrayList<Object>();
		if (!isEmpty(text)) {
			hql += " and o.person LIKE ? ";
			params.add("%" + text + "%");
			request.setAttribute("text", text);
		}

		// 参数
		hql += " order by o.uid desc";
		pageUtil = baseService.find(hql, params, pageUtil);
		request.setAttribute("pageUtil", pageUtil);
		return "applicationUseCar/applicationUseCarList";
	}

	/**
	 * 跳转新增投资人
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("goto/addCarUseApplication")
	public String goto_addCarUseApplication(HttpServletRequest request,
			HttpSession session) throws Exception {
		return "applicationUseCar/carUseApplication";
	}

	/**
	 * 跳转修改投资人
	 * 
	 * @return
	 */
	@RequestMapping("goto/editCarUseApplication")
	public String editCarUseApplication(HttpServletRequest request,
			HttpSession session, String id) {
		try {
			CgApplicationUseCar cauc = (CgApplicationUseCar) baseService.findEntity(CgApplicationUseCar.class, id);
			request.setAttribute("cauc", cauc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "applicationUseCar/carUseApplication";
	}

	/**
	 * 新增/编辑投资人
	 * 
	 * @return
	 */
	@RequestMapping("add/addCarUseApplication")
	@ResponseBody
	public String addCarUseApplication(HttpServletRequest request,HttpSession session, CgApplicationUseCar cauc,
			@RequestParam("file") MultipartFile file,String startTimes,String endtimes) {
		CgAdmin admin = null;
		try{
			admin = (CgAdmin) session.getAttribute("admin");
			
			if(isEmpty(cauc.getId())){
				String id = "cauc-" + UUID.randomUUID();
				cauc.setId(id);
				if(!isEmpty(startTimes)){
					cauc.setStartTime(Timestamp.valueOf(startTimes));
				}
				if(!isEmpty(endtimes)){
					cauc.setEndtime(Timestamp.valueOf(endtimes));
				}
				if(!file.isEmpty()){
					String path = saveFile(session,file,"useCarApplicationAccessory/"+file.getOriginalFilename());
					cauc.setAccessory("control/applicationUseCar/getfile?url="+path);
				}
				
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("添加用车申请");
					adminLog.setActionContent("管理员：" + admin.getNickname()
							+ "，申请人：" + cauc.getPerson() + "，申请Id："
							+ cauc.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
				baseService.save(cauc);
			}else{
				CgApplicationUseCar fcauc = (CgApplicationUseCar) baseService.findEntity(CgApplicationUseCar.class, cauc.getId());
				if(!isEmpty(startTimes)){
					fcauc.setStartTime(Timestamp.valueOf(startTimes));
				}else{
					fcauc.setStartTime(null);
				}
				if(!isEmpty(endtimes)){
					fcauc.setEndtime(Timestamp.valueOf(endtimes));
				}else{
					fcauc.setEndtime(null);
				}
				fcauc.setOddNumbers(cauc.getOddNumbers());
				fcauc.setType(cauc.getType());
				fcauc.setDeptId(cauc.getDeptId());
				fcauc.setPerson(cauc.getPerson());
				fcauc.setUsecarType(cauc.getUsecarType());
				fcauc.setUsecarReason(cauc.getUsecarReason());
				fcauc.setPointOfDeparture(cauc.getPointOfDeparture());
				fcauc.setDestination(cauc.getDestination());
				fcauc.setNumberOfPeope(cauc.getNumberOfPeope());
				fcauc.setRideHead(cauc.getRideHead());
				fcauc.setRequestNoteType(cauc.getRequestNoteType());
				
				if(!file.isEmpty()){
					String path = saveFile(session,file,"useCarApplicationAccessory/"+file.getOriginalFilename());
					fcauc.setAccessory("control/applicationUseCar/getfile?url="+path);
				}
				
				
				baseService.update(fcauc);
				
				if(admin!=null){
					CgAdminLog adminLog = new CgAdminLog();
					adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
					adminLog.setActionIp(GetIpUtil.getIpAddr(request));
					adminLog.setActionType(1);
					adminLog.setActionDesc("修改用车");
					adminLog.setActionContent("操作管理员：" + admin.getNickname()
							+ "，申请人：" + cauc.getPerson() + "，用车申请Id："
							+ cauc.getId());
					adminLog.setAdminId(admin.getId());
					adminLog.setAdmin(admin);
					adminLog.setId("cal-" + UUID.randomUUID().toString());
					adminLogService.save(adminLog);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "redirect:/control/investor/goto/addInvestor";
		return "1";
	}

	/**
	 * 删除投资人
	 * 
	 * @return
	 */
	@RequestMapping("delete/deleteApplicationUseCar")
	@ResponseBody
	public String deleteApplicationUseCar(HttpServletRequest request,
			HttpSession session, String ids) {
		CgAdmin admin = null;
		try {
			admin = (CgAdmin) session.getAttribute("admin");
			if (admin != null) {
				// 管理员操作 要创建日志
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgApplicationUseCar investor = (CgApplicationUseCar) baseService.findEntity(CgApplicationUseCar.class, id);
						if (investor != null) {
							CgAdminLog adminLog = new CgAdminLog();
							adminLog.setActionDate(new Timestamp(System
									.currentTimeMillis()));
							adminLog.setActionIp(GetIpUtil.getIpAddr(request));
							adminLog.setActionType(1);
							adminLog.setActionDesc("删除用车申请");
							adminLog.setActionContent("管理员："
									+ admin.getNickname() + "，删除用车申请："
									+ investor.getId());
							adminLog.setAdminId(admin.getId());
							adminLog.setAdmin(admin);
							adminLog.setId("cal-"
									+ UUID.randomUUID().toString());
							baseService.deleteByAdmin(investor, adminLog);
							//ImageController.deleteObjImage(investor,session);
						}
					}
				}
			} else {
				String[] idarrays = new Gson().fromJson(ids, String[].class);
				for (String id : idarrays) {
					if (id != null) {
						CgApplicationUseCar investor = (CgApplicationUseCar) baseService.findEntity(CgApplicationUseCar.class, id);
						if (investor != null) {
							baseService.delete(investor);
							//ImageController.deleteObjImage(investor,session);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}

	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 上传附件
	 * 
	 * */
	/**
	 * 保存上传文件，并将其相对路径返回
	 * 
	 * @param session
	 * @param logo
	 *            上传文件
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(HttpSession session, MultipartFile logo,
			String path) throws IOException {
		if (!logo.isEmpty()) {
			try {
				String filepath = filePath(session, path);
				File file3 = new File(filepath);
				//创建文件夹
				if(!file3.exists()){
					file3.mkdirs();
				}
				logo.transferTo(file3);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return path;
		}
		return "";
	}
	
	public static String filePath(HttpSession session, String filename)
			throws Exception, IOException {
		
		String[] paths = filename.split("/");
		StringBuffer dirPath = new StringBuffer();
		dirPath.append(session.getServletContext().getRealPath("/"));
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("WEB-INF");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("classes");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("conf");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("path.properties");
		Properties pps = new Properties();
		pps.load(new FileInputStream(dirPath.toString()));

		File dir = new File(pps.getProperty("path"));
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		if (paths.length > 0) {
			dirPath = new StringBuffer(pps.getProperty("path"));
			for (int i = 0; i < paths.length - 1; i++) {
				if (paths[i].length() > 0) {
					dirPath.append(System.getProperty("file.separator"));
					dirPath.append(paths[i]);
				}
			}
			dir = new File(dirPath.toString());
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			dirPath.append(System.getProperty("file.separator"));
			dirPath.append(paths[paths.length - 1]);
		}

		return dirPath.toString();
	}
	
	
	@RequestMapping("getfile")
	@ResponseBody
	public void getfile(HttpSession session, HttpServletResponse resp,HttpServletRequest request, String url)
			throws IOException {
		String newUrl = new String(url.getBytes("iso8859-1"),"utf-8");//get请求默认iso8859-1编码，中文参数到后台会乱码
		
		String[] urls = newUrl.split("/");
		resp.setHeader("Content-Disposition", "attachment;filename="+ new String((urls[urls.length-1]).getBytes(), "iso-8859-1"));
		String path = null;
		try {
			path = filePath(session, newUrl);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (path == null) {
			return;
		}
		OutputStream os = null;
		try {
			byte[] buffer = null;
			File file = new File(path);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (Exception e) {
				return;
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			try {
				fis.close();
				bos.close();
			} catch (Exception e) {
			}
			buffer = bos.toByteArray();
			os = resp.getOutputStream();
			if (buffer != null) {
				os.write(buffer);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
		
	
}
