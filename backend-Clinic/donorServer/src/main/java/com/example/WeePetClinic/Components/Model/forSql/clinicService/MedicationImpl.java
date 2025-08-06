package com.example.WeePetClinic.Components.Model.forSql.clinicService;
import com.example.WeePetClinic.utilities.TypeService;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Embeddable
@Table(name="medication_service")
public class MedicationImpl implements ClinicServiceOri<String> {
  @Id
  @Column (name = "medication_name")
  private String medName;
  @Column (name = "medication_description")
  private String description;
  @Column (name = "price")
  private double regularPrice;
  private final TypeService type = TypeService.medication;

  @Override
  public String getServiceID() {
    return this.medName;
  }

  @Override
  public void setServiceId(String serviceId) {
    this.medName = serviceId;
  }

  @Override
  public TypeService getServiceType() {
    return type;
  }

  @Override
  public String getServiceName() {
    return medName;
  }

  @Override
  public void setServiceName(String medName) {
    this.medName = medName;
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
  public double getRegularPrice() {
    return regularPrice;
  }

  @Override
  public void setRegularPrice(double regularPrice) {
    this.regularPrice = regularPrice;
  }
}
