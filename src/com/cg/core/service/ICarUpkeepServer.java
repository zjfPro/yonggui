package com.cg.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ICarUpkeepServer {
	public void deleteCarUpkeep(String ids,HttpSession session,HttpServletRequest request) throws Exception;

	public void deleteUpkeep(HttpSession session, HttpServletRequest request,
			String idc)throws Exception;
}
