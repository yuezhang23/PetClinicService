package com.example.WeePetClinic.Components.Model.forSql.Pet;

import com.example.WeePetClinic.utilities.TypePetCat;

import java.lang.annotation.ElementType;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pet_category")
@Embeddable
public class PetCat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "category_id")
  private int catID;
  @Column (name = "species_treated")
  @Enumerated(EnumType.STRING)
  private TypePetCat species;
  @Column (name = "breed_name")
  private String breed_name;
  @Column (name = "breed_description")
  private String breed_description;

  @OneToMany(mappedBy = "category")
  private List<PetImpl> pets;

  public int getCatID() {
    return catID;
  }

  public void setCatID(int catID) {
    this.catID = catID;
  }

  public TypePetCat getSpecies() {
    return species;
  }

  public void setSpecies(TypePetCat species) {
    this.species = species;
  }

  public String getBreed_name() {
    return breed_name;
  }

  public void setBreed_name(String breed_name) {
    this.breed_name = breed_name;
  }

  public String getBreed_description() {
    return breed_description;
  }

  public void setBreed_description(String breed_description) {
    this.breed_description = breed_description;
  }
}
