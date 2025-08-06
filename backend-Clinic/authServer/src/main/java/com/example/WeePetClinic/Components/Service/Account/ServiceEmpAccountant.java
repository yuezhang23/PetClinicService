package com.example.WeePetClinic.Components.Service.Account;

import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorOri;
import com.example.WeePetClinic.Components.Model.forPostSql.donateService.DonationOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerOri;

import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;

import java.sql.Timestamp;
import java.util.List;

public interface ServiceEmpAccountant<UserEmpAccountantOri> extends ServiceUserGeneral<UserEmpAccountantOri> {
  //getters setters
  repoAccessForAccountant getAccessAccountant();
  void setAccessAccountant(repoAccessForAccountant accessAccountant);

  ClinicImp getDetailForClinic(int clinicID);

  //crud
  UserEmpAccountantOri getAccountant(String id);

  UserEmployeeImpl getEmpAccountant(String id);

  void updateEmpAccountant(UserEmpAccountantOri emp);
  void deleteEmpAccountant(String id);
  void addEmpAccountant(UserEmpAccountantOri emp);

  //postPreSql
  List<ProjDonationDetail> getDonorTransactionList(UserClientDonorOri donor);

  List<UserClientDonorOri> getDonorsListByTime(Timestamp start, Timestamp end);

  List<DonationOri> getDonationsByTime(Timestamp start, Timestamp end);

  double findTopDonationByTime(Timestamp start, Timestamp end);

  UserClientDonorOri findDonorWithTopDonation();

  UserClientDonorOri findDonorWithTopTimesOfDonations();

  //sql
  List<ProjAppointmentDetail> displayTransactionsByVet(UserEmpVetOri vet);

  UserEmpVetOri displayVetProfile(String vetID);

  UserClientOri displayPetOwnerProfile(String clientID);

  List<ProjAppointmentDetail> displayTransactionsByPetOwner(UserClientPetOwnerOri owner);

}
