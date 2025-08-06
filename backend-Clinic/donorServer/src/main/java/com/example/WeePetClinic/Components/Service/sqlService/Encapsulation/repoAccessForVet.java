package com.example.WeePetClinic.Components.Service.sqlService.Encapsulation;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentImp;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.AppointmentOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetOri;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.MedicationImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.TreatmentImpl;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.medicationInAppointment;
import com.example.WeePetClinic.Components.Model.forSql.clinicService.treatmentInAppointment;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlClinic;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentInClinic;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjMedsTreatsForAppointment;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlAppointmentBasic;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpAdmin;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlMedicationInApt;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlMedications;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlTreatments;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlTreatmentInApt;
import com.example.WeePetClinic.Components.Repository.forSql.repoSqlEmpVet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Service
public class repoAccessForVet {
  private final repoSqlEmpVet repoVet;
  private final repoSqlEmpAdmin repoAdmin;
  private final repoSqlTreatmentInApt repoTreats;
  private final repoSqlMedicationInApt repoMeds;
  private final repoSqlTreatments repoServices;
  private final repoSqlMedications repoServicesM;
  private final repoSqlAppointmentBasic repoApt;
  private final repoSqlClinic repoClinic;

  @Autowired
  public repoAccessForVet(repoSqlEmpVet repoVet, repoSqlEmpAdmin repoAdmin,
                          repoSqlTreatmentInApt repoTreats, repoSqlMedicationInApt repoMeds,
                          repoSqlTreatments repoServices, repoSqlMedications repoServicesM, repoSqlAppointmentBasic repoApt, repoSqlClinic repoClinic) {
    this.repoVet = repoVet;
    this.repoAdmin = repoAdmin;
    this.repoTreats = repoTreats;
    this.repoMeds = repoMeds;
    this.repoServices = repoServices;
    this.repoServicesM = repoServicesM;
    this.repoApt = repoApt;
    this.repoClinic = repoClinic;
  }

  public Optional<UserEmpVetImpl> findVetProfileById(String userID) {
    return repoVet.findById(userID);
  }

  public ProjVetEmpDetail findVetDetailById(String userID) {
    return repoVet.findVetDetailById(userID);
  }

  public void deleteVetById(String id) {
    repoVet.deleteById(id);
  }

  public void addVet(UserEmpVetImpl vet, UserEmployeeImpl emp) {
    repoVet.save(vet);
    repoAdmin.save(emp);
  }

  public Optional<ClinicImp> getClinicById(int clinic_id){
    return repoClinic.findById(clinic_id);
  }


  public void resignVetInfoInAppointment(UserEmpVetOri vet) {
    UserEmployeeImpl vetEmp = repoAdmin.findById(vet.getVetID()).orElseThrow(()-> new NoSuchElementException("no vet emp found"));
    repoApt.resignVetInAppointment(vet.getVetID(), vetEmp.getFirstName() + " " + vetEmp.getLastName());
  }

  public void resignInVeterinarian(UserEmpVetOri vet) {
    UserEmployeeImpl vetEmp = repoAdmin.findById(vet.getVetID()).orElseThrow(()-> new NoSuchElementException("no vet emp found"));
    repoVet.resignInVeterinarian(vet.getVetID(), vetEmp.getFirstName() + " " + vetEmp.getLastName());
  }

  // logic
  public List<MedicationImpl> displayAvailableMedicationsInClinic(UserEmpVetOri vet) {
    UserEmployeeImpl vetEmp = repoAdmin.findById(vet.getVetID()).orElseThrow(()-> new NoSuchElementException("no vet employee found"));
    return repoServicesM.findAvailableMedicationsInClinic(vetEmp.getClinic().getClinicID());
  }

  public List<TreatmentImpl> displayAvailableTreatmentsInClinic(UserEmpVetOri vet) {
    UserEmployeeImpl vetEmp = repoAdmin.findById(vet.getVetID()).orElseThrow(()-> new NoSuchElementException("no vet employee found"));
    return repoServices.findAvailableTreatmentsInClinic(vetEmp.getClinic().getClinicID());
  }

  public List<ProjAppointmentInClinic> displayAppointmentsInClinic(UserEmpVetOri vet) {
    return repoApt.displayAllAppointmentsForEmployee(vet.getVetID());
  }

  // meds&treatments
  public void addTreatment(TreatmentImpl service, AppointmentOri appointment, double actualPrice) {
    repoTreats.addTreatment(appointment.getAppointID(), service.getServiceID(), actualPrice);
  }

  public void deleteTreatment(treatmentInAppointment service) {
    repoTreats.deleteTreatment(service.getAppointmentID(), service.getTreatmentID());
  }

  public void updateTreatment(treatmentInAppointment oldService, treatmentInAppointment newService) {
    repoTreats.updateTreatment(oldService.getAppointmentID(), oldService.getTreatmentID(), newService.getTreatmentID(), newService.getCharge());
  }

  public void addMedication(medicationInAppointment service, AppointmentOri appointment, int quantity, int period) {
    repoMeds.addMedication(appointment.getAppointID(), service.getMedicationID(), quantity, period);
  }


  public void deleteMedication(medicationInAppointment service) {
    repoMeds.deleteMedication(service.getAppointmentID(), service.getMedicationID());
  }

  public void updateMedication(medicationInAppointment oldService, medicationInAppointment newService) {
    repoMeds.updateMedication(oldService.getAppointmentID(), oldService.getMedicationID(), newService.getMedicationID(), newService.getQuantity(), newService.getTime_interval());
  }


  // appointment
  public List<AppointmentImp> displayAppointmentsForPet(PetOri pet, UserEmpVetOri vet) {
    UserEmployeeImpl vetEmp = repoAdmin.findById(vet.getVetID()).orElseThrow(()-> new NoSuchElementException("no vet emp found"));
    return repoApt.findAppointmentsByPetIdAndClinicID(pet.getPetID(), vetEmp.getClinic().getClinicID());
  }

  public List<AppointmentImp> displayAppointmentsByPetAndVet(PetOri pet, UserEmpVetOri vet) {
    return repoApt.findAppointmentsByPetIdAndVetID(pet.getPetID(), vet.getVetID());
  }

  public List<ProjMedsTreatsForAppointment> displayAllTreatsAndMedsForAppointment(AppointmentOri apt) {
    return repoApt.displayAllMedsAndTreatsInAppointment(apt.getAppointID());
  }

  public void completeAppointment(AppointmentOri appointment) {
    repoApt.completeAppointment(appointment.getAppointID());
  }


}
