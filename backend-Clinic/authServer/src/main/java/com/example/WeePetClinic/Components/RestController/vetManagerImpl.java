package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetOri;
import com.example.WeePetClinic.Components.Service.ServiceEmpVet;
import com.example.WeePetClinic.Components.RestController.Manager;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class vetManagerImpl implements Manager<UserEmpVetImpl> {
  private final ServiceEmpVet<UserEmpVetImpl> vetService;
  private UserEmpVetImpl vet;

  @Autowired
  public vetManagerImpl(ServiceEmpVet<UserEmpVetImpl> vetService) {
    this.vetService = vetService;
  }

  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public ProjVetEmpDetail login(String userID) {
    Optional<UserEmpVetOri> vetOpt = vetService.getEmpVet(userID);
    if (vetOpt.isPresent()) {
      this.vet = (UserEmpVetImpl) vetOpt.get();
      return vetService.getEmpVetFullProfile(userID);
    }
    return null;
  }
} 