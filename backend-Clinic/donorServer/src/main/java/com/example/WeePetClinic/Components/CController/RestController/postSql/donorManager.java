package com.example.WeePetClinic.Components.CController.RestController.postSql;

import com.example.WeePetClinic.Components.CController.RestController.Manager;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Service.postSqlUser.ServiceClientDonor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/service")
public class donorManager implements Manager<UserClientDonorImpl> {
  private final ServiceClientDonor<UserClientDonorImpl> donorService;
  private UserClientDonorImpl donor = new UserClientDonorImpl();

  public donorManager(ServiceClientDonor<UserClientDonorImpl> donorService) {
    this.donorService = donorService;
  }

  @GetMapping("/api/service/role=donor/signUp")
  @Override
  public void signUp(@RequestBody UserClientDonorImpl info) {
    try {
      donorService.addDonor(info);
    } catch (Exception e) {
      System.out.println("can't update donor table");
    }
  }

  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public UserClientDonorImpl login(String userID) {
    this.donor = donorService.getDonor(userID);
    donor.setInvoicesPos(new ArrayList<>());
    donor.setDonations(new ArrayList<>());
    return donor;
  }


}
