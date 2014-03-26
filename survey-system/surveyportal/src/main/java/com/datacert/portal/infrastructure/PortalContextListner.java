package com.datacert.portal.infrastructure;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.datacert.surveysystem.db.AdminServiceImpl;
import com.datacert.surveysystem.dto.DaoCoreConstant;

public class PortalContextListner {

  @Resource
  ServletContext context;

  @Resource
  private AdminServiceImpl adminService;

  public void initIt() {
	context.setAttribute(DaoCoreConstant.SURVEY_ADMIN_EMAIL, adminService.getAdminEmail());
  }
  
  public void cleanUp() {
	return;
   }

}
