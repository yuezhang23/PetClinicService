package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantImp;
import com.example.WeePetClinic.Components.Service.doubleRole.ServiceEmpAccountant;
import com.example.WeePetClinic.Components.RestController.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class accountantManager implements Manager<UserEmpAccountantImp> {
  private final ServiceEmpAccountant<UserEmpAccountantImp> accountantService;
  private UserEmpAccountantImp accountant;

  @Autowired
  public accountantManager(ServiceEmpAccountant<UserEmpAccountantImp> accountantService) {
    this.accountantService = accountantService;
  }

  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public UserEmpAccountantImp login(String userID) {
    this.accountant = accountantService.getAccountant(userID);
    return accountant;
  }
} 