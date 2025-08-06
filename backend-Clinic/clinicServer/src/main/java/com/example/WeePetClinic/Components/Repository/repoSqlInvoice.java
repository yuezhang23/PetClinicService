package com.example.WeePetClinic.Components.Repository;
import com.example.WeePetClinic.Components.Model.clinicService.InvoiceImplSql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repoSqlInvoice extends JpaRepository<InvoiceImplSql, Integer> {

  @Query(value = "SELECT * FROM invoice i WHERE appointment_id = :p_appointment_id", nativeQuery = true)
  List<InvoiceImplSql> displayInvoiceForAppointment(@Param("p_appointment_id") int appointId);

  @Query(value = "SELECT * FROM invoice i JOIN pet_owner p ON i.owner_id = p.owner_id WHERE i.owner_id = :oid", nativeQuery = true)
  List<Object> displayInvoiceByOwner(@Param("oid") String OwnerID);

  @Query(value = "DELETE FROM invoice WHERE owner_id = :id", nativeQuery = true)
  void deleteInvoicesByOwnerID(@Param("id") String petOwnerId);

  @Query (value = "CALL update_payment_received(:apt_id, :amount)", nativeQuery = true)
  void makePayment(@Param("apt_id") int appointmentId, @Param("amount") double invoiceAmount);


  @Query(value = "SELECT * FROM invoice i WHERE i.owner_id = :oid", nativeQuery = true)
  List<InvoiceImplSql> findAllByOwner_id(String oid);
}
