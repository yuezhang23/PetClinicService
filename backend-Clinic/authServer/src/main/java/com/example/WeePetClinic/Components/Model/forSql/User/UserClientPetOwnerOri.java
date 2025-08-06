package com.example.WeePetClinic.Components.Model.forSql.User;

import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.forSql.InvoiceImplSql;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;

import java.util.List;

public interface UserClientPetOwnerOri extends UserClientOri {
  List<PetImpl> getPets();
  void setPets(List<PetImpl> pets);
  List<InvoiceImplSql> getInvoices();
  void setInvoices(List<InvoiceImplSql> invoices);

}
