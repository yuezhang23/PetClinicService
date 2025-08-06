package com.example.WeePetClinic.Components.Service.sqlService.ImplementionSql;
import com.example.WeePetClinic.Components.CController.RestController.reponseDTO.ProjUser;
import com.example.WeePetClinic.Components.Service.sqlService.Encapsulation.repoAccessForAdmin;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceEmpAdmin;
import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.UserOri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;

@Component
@Service
public class sqlServiceEmpAdminImpI implements ServiceEmpAdmin<UserEmployeeImpl> {
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

  //crud
  @Override
  public List<ProjUser> getAllEmps() {
    List<UserEmployeeImpl> empLst =  accessEmp.getAllEmps();
    List<ProjUser> lst = new ArrayList<>();
    if (!empLst.isEmpty()) {
      for (UserEmployeeImpl emp : empLst) {
        lst.add(new ProjUser(emp.getLastName(), emp.getFirstName(), emp.getPhone(), emp.getAddress()));
      }
    }
    return lst;
  }

  @Override
  public UserEmployeeImpl getEmp(String id) {
    return accessEmp.findEmployeeById(id);
  }

  @Override
  public void deleteEmp(String id) {
    accessEmp.deleteEmployee(id);
  }

  @Override
  public void setEmp(UserEmployeeImpl emp) {
    accessEmp.addEmployee(emp);
  }

  // add
  @Override
  public void addEmp(UserEmployeeImpl user) {
    try {
      accessEmp.addEmployee(user);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // logic
  @Override
  public int is_booking_full(LocalDateTime appoint_time, UserEmployeeImpl user) {
    return accessEmp.is_booking_full(user, appoint_time);
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
  public AppointmentOri getAppointmentForAdmin(UserEmployeeImpl admin, int appointID) {
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
  public List<InvoiceOri> displayAllInvoicesForUser(UserOri user) {
    return null;
  }


  @Override
  public List<AppointmentOri> displayAppointmentsForUser(UserOri user) {
    UserEmployeeImpl emp = accessEmp.findEmployeeById(user.getUserID());
    return new ArrayList<>(emp.getAppointments());
  }


  @Override
  public boolean accountCancellation(boolean delete, UserOri user) {
    try {
      UserEmployeeImpl emp = accessEmp.findEmployeeById(user.getUserID());
      if (delete) {
        accessEmp.deleteEmployee(emp.getUserID());
      } else {
        accessEmp.resignInEmployee(emp);
        accessEmp.resignInAppointment(emp);
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

}
