package com.cg.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ICarInsuranceManagerService {
	public void deleteCarInsurance(String ids,HttpSession session,HttpServletRequest request) throws Exception;

	public void deleteInsurance(HttpSession session,HttpServletRequest request,String idc) throws Exception;
}
