package com.example.WeePetClinic.Components.Service;

import java.util.List;

public interface ServiceUserGeneral<T> {
  List<?> displayAllInvoicesForUser(T user);
  List<?> displayAppointmentsForUser(T user);
  boolean accountCancellation(boolean delete, T user);
  boolean updateAccount(T newAccount);
} 