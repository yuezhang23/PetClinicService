package com.example.WeePetClinic.Components.CController.RestController;
import com.example.WeePetClinic.Components.Model.UserOri;

import org.springframework.web.bind.annotation.GetMapping;

public interface Manager<T> {
  void signUp(T info);

  Object getService(String request);

  Object login(String userID);

//  void signUpMultiRole(Map<TypeUser, T> map, TypeUser oldRole, TypeUser newRole);


}
