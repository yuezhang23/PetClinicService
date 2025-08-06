package com.example.WeePetClinic.Components.CController.RestController.sql;
import com.example.WeePetClinic.Components.CController.RestController.reponseDTO.projPet;
import com.example.WeePetClinic.Components.Model.forSql.InvoiceImplSql;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjAppointmentDetail;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceClientPetOwner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/service/current=pet_owner")
public class petOwnerManagerImpl implements petOwenrManager {
  private final ServiceClientPetOwner<UserClientPetOwnerImpl> ownerService;
  private UserClientPetOwnerImpl owner;

  @Autowired
  public petOwnerManagerImpl(ServiceClientPetOwner<UserClientPetOwnerImpl> petOwnerService) {
    this.ownerService = petOwnerService;
  }

  @Override
  public void signUp(UserClientPetOwnerImpl info) {
    try {
      ownerService.addPetOwner(info);
    } catch (Exception e) {
      System.out.println("can't update pet_owner table");
    }
  }

  @Override
  public Object getService(String request) {
    if (request.equals("family")) {
      return ownerService.displayPetsInfo(owner);
    }
    if (request.equals("invoice")) {
      return ownerService.displayAllInvoicesForUser(owner);
    }

    if (request.contains("family=")) {
      List<PetImpl> pets = ownerService.getPetsByOwner(owner.getUserID());
      String petName = request.substring(request.indexOf("family=") + 7);
      for (PetImpl pet : pets) {
        if (pet.getPetName().equals(petName)) {
          List<ProjAppointmentDetail> aptDetailLst = ownerService.displayAppointmentsDetailsForPet(pet);
          return aptDetailLst;
        }
      }
    }


    return null;
  }




  @Override
  public UserClientPetOwnerImpl login(String userID) {
      this.owner = ownerService.getPetOwner(userID);
      return owner;
  }
}
