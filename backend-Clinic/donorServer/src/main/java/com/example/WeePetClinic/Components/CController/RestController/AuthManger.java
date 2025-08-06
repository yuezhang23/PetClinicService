package com.example.WeePetClinic.Components.CController.RestController;
import com.example.WeePetClinic.Components.CController.RestController.postSql.donorManager;
import com.example.WeePetClinic.Components.CController.RestController.requestDTO.logInReq;
import com.example.WeePetClinic.Components.CController.RestController.reponseDTO.ProjUser;
import com.example.WeePetClinic.Components.CController.RestController.sql.adminManager;
import com.example.WeePetClinic.Components.CController.RestController.sql.petOwnerManagerImpl;
import com.example.WeePetClinic.Components.CController.RestController.sql.vetManagerImpl;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserClientDonorImpl;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantImp;
import com.example.WeePetClinic.Components.Model.forSql.ClinicImp;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Service.ServiceUserLogin;
import com.example.WeePetClinic.Components.Service.postSqlUser.ServiceClientDonor;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceClientPetOwner;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceEmpAdmin;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceEmpVet;
import com.example.WeePetClinic.Components.Service.doubleRole.ServiceEmpAccountant;
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
@RequestMapping("/clinic")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthManger {
//  private AuthenticationManager authManager;
//  private final PasswordEncoder passEncoder;
  // entrance
  private final ServiceUserLogin serviceLogin;
  // sub-services
  // sql
  private final ServiceClientPetOwner<UserClientPetOwnerImpl> petOwnerService;
  private final ServiceEmpVet<UserEmpVetImpl> vetService;
  private final ServiceEmpAdmin<UserEmployeeImpl> adminService;
  // postGre Sql
  private final ServiceClientDonor<UserClientDonorImpl> donorService;
  //shared
  private final ServiceEmpAccountant<UserEmpAccountantImp> accountantService;
  // multi-role
  private final Map<TypeUser, Manager> userService = new HashMap<>();
  private final Map<String, Manager<UserOri>> managers = new HashMap<>();


  @Autowired
  public AuthManger(ServiceUserLogin serviceLogin, ServiceClientPetOwner<UserClientPetOwnerImpl> petOwnerService, ServiceEmpVet<UserEmpVetImpl> vetService, ServiceEmpAdmin<UserEmployeeImpl> adminService, ServiceClientDonor<UserClientDonorImpl> donorService, ServiceEmpAccountant<UserEmpAccountantImp> accountantService) {
    this.serviceLogin = serviceLogin;
    this.petOwnerService = petOwnerService;
    this.vetService = vetService;
    this.adminService = adminService;
    this.donorService = donorService;
    this.accountantService = accountantService;
    userService.put(TypeUser.pet_owner, new petOwnerManagerImpl(petOwnerService));
    userService.put(TypeUser.administrative, new adminManager(adminService));
    userService.put(TypeUser.veterinarian, new vetManagerImpl(vetService, adminService));
    userService.put(TypeUser.accountant, new accountantManager(accountantService));
    userService.put(TypeUser.donor, new donorManager(donorService));
  }


  @PostMapping("/login")
  public ResponseEntity<Map<TypeUser, String>> loginUser(@RequestBody logInReq login) {
    System.out.println("username: " + login.getUserID() + " password: " + login.getPassword());
    Map<TypeUser, String> roleLst = serviceLogin.signInRoles(login.getUserID(), login.getPassword());
    if (!roleLst.isEmpty()) {
      return new ResponseEntity<>(roleLst, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_FOUND);
    }
  }


  @GetMapping("/emps")
  public ResponseEntity<List<ProjUser>> fetchEmployees() {
    return new ResponseEntity<>(adminService.getAllEmps(), HttpStatus.OK);
  }


  // already sign in, roles are non-empty
  @PostMapping("/service/current={role}")
  public ResponseEntity<Object> switchRole(@RequestBody logInReq login,
                                           @PathVariable TypeUser role) {
    Map<TypeUser, String> roles = login.getRoles();
    if (roles.get(role) != null) {
      // or session
      String userID = login.getUserID();
      managers.put(userID, userService.get(role));
      return new ResponseEntity<>(managers.get(userID).login(userID), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("user info not found",HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/service/current={role}/{request}")
  public ResponseEntity<Object> runRequest(@RequestBody logInReq login,
                                           @PathVariable TypeUser role, @PathVariable String request) {
    Map<TypeUser, String> roles = login.getRoles();
    if (roles.get(role) != null) {
      return new ResponseEntity<>(managers.get(login.getUserID()).getService(request), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  @GetMapping("/service/clinic={clinic_id}")
  public ResponseEntity<Object> getClinicInfo(@PathVariable int clinic_id) {
    ClinicImp clinic = accountantService.getDetailForClinic(clinic_id);
    ClinicImp obj = new ClinicImp();
    obj.setAddress(clinic.getAddress());
    obj.setClinicPhone(clinic.getClinicPhone());
    return new ResponseEntity<>(obj, HttpStatus.OK);
  }


  @GetMapping("/service/profile/role=vet/appointment={apt_id}")
  public ResponseEntity<Object> getVetProfile(@PathVariable int apt_id) {
    try {
      UserEmpVetImpl vet = adminService.getAccessApt().getAppointmentById(apt_id).getVet();
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


//  @PostMapping("/service/current=petowner")
//  public ResponseEntity<List<PetImpl>> getPets(@RequestBody logInReq login) {
//    List<TypeUser> roles = login.getRoles();
//    if (roles.contains(TypeUser.pet_owner)) {
////      return new ResponseEntity<>(userService.get(TypeUser.pet_owner).runService(login.getUserID()), HttpStatus.OK);
//      petOwenrManager ownerManager = new petOwnerManager(petOwnerService);
//      return new ResponseEntity<>(ownerManager.getPets(), HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//  }


  // in process check sign up
  // pass boolean to isNew path var
  @GetMapping("/register?usertype={type}")
  public ResponseEntity<String> checkSignUp(@RequestBody logInReq user, @PathVariable TypeUser type) {
    // how to recognize encryption code
    // ??? encode
    Map<TypeUser, String> roles = serviceLogin.signInRoles(user.getUserID(), user.getPassword());
    if (roles == null) {
      return new ResponseEntity<>("new=true", HttpStatus.OK);
    } else if (roles.get(type) == null) {
      return new ResponseEntity<>("new=false", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("you are already a registered" + type, HttpStatus.OK);
    }
  }

  // add to db
  // first time sign up
//  @PostMapping("/register/new=true/client={role}/add")
//  public void signUpClient(@RequestBody Map<TypeUser, Object> userInfo, @PathVariable TypeUser role) {
//    // how to bundle with email address, validation.
//    // password encryption
//    if (userInfo != null) {
//      ObjectMapper mapper = new JsonMapper();
//      UserClientOri clientInfo = mapper.convertValue(userInfo.get(role), UserClientOri.class);
//      // with browser
////      boolean isEmailValidated = serviceLogin.emailValidation(clientInfo.getEmail());
//      boolean isEmailValidated = true;
//      if (isEmailValidated) {
////        clientInfo.setPassword(passEncoder.encode(clientInfo.getPassword()));
//        userService.get(role).signUp(clientInfo);
//      } else {
//        System.out.println("invalid email");
//      }
//    } else {
//      System.out.println("fetch error from request");
//    }
//  }


//  @PostMapping("/register/new=false/current={old_role}/new={new_role}/add")
//  public void signUpMultiRole(@RequestBody Map<TypeUser, Optional> map,
//                                  @PathVariable TypeUser old_role,  @PathVariable TypeUser new_role) {
//    ObjectMapper mapper = new JsonMapper();
//    if (new_role == TypeUser.donor || new_role == TypeUser.petOwner) {
//      UserClientOri newInfo = mapper.convertValue(map.get(new_role), UserClientOri.class);
//      userService.get(new_role).signUp(newInfo);
//    } else {
//      UserEmployeeOri newInfo = mapper.convertValue(map.get(new_role), UserEmployeeOri.class);
//      userService.get(new_role).signUp(newInfo);
//    }
//  }


  // first time sign up
//  @PostMapping("/register/new=true/employee={role}/add")
//  public void signUpEmployee(@RequestBody Map<TypeUser, Object> userInfo, @PathVariable TypeUser role) {
//    if (userInfo != null) {
//      ObjectMapper mapper = new JsonMapper();
//      UserEmployeeOri empInfo = mapper.convertValue(userInfo.get(TypeUser.administrative), UserEmployeeOri.class);
////      empInfo.setPassword(passEncoder.encode(empInfo.getPassword()));
////      empInfo.setEmail(adminService.createEmail(empInfo.getUserID()));
//      userService.get(TypeUser.administrative).signUp(empInfo);
//      if (role != TypeUser.administrative) {
//        userService.get(role).signUp(userInfo.get(role));
//      }
//    } else {
//      System.out.println("fetch error from request");
//    }
//  }




}
