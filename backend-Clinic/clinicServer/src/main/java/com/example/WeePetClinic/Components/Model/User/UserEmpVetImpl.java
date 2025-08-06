package com.example.WeePetClinic.Components.Model.User;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="veterinarian")
@Embeddable
public class UserEmpVetImpl implements UserEmpVetOri {
  @Id
  @Column(name = "vet_id")
  private String vetID;
  @Column (name = "vet_license")
  private String license;
  @Column (name = "vet_certificate")
  private String certificate;
  @Column (name = "work_years")
  private int yearsOfWork;
  @Column (name = "specialization")
  private String specialization;

  @OneToMany(mappedBy = "vet")
  private List<AppointmentImp> appointments;

  //getters setters
  @Override
  public List<AppointmentImp> getAppointments() {
    return appointments;
  }
  @Override
  public void setAppointments(List<AppointmentImp> appointments) {
    this.appointments = appointments;
  }

  @Override
  public String getVetID() {
    return vetID;
  }

  @Override
  public void setVetID(String userID) {
    this.vetID = userID;
  }

  @Override
  public String getLicense() {
    return license;
  }

  @Override
  public void setLicense(String license) {
    this.license = license;
  }

  @Override
  public String getCertificate() {
    return certificate;
  }

  @Override
  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }

  @Override
  public int getYearsOfWork() {
    return yearsOfWork;
  }

  @Override
  public void setYearsOfWork(int yearsOfWork) {
    this.yearsOfWork = yearsOfWork;
  }

  @Override
  public String getSpecialization() {
    return specialization;
  }

  @Override
  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }
}

