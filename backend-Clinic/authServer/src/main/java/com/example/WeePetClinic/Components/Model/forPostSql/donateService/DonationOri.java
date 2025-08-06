package com.example.WeePetClinic.Components.Model.forPostSql.donateService;

import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorOri;
import com.example.WeePetClinic.utilities.TypeDonation;

import java.sql.Timestamp;

public interface DonationOri {
  int getDonationId();

  void setDonationId(int donationId);

  UserClientDonorOri getDonor();

  void setDonor(UserClientDonorOri donor);

  TypeDonation getType();

  void setType(TypeDonation type);

  Timestamp getDate();

  void setDate(Timestamp date);

  int getClinicID();

  void setClinicID(int clinicID);
}
