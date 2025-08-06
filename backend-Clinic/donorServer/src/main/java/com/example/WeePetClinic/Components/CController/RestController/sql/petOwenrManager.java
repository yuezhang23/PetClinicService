package com.example.WeePetClinic.Components.CController.RestController.sql;

import com.example.WeePetClinic.Components.CController.RestController.Manager;
import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forSql.Pet.PetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserClientPetOwnerOri;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface petOwenrManager extends Manager<UserClientPetOwnerImpl> {

}
