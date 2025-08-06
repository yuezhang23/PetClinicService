package com.example.WeePetClinic.Components.Service.postSqlUser;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;
import com.example.WeePetClinic.Components.Service.postSqlUser.Encapsulation.repoAccessForDonor;

public interface ServiceClientDonor<UserClientDonorOri> extends ServiceUserGeneral<UserClientDonorOri> {

  //getter setter
  repoAccessForDonor getAccessDonor();
  void setAccessDonor(repoAccessForDonor accessDonor);

  //crud
  void setDonor(UserClientDonorOri emp);
  void deleteDonorById(String donorId);
  void addDonor(UserClientDonorOri emp);
  UserClientDonorOri getDonor(String id);

  //logic
  Boolean makeDonation(double invoiceAmount);

}
