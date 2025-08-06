package com.example.WeePetClinic.Components.Model.clinicService;
import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;

import java.time.LocalDateTime;

public interface AppointmentOri {

  // getter setter
  UserEmpVetImpl getVet();

  void setVet(UserEmpVetImpl vet);

  boolean getShowUp();

  void setShowUp(boolean showUp);

  // getter setter
  ClinicImp getClinic();

  void setClinic(ClinicImp clinic);

  int getAppointID();

  void setAppointID(int appointID);

  LocalDateTime getAppointment_time();

  void setAppointment_time(LocalDateTime appointment_time);

  String getDescription();

  void setDescription(String description);

  PetImpl getPet();

  void setPet(PetImpl pet);

  UserEmployeeImpl getAdmin();

  void setAdmin(UserEmployeeImpl admin);

  RoomImpl getRoom();

  void setRoom(RoomImpl room);
}
