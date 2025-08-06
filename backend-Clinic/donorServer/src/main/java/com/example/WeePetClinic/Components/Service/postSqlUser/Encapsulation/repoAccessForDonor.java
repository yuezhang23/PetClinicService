package com.example.WeePetClinic.Components.Service.postSqlUser.Encapsulation;

import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorOri;
import com.example.WeePetClinic.Components.Repository.forPostSql.repoPostSqlDonationBasic;
import com.example.WeePetClinic.Components.Repository.forPostSql.repoPostSqlUserDonor;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpAccountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


@Component
@Service
public class repoAccessForDonor {
  private final repoPostSqlUserDonor accessForDonor;
  private final repoSqlEmpAccountant repoAccountant;
  private final repoPostSqlDonationBasic accessForDonation;

  @Autowired
  public repoAccessForDonor(repoPostSqlUserDonor accessForDonor, repoSqlEmpAccountant repoAccountant, repoPostSqlDonationBasic accessForDonation) {
    this.accessForDonor = accessForDonor;
    this.repoAccountant = repoAccountant;
    this.accessForDonation = accessForDonation;
  }

  public void addDonor(UserClientDonorImpl role) {
      accessForDonor.save(role);
//    accessForDonor.addNewDonor(role.getUserID(), role.getDonorType(), role.getProfessionBackground(),role.getPersonalStatement(), role.getDonateTimes());
  }

  public void deleteDonor(String id){
    accessForDonor.deleteById(id);
  }

//  public void setDonorAnonymous(UserClientDonorOri donor) {
//    accessForDonor.deleteDonorById(donor.getUserID());
//    accessForDonation.anonymizeDonationsByDonorId(donor.getUserID());
//  }

  @Transactional
  public UserClientDonorImpl findDonorById(String userID) {
    return accessForDonor.findById(userID).orElseThrow(()-> new NoSuchElementException("no donor found"));
  }
}
