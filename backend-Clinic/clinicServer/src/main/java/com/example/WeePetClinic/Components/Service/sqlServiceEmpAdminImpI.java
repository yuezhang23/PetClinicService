package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForAdmin;
import com.example.WeePetClinic.Components.Model.clinicService.InvoiceOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.Pet.PetOri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;

@Component
@Service
public class sqlServiceEmpAdminImpI implements ServiceEmpAdmin {
  private repoAccessForAdmin accessEmp;
  private sqlServiceAppointmentCRUD accessApt;

  @Autowired
  public sqlServiceEmpAdminImpI(sqlServiceAppointmentCRUD accessApt, repoAccessForAdmin accessEmp) {
    this.accessEmp = accessEmp;
    this.accessApt = accessApt;
  }

  //getter setter
  @Override
  public repoAccessForAdmin getAccessEmp() {
    return accessEmp;
  }

  @Override
  public void setAccessEmp(repoAccessForAdmin accessEmp) {
    this.accessEmp = accessEmp;
  }

  @Override
  public sqlServiceAppointmentCRUD getAccessApt() {
    return accessApt;
  }

  @Override
  public void setAccessApt(sqlServiceAppointmentCRUD accessApt) {
    this.accessApt = accessApt;
  }


  // logic
  @Override
  public int is_booking_full(LocalDateTime appoint_time, UserEmployeeOri user) {
    return accessEmp.is_booking_full(user.getUserID(), appoint_time);
  }


  @Override
  public boolean makeNewAppointmentByAdmin(AppointmentOri apt) {
    try {
      accessApt.addAppointment(apt);
      return true;
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean updateAppointmentByAdmin(AppointmentOri apt) {
    try {
      accessApt.updateAppointment(apt.getAppointID(), apt.getAppointment_time(), apt.getDescription(), apt.getVet().getVetID());
      return true;
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean cancelAppointmentByAdmin(int appointID) {
    try {
      accessApt.deleteAppointment(appointID);
      return true;
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public AppointmentOri searchAppointmentByPetAndTime(PetOri pet, LocalDateTime time) {
    return accessApt.searchAppointmentByPetAndTime(pet, time);
  }

  @Override
  public AppointmentOri getAppointmentForAdmin(UserEmployeeOri admin, int appointID) {
    return accessEmp.findAppointmentById(admin, appointID);
  }

  // assign a room and
  @Override
  public boolean petCheckIn(int aptID) {
    try {
      accessEmp.checkIn(aptID);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public List<InvoiceOri> displayAllInvoicesForUser(UserEmployeeOri user) {
    return null;
  }

  @Override
  public List<AppointmentOri> displayAppointmentsForUser(UserEmployeeOri user) {
    List<AppointmentImp> lst = accessApt.getAppointmentByAdminID(user.getUserID());
    return new ArrayList<>(lst);
  }

  @Override
  public boolean accountCancellation(boolean delete, UserEmployeeOri user) {
    try {
      if (delete) {
        // async
        accessEmp.deleteEmployee(user.getUserID());
      } else {
        // sync
        accessEmp.resignInEmployee(user.getUserID());
        accessEmp.resignInAppointment(user);
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean updateAccount(UserEmployeeOri newAccount) {
    return accessEmp.updatePersonalAccount(newAccount);
  }

}
