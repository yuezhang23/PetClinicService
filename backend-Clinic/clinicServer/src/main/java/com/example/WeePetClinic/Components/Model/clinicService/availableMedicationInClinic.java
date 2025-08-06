package com.example.WeePetClinic.Components.Model.clinicService;
import com.example.WeePetClinic.utilities.CompositeKey;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "available_medication_in_clinic")
public class availableMedicationInClinic {
  @EmbeddedId
  @AttributeOverrides({
          @AttributeOverride(name = "keyPart1", column = @Column(name = "med_id")),
          @AttributeOverride(name = "keyPart2", column = @Column(name = "clinic_id")),
  })
  private CompositeKey<String, Integer> id;

  public CompositeKey<String, Integer> getId() {
    return id;
  }

  public void setId(CompositeKey<String, Integer> id) {
    this.id = id;
  }
}

