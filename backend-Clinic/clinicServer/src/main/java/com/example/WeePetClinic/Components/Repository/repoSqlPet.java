package com.example.WeePetClinic.Components.Repository;

import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjPetProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repoSqlPet extends JpaRepository<PetImpl, Integer> {
  @Query (value = "CALL display_pet_info(:owner_id)", nativeQuery = true)
  List<ProjPetProfile> displayOwnerPets(@Param("owner_id") String ownerID);

  @Query(value = "DELETE FROM pet WHERE owner_id = :id", nativeQuery = true)
  void deletePetsByOwner(@Param("id") String petOwnerId);

  @Query (value = "SELECT * FROM pet WHERE patient_id = :id", nativeQuery = true)
  PetImpl findPetById(@Param("id") int petId);

  @Query (value = "SELECT * FROM pet WHERE owner_id = :id", nativeQuery = true)
  List<PetImpl> findByOwner_id(String id);
}
