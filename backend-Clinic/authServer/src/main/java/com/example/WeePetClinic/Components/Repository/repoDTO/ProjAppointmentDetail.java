package com.example.WeePetClinic.Components.Repository.repoDTO;

import java.time.LocalDate;

public interface ProjAppointmentDetail {
  int getClinic_id();
  int getAppointment_id();
  LocalDate getMeeting_time();
  String getPet();
  String getPet_owner();
  String getBasic_description();
  String getVeterinarian();
  int getRoom();
  double getVisit_expenditure();
  double getConfirmed_amt();
  LocalDate getReceived_date();
} 