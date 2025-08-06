package com.example.WeePetClinic.Components.Model.forPostSql;

import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Model.forPostSql.donateService.DonationImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.utilities.TypeInvoice;

import java.sql.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "invoice_pos")
public class InvoiceImplPostSql implements invoiceForDonation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "invoice_id")
  private int invoiceId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "donation_id")
  private DonationImpl donation;

  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumn (name = "donor_id", nullable = false)
  private UserClientDonorImpl donor;

  @Column(name = "issued_date")
  private Date date;
  @Column(name = "total_amount_per_visit")
  private double total_amount;
  @Column(name = "pmt_received_amt")
  private double amount_received;
  @Column(name = "pmt_received_date")
  private Date date_received;

  @Column(name = "type")
  @Enumerated (EnumType.STRING)
  private TypeInvoice invoiceType = TypeInvoice.donation;

  //getters setters
  @Override
  public TypeInvoice getInvoiceType() {
    return invoiceType;
  }

  @Override
  public void setInvoiceType(TypeInvoice invoiceType) {
    this.invoiceType = invoiceType;
  }

  @Override
  public int getInvoiceId() {
    return invoiceId;
  }

  @Override
  public void setInvoiceId(int invoiceId) {
    this.invoiceId = invoiceId;
  }

  @Override
  public String getOwnerId() {
    return donor.getUserID();
  }

  @Override
  public void setOwnerId(String ownerId) {
    donor.setUserID(ownerId);
  }

  public DonationImpl getDonation() {
    return donation;
  }

  public void setDonation(DonationImpl donation) {
    this.donation = donation;
  }

  public UserClientDonorImpl getDonor() {
    return donor;
  }

  public void setDonor(UserClientDonorImpl donor) {
    this.donor = donor;
  }

  @Override
  public Date getDate() {
    return date;
  }

  @Override
  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public double getTotal_amount() {
    return total_amount;
  }

  @Override
  public void setTotal_amount(double total_amount) {
    this.total_amount = total_amount;
  }

  @Override
  public double getAmount_received() {
    return amount_received;
  }

  @Override
  public void setAmount_received(double amount_received) {
    this.amount_received = amount_received;
  }

  @Override
  public Date getDate_received() {
    return date_received;
  }

  @Override
  public void setDate_received(Date date_received) {
    this.date_received = date_received;
  }


}
