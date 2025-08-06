package com.example.WeePetClinic.Components.Service;

import com.example.WeePetClinic.Components.Model.clinicService.InvoiceOri;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicImp;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Service.Encapsulation.repoAccessForAccountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

@Component
@Service
public class sqlServiceEmpAccountImpl implements ServiceEmpAccountant {
  private repoAccessForAccountant accessAccountant;

  @Autowired
  public sqlServiceEmpAccountImpl(repoAccessForAccountant accountant) {
    this.accessAccountant = accountant;
  }

  //getters setters
  @Override
  public repoAccessForAccountant getAccessAccountant() {
    return accessAccountant;
  }

  @Override
  public void setAccessAccountant(repoAccessForAccountant accessAccountant) {
    this.accessAccountant = accessAccountant;
  }

  @Override
  public List<UserEmployeeOri> getAllEmpByClinic(int clinicID) {
    List<UserEmployeeImpl> lst = accessAccountant.getAllEmpByClinic(clinicID);
    return new ArrayList<>(lst);
  }

  @Override
  public ClinicImp getDetailForClinic(int clinicID) {
    return accessAccountant.findClinicById(clinicID);
  }

  @Override
  public boolean updateClinic(ClinicImp clinic) {
    return accessAccountant.updateClinic(clinic);
  }

  @Override
  public boolean deleteClinic(int id) {
    return accessAccountant.deleteClinic(id);
  }

  @Override
  public boolean addClinic(ClinicImp clinic) {
    return accessAccountant.addClinic(clinic);
  }

  //crud
  @Override
  public List<UserEmployeeOri> getAllEmployees() {
    List<UserEmployeeImpl> empLst = accessAccountant.getAllEmployees();
    return new ArrayList<>(empLst);
  }

  @Override
  public UserEmployeeOri getEmp(String id) {
    return null;
  }

  @Override
  public boolean updateEmp(UserEmployeeOri emp) {
    return false;
  }

  @Override
  public boolean deleteEmp(String id) {
    return false;
  }

  @Override
  public boolean addEmp(UserEmployeeOri emp) {
    return false;
  }

  //crud
  @Override
  public UserEmpVetOri getEmpVet(String id) {
    return accessAccountant.findVetProfileById(id);
  }

  @Override
  public boolean setEmpVet(UserEmpVetOri vet, UserEmployeeOri emp) {
    return accessAccountant.addVet(vet, emp);
  }

  @Override
  public boolean deleteEmpVet(String id) {
    return accessAccountant.deleteVetById(id);
  }

  @Override
  public boolean addEmpVet(UserEmpVetOri owner, UserEmployeeOri emp) {
    return accessAccountant.addVet(owner, emp);
  }


  @Override
  public UserClientOri getClient(String id) {
    return null;
  }

  @Override
  public boolean updateClient(UserClientOri user) {
    return false;
  }

  @Override
  public boolean deleteClient(String id) {
    return false;
  }


  @Override
  public boolean addClient(UserClientOri user) {
    return false;
  }


  //sql
  @Override
  public List<ProjAppointmentDetail> displayTransactionsByVet(UserEmpVetOri vet) {
    try {
      return accessAccountant.getVetTransactionList(vet);
    } catch (PersistenceException e) {
      System.err.println("Persistence exception: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return null;
  }

  @Override
  public UserEmpVetOri displayVetProfile(String vetID) {
    return accessAccountant.getVetProfile(vetID);
  }


  @Override
  public UserClientOri displayPetOwnerProfile(String clientID) {
    UserClientPetOwnerOri petOwner = accessAccountant.displayPetOwnerProfile(clientID);
    return petOwner;
  }


  @Override
  public List<ProjAppointmentDetail> displayTransactionsByPetOwner(UserClientPetOwnerOri owner) {
    try {
      return accessAccountant.getOwnerTransactionList(owner);
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
    return null;
  }


  @Override
  public List<InvoiceOri> displayAllInvoicesForUser(UserEmployeeOri user) {
    return null;
  }

  @Override
  public List<AppointmentOri> displayAppointmentsForUser(UserEmployeeOri user) {
    return null;
  }

  @Override
  public boolean accountCancellation(boolean delete, UserEmployeeOri user) {
    return false;
  }

  @Override
  public boolean updateAccount(UserEmployeeOri newAccount) {
    return false;
  }
}
