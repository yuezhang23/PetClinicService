package com.example.WeePetClinic.Components.Controller.requestDTO;

public class profileReq {
  private int appointmentID;
  private String name;

  public int getAppointmentID() {
    return appointmentID;
  }

  public void setAppointmentID(int appointmentID) {
    this.appointmentID = appointmentID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
