package com.example.WeePetClinic.Components.Model.forSql.clinicService;

import com.example.WeePetClinic.utilities.TypeService;

public interface ClinicServiceOri<T> {
  T getServiceID();

 void setServiceId (T serviceId);

  TypeService getServiceType();

  String getServiceName();

  void setServiceName(String medName);

  String getDescription();

  void setDescription(String description);

  double getRegularPrice();

  void setRegularPrice(double price);
}
