package com.example.WeePetClinic.Components.Model.forPostSql.User;

import com.example.WeePetClinic.Components.Model.BillingAddressImpl;
import com.example.WeePetClinic.Components.Model.UserClientOriAbs;
import com.example.WeePetClinic.Components.Model.forPostSql.InvoiceImplPostSql;
import com.example.WeePetClinic.Components.Model.forPostSql.donateService.DonationImpl;
import com.example.WeePetClinic.utilities.TypeDonor;
import com.example.WeePetClinic.utilities.TypeUser;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="donor")
@AttributeOverrides({
        @AttributeOverride(name = "userID", column = @Column(name = "donor_id")),
        @AttributeOverride(name = "firstName", column = @Column(name = "donor_firstname")),
        @AttributeOverride(name = "lastName", column = @Column(name = "donor_lastname")),
})
public class UserClientDonorImpl extends UserClientOriAbs implements UserClientDonorOri {


  @Enumerated(EnumType.STRING)
  @Column (name = "type")
  private TypeDonor donorType;

  @Column (name = "profession_background")
  private String professionBackground;
  @Column (name = "personal_statement")
  private String personalStatement;
  @Column (name = "donate_times")
  private int donateTimes;
  @Column (name = "birthday")
  private Date birthday = null;

  @OneToMany(mappedBy = "donor", fetch = FetchType.EAGER)
  private List<DonationImpl> donations;

  @OneToMany(mappedBy = "donor")
  private List<InvoiceImplPostSql> invoicesPos;

  // getters setters
  @Override
  public TypeUser getUserType() {
    return TypeUser.donor;
  }

  @Override
  public void setUserType(TypeUser userType) {

  }


  @Override
  public List<InvoiceImplPostSql> getInvoicesPos() {
    return invoicesPos;
  }

  @Override
  public void setInvoicesPos(List<InvoiceImplPostSql> invoicesPos) {
    this.invoicesPos = invoicesPos;
  }

  @Override
  public List<DonationImpl> getDonations() {
    return donations;
  }

  @Override
  public void setDonations(List<DonationImpl> donations) {
    this.donations = donations;
  }


  @Override
  public TypeDonor getDonorType() {
    return donorType;
  }

  @Override
  public void setDonorType(TypeDonor donorType) {
    this.donorType = donorType;
  }

  @Override
  public String getProfessionBackground() {
    return professionBackground;
  }

  @Override
  public void setProfessionBackground(String professionBackground) {
    this.professionBackground = professionBackground;
  }

  @Override
  public String getPersonalStatement() {
    return personalStatement;
  }

  @Override
  public void setPersonalStatement(String personalStatement) {
    this.personalStatement = personalStatement;
  }

  @Override
  public int getDonateTimes() {
    return donateTimes;
  }

  @Override
  public void setDonateTimes(int donateTimes) {
    this.donateTimes = donateTimes;
  }

  @Override
  public Date getBirthday() {
    return birthday;
  }

  @Override
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
}
