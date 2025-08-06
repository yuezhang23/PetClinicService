package com.example.WeePetClinic.Components.Repository;
import com.example.WeePetClinic.Components.Model.clinicService.MedicationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repoSqlMedications extends JpaRepository<MedicationImpl, Object> {

  @Query(value = "SELECT ts.* FROM available_medication_in_clinic ac JOIN medication_service ms ON ac.med_id = ms.medication_name WHERE clinic_id = :cid", nativeQuery = true)
  List<MedicationImpl> findAvailableMedicationsInClinic(@Param("cid") int clinicId);

  @Query(value = "SELECT * FROM medication_service ms WHERE ms.medication_name = :mid", nativeQuery = true)
  MedicationImpl findMedicationByID(@Param("mid") String medicationId);


}


