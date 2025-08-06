package com.example.WeePetClinic.Components.Repository.forSql;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.utilities.TypeCreditcard;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface repoSqlClientPetOwner extends JpaRepository<UserClientPetOwnerImpl, String> {
  //basic
  @Query(value = "SELECT * FROM pet_owner", nativeQuery = true)
  List<UserClientPetOwnerImpl> findAllPetOwners();

  @Query(value = "SELECT * FROM pet_owner p WHERE p.owner_id = :id", nativeQuery = true)
  Optional<UserClientPetOwnerImpl> findPetOwnerById(@Param("id") String id);

  @Query(value = "DELETE FROM pet_owner WHERE owner_id = :id", nativeQuery = true)
  void deletePetOwner(@Param("id") String petOwnerId);

  @Query(value = "INSERT INTO pet_owner (owner_id, id_password, card_number, owner_firstname, owner_lastname, card_type, billing_street_number, billing_street_name, billing_town, billing_state_abbrev, billing_zip) "
          + "VALUES (:id, :password, :card_number, :firstname, :lastname, :card_type, :street_number, :street_name, :town, :state_abbrev, :zip)", nativeQuery = true)
  void addOwner(@Param("id") String ownerId, @Param("password") String password, @Param("card_number") String cardNumber,
                @Param("firstname") String firstN, @Param("lastname") String lastN, @Param("card_type")TypeCreditcard creditCardType,
                @Param("street_number") int streetNo, @Param("street_name") String streetName,
                @Param("town") String cityName, @Param("state_abbrev") String stateAbbrev,
                @Param("zip") String zip);

  @Query(value = "SELECT * FROM pet_owner WHERE owner_lastname = :lname AND owner_firstname = :fname AND id_password = :password", nativeQuery = true)
  UserClientPetOwnerImpl findPetOwnerByNameAndPassword(@Param("fname") String firstName, @Param("lname") String lastName, @Param("password") String password);

}



