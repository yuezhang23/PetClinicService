package com.example.WeePetClinic.Components.Model.User;

import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;

import java.util.List;

public interface UserEmpVetOri {
  //getters setters
  List<AppointmentImp> getAppointments();

  void setAppointments(List<AppointmentImp> appointments);

  String getVetID();

  void setVetID(String userID);

  String getLicense();

  void setLicense(String license);

  String getCertificate();

  void setCertificate(String certificate);

  int getYearsOfWork();

  void setYearsOfWork(int yearsOfWork);

  String getSpecialization();

  void setSpecialization(String specialization);
}
