package com.example.WeePetClinic.Components.Model.forSql.clinicService;
import com.example.WeePetClinic.utilities.TypeService;
import com.example.WeePetClinic.utilities.TypeTreatment;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Embeddable
@Table(name="treatment_service")
public class TreatmentImpl implements ClinicServiceOri<Integer> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "service_id")
  private int serviceId;
  @Column(name = "treatment_type")
  private TypeTreatment type;
  @Column(name = "treatment_name")
  private String treatmentName;
  @Column(name = "treatment_description")
  private String treatment_description;
  @Column(name = "regular_price")
  private double price;
  private final TypeService serviceType = TypeService.treatment;


  @Override
  public Integer getServiceID() {
    return serviceId;
  }

  @Override
  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  @Override
  public TypeService getServiceType() {
    return serviceType;
  }

  @Override
  public String getServiceName() {
    return this.treatmentName;
  }

  @Override
  public void setServiceName(String treatmentName) {
    this.treatmentName = treatmentName;
  }

  @Override
  public String getDescription() {
    return this.treatment_description;
  }

  @Override
  public void setDescription(String description) {
    this.treatment_description = description;
  }

  @Override
  public double getRegularPrice() {
    return this.price;
  }

  @Override
  public void setRegularPrice(double price) {
    this.price = price;
  }
}
