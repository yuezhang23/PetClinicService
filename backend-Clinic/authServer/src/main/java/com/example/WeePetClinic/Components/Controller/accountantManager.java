package com.example.WeePetClinic.Components.Controller;

import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantImp;
import com.example.WeePetClinic.Components.Service.doubleRole.ServiceEmpAccountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/service/accountant")
public class accountantManager implements Manager<UserEmpAccountantImp> {
  private ServiceEmpAccountant<UserEmpAccountantImp> accountantService;
  private UserEmpAccountantImp account = new UserEmpAccountantImp();

  @Autowired
  public accountantManager(ServiceEmpAccountant<UserEmpAccountantImp> accountantService) {
    this.accountantService = accountantService;
  }

  @Override
  public void signUp(UserEmpAccountantImp info) {

  }

  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public UserEmpAccountantImp login(String userID) {
    return null;
  }


}
