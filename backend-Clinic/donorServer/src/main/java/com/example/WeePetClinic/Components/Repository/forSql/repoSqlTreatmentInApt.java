package com.example.WeePetClinic.Components.Repository.forSql;

import com.example.WeePetClinic.Components.Model.forSql.clinicService.treatmentInAppointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repoSqlTreatmentInApt extends JpaRepository<treatmentInAppointment, Object> {
  @Query(value = "CALL add_treatment(:appoint_p, :service_p, :actual_charge)", nativeQuery = true)
  void addTreatment(@Param("appoint_p") int aptID, @Param("service_p") int treatmentID, @Param("actual_charge") double charge);

  @Query(value = "DELETE FROM appointment_treatment WHERE appoint_id = :appoint_p AND service_id = :service_p", nativeQuery = true)
  void deleteTreatment(@Param("appoint_p") int aptID, @Param("service_p") int treatmentID);

  @Query(value = "UPDATE appointment_treatment SET service_id = :v1, charge = :v2 WHERE appoint_id = :appoint_p AND service_id = :service_p", nativeQuery = true)
  void updateTreatment(@Param("appoint_p") int aptID, @Param("service_p") int treatmentID, @Param("v1") int newID, @Param("v2") double charge);
}
