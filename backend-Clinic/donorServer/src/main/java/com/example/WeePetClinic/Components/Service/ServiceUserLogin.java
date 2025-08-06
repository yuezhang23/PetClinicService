package com.example.WeePetClinic.Components.Service;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.utilities.TypeUser;

//import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface ServiceUserLogin {
  // crud personal info
  Map<TypeUser, String> signInRoles(String id, String password);
  boolean passwordReset(UserOri user, String newPassword);

}
