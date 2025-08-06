package com.example.WeePetClinic.Components.Model.forSql.clinicService;
import com.example.WeePetClinic.utilities.CompositeKey;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
