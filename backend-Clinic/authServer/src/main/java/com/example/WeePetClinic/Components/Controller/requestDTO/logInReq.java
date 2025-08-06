package com.example.WeePetClinic.Components.Controller.requestDTO;

import com.example.WeePetClinic.utilities.TypeUser;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Map;
import java.util.HashMap;

public class logInReq {
  private String userID;
  private String password;
  @Enumerated(EnumType.STRING)
  private Map<TypeUser, String> roles = new HashMap<>();
  @Enumerated(EnumType.STRING)
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
