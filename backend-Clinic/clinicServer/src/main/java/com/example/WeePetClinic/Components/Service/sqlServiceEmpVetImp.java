package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForAccountant;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForVet;
import com.example.WeePetClinic.Components.Model.clinicService.InvoiceOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.clinicService.MedicationImpl;
import com.example.WeePetClinic.Components.Model.clinicService.TreatmentImpl;
import com.example.WeePetClinic.Components.Model.clinicService.medicationInAppointment;
import com.example.WeePetClinic.Components.Model.clinicService.treatmentInAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

@Component
@Service
public class sqlServiceEmpVetImp implements ServiceEmpVet {
  private repoAccessForVet accessVet;
  private final repoAccessForAccountant accessAccount;

  @Autowired
  public sqlServiceEmpVetImp(repoAccessForVet accessVet, repoAccessForAccountant accessAccount) {
    this.accessVet = accessVet;
    this.accessAccount = accessAccount;
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
  public ClinicOri getClinicByVet(String vetID) {
    return accessVet.getClinicByVet(vetID);
  }

  // appointment repo for vet
  @Override
  public List<AppointmentOri> displayAppointmentsByPetAndVet(PetOri pet, UserEmpVetOri vet) {
    return new ArrayList<>(accessVet.displayAppointmentsForPet(pet, vet));
  }

  @Override
  public ProjVetEmpDetail getEmpVetFullProfile(String id) {
    return accessVet.findVetDetailById(id);
  }

  @Override
  public UserEmpVetOri getEmpVet(String id) {
    return accessVet.findVetById(id);
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
  public List<MedicationImpl> displayAvailableMedicationsForVet(UserEmpVetOri vet) {
    return null;
  }

  @Override
  public List<TreatmentImpl> displayAvailableTreatmentsForVet(UserEmpVetOri vet) {
    return null;
  }

  @Override
  public List<AppointmentOri> displayAppointmentsForUser(UserEmpVetOri user) {
    List<AppointmentImp> lst = accessVet.displayAppointmentsByVetId(user.getVetID());
    return new ArrayList<>(lst);
  }

  @Override
  public List<InvoiceOri> displayAllInvoicesForUser(UserEmpVetOri user) {
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
  public boolean accountCancellation(boolean delete, UserEmpVetOri vet) {
    try {
      if (delete) {
        accessAccount.deleteVetById(vet.getVetID());
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

  @Override
  public boolean updateAccount(UserEmpVetOri newAccount) {
    return accessAccount.updateVet(newAccount);
  }

}
