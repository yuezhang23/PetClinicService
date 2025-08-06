package com.example.WeePetClinic.Components.Repository.repoDTO;

import java.sql.Date;

public interface ProjAppointmentInClinic {
  int getAppointment();
  Date getMeeting_time();
  String getPet();
  String getFamily();
  String getBasic_description();
  String getVeterinarian();
}
