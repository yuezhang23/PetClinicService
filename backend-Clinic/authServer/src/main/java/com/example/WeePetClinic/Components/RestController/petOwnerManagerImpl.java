package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Service.ServiceClientPetOwner;
import com.example.WeePetClinic.Components.RestController.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class petOwnerManagerImpl implements Manager<UserClientPetOwnerImpl> {
  private final ServiceClientPetOwner<UserClientPetOwnerImpl> ownerService;
  private UserClientPetOwnerImpl owner;

  @Autowired
  public petOwnerManagerImpl(ServiceClientPetOwner<UserClientPetOwnerImpl> petOwnerService) {
    this.ownerService = petOwnerService;
  }

  @Override
  public Object getService(String request) {
    if (request.equals("family")) {
      return ownerService.displayPetsInfo(owner);
    }
    if (request.equals("invoice")) {
      return ownerService.displayAllInvoicesForUser(owner);
    }
    return null;
  }

  @Override
  public UserClientPetOwnerImpl login(String userID) {
    this.owner = ownerService.getPetOwner(userID);
    return owner;
  }
} 