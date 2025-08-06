package com.example.WeePetClinic.Components.Service.sqlService;

import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;
import com.example.WeePetClinic.Components.Service.sqlService.Encapsulation.repoAccessForVet;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.MedicationImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.TreatmentImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.medicationInAppointment;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.treatmentInAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;

import java.util.List;
import java.util.Optional;

public interface ServiceEmpVet<UserEmpVetOri> extends ServiceUserGeneral<UserEmpVetOri> {
  //getter setter
  repoAccessForVet getAccessVet();
  void setAccessVet(repoAccessForVet accessVet);

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

  List<ProjMedsTreatsForAppointment> displayAllTreatsAndMedsForAppointment(AppointmentOri apt);

  boolean appointmentComplete(AppointmentOri appointment);

  boolean treatmentAdd(TreatmentImpl service, AppointmentOri appointment, double actualPrice);

  boolean treatmentDelete(treatmentInAppointment service);

  boolean treatmentModify(treatmentInAppointment oldService, treatmentInAppointment newService);

  boolean medicationAdd(medicationInAppointment service, AppointmentOri appointment, int quantity, int period);

  boolean medicationDelete(medicationInAppointment service);

  boolean medicationModify(medicationInAppointment oldService, medicationInAppointment newService);

  // services for vet
  List<MedicationImpl> displayAvailableMedicationsForVet(UserEmpVetOri vet);

  List<TreatmentImpl> displayAvailableTreatmentsForVet(UserEmpVetOri vet);
}
