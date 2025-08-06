package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicImp;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForAccountant;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;

import java.util.List;
import java.util.Optional;

public interface ServiceEmpAccountant extends ServiceUserGeneral<UserEmployeeOri> {
  //getters setters
  repoAccessForAccountant getAccessAccountant();
  void setAccessAccountant(repoAccessForAccountant accessAccountant);

  //crud emp
  List<UserEmployeeOri> getAllEmpByClinic(int clinicID);
  List<UserEmployeeOri> getAllEmployees();
  UserEmployeeOri getEmp(String id);
  boolean updateEmp(UserEmployeeOri emp);
  boolean deleteEmp(String id);
  boolean addEmp(UserEmployeeOri emp);

  // crud vet
  UserEmpVetOri getEmpVet(String id);
  boolean setEmpVet(UserEmpVetOri vet, UserEmployeeOri emp);
  boolean deleteEmpVet(String id);
  boolean addEmpVet(UserEmpVetOri owner, UserEmployeeOri emp);


  //crud user
  UserClientOri getClient(String id);
  boolean updateClient(UserClientOri user);
  boolean deleteClient(String id);
  boolean addClient(UserClientOri user);

  //crud clinic
  ClinicImp getDetailForClinic(int clinicID);
  boolean updateClinic(ClinicImp clinic);
  boolean deleteClinic(int id);
  boolean addClinic(ClinicImp clinic);

  // business
  List<ProjAppointmentDetail> displayTransactionsByVet(UserEmpVetOri vet);
  UserEmpVetOri displayVetProfile(String vetID);
  UserClientOri displayPetOwnerProfile(String clientID);
  List<ProjAppointmentDetail> displayTransactionsByPetOwner(UserClientPetOwnerOri owner);

}
