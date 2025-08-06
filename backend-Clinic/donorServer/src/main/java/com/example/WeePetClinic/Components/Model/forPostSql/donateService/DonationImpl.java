package com.example.WeePetClinic.Components.Model.forPostSql.donateService;

import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorOri;
import com.example.WeePetClinic.utilities.TypeDonation;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "donation")
public class DonationImpl implements DonationOri {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "donation_id")
  private int donationId;

  @ManyToOne
  @JoinColumn(name = "donor_id", nullable = false)
  private UserClientDonorImpl donor;

  @Enumerated(EnumType.STRING)
  @Column (name = "donation_type")
  private TypeDonation type;

  @Column (name = "donation_time")
  private Timestamp date;

  @Column (name = "clinic_location_id")
  private int clinicID;

  @Column(name = "description")
  private String description;

  @Column (name = "admin_id")
  private String adminID;

  @Column (name = "buffer_period")
  private boolean buffer;

  //setter getter


  public void setDonor(UserClientDonorImpl donor) {
    this.donor = donor;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAdminID() {
    return adminID;
  }

  public void setAdminID(String adminID) {
    this.adminID = adminID;
  }

  public boolean isBuffer() {
    return buffer;
  }

  public void setBuffer(boolean buffer) {
    this.buffer = buffer;
  }

  @Override
  public int getDonationId() {
    return donationId;
  }

  @Override
  public void setDonationId(int donationId) {
    this.donationId = donationId;
  }

  @Override
  public UserClientDonorOri getDonor() {
    return donor;
  }

  @Override
  public void setDonor(UserClientDonorOri donor) {
    this.donor = (UserClientDonorImpl) donor;
  }

  @Override
  public TypeDonation getType() {
    return type;
  }

  @Override
  public void setType(TypeDonation type) {
    this.type = type;
  }

  @Override
  public Timestamp getDate() {
    return date;
  }

  @Override
  public void setDate(Timestamp date) {
    this.date = date;
  }

  @Override
  public int getClinicID() {
    return clinicID;
  }

  @Override
  public void setClinicID(int clinicID) {
    this.clinicID = clinicID;
  }
}
