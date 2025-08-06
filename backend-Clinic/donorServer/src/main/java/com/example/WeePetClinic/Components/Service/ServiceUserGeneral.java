package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.InvoiceOri;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;

import java.util.List;

public interface ServiceUserGeneral<T> {
  List<InvoiceOri> displayAllInvoicesForUser(UserOri user);
  List<AppointmentOri> displayAppointmentsForUser(UserOri user);
  boolean accountCancellation(boolean delete, UserOri user);

  //getters setters

}

