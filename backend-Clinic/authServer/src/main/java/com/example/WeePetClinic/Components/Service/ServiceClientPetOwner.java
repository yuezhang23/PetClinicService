package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ServiceClientPetOwner<T> extends ServiceUserGeneral<T> {
  //getter setter
  void setAccessPetOwner(Object accessPetOwner);

  // logic
  Boolean makePayment(int appointmentId, double invoiceAmount);

  List<PetImpl> getPetsByOwner(String ownerID);

  List<Object> displayPetsInfo(UserClientPetOwnerOri owner);

  List<AppointmentOri> displayAppointmentsDetailsForPet(PetOri pet, UserClientPetOwnerOri petOwner);

  List<InvoiceOri> displayInvoicesForAppointment(int appointId, UserClientPetOwnerOri petOwner);

  Map<String, Double> displayAppointmentExpenditureForPet(PetOri pet, LocalDateTime start, LocalDateTime end);

  List<ProjAppointmentDetail> displayAppointmentsDetailsForUserInClinic(int clinicID, UserClientPetOwnerOri owner);

  List<ProjAppointmentDetail> displayAppointmentsDetailsForPet(PetOri pet);

  //crud
  UserClientPetOwnerImpl getPetOwner(String id);
  void setPetOwner(UserClientPetOwnerImpl emp);
  void deletePetOwner(String id);
  void addPetOwner(UserClientPetOwnerImpl owner);
} 