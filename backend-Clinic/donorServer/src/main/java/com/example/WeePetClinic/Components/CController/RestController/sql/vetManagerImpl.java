package com.example.WeePetClinic.Components.CController.RestController.sql;

import com.example.WeePetClinic.Components.Model.BillingAddressImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmpVetImpl;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Repository.repoDTO.ProjVetEmpDetail;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceEmpAdmin;
import com.example.WeePetClinic.Components.Service.sqlService.ServiceEmpVet;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/service/veterinarian")
public class vetManagerImpl implements vetManager {
  private final ServiceEmpVet<UserEmpVetImpl> vetService;
  private final ServiceEmpAdmin<UserEmployeeImpl> adminService;
  private UserEmpVetImpl vet;
  private UserEmployeeImpl emp;

  @Autowired
  public vetManagerImpl(ServiceEmpVet<UserEmpVetImpl> vetService, ServiceEmpAdmin<UserEmployeeImpl> adminService) {
    this.vetService = vetService;
    this.adminService = adminService;
  }

  @Override
  public void signUp(ProjVetEmpDetail info) {
    UserEmpVetImpl newV = new UserEmpVetImpl();
    newV.setVetID(info.getVet_id());
    newV.setSpecialization(info.getSpecialization());
    newV.setLicense(info.getVet_license());
    newV.setCertificate(info.getVet_certificate());
    newV.setYearsOfWork(info.getWork_years());

    UserEmployeeImpl emp = new UserEmployeeImpl();
    emp.setUserID(info.getVet_id());
    emp.setPassword(info.getId_password());
    emp.setPhone(info.getPhone());
    emp.setClinic(vetService.getClinicById(info.getClinic_id()));

    BillingAddressImpl address = new BillingAddressImpl();
    address.setCountry("USA");
    address.setStateAbbrev(info.getState_abbrev());
    address.setTown(info.getTown());
    address.setZip(info.getZip());
    address.setStreetName(info.getStreet_name());
    address.setStreetNumber(info.getStreet_number());

    emp.setAddress(address);
    emp.setEmail(info.getEmail());
    emp.setFirstName(info.getEmp_first_name());
    emp.setLastName(info.getEmp_last_name());
    emp.setUserType(TypeUser.veterinarian);
    try {
      vetService.addEmpVet(newV, emp);
      this.vet = newV;
      this.emp = emp;
    } catch (Exception e) {
      System.out.println("can't update donor table");
    }

  }


  @Override
  public Object getService(String request) {
    return null;
  }

  @Override
  public ProjVetEmpDetail login(String userID) {
    this.vet = vetService.getEmpVet(userID).orElseThrow(()-> new NoSuchElementException("vet not found"));
    this.emp = adminService.getEmp(userID);
    return vetService.getEmpVetFullProfile(userID);
  }



}
