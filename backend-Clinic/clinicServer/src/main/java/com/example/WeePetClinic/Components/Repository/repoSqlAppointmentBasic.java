package com.example.WeePetClinic.Components.Repository;
import com.example.WeePetClinic.Components.Model.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentInClinic;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface repoSqlAppointmentBasic extends JpaRepository<AppointmentImp, Integer> {
  //basic
  @Query(value = "SELECT veterinarian_match(:clinic_id, :time)", nativeQuery = true)
  String veterinarianMatch(@Param("clinic_id") int clinicID, @Param("time") LocalDateTime time);

  @Query(value = "UPDATE appointment SET vet_id = :name WHERE vet_id = :id", nativeQuery = true)
  void resignVetInAppointment(@Param("id") String vetId, @Param("name") String vetName) ;

  @Query(value = "DELETE FROM appointment WHERE patient_id = :id", nativeQuery = true)
  void deleteAppointmentByPet(@Param("id") int petId);

  @Query(value = "DELETE FROM appointment WHERE appointment_id = :aid", nativeQuery = true)
  void deleteAppointmentById(@Param("aid") int aptId);

  @Query(value = "DELETE FROM appointment WHERE appointment_id = :aid AND clinic_id = :cid", nativeQuery = true)
  void deleteAppointmentByAptIdAndClinic(@Param("aid") int aptId, @Param("cid") int clinicID);

  @Query(value = "SELECT * FROM appointment WHERE appointment_id = :id", nativeQuery = true)
  AppointmentImp findAppointmentById(@Param("id") int id);

  @Query(value = "SELECT * FROM appointment WHERE appointment_id = :aid AND clinic_id = :cid", nativeQuery = true)
  AppointmentImp findAppointmentByIdAndClinic(@Param("aid") int aptId, @Param("cid") int clinicID);

  @Query(value = "SELECT * FROM appointment WHERE patient_id = :id AND appointment_datetime = :time", nativeQuery = true)
  AppointmentImp findAppointmentByPetIdAndTime(@Param("id") int pet_id, @Param("time") LocalDateTime time);

  // pet_owner only
  @Query(value = "SELECT * FROM appointment WHERE patient_id = :id", nativeQuery = true)
  List<AppointmentImp> findAppointmentsByPetId(@Param("id") int pet_id);

  @Query(value = "SELECT * FROM appointment WHERE patient_id = :pid AND clinic_id = :cid", nativeQuery = true)
  List<AppointmentImp> findAppointmentsByPetIdAndClinicID(@Param("pid") int pet_id, @Param("cid") int clinicID);

  @Query(value = "SELECT * FROM appointment WHERE patient_id = :pid AND vet_id = :vid", nativeQuery = true)
  List<AppointmentImp> findAppointmentsByPetIdAndVetID(@Param("pid") int pet_id, @Param("vid") String vid);

  @Query(value = "SELECT * FROM appointment WHERE patient_id = :pid AND admin_id = :eid", nativeQuery = true)
  List<AppointmentImp> findAppointmentsByPetIdAndAdminId(@Param("pid") int pet_id, @Param("eid") String empId);

  @Query(value = "SELECT * FROM appointment", nativeQuery = true)
  List<AppointmentImp> findAllAppointments();

  @Query(value = "SELECT * FROM appointment WHERE clinic_id = :cid", nativeQuery = true)
  List<AppointmentImp> findAllAppointmentsByClinic(@Param("cid") int cid);

  // procedure
  @Query(value = "CALL display_appointment(:employee_id)", nativeQuery = true)
  List<ProjAppointmentInClinic> displayAllAppointmentsForEmployee(@Param("employee_id") String empID);

  @Query(value = "SELECT * FROM appointment WHERE vet_id = :vid", nativeQuery = true)
  List<AppointmentImp> findAppointmentsByVetEmployee(@Param("vid") String empID);

  @Query(value = "SELECT * FROM appointment WHERE admin_id = :aid", nativeQuery = true)
  List<AppointmentImp> findAppointmentsByAdminEmployee(@Param("aid") String empID);

  // accountant only
  @Query(value = "CALL display_appointment_detail(:c_id, :apt_id)", nativeQuery = true)
  ProjAppointmentDetail displayDetailsOfAppointmentInClinic(@Param("apt_id") int appointID, @Param("c_id") int clinicID);

  // add new!!!
  @Query(value = "CALL display_appointment_detail(-1, :apt_id)", nativeQuery = true)
  ProjAppointmentDetail displayDetailsOfAppointment(@Param("apt_id") int appointID);

  // vet/petowner only
  @Query(value = "CALL display_treatments_and_medications_for_appointment(:appoint_id)", nativeQuery = true)
  List<ProjMedsTreatsForAppointment> displayAllMedsAndTreatsInAppointment(@Param("appoint_id") int appointID);

  // vet only
  @Query(value = "CALL complete_appoint(:aptID)", nativeQuery = true)
  void completeAppointment(@Param("aptID") int appointID);

}
