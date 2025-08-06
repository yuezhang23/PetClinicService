package com.example.WeePetClinic.Components.Service.postSqlUser.Implementation;

import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forPostSql.InvoiceImplPostSql;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Service.postSqlUser.Encapsulation.repoAccessForDonor;
import com.example.WeePetClinic.Components.Service.postSqlUser.ServiceClientDonor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class postSqlServiceClientDonorImpl implements ServiceClientDonor<UserClientDonorImpl> {
  private repoAccessForDonor accessDonor;

  @Autowired
  public postSqlServiceClientDonorImpl(repoAccessForDonor donorM) {
    this.accessDonor = donorM;
  }

  //getters setters
  @Override
  public repoAccessForDonor getAccessDonor() {
    return accessDonor;
  }
  @Override
  public void setAccessDonor(repoAccessForDonor accessDonor) {
    this.accessDonor = accessDonor;
  }

  //crud
  @Override
  @Transactional
  public UserClientDonorImpl getDonor(String id) {

    UserClientDonorImpl donor = accessDonor.findDonorById(id);
    return donor;
  }

  @Override
  public void setDonor(UserClientDonorImpl donor) {
    accessDonor.addDonor(donor);
  }

  @Override
  public void deleteDonorById(String donorId) {
    accessDonor.deleteDonor(donorId);
  }

  @Override
  public void addDonor(UserClientDonorImpl client) {
    accessDonor.addDonor(client);
  }

  // logic
  @Override
  public Boolean makeDonation(double invoiceAmount) {
    return null;
  }


  @Override
  public List<InvoiceOri> displayAllInvoicesForUser(UserOri user) {
    List<InvoiceOri> lst = new ArrayList<>();
    UserClientDonorImpl donor = accessDonor.findDonorById(user.getUserID());
    List<InvoiceImplPostSql> invoices =  donor.getInvoicesPos();
    lst.addAll(invoices);
    return lst;
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
