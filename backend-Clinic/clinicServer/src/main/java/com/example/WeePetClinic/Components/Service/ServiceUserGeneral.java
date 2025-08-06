package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.clinicService.InvoiceOri;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentOri;

import java.util.List;

public interface ServiceUserGeneral<T> {
  List<InvoiceOri> displayAllInvoicesForUser(T user);
  List<AppointmentOri> displayAppointmentsForUser(T user);

  boolean accountCancellation(boolean delete, T user);
  boolean updateAccount(T newAccount);

}

