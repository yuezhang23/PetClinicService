package com.example.WeePetClinic.Components.Repository;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface repoSqlEmpVet extends JpaRepository<UserEmpVetImpl, String> {
  //basic
  @Query(value = "SELECT * FROM veterinarian", nativeQuery = true)
  List<UserEmpVetImpl> findAllVetsByProfile();

  @Query(value = "SELECT * FROM veterinarian WHERE vet_id = :id", nativeQuery = true)
  UserEmpVetImpl findVetById(@Param("id") String id);

  @Query(value = "CALL update_password(:p_type, :p_username,:p_password)", nativeQuery = true)
  void passwordReset(@Param("p_type") TypeUser usrType, @Param("p_username") String userID, @Param("p_password") String newPassword);

  @Query(value = "SELECT * FROM employee e JOIN veterinarian v ON v.vet_id = e.emp_id WHERE v.vet_id = :id", nativeQuery = true)
  ProjVetEmpDetail findVetDetailById(@Param("id") String id);

  @Query(value = "UPDATE veterinarian SET vet_id = :name WHERE vet_id = :id", nativeQuery = true)
  void resignInVeterinarian(@Param("id") String vetId, @Param("name") String vetName) ;


  @Query(value = "INSERT INTO veterinarian (vet_id, vet_license, vet_certificate, work_years, specialization) VALUES (:id, :license, :certificate, :years, :specialty)", nativeQuery = true)
  void addVeterinarian(@Param("id") String vetId, @Param("license") String vetLicense, @Param("certificate") String certificate, @Param("years") int workYears, @Param("specialty") String specialty);

}
