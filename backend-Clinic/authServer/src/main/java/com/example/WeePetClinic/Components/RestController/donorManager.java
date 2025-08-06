package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Service.ServiceClientDonor;
import com.example.WeePetClinic.Components.RestController.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class donorManager implements Manager<UserClientDonorImpl> {
  private final ServiceClientDonor<UserClientDonorImpl> donorService;
  private UserClientDonorImpl donor = new UserClientDonorImpl();

  @Autowired
  public donorManager(ServiceClientDonor<UserClientDonorImpl> donorService) {
    this.donorService = donorService;
  }

  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public UserClientDonorImpl login(String userID) {
    this.donor = donorService.getDonor(userID);
    return donor;
  }
} 