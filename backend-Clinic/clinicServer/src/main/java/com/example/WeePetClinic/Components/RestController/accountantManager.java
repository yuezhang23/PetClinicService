package com.example.WeePetClinic.Components.RestController;


import com.example.WeePetClinic.Components.Model.User.UserEmployeeOri;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicImp;
import com.example.WeePetClinic.Components.Model.clinicService.ClinicOri;
import com.example.WeePetClinic.Components.RestController.reponseDTO.ProjUser;
import com.example.WeePetClinic.Components.RestController.requestDTO.profileReq;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.ServiceAppointment;
import com.example.WeePetClinic.Components.Service.ServiceInClinic;
import com.example.WeePetClinic.Components.Service.ServiceEmpAccountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clinic")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class accountantManager {
  private final ServiceAppointment aptService;
  private final ServiceEmpAccountant accountService;
//  private final ServiceInClinic clinicService;

  @Autowired
  public accountantManager(ServiceAppointment aptService, ServiceEmpAccountant accountService) {
//    this.clinicService = clinicService;
    this.aptService = aptService;
    this.accountService = accountService;
  }

  @PostMapping("/profile/vet")
  public ResponseEntity<ProjVetEmpDetail> loginUser(@RequestBody profileReq info) {
    String[] vetName = info.getName().split(",");
    int aptID = info.getAppointmentID();
    ProjVetEmpDetail result = aptService.getVetProfileForAppointment(aptID);

    if (result.getEmp_first_name().equals(vetName[1])
            && result.getEmp_last_name().equals(vetName[0])) {
      return new ResponseEntity<>(result, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/emps")
  public ResponseEntity<List<ProjUser>> fetchEmployees() {
    List<UserEmployeeOri> empLst = accountService.getAllEmployees();
    List<ProjUser> lst = new ArrayList<>();
    if (!empLst.isEmpty()) {
      for (UserEmployeeOri emp : empLst) {
        lst.add(new ProjUser(emp.getClinic().getClinicID(), emp.getLastName(), emp.getFirstName(), emp.getPhone(), emp.getAddress()));
      }
    }
    return new ResponseEntity<>(lst, HttpStatus.OK);
  }

//  @GetMapping("/service/clinic={clinic_id}")
//  public ResponseEntity<Object> getClinicInfo(@PathVariable int clinic_id) {
//    ClinicOri clinic = clinicService.getClinicById(clinic_id);
//    ClinicImp obj = new ClinicImp();
//    obj.setAddress(clinic.getAddress());
//    obj.setClinicPhone(clinic.getClinicPhone());
//    return new ResponseEntity<>(obj, HttpStatus.OK);
//  }







}
