package com.example.WeePetClinic.Components.RestController;
import com.example.WeePetClinic.Components.Model.UserOri;

public interface Manager<T> {
  Object getService(String request);
  Object login(String userID);
} 