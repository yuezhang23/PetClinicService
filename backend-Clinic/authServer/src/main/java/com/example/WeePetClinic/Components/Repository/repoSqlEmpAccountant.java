package com.example.WeePetClinic.Components.Repository;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface repoSqlEmpAccountant extends JpaRepository<UserEmployeeImpl, String> {

  @Query(value = "SELECT * FROM employee JOIN accoutant a ON emp_id = a.account_id WHERE a.account_id = :id", nativeQuery = true)
  UserEmployeeImpl findAccountantById(@Param("id") String id);



}
