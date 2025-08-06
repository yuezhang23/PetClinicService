package com.example.WeePetClinic.Components.Service.Encapsulation;

import com.example.WeePetClinic.Components.Model.clinicService.InvoiceOri;
import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicImp;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Repository.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.repoSqlClientPetOwner;
import com.example.WeePetClinic.Components.Repository.repoSqlClinic;
import com.example.WeePetClinic.Components.Repository.repoSqlEmpAdmin;
import com.example.WeePetClinic.Components.Repository.repoSqlEmpVet;
import com.example.WeePetClinic.Components.Repository.repoSqlInvoice;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Component
@Service
public class repoAccessForAccountant {
  //sql
  private final repoSqlEmpAdmin repoAccountant;
  private final repoSqlInvoice repoSqlInvoice;
  private final repoSqlAppointmentBasic repoApt;
  private final repoSqlEmpVet repoVet;
  private final repoSqlClientPetOwner repoPetOwner;
  private final repoSqlClinic repoClinic;

  @Autowired
  public repoAccessForAccountant(repoSqlEmpAdmin repoAccountant, repoSqlInvoice repoSqlInvoice,
                                 repoSqlAppointmentBasic repoApt, repoSqlEmpVet repoVet,
                                 repoSqlClientPetOwner repoPetOwner, repoSqlClinic repoClinic) {
    this.repoAccountant = repoAccountant;
    this.repoSqlInvoice = repoSqlInvoice;
    this.repoApt = repoApt;
    this.repoVet = repoVet;
    this.repoPetOwner = repoPetOwner;
    this.repoClinic = repoClinic;
  }

  public Optional<UserEmployeeImpl> findEmpById(String id) {
    return repoAccountant.findById(id);
  }

  public List<UserEmployeeImpl> getAllEmpByClinic(int clinicID) {
    return repoAccountant.findAllEmployees();
  }

  public UserEmployeeImpl findEmployeeById(String adminID) {
    return repoAccountant.findById(adminID).orElseThrow(() -> new NoSuchElementException("employ not found"));
  }

  public List<UserEmployeeImpl> getAllEmployees() {
    return repoAccountant.findAll();
}

  public void addEmployee(UserEmployeeImpl emp) {
    repoAccountant.save(emp);
  }


  public UserClientPetOwnerImpl findPetOwnerById(String id) {
    return repoPetOwner.findById(id).orElseThrow(()-> new NoSuchElementException("no such pet owner"));
  }

  public UserEmpVetImpl findVetById(String id) {
    return repoVet.findVetById(id);
  }


  //logic
  public UserOri findUserByIdAndType(TypeUser type, String userID) {
    if (type == TypeUser.administrative || type == TypeUser.accountant || type == TypeUser.veterinarian) {
      return repoAccountant.findById(userID).orElseThrow(()->new NoSuchElementException("no emp found"));
    }
    else {
      return repoPetOwner.findPetOwnerById(userID).orElseThrow(()-> new NoSuchElementException("no pet owner found"));
    }
  }

  // sql
  public UserEmpVetOri getVetProfile(String vetID) {
    return repoVet.findVetById(vetID);
  }

  public UserClientPetOwnerOri displayPetOwnerProfile(String owner_id) {
    return repoPetOwner.findPetOwnerById(owner_id).orElseThrow(()-> new NoSuchElementException("no owner found"));
  }

  // sql
  public List<ProjAppointmentDetail> getVetTransactionList(UserEmpVetOri vet) {
    List<ProjAppointmentDetail> transactions = new ArrayList<>();
    List<AppointmentImp> apts = repoApt.findAppointmentsByVetEmployee(vet.getVetID());
    for (AppointmentOri apt : apts) {
      UserEmployeeImpl empVet = repoAccountant.findById(vet.getVetID()).orElseThrow(()-> new NoSuchElementException("vet not found"));
      transactions.add(repoApt.displayDetailsOfAppointmentInClinic(apt.getAppointID(), empVet.getClinic().getClinicID()));
    }
    return transactions;
  }

  public List<ProjAppointmentDetail> getOwnerTransactionList(UserClientPetOwnerOri owner) {
    List<PetImpl> pets = owner.getPets();
    List<AppointmentOri> apts = new ArrayList<>();
    for (PetImpl pet : pets) {
      apts.addAll(repoApt.findAppointmentsByPetId(pet.getPetID()));
    }
    List<ProjAppointmentDetail> transactions = new ArrayList<>();
    for (AppointmentOri apt : apts) {
      transactions.add(repoApt.displayDetailsOfAppointment(apt.getAppointID()));
    }
    return transactions;
  }

  // clinic
  public ClinicImp findClinicById(int clinicID) {
    return repoClinic.findById(clinicID).orElseThrow(()-> new NoSuchElementException("clinic not in database"));
  }

  public List<InvoiceOri> getAllTransactions() {
    return new ArrayList<>(repoSqlInvoice.findAll());
  }

  public UserEmployeeImpl findAccountantById(String id) {
    return repoAccountant.findById(id).orElseThrow(() -> new NoSuchElementException("accoutant not found"));
  }

  public void setEmpType(UserOri user, TypeUser type) {
    repoAccountant.updateEmployeeType(user.getUserID(), type);
  }

  public boolean updateClinic(ClinicOri clinic) {
    try {
      repoClinic.save((ClinicImp) clinic);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public boolean deleteClinic(int clinicID) {
    try {
      repoClinic.deleteById(clinicID);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public boolean addClinic(ClinicImp clinic) {
    try {
      repoClinic.save(clinic);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public UserEmpVetOri findVetProfileById(String userID) {
    return repoVet.findById(userID).orElseThrow(() -> new NoSuchElementException("no vet found"));
  }

  public boolean deleteVetById(String id) {
    try {
      repoVet.deleteById(id);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public boolean addVet(UserEmpVetOri vet, UserEmployeeOri emp) {
    try {
      repoVet.save((UserEmpVetImpl) vet);
      repoAccountant.save((UserEmployeeImpl) emp);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public boolean updateVet(UserEmpVetOri vet) {
    try {
      repoVet.save((UserEmpVetImpl) vet);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }


}
