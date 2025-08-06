package com.example.WeePetClinic.Components.Model.clinicService;

import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerImpl;

public interface InvoiceForClinic extends InvoiceOri  {

  AppointmentImp getAppointment();

  void setAppointment(AppointmentImp appointment);

  UserClientPetOwnerImpl getPetOwner();

  void setPetOwner(UserClientPetOwnerImpl petOwner);
}
