package com.example.WeePetClinic.Components.Repository.forSql;

import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface repoSqlEmpAdmin extends JpaRepository<UserEmployeeImpl, String> {
  @Query(value = "UPDATE appointment SET emp_id = :name WHERE emp_id = :id", nativeQuery = true)
  void resignInAppointment(@Param("id") String empId, @Param("name") String empName);

  //crud appointments
  @Query(value = "CALL make_appointment (:emp_id, :time, :pet_id, :description, :owner_id)", nativeQuery = true)
  void make_appointment(@Param("emp_id")String adminId, @Param("time") LocalDateTime time, @Param("pet_id") int petID,
                        @Param("description") String description, @Param("owner_id") String ownerID);

  @Query(value = "CALL cancel_appointment (:emp_id, :time, :pet_id, :appoint_id)", nativeQuery = true)
  void delete_appointment(@Param("emp_id")String adminId, @Param("time") LocalDateTime time,
                        @Param("pet_id") int petID, @Param("appoint_id") int appointID);

  @Query(value = "CALL update_appointment (:emp_id, :time, :vet_id, :appoint_id, :description)", nativeQuery = true)
  void update_appointment(@Param("emp_id")String adminId, @Param("appoint_id") int appointID,
                          @Param("time") LocalDateTime time, @Param("description") String description, @Param("vet_id") String vetID);

  @Query(value = "SELECT is_book_full (:time, :emp_id)", nativeQuery = true)
  int is_book_full(@Param("emp_id") String adminId, @Param("time") LocalDateTime time);

  @Query(value = "CALL appoint_show_up (:appoint_id)", nativeQuery = true)
  void check_in(@Param("appoint_id") int aptID);



  @Query(value = "SELECT * FROM employee", nativeQuery = true)
  List<UserEmployeeImpl> findAllEmployees();

//  @Query(value = "SELECT * FROM employee WHERE emp_id = :id", nativeQuery = true)
//  UserEmployeeImpl findEmployeeById(@Param("id") String id);

  @Query(value = "SELECT * FROM employee WHERE emp_last_name = :lname AND emp_first_name = :fname AND id_password = :password", nativeQuery = true)
  UserEmployeeImpl findEmployeeByNameAndPassword(@Param("fname") String firstName, @Param("lname") String lastName, @Param("password") String password);

  @Query(value = "INSERT INTO employee (emp_id, id_password, phone, emp_last_name, emp_first_name,"
          + "street_number, street_name, town, state_abbrev, zip, emp_type, clinic_id) VALUES "
          + "(:id, :password, :phone, :last_name, :first_name, :street_number, :street_name, :town, :state, :zip, :emp_type, :clinic_id)", nativeQuery = true)
  void addEmployee(@Param("id") String empId, @Param("password") String password, @Param("phone") String phone, @Param("last_name") String lastN,
                   @Param("first_name") String firstN, @Param("street_number") int streetNo, @Param("street_name") String streetName,
                   @Param("town") String cityName, @Param("state") String stateAbbrev,
                   @Param("zip") String zip, @Param("emp_type") TypeUser empType, @Param("clinic_id") int clinicID);

  @Query(value = "DELETE FROM employee WHERE emp_id = :id", nativeQuery = true)
  void deleteEmployee(@Param("id") String empId);

  @Query(value = "UPDATE employee SET emp_type = 'resigned', emp_id = 'null' WHERE emp_id = :id", nativeQuery = true)
  void resignInEmployee(@Param("id") String empId) ;

  @Query(value = "UPDATE employee SET emp_type = :type WHERE emp_id = :eid", nativeQuery = true)
  void updateEmployeeType(@Param("eid") String empID, @Param("type") TypeUser empType) ;

}
