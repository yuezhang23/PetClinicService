package com.example.WeePetClinic.Components.Repository;

import com.example.WeePetClinic.Components.Model.clinicService.ClinicImp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repoSqlClinic extends JpaRepository<ClinicImp, Integer> {
  @Query(value = "SELECT * FROM pet_clinic", nativeQuery = true)
  List<ClinicImp> findAllClinics();

  @Query(value = "SELECT * FROM pet_clinic WHERE clinic_id = :id", nativeQuery = true)
  ClinicImp findClinicById(@Param("id") int cid);
}
