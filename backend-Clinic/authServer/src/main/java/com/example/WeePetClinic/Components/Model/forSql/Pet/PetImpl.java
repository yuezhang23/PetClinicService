package com.example.WeePetClinic.Components.Model.forSql.Pet;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "pet")
@Embeddable
public class PetImpl implements PetOri {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "patient_id")
  private int petID;

  @Column(name = "pet_name")
  private String petName;


  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private UserClientPetOwnerImpl petOwner;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private PetCat category;

  @Column(name = "date_of_birth")
  @Temporal(TemporalType.DATE)
  private Date birthDate;
  @Column(name = "weight_in_lb")
  private Float weight;
  @Column(name = "height_in_cm")
  private Float height;
  @Column(name = "pet_status")
  private String statusOfHealth;

  @JsonIgnore
  @OneToMany(mappedBy = "pet",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<AppointmentImp> appointments;

  //getters setters
  @Override
  public List<AppointmentOri> getAppointments() {
    List<AppointmentOri> lst = new ArrayList<>(appointments);
    return lst;
  }

  @Override
  public void setAppointments(List<AppointmentOri> appointments) {
    List<AppointmentImp> lst = new ArrayList<>((Collection) appointments);
    this.appointments = lst;
  }

  @Override
  public int getPetID() {
    return petID;
  }

  @Override
  public void setPetID(int petID) {
    this.petID = petID;
  }

  @Override
  public String getPetName() {
    return petName;
  }

  @Override
  public void setPetName(String petName) {
    this.petName = petName;
  }

  @Override
  public UserClientPetOwnerImpl getOwner() {
    return petOwner;
  }

  @Override
  public void setOwner(UserClientPetOwnerImpl owner) {
    this.petOwner = owner;
  }

  @Override
  public PetCat getCategory() {
    return category;
  }

  @Override
  public void setCategory(PetCat category) {
    this.category = category;
  }

  @Override
  public Date getBirthDate() {
    return birthDate;
  }

  @Override
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public Float getWeight() {
    return weight;
  }

  @Override
  public void setWeight(Float weight) {
    this.weight = weight;
  }

  @Override
  public Float getHeight() {
    return height;
  }

  @Override
  public void setHeight(Float height) {
    this.height = height;
  }

  @Override
  public String getStatusOfHealth() {
    return statusOfHealth;
  }

  @Override
  public void setStatusOfHealth(String statusOfHealth) {
    this.statusOfHealth = statusOfHealth;
  }
}
