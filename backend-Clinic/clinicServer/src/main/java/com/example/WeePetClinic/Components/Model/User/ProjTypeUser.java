package com.example.WeePetClinic.Components.Model.User;

import com.example.WeePetClinic.utilities.TypeUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class ProjTypeUser {

  @Enumerated(EnumType.STRING)
  @Column (name = "user_type")
  @Id
  private TypeUser user_type;

  public TypeUser getUser_type() {
    return user_type;
  }

  public void setUser_type(TypeUser user_type) {
    this.user_type = user_type;
  }
}
