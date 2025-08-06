package com.example.WeePetClinic.Components.RestController.reponseDTO;

import java.sql.Date;

public class projPet {
  private int Patient_id;
  private String Pet_name;
  private String Breed_name;
  private float Height_in_cm;
  private float Weight_in_lb;
  private String Pet_status;
  private Date Date_of_birth;

  public int getPatient_id() {
    return Patient_id;
  }

  public void setPatient_id(int patient_id) {
    Patient_id = patient_id;
  }

  public String getPet_name() {
    return Pet_name;
  }

  public void setPet_name(String pet_name) {
    Pet_name = pet_name;
  }

  public String getBreed_name() {
    return Breed_name;
  }

  public void setBreed_name(String breed_name) {
    Breed_name = breed_name;
  }

  public float getHeight_in_cm() {
    return Height_in_cm;
  }

  public void setHeight_in_cm(float height_in_cm) {
    Height_in_cm = height_in_cm;
  }

  public float getWeight_in_lb() {
    return Weight_in_lb;
  }

  public void setWeight_in_lb(float weight_in_lb) {
    Weight_in_lb = weight_in_lb;
  }

  public String getPet_status() {
    return Pet_status;
  }

  public void setPet_status(String pet_status) {
    Pet_status = pet_status;
  }

  public Date getDate_of_birth() {
    return Date_of_birth;
  }

  public void setDate_of_birth(Date date_of_birth) {
    Date_of_birth = date_of_birth;
  }
}
