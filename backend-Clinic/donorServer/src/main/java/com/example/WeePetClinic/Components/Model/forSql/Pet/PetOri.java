package com.example.WeePetClinic.Components.Model.forSql.Pet;

import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;

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
