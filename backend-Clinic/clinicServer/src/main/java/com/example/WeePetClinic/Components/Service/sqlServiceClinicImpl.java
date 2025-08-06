package com.example.WeePetClinic.Components.Service;

import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentInClinic;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicOri;
import com.example.WeePetClinic.Components.Repository.repoSqlClinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class sqlServiceClinicImpl implements ServiceInClinic {
  private final repoSqlClinic accessClinic;

  @Autowired
  public sqlServiceClinicImpl(repoSqlClinic accessClinic) {
    this.accessClinic = accessClinic;
  }

  @Override
  public List<ServiceInClinic> displayAvailableTreatmentsInClinic(ClinicOri clinic) {
    return null;
  }

  @Override
  public List<ServiceInClinic> displayAvailableMedicationsInClinic(ClinicOri clinic) {
    return null;
  }

  @Override
  public ClinicOri getClinicById(int clinicId) {
    return accessClinic.findClinicById(clinicId);
  }

  // this can be extended to admission function for external user.
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  @Override
  public List<ProjAppointmentInClinic> displayAppointmentsInClinic(ClinicOri clinic) {
//    if (clinic == emp.getClinic()) {
//      try {
//        return accessEmp.displayAppointmentsInClinic(emp);
//      } catch (Exception e) {
//        System.err.println("An unexpected error occurred: " + e.getMessage());
//      }
//    } else {
//      System.out.println("you are not employee in this clinic");
//    }
    return null;
  }
}
