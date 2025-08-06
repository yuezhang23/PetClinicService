package com.example.WeePetClinic.Components.Model.forSql.clinicService;
import com.example.WeePetClinic.utilities.CompositeKey;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "appointment_treatment")
public class treatmentInAppointment {
  @EmbeddedId
  @AttributeOverrides({
          @AttributeOverride(name = "keyPart1", column = @Column(name = "appoint_id")),
          @AttributeOverride(name = "keyPart2", column = @Column(name = "service_id")),
  })
  private CompositeKey<Integer, Integer> id;

  @Column(name = "charge")
  private int charge;

  public int getAppointmentID () {
    return this.id.getKeyPart1();
  }

  public int getTreatmentID () {
    return this.id.getKeyPart2();
  }
  public void setId(CompositeKey<Integer, Integer> id) {
    this.id = id;
  }

  public int getCharge() {
    return charge;
  }

  public void setCharge(int charge) {
    this.charge = charge;
  }
}



