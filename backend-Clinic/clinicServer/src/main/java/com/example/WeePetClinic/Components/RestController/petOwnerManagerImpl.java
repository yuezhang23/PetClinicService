package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.RestController.requestDTO.logInReq;
import com.example.WeePetClinic.Components.Service.ServiceClientPetOwner;
import com.example.WeePetClinic.Components.Service.ServiceEmpAccountant;
import com.example.WeePetClinic.utilities.TypeUser;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/service/current=pet_owner")
public class petOwnerManagerImpl implements Manager<UserClientPetOwnerImpl> {
  private final ServiceClientPetOwner ownerService;
  private UserClientPetOwnerImpl owner;

  @Autowired
  public petOwnerManagerImpl(ServiceClientPetOwner petOwnerService) {
    this.ownerService = petOwnerService;
  }


  @Override
  @PostMapping("/{request}")
  public Object getService(@PathVariable String request) {
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
          return ownerService.displayAppointmentsDetailsForPet(pet);
        }
      }
    }
    return null;
  }

  @Override
  @PostMapping("/login")
  public UserClientPetOwnerImpl login(@RequestBody String userID) {
      this.owner = ownerService.getPetOwner(userID);
      return owner;
  }


  @GetMapping("/greetings")
  public ResponseEntity<String> getGreeting() {
    return new ResponseEntity<>("Hello from Jackie", HttpStatus.OK);
  }


  @GetMapping("/service/profile/role=vet/appointment={apt_id}")
  public ResponseEntity<Object> getVetPublicProfile(@PathVariable int apt_id) {
    try {
      UserEmpVetImpl vet = ownerService.findVetByAppointment(apt_id);
      UserEmpVetImpl data = new UserEmpVetImpl();
      data.setLicense(vet.getLicense());
      data.setCertificate(vet.getCertificate());
      data.setYearsOfWork(vet.getYearsOfWork());
      data.setSpecialization(vet.getSpecialization());
      return new ResponseEntity<>(data, HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
