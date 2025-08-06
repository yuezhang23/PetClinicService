package com.example.WeePetClinic.Components.Service.sqlService.Encapsulation;

import com.example.WeePetClinic.Components.Model.forSql.InvoiceImplSql;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjPetProfile;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlInvoice;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlPet;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlClientPetOwner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Service
@Lazy
public class repoAccessForPetOwner {
  private final repoSqlClientPetOwner repoPetOwner;
  private final repoSqlAppointmentBasic repoApt;
  private final repoSqlInvoice repoInvoice;
  private final repoSqlPet repoPet;

  @Autowired
  public repoAccessForPetOwner(repoSqlClientPetOwner repoPetOwner, repoSqlAppointmentBasic repoApt, repoSqlInvoice repoInvoice, repoSqlPet repoPet) {
    this.repoPetOwner = repoPetOwner;
    this.repoApt = repoApt;
    this.repoInvoice = repoInvoice;
    this.repoPet = repoPet;
  }

  public List<PetImpl> getPetsByOwnerId(String ownerID) {
    return repoPet.findByOwner_id(ownerID);
  }

  public UserClientPetOwnerImpl findPetOwnerById(String petOwnerID) {
    return repoPetOwner.findById(petOwnerID).orElseThrow(() -> new NoSuchElementException("owner not exist"));
  }

  public void makePayment(int appointmentId, double invoiceAmount) {
    repoInvoice.makePayment(appointmentId, invoiceAmount);
  }

  public List<ProjPetProfile> displayOwnerPets(String ownerID) {
    return repoPet.displayOwnerPets(ownerID);
  }

  public List<InvoiceImplSql> findInvoiceByOwner(String ownerID) {
    return repoInvoice.findAllByOwner_id(ownerID);
  }

  public List<ProjMedsTreatsForAppointment> displayAllMedsAndTreatsInAppointment(AppointmentOri apt) {
    return repoApt.displayAllMedsAndTreatsInAppointment(apt.getAppointID());
  }

  public void deletePetsByOwner(String ownerID) {
    repoPet.deletePetsByOwner(ownerID);
  }

  public void deletePetOwner(String id) {
    repoPetOwner.deleteById(id);
  }

  public void addPetOwner(UserClientPetOwnerImpl user) {
    repoPetOwner.save(user);
  }


  public void deleteInvoices(String ownerID) {
    repoInvoice.deleteInvoicesByOwnerID(ownerID);
  }

  public ProjAppointmentDetail displayAppointmentDetailInClinic(int clinicID, int aptID) {
    return repoApt.displayDetailsOfAppointmentInClinic(aptID, clinicID);
  }

  public ProjAppointmentDetail displayAppointmentDetail(int aptID) {
    return repoApt.displayDetailsOfAppointment(aptID);
  }
}
