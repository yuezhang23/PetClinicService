package com.example.WeePetClinic.Components.RestController;

import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Service.ServiceEmpAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/service/admin")
public class adminManager implements Manager<UserEmployeeImpl> {
  private ServiceEmpAdmin adminService;
  private UserEmployeeImpl admin = new UserEmployeeImpl();

  @Autowired
  public adminManager(ServiceEmpAdmin adminService) {
    this.adminService = adminService;
  }


  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public UserEmployeeImpl login(String userID) {
    return null;
  }


}
