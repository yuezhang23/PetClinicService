package com.example.WeePetClinic.Components.Service.sqlService;
import com.example.WeePetClinic.Components.CController.RestController.reponseDTO.ProjUser;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Service.ServiceUserGeneral;
import com.example.WeePetClinic.Components.Service.sqlService.Encapsulation.repoAccessForAdmin;
import com.example.WeePetClinic.Components.Service.sqlService.ImplementionSql.sqlServiceAppointmentCRUD;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceEmpAdmin<UserEmployeeOri> extends ServiceUserGeneral<UserEmployeeOri> {
  //getter setter
  repoAccessForAdmin getAccessEmp();
  void setAccessEmp(repoAccessForAdmin accessEmp);
  sqlServiceAppointmentCRUD getAccessApt();
  void setAccessApt(sqlServiceAppointmentCRUD accessApt);

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

  // apt when on phone, Admin only

}
