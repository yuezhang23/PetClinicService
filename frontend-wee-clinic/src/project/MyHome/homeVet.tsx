import React, {useEffect, useState } from 'react';
import * as client from "../User/client";
import 'react-toastify/dist/ReactToastify.css';
import "../../hommie.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { ProjectState } from '../store';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
import {setCurrentUser, setCurrentVet} from "../../utils/redux/reducerUser";
import SwitchRole from './switchRole';


function HommieVet() {
  const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
  const { currentVet } = useSelector((state: ProjectState) => state.vetReducer);
  const itemList: string[] = ["Appointments", "Treatments", "Medications", "Profile"];
  const rolesLst = Object.keys(currentUser.roles).filter((i) => i !== "veterinarian");

  
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const fetchProfile= async ()=> {
    try {
      const res = await client.getUserProfile(currentUser);
      dispatch(setCurrentVet({
        ...currentUser,
        firstName: res.emp_first_name,
        lastName: res.emp_last_name,
        email: res.email,
        address: res.address,
        phone: res.phone,
        role: "veterinarian",
        clinic_id: res.clinic_id,
        statement: res.statement,
        vet_license: res.vet_license,
        vet_certificate: res.vet_certificate,
        work_years: res.work_years,
        specialization: res.specialization,
      }))
    } catch (err) {
      navigate(`./${err}`);
    }
  }
  
  useEffect(() => {
    fetchProfile();
  }, [currentUser.role]);   


  return (
      <>
        <SwitchRole roleLst = {rolesLst} eventLst={itemList} />         
        <div className='justify-content-start'>
            <div className="flex-grow-1">
                <h4>Current User Info</h4>
                <pre>{JSON.stringify(currentVet,null, 2)}</pre>
            </div>
        </div>
      </>
    );
}
export default HommieVet;