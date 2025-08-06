package com.example.WeePetClinic.Components.Model.forSql.User;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentImp;

import java.util.List;

public interface UserEmployeeOri extends UserOri {
  String getStatement();

  void setStatement(String statement);

  ClinicImp getClinic();

  void setClinic(ClinicImp clinic);

  List<AppointmentImp> getAppointments();

  void setAppointments(List<AppointmentImp> appointments);

}
