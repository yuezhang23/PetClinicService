package com.example.WeePetClinic.Components.Service.sqlService.ImplementionSql;
import com.example.WeePetClinic.Components.Model.forPostSql.InvoiceImplPostSql;
import com.example.WeePetClinic.Components.Model.forSql.InvoiceImplSql;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Service.sqlService.Encapsulation.repoAccessForPetOwner;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceClientPetOwner;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjPetProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import static com.example.WeePetClinic.utilities.mapToJsonString.mapToJsonString;

@Component
@Service
public class sqlServiceClientPetOwnerImpl implements ServiceClientPetOwner<UserClientPetOwnerImpl> {
  private repoAccessForPetOwner accessPetOwner;

  @Autowired
  public sqlServiceClientPetOwnerImpl(repoAccessForPetOwner petOwnerM) {
    this.accessPetOwner = petOwnerM;
  }

  //getter setter
  @Override
  public repoAccessForPetOwner getAccessPetOwner() {
    return accessPetOwner;
  }

  @Override
  public void setAccessPetOwner(repoAccessForPetOwner accessPetOwner) {
    this.accessPetOwner = accessPetOwner;
  }

//  //crud
  @Override
  public UserClientPetOwnerImpl getPetOwner(String id) {
    UserClientPetOwnerImpl owner = accessPetOwner.findPetOwnerById(id);
    return owner;
  }


  @Override
  public void setPetOwner(UserClientPetOwnerImpl role) {
    accessPetOwner.addPetOwner(role);
  }
  @Override
  public void deletePetOwner(String id) {
    accessPetOwner.deletePetOwner(id);
  }

  @Override
  public void addPetOwner(UserClientPetOwnerImpl owner) {
    accessPetOwner.addPetOwner(owner);
  }

  // logic
  // opts crud



  @Override
  public Boolean makePayment(int appointmentId, double invoiceAmount) {
    if (invoiceAmount <= 0) {
      return false;
    }
    try {
      accessPetOwner.makePayment(appointmentId, invoiceAmount);
      return true;
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
      return false;
    }
  }

  @Override
  public List<PetImpl> getPetsByOwner(String ownerID) {
    return accessPetOwner.getPetsByOwnerId(ownerID);
  }

  @Override
  public List<ProjPetProfile> displayPetsInfo(UserClientPetOwnerImpl owner) {
    try {
      return accessPetOwner.displayOwnerPets(owner.getUserID());
    } catch (
    PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return null;
  }

  @Override
  public List<AppointmentOri> displayAppointmentsDetailsForPet(PetOri pet, UserClientPetOwnerImpl petOwner) {
    if (pet.getOwner() == petOwner) {
      return pet.getAppointments();
    } else {
      System.out.println("this is not your pet ");
      return null;
    }
  }

  @Override
  public List<InvoiceOri> displayInvoicesForAppointment(int appointId, UserClientPetOwnerImpl petOwner) {
    List<InvoiceOri> lst = new ArrayList<>();
    for (InvoiceOri invoice : petOwner.getInvoices()) {
      if (invoice.getInvoiceId() == appointId) {
        lst.add(invoice);
      }
    }
    return lst;
  }

  @Override
  public Map<String, Double> displayAppointmentExpenditureForPet(PetOri pet, LocalDateTime start, LocalDateTime end) {
    List<AppointmentOri> apts = pet.getAppointments();
    double costSum = 0.0;
    Map<String, Double> aptCost = new HashMap<>();
    for (AppointmentOri apt : apts) {
      double cost = 0.0;
      LocalDateTime time = apt.getAppointment_time();
      if (time.isAfter(start) && time.isBefore(end)) {
        List<ProjMedsTreatsForAppointment> services = accessPetOwner.displayAllMedsAndTreatsInAppointment(apt);
        for (ProjMedsTreatsForAppointment service : services) {
          costSum += service.getPrice();
          cost += service.getPrice();
        }
        aptCost.put("AppointmentID: " + apt.getAppointID(), cost);
      }
    }
    aptCost.put("Sum: ", costSum);
    Map<String, Object> objectMap = aptCost.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    mapToJsonString(objectMap);
    return aptCost;
  }

  @Override
  public List<ProjMedsTreatsForAppointment> displayAllTreatsAndMedsForPet(PetOri pet) {
    List<ProjMedsTreatsForAppointment> target = new ArrayList<>();
    List<AppointmentOri> aptLst = pet.getAppointments();
    for (AppointmentOri apt : aptLst) {
      target.addAll(accessPetOwner.displayAllMedsAndTreatsInAppointment(apt));
    }
    return target;
  }

  @Override
  public List<InvoiceOri> displayAllInvoicesForUser(UserOri petOwner) {
    List<InvoiceImplSql> in1 =  accessPetOwner.findInvoiceByOwner(petOwner.getUserID());
    return new ArrayList<>(in1);
  }

  @Override
  public List<AppointmentOri> displayAppointmentsForUser(UserOri petOwner) {
    UserClientPetOwnerOri owner = accessPetOwner.findPetOwnerById(petOwner.getUserID());
    List<AppointmentOri> lst = new ArrayList<>();
    List<PetImpl> pets = owner.getPets();
    for (PetImpl pet : pets) {
      lst.addAll(pet.getAppointments());
    }
    return lst;
  }

  @Override
  public List<ProjAppointmentDetail> displayAppointmentsDetailsForUserInClinic(int clinicID, UserClientPetOwnerImpl owner) {
    List<ProjAppointmentDetail> apts = new ArrayList<>();
    List<AppointmentOri> lst = this.displayAppointmentsForUser(owner);
    for (AppointmentOri apt : lst) {
      if(apt.getClinic().getClinicID() == clinicID) {
        apts.add(accessPetOwner.displayAppointmentDetailInClinic(clinicID, apt.getAppointID()));
      }
    }
    return apts;
  }

  @Override
  public List<ProjAppointmentDetail> displayAppointmentsDetailsForPet(PetOri pet) {
    List<ProjAppointmentDetail> apts = new ArrayList<>();
    List<AppointmentOri> lst = pet.getAppointments();
    for (AppointmentOri apt : lst) {
        apts.add(accessPetOwner.displayAppointmentDetail(apt.getAppointID()));
    }


    return apts;
  }

  @Override
  public boolean accountCancellation(boolean delete, UserOri user) {
    try {
      if (delete) {
        accessPetOwner.deletePetsByOwner(user.getUserID());
        accessPetOwner.deleteInvoices(user.getUserID());
      }
      accessPetOwner.deletePetOwner(user.getUserID());
    } catch (Exception e) {
      return false;
    }
    return true;
  }


}
