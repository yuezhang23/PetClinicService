package com.example.WeePetClinic.Components.Model.forSql.User;

import com.example.WeePetClinic.Components.Model.UserOriAbs;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.utilities.TypeUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class UserEmployeeImpl extends UserOriAbs implements UserEmployeeOri {
  @Enumerated(EnumType.STRING)
  @Column(name = "emp_type")
  private TypeUser userType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "clinic_id", nullable = false)
  private ClinicImp clinic;

  @Override
  public String getStatement() {
    return statement;
  }

  @Override
  public void setStatement(String statement) {
    this.statement = statement;
  }

  @Column(name = "statement")
  private String statement;

  @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<AppointmentImp> appointments;

  //getters setters
  @Override
  public ClinicImp getClinic() {
    return clinic;
  }

  @Override
  public void setClinic(ClinicImp clinic) {
    this.clinic = clinic;
  }


  @Override
  public List<AppointmentImp> getAppointments() {
    return appointments;
  }

  @Override
  public void setAppointments(List<AppointmentImp> appointments) {
    this.appointments = appointments;
  }

  @Override
  public TypeUser getUserType() {
    return userType;
  }

  @Override
  public void setUserType(TypeUser userType) {
    this.userType = userType;
  }
}
