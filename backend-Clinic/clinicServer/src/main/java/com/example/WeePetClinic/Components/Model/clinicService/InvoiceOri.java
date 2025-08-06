package com.example.WeePetClinic.Components.Model.clinicService;

import com.example.WeePetClinic.utilities.TypeInvoice;

import java.sql.Date;

public interface InvoiceOri {
  int getInvoiceId();
  void setInvoiceId(int invoiceID);

  String getOwnerId();
  void setOwnerId(String ownerId);

  Date getDate();

  void setDate(Date date);

  double getTotal_amount();

  void setTotal_amount(double total_amount);

  double getAmount_received();

  void setAmount_received(double amount_received);

  Date getDate_received();

  void setDate_received(Date date_received);

  TypeInvoice getInvoiceType();

  void setInvoiceType(TypeInvoice type);
}
