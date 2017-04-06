package com.cg.core.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarRentalItem;
import com.cg.common.utils.GetIpUtil;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IRentalItemServer;

@Service
public class RentalItemServerImpl implements IRentalItemServer{
	
	@Autowired
	private IBaseDao baseDao;

	@Override
	public void deleteCarRentalItem(String ids, HttpSession session,
			HttpServletRequest request) throws Exception {
		
		String[] split = ids.split(",");
		for (String id : split) {
			
			CgCarRentalItem cci = (CgCarRentalItem) baseDao.findEntity(CgCarRentalItem.class, id);
			cci.setStatus(-2);
			baseDao.update(cci);
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("删除租车项目");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除租车项目：" + cci.getId());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseDao.save(adminLog);
			}
			
		}
	}

}
