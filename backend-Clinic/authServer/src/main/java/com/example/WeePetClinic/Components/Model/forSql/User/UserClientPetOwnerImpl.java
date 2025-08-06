package com.example.WeePetClinic.Components.Model.forSql.User;
import com.example.WeePetClinic.Components.Model.UserClientOriAbs;
import com.example.WeePetClinic.Components.Model.forSql.InvoiceImplSql;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.utilities.TypeUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

@Entity
@Table(name="pet_owner")
@AttributeOverrides({
  @AttributeOverride(name = "userID", column = @Column(name = "owner_id")),
  @AttributeOverride(name = "firstName", column = @Column(name = "owner_firstname")),
  @AttributeOverride(name = "lastName", column = @Column(name = "owner_lastname")),
  @AttributeOverride(name = "address.streetNumber", column = @Column(name = "billing_street_number")),
  @AttributeOverride(name = "address.streetName", column = @Column(name = "billing_street_name")),
  @AttributeOverride(name = "address.town", column = @Column(name = "billing_town")),
  @AttributeOverride(name = "address.stateAbbrev", column = @Column(name = "billing_state_abbrev")),
  @AttributeOverride(name = "address.zip", column = @Column(name = "billing_zip"))
})
public class UserClientPetOwnerImpl extends UserClientOriAbs implements UserClientPetOwnerOri {
  @JsonIgnore
  @OneToMany(mappedBy = "petOwner")
  private List<PetImpl> pets;

  @JsonIgnore
  @OneToMany(mappedBy = "petOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<InvoiceImplSql> invoices;


  //setters getters
  @Override
  public TypeUser getUserType() {
    return TypeUser.pet_owner;
  }

  @Override
  public void setUserType(TypeUser userType) {
  }

  @Override
  public List<PetImpl> getPets() {
    return pets;
  }

  @Override
  public void setPets(List<PetImpl> pets) {
    this.pets = pets;
  }

  @Override
  public List<InvoiceImplSql> getInvoices() {
    return invoices;
  }

  @Override
  public void setInvoices(List<InvoiceImplSql> invoices) {
    this.invoices = invoices;
  }
}
