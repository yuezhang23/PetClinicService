package com.example.WeePetClinic.Components.Model.forSql.clinicService;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.forSql.RoomImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class AppointmentImp implements AppointmentOri {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "appointment_id")
  private int appointID;

  @Column (name = "appointment_datetime")
  private LocalDateTime appointment_time;

  @Column (name = "appointment_description")
  private String description;

  @Column (name = "show_up")
  private boolean showUp;

  @JsonIgnore
  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumn (name = "clinic_id", nullable = false)
  private ClinicImp clinic;

  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumn(name = "patient_id", nullable = false)
  private PetImpl pet;

  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumn (name = "admin_id", nullable = false)
  private UserEmployeeImpl admin;

  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumn (name = "vet_id", nullable = false)
  private UserEmpVetImpl vet;

  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumn (name = "room_id")
  private RoomImpl room;


  // getter setter

  @Override
  public int getAppointID() {
    return appointID;
  }

  @Override
  public void setAppointID(int appointID) {
    this.appointID = appointID;
  }

  @Override
  public LocalDateTime getAppointment_time() {
    return appointment_time;
  }

  @Override
  public void setAppointment_time(LocalDateTime appointment_time) {
    this.appointment_time = appointment_time;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean getShowUp() {
    return showUp;
  }

  @Override
  public void setShowUp(boolean showUp) {
    this.showUp = showUp;
  }

  @Override
  public ClinicImp getClinic() {
    return clinic;
  }

  @Override
  public void setClinic(ClinicImp clinic) {
    this.clinic = clinic;
  }

  @Override
  public PetImpl getPet() {
    return pet;
  }

  @Override
  public void setPet(PetImpl pet) {
    this.pet = pet;
  }

  @Override
  public UserEmployeeImpl getAdmin() {
    return admin;
  }

  @Override
  public void setAdmin(UserEmployeeImpl admin) {
    this.admin = admin;
  }

  @Override
  public UserEmpVetImpl getVet() {
    return vet;
  }

  @Override
  public void setVet(UserEmpVetImpl vet) {
    this.vet = vet;
  }

  @Override
  public RoomImpl getRoom() {
    return room;
  }

  @Override
  public void setRoom(RoomImpl room) {
    this.room = room;
  }
}
