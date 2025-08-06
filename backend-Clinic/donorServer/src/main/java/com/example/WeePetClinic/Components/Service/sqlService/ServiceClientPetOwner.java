package com.example.WeePetClinic.Components.Service.sqlService;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;
import com.example.WeePetClinic.Components.Service.sqlService.Encapsulation.repoAccessForPetOwner;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjPetProfile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ServiceClientPetOwner<UserClientPetOwnerOri> extends ServiceUserGeneral<UserClientPetOwnerOri> {
  //getter setter
  repoAccessForPetOwner getAccessPetOwner();
  void setAccessPetOwner(repoAccessForPetOwner accessPetOwner);

  // logic

  Boolean makePayment(int appointmentId, double invoiceAmount);

  List<PetImpl> getPetsByOwner(String ownerID);

  List<ProjPetProfile> displayPetsInfo(UserClientPetOwnerOri owner);

  List<AppointmentOri> displayAppointmentsDetailsForPet(PetOri pet, UserClientPetOwnerOri petOwner);

  List<InvoiceOri> displayInvoicesForAppointment(int appointId, UserClientPetOwnerOri petOwner);

  Map<String, Double> displayAppointmentExpenditureForPet(PetOri pet, LocalDateTime start, LocalDateTime end);

  List<ProjMedsTreatsForAppointment> displayAllTreatsAndMedsForPet(PetOri pet);

  List<ProjAppointmentDetail> displayAppointmentsDetailsForUserInClinic(int clinicID, UserClientPetOwnerOri owner);

  // crud
  UserClientPetOwnerOri getPetOwner(String id);
  void setPetOwner(UserClientPetOwnerOri emp);
  void deletePetOwner(String id);
  void addPetOwner(UserClientPetOwnerOri owner);


  List<ProjAppointmentDetail> displayAppointmentsDetailsForPet(PetOri pet);
}
