package com.example.WeePetClinic.Components.Repository.repoDTO;

import com.example.WeePetClinic.utilities.TypeUser;

public interface ProjVetEmpDetail {
  int getClinic_id();
  String getVet_license();
  String getVet_certificate();
  int getWork_years();
  String getSpecialization();

  String getVet_id();
  String getId_password();
  String getPhone();
  String getEmp_last_name();
  String getEmp_first_name();
  int getStreet_number();
  String getStreet_name();
  String getTown();
  String getState_abbrev();
  String getZip();
  String getStatement();

  TypeUser getEmp_type();
  String getEmail();
} 