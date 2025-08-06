package com.example.WeePetClinic.Components.Model.Pet;

import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;

import java.sql.Date;
import java.util.List;

public interface PetOri {
  List<AppointmentOri> getAppointments();
  void setAppointments(List<AppointmentOri> appointments);

  int getPetID();

  void setPetID(int petID);

  String getPetName();

  void setPetName(String petName);

  UserClientPetOwnerImpl getOwner();

  void setOwner(UserClientPetOwnerImpl owner);

  PetCat getCategory();

  void setCategory(PetCat category);

  Date getBirthDate();

  void setBirthDate(Date birthDate);

  Float getWeight();

  void setWeight(Float weight);

  Float getHeight();

  void setHeight(Float height);

  String getStatusOfHealth();

  void setStatusOfHealth(String statusOfHealth);
}
