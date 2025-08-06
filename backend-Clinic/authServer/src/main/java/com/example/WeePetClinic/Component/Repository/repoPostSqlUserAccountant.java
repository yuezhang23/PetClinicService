package com.example.WeePetClinic.Components.Repository.forPostSql;

import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantImp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repoPostSqlUserAccountant extends JpaRepository<UserEmpAccountantImp, String> {

  // basic
  @Query(value = "SELECT * FROM accountant", nativeQuery = true)
  List<UserEmpAccountantImp> findAllAccountants();

  @Query(value = "UPDATE accountant SET empid = :name WHERE empid = :aid", nativeQuery = true)
  void resignInAccountant(@Param("aid") String accountantId, @Param("name") String accountantName) ;

  @Query(value = "DELETE FROM accountant WHERE empid = :id", nativeQuery = true)
  void deleteAccountantProfile(@Param("id") String accountantId);
}
