package com.example.WeePetClinic.Components.Model.clinicService;
import com.example.WeePetClinic.Components.Model.BillingAddressImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;
import com.example.WeePetClinic.utilities.TypeClinic;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pet_clinic")
public class ClinicImp implements ClinicOri {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "clinic_id")
  private int ClinicID;
  @Column(name = "clinic_phone")
  private String clinicPhone;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "streetNumber", column = @Column(name = "clinic_street_number")),
          @AttributeOverride(name = "streetName", column = @Column(name = "clinic_street_name")),
          @AttributeOverride(name = "town", column = @Column(name = "clinic_town")),
          @AttributeOverride(name = "stateAbbrev", column = @Column(name = "clinic_state_abbrev")),
          @AttributeOverride(name = "zip", column = @Column(name = "clinic_zip"))
  })
  private BillingAddressImpl address;

  @Enumerated(EnumType.STRING)
  @Column(name = "clinic_type")
  private TypeClinic type;

  @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY)
  private List<UserEmployeeImpl> employees;

  @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY)
  private List<RoomImpl> rooms;

  @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY)
  private List<AppointmentImp> appointments;

  //getters setters
  @Override
  public TypeClinic getType() {
    return type;
  }

  @Override
  public void setType(TypeClinic type) {
    this.type = type;
  }

  @Override
  public List<RoomImpl> getRooms() {
    return rooms;
  }

  @Override
  public void setRooms(List<RoomImpl> rooms) {
    this.rooms = rooms;
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
  public int getClinicID() {
    return ClinicID;
  }

  @Override
  public void setClinicID(int clinicID) {
    ClinicID = clinicID;
  }

  @Override
  public String getClinicPhone() {
    return clinicPhone;
  }

  @Override
  public void setClinicPhone(String phone) {
    this.clinicPhone = phone;
  }

  @Override
  public BillingAddressImpl getAddress() {
    return address;
  }

  @Override
  public void setAddress(BillingAddressImpl address) {
    this.address = address;
  }

  @Override
  public TypeClinic getClinicType() {
    return type;
  }

  @Override
  public void setClinicType(TypeClinic type) {
    this.type = type;
  }

  @Override
  public List<UserEmployeeImpl> getEmployees() {
    return employees;
  }

  @Override
  public void setEmployees(List<UserEmployeeImpl> employees) {
    this.employees = employees;
  }
}
