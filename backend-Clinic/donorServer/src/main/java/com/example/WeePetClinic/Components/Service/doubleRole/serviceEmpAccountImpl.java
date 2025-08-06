package com.example.WeePetClinic.Components.Service.doubleRole;

import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantImp;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorOri;
import com.example.WeePetClinic.Components.Model.forPostSql.donateService.DonationOri;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjDonationDetail;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.PersistenceException;

@Component
@Service
public class serviceEmpAccountImpl implements ServiceEmpAccountant<UserEmpAccountantImp> {
  private repoAccessForAccountant accessAccountant;

  @Autowired
  public serviceEmpAccountImpl(repoAccessForAccountant accountant) {
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
  public ClinicImp getDetailForClinic(int clinicID) {
    return accessAccountant.findClinicById(clinicID);
  }

  //crud
  @Override
  public UserEmpAccountantImp getAccountant(String id) {
    return accessAccountant.findAccountantById(id);
  }

  @Override
  public UserEmployeeImpl getEmpAccountant(String id) {
    return accessAccountant.findEmpById(id).orElseThrow(()-> new NoSuchElementException("this is not an employee"));
  }

  @Override
  public void updateEmpAccountant(UserEmpAccountantImp emp) {
    accessAccountant.addAccountant((UserEmpAccountantImp)emp);
  }
  @Override
  public void deleteEmpAccountant(String id) {
    accessAccountant.deleteAccountantById(id);
  }

  @Override
  public void addEmpAccountant(UserEmpAccountantImp emp) {
    accessAccountant.addAccountant((UserEmpAccountantImp)emp);
  }

  @Override
  public List<ProjDonationDetail> getDonorTransactionList(UserClientDonorOri donor) {
    return null;
  }

  @Override
  public List<UserClientDonorOri> getDonorsListByTime(Timestamp start, Timestamp end) {
    return null;
  }

  @Override
  public List<DonationOri> getDonationsByTime(Timestamp start, Timestamp end) {
    return null;
  }

  @Override
  public double findTopDonationByTime(Timestamp start, Timestamp end) {
    return 0;
  }

  @Override
  public UserClientDonorOri findDonorWithTopDonation() {
    return null;
  }

  @Override
  public UserClientDonorOri findDonorWithTopTimesOfDonations() {
    return null;
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
  public List<InvoiceOri> displayAllInvoicesForUser(UserOri user) {
    return null;
  }

  @Override
  public List<AppointmentOri> displayAppointmentsForUser(UserOri user) {
    return null;
  }

  @Override
  public boolean accountCancellation(boolean delete, UserOri user) {
    return false;
  }
}
