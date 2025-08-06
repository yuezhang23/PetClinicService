import React, {useEffect, useState } from 'react';
import * as client from "../User/client";
import 'react-toastify/dist/ReactToastify.css';
import { ProjectState } from '../store';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
import {setCurrentDonor, setCurrentUser} from "../../utils/redux/reducerUser";
import SwitchRole from './switchRole';


function HommieDonor() {
  const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
  const { currentDonor } = useSelector((state: ProjectState) => state.donorReducer);
  const itemList: string[] = ["My Donations", "Events" , "Profile"];
  
  const rolesLst = Object.keys(currentUser.roles).filter((i) => i !== "donor");

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const fetchProfile= async ()=> {
    console.log('DDDDDDDD')
    try {
      const res = await client.getUserProfile(currentUser);
      dispatch(setCurrentDonor({
        ...currentUser,
        firstName: res.firstName,
        lastName: res.lastName,
        email: res.email,
        address: {...res.address},
        phone: res.phone,
        role: "donor",
        card_type: res.typeCreditcard,
        card_number: res.cardNumber,
        donate_times: res.donateTimes,
        birthday: res.birthday,
        type: res.donorType,
        personal_statement: res.personalStatement,
        profession_background: res.professionBackground,
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
      <SwitchRole roleLst = {rolesLst}  eventLst={itemList} />
      <div className='justify-content-start'>
            <h4>Current User Info</h4>
            <pre>{JSON.stringify(currentDonor,null, 2)}</pre>
      </div>
    </>
  );

}
export default HommieDonor;