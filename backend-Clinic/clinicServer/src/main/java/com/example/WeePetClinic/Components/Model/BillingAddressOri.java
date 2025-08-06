package com.example.WeePetClinic.Components.Model;

public interface BillingAddressOri {
  int getStreetNumber();

  void setStreetNumber(int streetNumber);

  String getStreetName();

  void setStreetName(String streetName);

  String getTown();

  void setTown(String town);

  String getStateAbbrev();

  void setStateAbbrev(String stateAbbrev);

  String getZip();

  void setZip(String zip);

  String getCountry();

  void setCountry(String country);
}
