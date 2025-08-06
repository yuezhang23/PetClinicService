package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForPetOwner;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.clinicService.InvoiceOri;
import com.example.WeePetClinic.Components.Model.Pet.PetOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjPetProfile;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ServiceClientPetOwner extends ServiceUserGeneral<UserClientPetOwnerOri> {
  //getter setter
  repoAccessForPetOwner getAccessPetOwner();
  void setAccessPetOwner(repoAccessForPetOwner accessPetOwner);

  // logic
  UserEmpVetImpl findVetByAppointment(int apt_id);

  //crud
  UserClientPetOwnerImpl getPetOwner(String id);

  Boolean makePayment(int appointmentId, double invoiceAmount);

  List<PetImpl> getPetsByOwner(String ownerID);

  List<ProjPetProfile> displayPetsInfo(UserClientPetOwnerOri owner);

  List<AppointmentOri> displayAppointmentsDetailsForPet(PetOri pet, UserClientPetOwnerOri petOwner);

  List<InvoiceOri> displayInvoicesForAppointment(int appointId, UserClientPetOwnerOri petOwner);

  Map<String, Double> displayAppointmentExpenditureForPet(PetOri pet, LocalDateTime start, LocalDateTime end);

  List<ProjMedsTreatsForAppointment> displayAllTreatsAndMedsForPet(PetOri pet);

  List<ProjAppointmentDetail> displayAppointmentsDetailsForUserInClinic(int clinicID, UserClientPetOwnerOri owner);


  List<ProjAppointmentDetail> displayAppointmentsDetailsForPet(PetOri pet);
}
