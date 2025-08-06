package com.example.WeePetClinic.Components.Controller.requestDTO;

import java.time.LocalDateTime;

public class appointReq {
     private String ownerID;
     private int petID;
     private LocalDateTime appoint_time;
     private String description;

  public String getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }

  public int getPetID() {
    return petID;
  }

  public void setPetID(int petID) {
    this.petID = petID;
  }

  public LocalDateTime getAppoint_time() {
    return appoint_time;
  }

  public void setAppoint_time(LocalDateTime appoint_time) {
    this.appoint_time = appoint_time;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
