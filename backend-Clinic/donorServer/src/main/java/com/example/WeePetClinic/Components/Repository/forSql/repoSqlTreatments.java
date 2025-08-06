package com.example.WeePetClinic.Components.Repository.forSql;

import com.example.WeePetClinic.Components.Model.forSql.clinicService.ClinicServiceOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.MedicationImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.TreatmentImpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repoSqlTreatments extends JpaRepository<TreatmentImpl, Object> {

  @Query(value = "SELECT ts.* FROM available_treatment_in_clinic ac JOIN treatment_service ts ON ac.service_id = ts.service_id WHERE clinic_id = :cid", nativeQuery = true)
  List<TreatmentImpl> findAvailableTreatmentsInClinic(@Param("cid") int clinicId);

  @Query(value = "SELECT * FROM treatment_service ts WHERE service_id = :sid", nativeQuery = true)
  TreatmentImpl findTreatmentByID(@Param("sid") int treatmentId);

  @Query(value = "SELECT * FROM treatment_service ts WHERE treatment_name = :t_name AND treatment_description = :description", nativeQuery = true)
  TreatmentImpl findTreatmentByNameDescription(@Param("t_name") String name, @Param("description") String description );


}
