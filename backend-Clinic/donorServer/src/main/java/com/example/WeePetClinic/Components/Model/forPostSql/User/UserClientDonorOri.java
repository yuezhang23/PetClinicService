package com.example.WeePetClinic.Components.Model.forPostSql.User;

import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.forPostSql.InvoiceImplPostSql;
import com.example.WeePetClinic.Components.Model.forPostSql.donateService.DonationImpl;
import com.example.WeePetClinic.utilities.TypeDonor;

import java.util.Date;
import java.util.List;

public interface UserClientDonorOri extends UserClientOri {

  TypeDonor getDonorType();

  void setDonorType(TypeDonor donorType);

  List<InvoiceImplPostSql> getInvoicesPos();

  void setInvoicesPos(List<InvoiceImplPostSql> invoicesPos);

  List<DonationImpl> getDonations();

  void setDonations(List<DonationImpl> donations);

  String getProfessionBackground();

  void setProfessionBackground(String professionBackground);

  String getPersonalStatement();

  void setPersonalStatement(String personalStatement);

  int getDonateTimes();

  void setDonateTimes(int donateTimes);

  Date getBirthday();

  void setBirthday(Date birthday);
}
