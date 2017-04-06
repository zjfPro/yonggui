package com.cg.core.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;
import com.cg.common.entity.CgCarInsurance;
import com.cg.common.utils.GetIpUtil;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.ICarInsuranceManagerService;

@Service
public class CarInsuranceManagerServiceImpl implements ICarInsuranceManagerService{
	@Autowired
	private IBaseDao baseDao;

	@Override
	public void deleteCarInsurance(String ids, HttpSession session,
			HttpServletRequest request) throws Exception {
		String[] split = ids.split(",");
		for (String id : split) {
			
			CgCarInsurance cci = (CgCarInsurance) baseDao.findEntity(CgCarInsurance.class, id);
			cci.setIsdel(1);
			cci.setDeltime(new Timestamp(System.currentTimeMillis()));
			baseDao.update(cci);
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("删除车辆保险");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆保险：保险人：" + cci.getInsured()+" 保险单："+cci.getInsuranceCode());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseDao.save(adminLog);
			}
			
		}
	}

	@Override
	public void deleteInsurance(HttpSession session,HttpServletRequest request,String idc) throws Exception {
		// TODO Auto-generated method stub
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				CgCarInsurance cci = (CgCarInsurance) baseDao.findEntity(CgCarInsurance.class, idc);
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(3);
				adminLog.setActionDesc("删除车辆保险");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除车辆保险：保险人：" + cci.getInsured()+" 保险单："+cci.getInsuranceCode());
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
			}
			baseDao.updateForHql("delete from CgCarInsurance where id = ?", new Object[] { idc });
			baseDao.save(adminLog);
		}
}
