package com.cg.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.cgcheck.CgCheckException;
import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgStaffManageModel;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.common.entity.CgStaffManageRoleContainerMapping;
import com.cg.common.utils.GetIpUtil;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IAuthorityManagementService;

@Service
public class AuthorityManagementServiceImpl implements
		IAuthorityManagementService {
	@Autowired
	private IBaseDao baseDao;

	@Override
	public void addCgStaffManageRoleContainerMapping(String ids, String name,String shopFrontId)
			throws Exception {
		CgStaffManageRoleContainer csmrc = new CgStaffManageRoleContainer();
		csmrc.setId("csmrc-" + UUID.randomUUID().toString());
		csmrc.setName(name);
		csmrc.setShopFrontId(shopFrontId);
		baseDao.save(csmrc);
		String[] split = ids.split(",");
		for (String id : split) {
			CgStaffManageRoleContainerMapping mapping = new CgStaffManageRoleContainerMapping();
			mapping.setStaffManageModelid(Integer.valueOf(id));
			mapping.setStaffManageRoleContainerId(csmrc.getId());
			baseDao.save(mapping);
		}
	}

	@Override
	public void updateCgStaffManageRoleContainerMapping(String ids,
			String name, String csmrcID,String shopFrontId) throws Exception {
		CgStaffManageRoleContainer csmrc = (CgStaffManageRoleContainer) baseDao
				.findEntity(CgStaffManageRoleContainer.class, csmrcID);
		csmrc.setName(name);
		csmrc.setShopFrontId(shopFrontId);
		baseDao.update(csmrc);
		String hql = "from CgStaffManageRoleContainerMapping where staffManageRoleContainerId = ?";
		List<Object> parmas = new ArrayList<Object>();
		parmas.add(csmrcID);
		List<CgStaffManageRoleContainerMapping> mapings = baseDao.find(hql,
				parmas);
		for (CgStaffManageRoleContainerMapping cg : mapings) {
			baseDao.delete(cg);
		}
		String[] split = ids.split(",");
		for (String id : split) {
			CgStaffManageRoleContainerMapping mapping = new CgStaffManageRoleContainerMapping();
			mapping.setStaffManageModelid(Integer.valueOf(id));
			mapping.setStaffManageRoleContainerId(csmrc.getId());
			baseDao.save(mapping);
		}
	}

	@Override
	public void deleteModel(String ids) throws Exception {
		String[] split = ids.split(",");
		List<CgStaffManageModel>  parent = new ArrayList<CgStaffManageModel>();
		List<CgStaffManageModel>  son = new ArrayList<CgStaffManageModel>();
		for (String id : split) {
			CgStaffManageModel find = (CgStaffManageModel) baseDao.findEntity(CgStaffManageModel.class, Integer.valueOf(id));
			if(find.getUrl().equals("")){
				parent.add(find);
			}else{
				son.add(find);
			}
		}
		if(parent.size()>0){
			for(CgStaffManageModel c:parent){
				String hql = "from CgStaffManageModel where fatId = ?";
				List<Object> params = new ArrayList<Object>();
				params.add(c.getId());
				List<CgStaffManageModel> finds = baseDao.find(hql, params);
				if(finds!=null){
					if(finds.size()!=0){
						throw new CgCheckException("父模块还含有子模块，不能删除");
					}else{
						baseDao.delete(c);
					}
				}
			}
		}
		
		if(son.size()>0){
			for(CgStaffManageModel sc:son){
				baseDao.delete(sc);
				String hql = "from CgStaffManageRoleContainerMapping where staffManageModelid = ?";
				List<Object> params = new ArrayList<Object>();
				params.add(sc.getId());
				List<CgStaffManageRoleContainerMapping> finds = baseDao.find(hql, params);
				if(finds!=null){
					if(finds.size()>0){
						for(CgStaffManageRoleContainerMapping mappings:finds){
							baseDao.delete(mappings);
						}
						
					}
				}
			}
		}
		
		
	}

	@Override
	public void deleteRole(HttpServletRequest request,HttpSession session,String ids) throws Exception {
		String[] split = ids.split(",");
		CgAdmin admin = (CgAdmin) session.getAttribute("admin");
		CgAdminLog adminLog = null;
		
		for (String id : split) {
			
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("删除角色");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除角色：" + id);
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseDao.save(adminLog);
			}
			
			String hql = "delete from CgStaffManageRoleContainerMapping where staffManageRoleContainerId = ?";
			List<Object> parmas = new ArrayList<Object>();
			parmas.add(id);
			baseDao.executeHQL(hql, parmas);
			
			String hql2 = "delete from CgStaffManageRoleContainer where id = ?";
			List<Object> parmas2 = new ArrayList<Object>();
			parmas2.add(id);
			baseDao.executeHQL(hql2, parmas2);
			
		}
	}
}
