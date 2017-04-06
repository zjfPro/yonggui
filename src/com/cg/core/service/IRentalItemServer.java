package com.cg.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IRentalItemServer {
	public void deleteCarRentalItem(String ids,HttpSession session,HttpServletRequest request) throws Exception;
}
