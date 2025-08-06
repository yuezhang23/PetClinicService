package com.example.WeePetClinic.Components.Repository.forPostSql;

import com.example.WeePetClinic.Components.Model.forPostSql.donateService.DonationImpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repoPostSqlDonationBasic extends JpaRepository<DonationImpl, Integer> {

  //basic
//  @Query(value = "SELECT * FROM donation WHERE donor_id = :id", nativeQuery = true)
//  List<DonationImpl> findDonationsByDonorId(@Param("id") String donorId) ;
//
//  @Query(value = "CALL display_donation_detail(:employee_id, :donate_id)", nativeQuery = true)
//  List<ProjDonationDetail> displayDetailsInDonation(@Param("donate_id") int donateID, @Param("employee_id") String emp);
//
//
//  @Query(value = "UPDATE donation SET donor_id = 'Anonymous' WHERE donor_id = :id", nativeQuery = true)
//  void anonymizeDonationsByDonorId(@Param("id") String donorId);


}
