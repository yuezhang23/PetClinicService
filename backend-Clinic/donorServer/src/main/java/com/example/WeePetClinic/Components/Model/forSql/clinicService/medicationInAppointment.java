package com.example.WeePetClinic.Components.Model.forSql.clinicService;
import com.example.WeePetClinic.utilities.CompositeKey;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "appointment_medication")
public class medicationInAppointment {
  @EmbeddedId
  @AttributeOverrides({
          @AttributeOverride(name = "keyPart1", column = @Column(name = "appointment_id")),
          @AttributeOverride(name = "keyPart2", column = @Column(name = "medication_name")),
  })
  private CompositeKey<Integer, String> id;

  @Column(name = "quantity")
  private int quantity;
  @Column(name = "time_interval_days")
  private int time_interval;

  public int getAppointmentID () {
    return this.id.getKeyPart1();
  }

  public String getMedicationID () {
    return this.id.getKeyPart2();
  }

  public void setId(CompositeKey<Integer, String> id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getTime_interval() {
    return time_interval;
  }

  public void setTime_interval(int time_interval) {
    this.time_interval = time_interval;
  }
}



