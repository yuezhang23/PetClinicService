package com.example.WeePetClinic.Components.Model;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class BillingAddressImpl implements BillingAddressOri {
  @Column(name = "street_number")
  private int streetNumber;
  @Column(name = "street_name")
  private String streetName;
  @Column(name = "town")
  private String town;
  @Column(name = "state_abbrev")
  private String stateAbbrev;
  @Column(name = "zip")
  private String zip;
  @Transient
  private String country = "USA";

  // Getters and Setters
  @Override
  public int getStreetNumber() {
    return streetNumber;
  }

  @Override
  public void setStreetNumber(int streetNumber) {
    this.streetNumber = streetNumber;
  }

  @Override
  public String getStreetName() {
    return streetName;
  }

  @Override
  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  @Override
  public String getTown() {
    return town;
  }

  @Override
  public void setTown(String town) {
    this.town = town;
  }

  @Override
  public String getStateAbbrev() {
    return stateAbbrev;
  }

  @Override
  public void setStateAbbrev(String stateAbbrev) {
    this.stateAbbrev = stateAbbrev;
  }

  @Override
  public String getZip() {
    return zip;
  }

  @Override
  public void setZip(String zip) {
    this.zip = zip;
  }

  @Override
  public String getCountry() {
    return country;
  }

  @Override
  public void setCountry(String country) {
    this.country = country;
  }
}
