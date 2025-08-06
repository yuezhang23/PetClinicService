package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Service.ServiceEmpAdmin;
import com.example.WeePetClinic.Components.RestController.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class adminManager implements Manager<UserEmployeeImpl> {
  private final ServiceEmpAdmin<UserEmployeeImpl> adminService;
  private UserEmployeeImpl admin = new UserEmployeeImpl();

  @Autowired
  public adminManager(ServiceEmpAdmin<UserEmployeeImpl> adminService) {
    this.adminService = adminService;
  }

  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public UserEmployeeImpl login(String userID) {
    this.admin = (UserEmployeeImpl) adminService.getEmp(userID);
    return admin;
  }
} 