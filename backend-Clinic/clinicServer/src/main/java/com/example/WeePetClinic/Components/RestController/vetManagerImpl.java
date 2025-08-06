package com.example.WeePetClinic.Components.RestController;

import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.ServiceEmpVet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/service/veterinarian")
public class vetManagerImpl implements Manager<UserEmpVetImpl> {
  private final ServiceEmpVet vetService;
  private UserEmpVetImpl vet;
  private UserEmployeeImpl emp;

  @Autowired
  public vetManagerImpl(ServiceEmpVet vetService) {
    this.vetService = vetService;
  }

  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  @PostMapping("/service/current=veterinarian/login")
  public ProjVetEmpDetail login(@RequestBody String userID) {
    this.vet = (UserEmpVetImpl) vetService.getEmpVet(userID);
    return vetService.getEmpVetFullProfile(userID);
  }



}
