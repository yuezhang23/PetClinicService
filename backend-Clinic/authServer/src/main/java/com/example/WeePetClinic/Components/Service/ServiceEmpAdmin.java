package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjUser;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceEmpAdmin<T> extends ServiceUserGeneral<T> {
  //getter setter
  void setAccessEmp(Object accessEmp);
  void setAccessApt(Object accessApt);

  //crud
  List<ProjUser> getAllEmps();

  //crud
  UserEmployeeOri getEmp(String id);
  void setEmp(UserEmployeeOri emp);
  void deleteEmp(String id);
  void addEmp(UserEmployeeOri owner);

  // logic
  int is_booking_full(LocalDateTime appoint_time, UserEmployeeOri user);

  boolean makeNewAppointmentByAdmin(AppointmentOri apt);

  boolean updateAppointmentByAdmin(AppointmentOri apt);

  boolean cancelAppointmentByAdmin(int appointID);

  AppointmentOri searchAppointmentByPetAndTime(PetOri pet, LocalDateTime time);

  AppointmentOri getAppointmentForAdmin(UserEmployeeOri admin, int appointID);

  boolean petCheckIn(int aptID);
} 