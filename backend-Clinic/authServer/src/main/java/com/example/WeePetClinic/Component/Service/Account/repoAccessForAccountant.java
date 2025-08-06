package com.example.WeePetClinic.Component.Service.Account;

import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantImp;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorOri;
import com.example.WeePetClinic.Components.Model.forPostSql.donateService.DonationOri;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjDonationDetail;
import com.example.WeePetClinic.Components.Repository.forPostSql.repoPostSqlDonationBasic;
import com.example.WeePetClinic.Components.Repository.forPostSql.repoPostSqlInvoice;
import com.example.WeePetClinic.Components.Repository.forPostSql.repoPostSqlUserAccountant;
import com.example.WeePetClinic.Components.Repository.forPostSql.repoPostSqlUserDonor;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlClinic;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpAdmin;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlInvoice;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpAccountant;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlClientPetOwner;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpVet;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@Component
@Service
public class repoAccessForAccountant {
  //sql
  private final repoSqlEmpAccountant repoAccountant;
  private final repoSqlEmpAdmin repoAdmin;
  private final repoSqlInvoice repoSqlInvoice;
  private final repoSqlAppointmentBasic repoApt;
  private final repoSqlEmpVet repoVet;
  private final repoSqlClientPetOwner repoPetOwner;
  private final repoSqlClinic repoClinic;

  //postPreSql
  private final repoPostSqlUserAccountant repoPosAccountant;
  private final repoPostSqlInvoice repoPosInvoice;
  private final repoPostSqlUserDonor repoDonor;
  private final repoPostSqlDonationBasic repoDonate;

  @Autowired
  public repoAccessForAccountant(repoSqlEmpAccountant repoAccountant, repoSqlEmpAdmin repoAdmin, repoSqlInvoice repoSqlInvoice,
                                 repoSqlAppointmentBasic repoApt, repoSqlEmpVet repoVet,
                                 repoSqlClientPetOwner repoPetOwner, repoSqlClinic repoClinic, repoPostSqlUserAccountant repoPosAccountant,
                                 repoPostSqlInvoice repoPosInvoice, repoPostSqlUserDonor repoDonor,
                                 repoPostSqlDonationBasic repoDonate) {
    this.repoAccountant = repoAccountant;
    this.repoAdmin = repoAdmin;
    this.repoSqlInvoice = repoSqlInvoice;
    this.repoApt = repoApt;
    this.repoVet = repoVet;
    this.repoPetOwner = repoPetOwner;
    this.repoClinic = repoClinic;
    this.repoPosAccountant = repoPosAccountant;
    this.repoPosInvoice = repoPosInvoice;
    this.repoDonor = repoDonor;
    this.repoDonate = repoDonate;
  }

  public UserEmpAccountantImp findAccountantById(String userID) {
    return repoPosAccountant.findById(userID).orElseThrow(()-> new NoSuchElementException("accountant not found"));
  }


  public void addAccountant(UserEmpAccountantImp emp) {
    repoPosAccountant.save(emp);
  }

  public void deleteAccountantById(String id) {
    repoPosAccountant.deleteById(id);
  }


  public Optional<UserEmployeeImpl> findEmpById(String id) {
    return repoAdmin.findById(id);
  }


  public UserClientDonorImpl findDonorById(String id) {
    return repoDonor.findById(id).orElseThrow(()-> new NoSuchElementException("no such donor"));
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
      return repoAdmin.findById(userID).orElseThrow(()->new NoSuchElementException("no emp found"));
    }
    else if (type == TypeUser.pet_owner) {
      return repoPetOwner.findPetOwnerById(userID).orElseThrow(()-> new NoSuchElementException("no pet owner found"));
    } else {
      return repoDonor.findById(userID).orElseThrow(()-> new NoSuchElementException("no donor found"));
    }
  }



  public UserClientDonorOri displayDonorProfile(String donor_id) {
    return repoDonor.findById(donor_id).orElseThrow(()-> new NoSuchElementException("no such donor"));
  }

  public List<ProjDonationDetail> getDonorTransactionList(UserClientDonorOri donor, UserEmpAccountantOri accountant) {
    List<ProjDonationDetail> transactions = new ArrayList<>();
    List<DonationOri> dons = new ArrayList<>();
//    dons.addAll(repoDonate.findDonationsByDonorId(donor.getUserID()));
//    for (DonationOri don : dons) {
//      transactions.addAll(repoDonate.displayDetailsInDonation(don.getDonationId(), accountant.getUserID()));
//    }
    return transactions;
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
    List<InvoiceOri> lst = new ArrayList<>();
    lst.addAll(repoSqlInvoice.findAll());
    return lst;
  }

  //helper
  public Map<TypeUser, String>  validateMultipleRole(String userID, String password) {
    Map<TypeUser, String> idMap = new HashMap<>();

    String fName = "";
    String lName = "";

    // check client role

    if (userID.contains("CUS") || userID.contains("DONOR")) {
      Optional<UserClientPetOwnerImpl> petOwner = repoPetOwner.findById(userID.replace("DONOR", "CUS")
              .substring(Math.max(0, userID.indexOf("CUS"))));

      Optional<UserClientDonorImpl> donor = repoDonor.findById(userID.replace("CUS", "DONOR")
              .substring(Math.max(0, userID.indexOf("DONOR"))));

      if (petOwner.isPresent() && petOwner.get().getPassword().equals(password)) {
        idMap.put(TypeUser.pet_owner, petOwner.get().getUserID());

        fName = petOwner.get().getFirstName();
        lName = petOwner.get().getLastName();
      }
      if (donor.isPresent() && donor.get().getPassword().equals(password)) {
        idMap.put(TypeUser.donor, donor.get().getUserID());
        fName = donor.get().getFirstName();
        lName = donor.get().getLastName();
      }

      UserEmployeeImpl emp1 = repoAdmin.findEmployeeByNameAndPassword(fName, lName, password);
      if (emp1 != null) {
        idMap.put(emp1.getUserType(), emp1.getUserID());
      }
    } else if (userID.contains("emp")) {
      Optional<UserEmployeeImpl> emp = repoAdmin.findById(userID);
      if (emp.isPresent() && emp.get().getPassword().equals(password)) {
        idMap.put(emp.get().getUserType(), emp.get().getUserID());
      }

      Optional<UserClientPetOwnerImpl> petOwner = repoPetOwner.findById(userID.substring(userID.indexOf("emp")).replace("emp", "CUS"));
      Optional<UserClientDonorImpl> donor = repoDonor.findById(userID.substring(userID.indexOf("emp")).replace("emp", "DONOR"));

      if (petOwner.isPresent() && petOwner.get().getPassword().equals(password)) {
        idMap.put(TypeUser.pet_owner, petOwner.get().getUserID());
      }
      if (donor.isPresent() && donor.get().getPassword().equals(password)) {
        idMap.put(TypeUser.donor, donor.get().getUserID());
      }
    } else {
      return new HashMap<>();
    }
    return idMap;
  }



}
