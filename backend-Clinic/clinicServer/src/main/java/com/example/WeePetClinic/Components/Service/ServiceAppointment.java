package com.example.WeePetClinic.Components.Service;

import com.example.WeePetClinic.Components.Model.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;

import java.time.LocalDateTime;

public interface ServiceAppointment {
  ProjVetEmpDetail getVetProfileForAppointment(int aptID);

  void addAppointment(AppointmentOri apt);

  void updateAppointment(int appointID, LocalDateTime time, String description, String vetID);

  void deleteAppointment(int appointID);

  AppointmentImp searchAppointmentByPetAndTime(PetOri pet, LocalDateTime time);

  AppointmentImp getAppointmentById(int appointID);
}
