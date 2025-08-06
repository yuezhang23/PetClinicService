package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.UserOri;

import org.springframework.web.bind.annotation.GetMapping;

public interface Manager<T> {
  Object getService(String request);
  Object login(String userID);


}
