package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;

public interface ServiceClientDonor<T> extends ServiceUserGeneral<T> {
  //getter setter
  void setAccessDonor(Object accessDonor);

  //crud
  void setDonor(UserClientDonorImpl emp);
  void deleteDonorById(String donorId);
  void addDonor(UserClientDonorImpl emp);
  UserClientDonorImpl getDonor(String id);

  //logic
  Boolean makeDonation(double invoiceAmount);
} 