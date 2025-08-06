package com.example.WeePetClinic.Components.RestController.requestDTO;

import com.example.WeePetClinic.utilities.TypeUser;

import java.util.HashMap;
import java.util.Map;

public class logInReq {
  private String userID;
  private String password;
  private Map<TypeUser, String> roles = new HashMap<>();
  private TypeUser role;

  public TypeUser getRole() {
    return role;
  }

  public void setRole(TypeUser role) {
    this.role = role;
  }

  public Map<TypeUser, String> getRoles() {
    return roles;
  }

  public void setRoles(Map<TypeUser, String> roles) {
    this.roles = roles;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
} 