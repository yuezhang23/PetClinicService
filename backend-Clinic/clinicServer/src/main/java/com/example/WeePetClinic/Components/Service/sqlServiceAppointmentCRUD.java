package com.example.WeePetClinic.Components.Service;

import com.example.WeePetClinic.Components.Model.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Repository.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.repoSqlEmpVet;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.ServiceAppointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Component
public class sqlServiceAppointmentCRUD implements ServiceAppointment {
  private final repoSqlAppointmentBasic repoApt;
  private final repoSqlEmpVet repoVet;


  @Autowired
  public sqlServiceAppointmentCRUD(repoSqlAppointmentBasic repoApt, repoSqlEmpVet repoVet) {
    this.repoApt = repoApt;
    this.repoVet = repoVet;
  }


  @Override
  public ProjVetEmpDetail getVetProfileForAppointment(int aptID) {
    AppointmentImp apt = repoApt.findAppointmentById(aptID);
    return repoVet.findVetDetailById(apt.getVet().getVetID());
  }

  public void addAppointment(AppointmentOri apt) {
    AppointmentImp appoint = new AppointmentImp();
    appoint.setAppointment_time(apt.getAppointment_time());
    appoint.setPet(apt.getPet());
    appoint.setDescription(apt.getDescription());
    appoint.setClinic(apt.getClinic());
    // check if exists
    AppointmentImp check = repoApt.findAppointmentByPetIdAndTime(apt.getPet().getPetID(), apt.getAppointment_time());
    if (check != null) {
      System.out.println("appointment already exist!");
    }
    String vetId = repoApt.veterinarianMatch(apt.getClinic().getClinicID(), apt.getAppointment_time());
    if (!vetId.isEmpty()) {
      repoApt.save(appoint);
    }
  }

  public void updateAppointment(int appointID, LocalDateTime time, String description, String vetID) {
    AppointmentImp apt = repoApt.findById(appointID).orElseThrow(() -> new NoSuchElementException("appointment not found"));
    apt.setDescription(description);
    UserEmpVetImpl vet = repoVet.findById(vetID).orElseThrow(() -> new NoSuchElementException("not vet available"));
    apt.setVet(vet);
    apt.setAppointment_time(time);
    repoApt.save(apt);
  }

  public void deleteAppointment(int appointID) {
    repoApt.deleteById(appointID);
  }

  public AppointmentImp searchAppointmentByPetAndTime(PetOri pet, LocalDateTime time) {
    return repoApt.findAppointmentByPetIdAndTime(pet.getPetID(), time);
  }

  public AppointmentImp getAppointmentById(int appointID) {
    return repoApt.findById(appointID).orElseThrow(()-> new NoSuchElementException("appointment not found"));
  }

  public List<AppointmentImp> getAppointmentByAdminID(String usrID) {
    return repoApt.findAppointmentsByAdminEmployee(usrID);
  }

}
