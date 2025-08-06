package com.example.WeePetClinic.Components.Service.Encapsulation;

import com.example.WeePetClinic.Components.Model.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Repository.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.repoSqlEmpAdmin;
import com.example.WeePetClinic.Components.Repository.repoSqlEmpVet;
import com.example.WeePetClinic.Components.Repository.repoSqlPet;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Component
@Service
public class repoAccessForAdmin {
  private final repoSqlEmpAdmin repoAdmin;
  private final repoSqlAppointmentBasic repoApt;
  private final repoSqlEmpVet repoVet;
  private final repoSqlPet repoPet;

  @Autowired
  public repoAccessForAdmin(repoSqlEmpAdmin repoAdmin, repoSqlAppointmentBasic repoApt, repoSqlEmpVet repoVet, repoSqlPet repoPet) {
    this.repoAdmin = repoAdmin;
    this.repoApt = repoApt;
    this.repoVet = repoVet;
    this.repoPet = repoPet;
  }

  public boolean updatePersonalAccount(UserEmployeeOri obj) {
    try {
      repoAdmin.save((UserEmployeeImpl) obj);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public PetImpl findPetById(int petId) {
    return repoPet.findPetById(petId);
  }

  // appointment related
  public int is_booking_full(String adminID, LocalDateTime appoint_time) {
    return repoAdmin.is_book_full(adminID, appoint_time);
  }

  public AppointmentOri findAppointmentByPetIdAndTime(int petID, LocalDateTime time) {
    return repoApt.findAppointmentByPetIdAndTime(petID, time);
  }

  public void makeNewAppointment(UserEmployeeImpl admin, PetImpl pet, LocalDateTime ts, String description) {
    try {
      AppointmentImp apt = new AppointmentImp();
      apt.setPet(pet);
      apt.setDescription(description);
      apt.setAppointment_time(ts);
      apt.setClinic(admin.getClinic());
      apt.setAdmin(admin);
      String vetID = repoApt.veterinarianMatch(admin.getClinic().getClinicID(), ts);
      apt.setVet(repoVet.findVetById(vetID));
      apt.setShowUp(false);
      repoApt.save(apt);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


  public void updateAppointment(UserEmployeeImpl admin, int appointID, LocalDateTime time, String description, String vetID) {
    repoAdmin.update_appointment(admin.getUserID(), appointID, time, description, vetID);
  }

  public void deleteAppointment(UserEmployeeImpl admin, AppointmentOri appointment) {
    repoAdmin.delete_appointment(admin.getUserID(), appointment.getAppointment_time(), appointment.getPet().getPetID(), appointment.getAppointID());
  }

  public AppointmentOri findAppointmentById(UserEmployeeOri admin, int appointID) {
    return repoApt.findAppointmentByIdAndClinic(appointID, admin.getClinic().getClinicID());
  }

  // assign a room
  public void checkIn(int aptID) {
    repoAdmin.check_in(aptID);
  }

  // emp related
  public void deleteEmployee(String id) {
    repoAdmin.deleteById(id);
  }

  public void resignInEmployee(String adminID) {
    repoAdmin.resignInEmployee(adminID);
  }

  public void resignInAppointment(UserOri admin) {
    repoAdmin.resignInAppointment(admin.getUserID(), admin.getFirstName() + " " + admin.getLastName());
  }


}
