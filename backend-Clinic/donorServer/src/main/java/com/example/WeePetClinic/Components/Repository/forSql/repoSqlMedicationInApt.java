package com.example.WeePetClinic.Components.Repository.forSql;

import com.example.WeePetClinic.Components.Model.forSql.clinicService.medicationInAppointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repoSqlMedicationInApt extends JpaRepository<medicationInAppointment, Object> {
  @Query(value = "CALL add_medication(:appoint_p, :medication_p, :quantity_p, :interval_p)", nativeQuery = true)
  void addMedication(@Param("appoint_p") int aptID, @Param("medication_p") String medID, @Param("quantity_p") int quantity, @Param("interval_p") int interval);

  @Query(value = "DELETE FROM appointment_medication WHERE appointment_id = :appoint_p AND medication_name = :medication_p", nativeQuery = true)
  void deleteMedication(@Param("appoint_p") int aptID, @Param("medication_p") String medID);

  @Query(value = "UPDATE appointment_medication SET medication_name = :v1, quantity = :v2, time_interval_days = :v3 WHERE appoint_id = :appoint_p AND medication_name = :medication_p", nativeQuery = true)
  void updateMedication(@Param("appoint_p") int aptID, @Param("medication_p") String medID, @Param("v1") String newMed, @Param("v2") int newQuantity, @Param("v3") int newInterval);

}
