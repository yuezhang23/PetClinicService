package com.example.WeePetClinic.Components.Model.forSql;

import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentImp;

public interface InvoiceForClinic extends InvoiceOri  {

  AppointmentImp getAppointment();

  void setAppointment(AppointmentImp appointment);

  UserClientPetOwnerImpl getPetOwner();

  void setPetOwner(UserClientPetOwnerImpl petOwner);
}
