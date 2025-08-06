package com.example.WeePetClinic.Components.Model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserOriAbs implements UserOri {
  @Id
  @Column(name = "emp_id")
  private String userID;
  @Column(name = "id_password")
  private String password;
  @Column(name = "emp_first_name")
  private String firstName;
  @Column(name = "emp_last_name")
  private String lastName;
  @Column(name = "phone")
  private String phone;
  @Column (name = "email")
  private String email;
  @Embedded
  private BillingAddressImpl address;
  private com.example.WeePetClinic.utilities.TypeUser userType;

  // getters setters
  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getUserID() {
    return userID;
  }

  @Override
  public void setUserID(String userID) {
    this.userID = userID;
  }


  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }


  @Override
  public BillingAddressImpl getAddress() {
    return address;
  }

  @Override
  public void setAddress(BillingAddressImpl address) {
    this.address = address;
  }

  @Override
  public com.example.WeePetClinic.utilities.TypeUser getUserType() {
    return userType;
  }

  @Override
  public void setUserType(com.example.WeePetClinic.utilities.TypeUser userType) {
    this.userType = userType;
  }

}





