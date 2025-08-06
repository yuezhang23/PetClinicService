package com.example.WeePetClinic.Components.Service;

import com.example.WeePetClinic.Components.Model.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForVet;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.clinicService.MedicationImpl;
import com.example.WeePetClinic.Components.Model.clinicService.TreatmentImpl;
import com.example.WeePetClinic.Components.Model.clinicService.medicationInAppointment;
import com.example.WeePetClinic.Components.Model.clinicService.treatmentInAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;

import java.util.List;

public interface ServiceEmpVet extends ServiceUserGeneral<UserEmpVetOri> {
  //getter setter
  repoAccessForVet getAccessVet();
  void setAccessVet(repoAccessForVet accessVet);

  ClinicOri getClinicByVet(String vetID);
  ProjVetEmpDetail getEmpVetFullProfile(String id);
  UserEmpVetOri getEmpVet(String id);

  // appointment repo for vet
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

  // services for vet
  List<MedicationImpl> displayAvailableMedicationsForVet(UserEmpVetImpl vet);
  List<TreatmentImpl> displayAvailableTreatmentsForVet(UserEmpVetImpl vet);
}
