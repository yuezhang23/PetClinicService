package com.example.WeePetClinic.Components.Model.clinicService;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.utilities.TypeInvoice;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "invoice")
public class InvoiceImplSql implements InvoiceForClinic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "appointment_id")
  private int invoiceId;

  @OneToOne
  @MapsId
  @JoinColumn (name = "appointment_id", nullable = false)
  private AppointmentImp appointment;

  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn (name = "owner_id", nullable = false)
  private UserClientPetOwnerImpl petOwner;

  @Column(name = "issued_date")
  private Date date;
  @Column(name = "total_amount_per_visit")
  private double total_amount;
  @Column(name = "pmt_received_amt")
  private double amount_received;
  @Column(name = "pmt_received_date")
  private Date date_received;

  @Transient
  private TypeInvoice invoiceType = TypeInvoice.clinicTransaction;

  //getters setters
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
    return petOwner.getUserID();
  }

  @Override
  public void setOwnerId(String ownerId) {
    petOwner.setUserID(ownerId);
  }

  @Override
  public AppointmentImp getAppointment() {
    return appointment;
  }

  @Override
  public void setAppointment(AppointmentImp appointment) {
    this.appointment = appointment;
  }

  @Override
  public UserClientPetOwnerImpl getPetOwner() {
    return petOwner;
  }

  @Override
  public void setPetOwner(UserClientPetOwnerImpl petOwner) {
    this.petOwner = petOwner;
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

  @Override
  public TypeInvoice getInvoiceType() {
    return invoiceType;
  }

  @Override
  public void setInvoiceType(TypeInvoice invoiceType) {
    this.invoiceType = invoiceType;
  }
}