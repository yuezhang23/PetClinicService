package com.example.WeePetClinic.Components.Service.Encapsulation;

import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.clinicService.InvoiceImplSql;
import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjPetProfile;
import com.example.WeePetClinic.Components.Repository.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.repoSqlInvoice;
import com.example.WeePetClinic.Components.Repository.repoSqlPet;
import com.example.WeePetClinic.Components.Repository.repoSqlClientPetOwner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

  public UserEmpVetImpl findVetByAppointment(int apt_id) {
    return repoApt.findAppointmentById(apt_id).getVet();
  }


  public List<PetImpl> getPetsByOwnerId(String ownerID) {
    return repoPet.findByOwner_id(ownerID);
  }

  public UserClientPetOwnerImpl findPetOwnerById(String petOwnerID) {
    String message = petOwnerID + ":owner not exist";
    return repoPetOwner.findById(petOwnerID).orElseThrow(() -> new NoSuchElementException(message));
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
