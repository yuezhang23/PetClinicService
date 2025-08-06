package com.example.WeePetClinic.Components.RestController.reponseDTO;

import com.example.WeePetClinic.utilities.TypeUser;

public class ProjUser {
  private String emp_id;
  private String id_password;
  private String phone;
  private String emp_last_name;
  private String emp_first_name;
  private int street_number;
  private String street_name;
  private String town;
  private String state_abbrev;
  private String zip;
  private String statement;
  private TypeUser emp_type;
  private String email;

  // Getters and setters
  public String getEmp_id() { return emp_id; }
  public void setEmp_id(String emp_id) { this.emp_id = emp_id; }

  public String getId_password() { return id_password; }
  public void setId_password(String id_password) { this.id_password = id_password; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getEmp_last_name() { return emp_last_name; }
  public void setEmp_last_name(String emp_last_name) { this.emp_last_name = emp_last_name; }

  public String getEmp_first_name() { return emp_first_name; }
  public void setEmp_first_name(String emp_first_name) { this.emp_first_name = emp_first_name; }

  public int getStreet_number() { return street_number; }
  public void setStreet_number(int street_number) { this.street_number = street_number; }

  public String getStreet_name() { return street_name; }
  public void setStreet_name(String street_name) { this.street_name = street_name; }

  public String getTown() { return town; }
  public void setTown(String town) { this.town = town; }

  public String getState_abbrev() { return state_abbrev; }
  public void setState_abbrev(String state_abbrev) { this.state_abbrev = state_abbrev; }

  public String getZip() { return zip; }
  public void setZip(String zip) { this.zip = zip; }

  public String getStatement() { return statement; }
  public void setStatement(String statement) { this.statement = statement; }

  public TypeUser getEmp_type() { return emp_type; }
  public void setEmp_type(TypeUser emp_type) { this.emp_type = emp_type; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
} 