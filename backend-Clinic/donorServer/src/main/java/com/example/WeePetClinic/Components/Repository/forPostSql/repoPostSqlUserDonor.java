package com.example.WeePetClinic.Components.Repository.forPostSql;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repoPostSqlUserDonor extends JpaRepository<UserClientDonorImpl, String> {

//  @Query(value = "SELECT * FROM donor WHERE donor_id = :id", nativeQuery = true)
//  UserClientDonorImpl findDonorById(@Param("id") String id);
//
//  @Query(value = "INSERT INTO donor (donor_id, donor_type, donor_certificate, specialization, donateTimes) VALUES (:id, :type, :certificate, :specialty, :quantity)", nativeQuery = true)
//  void addNewDonor(@Param("id") String donorId, @Param("type") TypeDonor type, @Param("certificate") String certificate, @Param("specialty") String specialty, @Param("quantity") int times);
//
//  @Query(value = "UPDATE donor SET donor_type = :type WHERE donor_id = :id", nativeQuery = true)
//  void updateDonorType(@Param("id") String donorId, @Param("type") TypeDonor donorType) ;
//
//  @Query(value = "DELETE FROM donor WHERE donor_id = :id", nativeQuery = true)
//  void deleteDonorById(@Param("id") String donorId);
//
//  @Query(value = "SELECT * FROM donor WHERE donor_lastname = :lname AND donor_firstname = :fname AND id_password = :password", nativeQuery = true)
//  UserClientDonorImpl findDonorByNameAndPassword(@Param("fname") String firstName, @Param("lname") String lastName, @Param("password") String password);

}
