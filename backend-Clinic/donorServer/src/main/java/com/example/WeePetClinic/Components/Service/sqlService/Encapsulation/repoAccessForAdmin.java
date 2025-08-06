package com.example.WeePetClinic.Components.Service.sqlService.Encapsulation;

import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpAdmin;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpVet;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlPet;
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

  public List<UserEmployeeImpl> getAllEmps() {
    return repoAdmin.findAllEmployees();
  }

  public PetImpl findPetById(int petId) {
    return repoPet.findPetById(petId);
  }

  public UserEmployeeImpl findEmployeeById(String adminID) {
    return repoAdmin.findById(adminID).orElseThrow(() -> new NoSuchElementException("employ not found"));
  }

  public void deleteEmployee(String id) {
    repoAdmin.deleteById(id);
  }


  public void addEmployee(UserEmployeeImpl emp) {
    repoAdmin.save(emp);
  }

  public void resignInEmployee(UserEmployeeImpl admin) {
    repoAdmin.resignInEmployee(admin.getUserID());
  }

  public void resignInAppointment(UserEmployeeImpl admin) {
    repoAdmin.resignInAppointment(admin.getUserID(), admin.getFirstName() + " " + admin.getLastName());
  }

  public int is_booking_full(UserEmployeeImpl admin, LocalDateTime appoint_time) {
    return repoAdmin.is_book_full(admin.getUserID(), appoint_time);
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

  public AppointmentOri findAppointmentById(UserEmployeeImpl admin, int appointID) {
    return repoApt.findAppointmentByIdAndClinic(appointID, admin.getClinic().getClinicID());
  }

  // assign a room and
  public void checkIn(int aptID) {
    repoAdmin.check_in(aptID);
  }


  public void setEmpType(UserOri user, TypeUser type) {
    repoAdmin.updateEmployeeType(user.getUserID(), type);
  }

}
