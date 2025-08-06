package com.example.WeePetClinic.Components.Model.clinicService;

import com.example.WeePetClinic.Components.Model.BillingAddressImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;
import com.example.WeePetClinic.utilities.TypeClinic;

import java.util.List;

public interface ClinicOri {
  //getters setters
  TypeClinic getType();

  void setType(TypeClinic type);

  List<RoomImpl> getRooms();

  void setRooms(List<RoomImpl> rooms);

  List<AppointmentImp> getAppointments();

  void setAppointments(List<AppointmentImp> appointments);

  int getClinicID();

  void setClinicID(int clinicID);

  String getClinicPhone();

  void setClinicPhone(String phone);

  BillingAddressImpl getAddress();

  void setAddress(BillingAddressImpl address);

  TypeClinic getClinicType();

  void setClinicType(TypeClinic type);

  List<UserEmployeeImpl> getEmployees();

  void setEmployees(List<UserEmployeeImpl> employees);
}
