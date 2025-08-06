package com.example.WeePetClinic.Components.Model;

import com.example.WeePetClinic.utilities.TypeUser;

public interface UserOri {
  String getUserID();

  void setUserID(String userID);

  String getPassword();

  void setPassword(String password);

  String getFirstName();

  void setFirstName(String firstName);

  String getLastName();

  void setLastName(String lastName);

  String getPhone();

  void setPhone(String phone);

  // shared getter setter

  BillingAddressImpl getAddress();

  void setAddress(BillingAddressImpl address);

  TypeUser getUserType();

  void setUserType(TypeUser userType);

  String getEmail();

  void setEmail(String email);

}
