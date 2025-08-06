//package com.example.WeePetClinic.Components.CController.Controller;
//
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//
//public class ContentController {
//  // entrance
////  private final ServiceUserLogin serviceLogin;
//
////  @Autowired
////  public ContentController(ServiceUserLogin serviceLogin) {
////    this.serviceLogin = serviceLogin;
////  }
//
//  // front end: direct to defaultRole specific page
//  // back end: direct to microservice by sending user type and user ID;
//  // API gateway to different microservice, then to different db
//  @GetMapping("/login")
//  public String login() {
//    return "login";
//  }
//
//
////  @GetMapping("req/signup")
////  public String signup() {
////    return "signup";
////  }
//
////  public ResponseEntity<String> loginUser(@RequestBody logInReq login) {
////    String userID = login.getUserID();
////    String password = login.getPassword();
////
////    List<TypeUser> roles = serviceLogin.signInRoles(login.getUserID(), login.getPassword());
////    if (roles == null) {
////      return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
////    } else {
////      return new ResponseEntity<>(roles.get(0) + " ", HttpStatus.OK);
////    }
////  }
//
//
//
//
//
//
//}
