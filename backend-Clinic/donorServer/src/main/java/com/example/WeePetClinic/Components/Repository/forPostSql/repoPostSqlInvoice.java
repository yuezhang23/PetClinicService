package com.example.WeePetClinic.Components.Repository.forPostSql;

import com.example.WeePetClinic.Components.Model.forPostSql.InvoiceImplPostSql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repoPostSqlInvoice extends JpaRepository<InvoiceImplPostSql, Integer> {
//
//  @Query(value = "SELECT * FROM invoicePos i WHERE appointment_id = :p_appointment_id", nativeQuery = true)
//  List<InvoiceImplPostSql> displayInvoiceForAppointment(@Param("p_appointment_id") int appointId);

}
