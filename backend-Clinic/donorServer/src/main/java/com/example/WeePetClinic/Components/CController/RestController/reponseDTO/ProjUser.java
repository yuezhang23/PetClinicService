package com.example.WeePetClinic.Components.CController.RestController.reponseDTO;

import com.example.WeePetClinic.Components.Model.BillingAddressImpl;

public class ProjUser {
  private String emp_last_name;
  private String emp_first_name;
  private String phone;
  private BillingAddressImpl address;

  public ProjUser(String emp_last_name, String emp_first_name, String phone, BillingAddressImpl address) {
    this.emp_last_name = emp_last_name;
    this.emp_first_name = emp_first_name;
    this.phone = phone;
    this.address = address;
  }

  public String getEmp_last_name() {
    return emp_last_name;
  }

  public void setEmp_last_name(String emp_last_name) {
    this.emp_last_name = emp_last_name;
  }

  public String getEmp_first_name() {
    return emp_first_name;
  }

  public void setEmp_first_name(String emp_first_name) {
    this.emp_first_name = emp_first_name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public BillingAddressImpl getAddress() {
    return address;
  }

  public void setAddress(BillingAddressImpl address) {
    this.address = address;
  }
}
