package com.example.WeePetClinic.Components.Model.User;

import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.UserClientOri;
import com.example.WeePetClinic.Components.Model.clinicService.InvoiceImplSql;

import java.util.List;

public interface UserClientPetOwnerOri extends UserClientOri {
  List<PetImpl> getPets();
  void setPets(List<PetImpl> pets);
  List<InvoiceImplSql> getInvoices();
  void setInvoices(List<InvoiceImplSql> invoices);

}
