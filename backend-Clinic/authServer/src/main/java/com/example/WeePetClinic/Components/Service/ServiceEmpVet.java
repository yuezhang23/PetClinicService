package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;

import java.util.List;
import java.util.Optional;

public interface ServiceEmpVet<T> extends ServiceUserGeneral<T> {
  //getter setter
  void setAccessVet(Object accessVet);

  ClinicImp getClinicById(int clinic_id);

  // crud
  Optional<UserEmpVetOri> getEmpVet(String id);
  void setEmpVet(UserEmpVetImpl vet, UserEmployeeOri emp);
  void deleteEmpVet(String id);
  void addEmpVet(UserEmpVetOri owner, UserEmployeeOri emp);

  ProjVetEmpDetail getEmpVetFullProfile(String id);

  // appointment repo for vet
  List<AppointmentOri> displayAppointmentsForPet(PetOri pet, UserEmpVetOri vet);
  List<AppointmentOri> displayAppointmentsByPetAndVet(PetOri pet, UserEmpVetOri vet);
} 