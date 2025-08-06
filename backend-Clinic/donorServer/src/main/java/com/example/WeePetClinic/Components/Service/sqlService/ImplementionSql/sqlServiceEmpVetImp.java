package com.example.WeePetClinic.Components.Service.sqlService.ImplementionSql;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Service.sqlService.Encapsulation.repoAccessForVet;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceEmpVet;
import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.MedicationImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.TreatmentImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.medicationInAppointment;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.treatmentInAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.PersistenceException;

@Component
@Service
public class sqlServiceEmpVetImp implements ServiceEmpVet<UserEmpVetImpl> {
  private repoAccessForVet accessVet;

  @Autowired
  public sqlServiceEmpVetImp(repoAccessForVet accessVet) {
    this.accessVet = accessVet;
  }

  @Override
  public repoAccessForVet getAccessVet() {
    return this.accessVet;
  }

  @Override
  public void setAccessVet(repoAccessForVet access) {
    this.accessVet = access;
  }


  @Override
  public ClinicImp getClinicById(int clinic_id) {
    return accessVet.getClinicById(clinic_id).orElseThrow(()-> new NoSuchElementException("bad request"));
  }

  //crud
  @Override
  public Optional<UserEmpVetImpl> getEmpVet(String id) {
    return accessVet.findVetProfileById(id);
  }

  @Override
  public ProjVetEmpDetail getEmpVetFullProfile(String id) {
    return accessVet.findVetDetailById(id);
  }

  @Override
  public void setEmpVet(UserEmpVetImpl vet, UserEmployeeOri emp) {
    accessVet.addVet(vet,(UserEmployeeImpl)emp);
  }

  @Override
  public void deleteEmpVet(String id) {
    accessVet.deleteVetById(id);
  }

  @Override
  public void addEmpVet(UserEmpVetImpl owner, UserEmployeeOri emp) {
    accessVet.addVet(owner, (UserEmployeeImpl) emp);
  }

  // appointment repo for vet
  @Override
  public List<AppointmentOri> displayAppointmentsForPet(PetOri pet, UserEmpVetImpl vet) {
    return new ArrayList<>(accessVet.displayAppointmentsForPet(pet, vet));
  }
  @Override
  public List<AppointmentOri> displayAppointmentsByPetAndVet(PetOri pet, UserEmpVetImpl vet) {
    return new ArrayList<>(accessVet.displayAppointmentsByPetAndVet(pet, vet));
  }

  @Override
  public List<ProjMedsTreatsForAppointment> displayAllTreatsAndMedsForAppointment(AppointmentOri apt) {
    return accessVet.displayAllTreatsAndMedsForAppointment(apt);
  }


  public boolean appointmentComplete(AppointmentOri appointment) {
    try {
      accessVet.completeAppointment(appointment);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  // treatments&medication
  @Override
  public boolean treatmentAdd(TreatmentImpl service, AppointmentOri appointment, double actualPrice) {
    if (actualPrice <= 0.0) {
      actualPrice = service.getRegularPrice();
    }
    try {
      accessVet.addTreatment(service, appointment, actualPrice);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean treatmentDelete(treatmentInAppointment service) {
    try {
      accessVet.deleteTreatment(service);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean treatmentModify(treatmentInAppointment oldService, treatmentInAppointment newService) {
    try {
      accessVet.updateTreatment(oldService, newService);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public boolean medicationAdd(medicationInAppointment service, AppointmentOri appointment, int quantity, int period) {
    try {
      accessVet.addMedication(service, appointment, quantity, period);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }


  @Override
  public boolean medicationDelete(medicationInAppointment service) {
    try {
      accessVet.deleteMedication(service);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
      return false;
  }

  @Override
  public boolean medicationModify(medicationInAppointment oldService, medicationInAppointment newService) {
    try {
      accessVet.updateMedication(oldService, newService);
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return false;
  }

  @Override
  public List<AppointmentOri> displayAppointmentsForUser(UserOri user) {
    UserEmpVetImpl vet = accessVet.findVetProfileById(user.getUserID()).get();
    return new ArrayList<>(vet.getAppointments());
  }

  @Override
  public List<InvoiceOri> displayAllInvoicesForUser(UserOri user) {
    return null;
  }

  // services for vet
  @Override
  public List<MedicationImpl> displayAvailableMedicationsForVet(UserEmpVetImpl vet) {
    return accessVet.displayAvailableMedicationsInClinic(vet);
  }

  @Override
  public List<TreatmentImpl> displayAvailableTreatmentsForVet(UserEmpVetImpl vet) {
    return accessVet.displayAvailableTreatmentsInClinic(vet);
  }

  @Override
  public boolean accountCancellation(boolean delete, UserOri user) {
    try {
      UserEmpVetImpl vet = accessVet.findVetProfileById(user.getUserID()).get();
      if (delete) {
        accessVet.deleteVetById(user.getUserID());
      } else {
        accessVet.resignVetInfoInAppointment(vet);
        accessVet.resignInVeterinarian(vet);
      }
      return true;
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    }
    return false;
  }

}
