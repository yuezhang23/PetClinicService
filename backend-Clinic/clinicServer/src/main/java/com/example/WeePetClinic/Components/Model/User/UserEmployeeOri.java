package com.example.WeePetClinic.Components.Model.User;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicImp;

import java.util.List;

public interface UserEmployeeOri extends UserOri {
  String getStatement();

  void setStatement(String statement);

  ClinicImp getClinic();

  void setClinic(ClinicImp clinic);

  List<AppointmentImp> getAppointments();

  void setAppointments(List<AppointmentImp> appointments);

}
