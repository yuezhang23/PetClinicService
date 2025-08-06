package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.Pet.PetOri;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForAdmin;

import java.time.LocalDateTime;

public interface ServiceEmpAdmin extends ServiceUserGeneral<UserEmployeeOri> {
  //getter setter
  repoAccessForAdmin getAccessEmp();
  void setAccessEmp(repoAccessForAdmin accessEmp);
  sqlServiceAppointmentCRUD getAccessApt();
  void setAccessApt(sqlServiceAppointmentCRUD accessApt);

  // logic
  int is_booking_full(LocalDateTime appoint_time, UserEmployeeOri user);

  boolean makeNewAppointmentByAdmin(AppointmentOri apt);

  boolean updateAppointmentByAdmin(AppointmentOri apt);

  boolean cancelAppointmentByAdmin(int appointID);

  AppointmentOri searchAppointmentByPetAndTime(PetOri pet, LocalDateTime time);

  AppointmentOri getAppointmentForAdmin(UserEmployeeOri admin, int appointID);

  boolean petCheckIn(int aptID);

  //


}
