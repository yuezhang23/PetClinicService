package com.example.WeePetClinic.Components.Repository.repoDTO;

import com.example.WeePetClinic.utilities.TypeService;

public interface ProjMedsTreatsForAppointment {
  int getAppointment_id();
  String getName();
  String getDescription();
  double getPrice();
  TypeService getType();
}
