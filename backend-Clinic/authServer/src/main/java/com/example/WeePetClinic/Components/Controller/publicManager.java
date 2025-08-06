package com.example.WeePetClinic.Components.Controller;


import com.example.WeePetClinic.Components.CController.RestController.requestDTO.logInReq;
import com.example.WeePetClinic.Components.CController.RestController.requestDTO.profileReq;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.ClinicOri;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceAppointment;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceInClinic;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic/info")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class publicManager {
  private final ServiceInClinic clinicService;
  private final ServiceAppointment aptService;

  @Autowired
  public publicManager(ServiceInClinic clinicService, ServiceAppointment aptService) {
    this.clinicService = clinicService;
    this.aptService = aptService;
  }

  @PostMapping("/{clinic_id}")
  public ResponseEntity<ClinicOri> getClinicInfo(@PathVariable int clinic_id) {
    ClinicOri clinic = clinicService.findClinicById(clinic_id);
    if (clinic != null) {
      return new ResponseEntity<>(clinic, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
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

}
