package com.example.WeePetClinic.Components.Model.forSql.clinicService;
import com.example.WeePetClinic.utilities.CompositeKey;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "available_treatment_in_clinic")
public class availableTreatmentInClinic {
  @EmbeddedId
  @AttributeOverrides({
          @AttributeOverride(name = "keyPart1", column = @Column(name = "service_id")),
          @AttributeOverride(name = "keyPart2", column = @Column(name = "clinic_id")),
  })
  private CompositeKey<Integer, Integer> id;
  public CompositeKey<Integer, Integer> getId() {
    return id;
  }
  public void setId(CompositeKey<Integer, Integer> id) {
    this.id = id;
  }


}
