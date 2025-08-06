package com.example.WeePetClinic.Components.Service;

import com.example.WeePetClinic.Components.Model.clinicService.ClinicOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentInClinic;

import java.util.List;

public interface ServiceInClinic {
  List<ProjAppointmentInClinic> displayAppointmentsInClinic(ClinicOri clinic);
  List<ServiceInClinic> displayAvailableTreatmentsInClinic(ClinicOri clinic);
  List<ServiceInClinic> displayAvailableMedicationsInClinic(ClinicOri clinic);
  ClinicOri getClinicById(int clinicId);
}
