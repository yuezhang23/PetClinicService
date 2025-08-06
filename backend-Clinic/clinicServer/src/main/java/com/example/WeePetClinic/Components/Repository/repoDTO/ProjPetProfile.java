package com.example.WeePetClinic.Components.Repository.repoDTO;

import java.sql.Date;

public interface ProjPetProfile {
  int getPatient_id();
  String getPet_name();
  String getBreed_name();
  float getHeight_in_cm();
  float getWeight_in_lb();
  String getPet_status();
  Date getDate_of_birth();

}
