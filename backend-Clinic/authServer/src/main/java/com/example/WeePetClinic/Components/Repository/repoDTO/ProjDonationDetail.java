package com.example.WeePetClinic.Components.Repository.repoDTO;

import java.sql.Date;

public interface ProjDonationDetail {
  int getDonation_id();
  Date getDonation_time();
  String getDonor_firstName();
  String getDonor_lastName();
  String getDonor_contact();
  String getBasic_description();
  double getConfirmed_amt();
  Date getReceived_date();
} 