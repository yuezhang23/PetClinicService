package com.example.WeePetClinic.Components.Service.sqlService.ImplementionSql;

import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentInClinic;
import com.example.WeePetClinic.Components.Model.forSql.ClinicOri;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceInClinic;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlClinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
  public ClinicOri findClinicById(int clinic_id) {
    ClinicImp clinic =  accessClinic.findById(clinic_id).orElseThrow(()-> new NoSuchElementException("clinic not found"));
    return clinic;
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
