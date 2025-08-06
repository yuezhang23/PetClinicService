package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Service.doubleRole.repoAccessForAccountant;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
@Service
public class ServiceUserLoginImpl implements ServiceUserLogin {
  // repository
  private final repoAccessForAccountant accountantService;
  private Map<TypeUser, String> typeUsers = new HashMap<>();

  @Autowired
  public ServiceUserLoginImpl(repoAccessForAccountant accountantService) {
    this.accountantService = accountantService;
  }

  @Override
  public Map<TypeUser, String> signInRoles(String validateUser, String validatePassword) {
      typeUsers = accountantService.validateMultipleRole(validateUser, validatePassword);
      return typeUsers;
  }

  @Override
  public boolean passwordReset(UserOri user, String newPassword) {
    typeUsers = accountantService.validateMultipleRole(user.getUserID(), user.getPassword());
    if (typeUsers == null || typeUsers.isEmpty()) {
      return false;
    } else {
      if (user.getPassword().equals(newPassword)) {
        System.out.println("Don't use the same Password");
      }
      for (TypeUser type : TypeUser.values()) {
        if (typeUsers.get(type) != null) {
          accountantService.findUserByIdAndType(type, user.getUserID()).setPassword(newPassword);
        }
      }
      System.out.println("Password updated successfully.");
      return true;
    }
  }
} 