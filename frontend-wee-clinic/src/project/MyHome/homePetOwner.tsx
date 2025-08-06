import React, {useEffect } from 'react';
import * as client from "../User/client";
import 'react-toastify/dist/ReactToastify.css';
import "../../hommie.css"
import { ProjectState } from '../store';
import { useDispatch, useSelector } from 'react-redux';
import "bootstrap/dist/css/bootstrap.min.css";
import { useLocation, useNavigate } from 'react-router';
import {setCurrentUser, setCurrentOwner} from "../../utils/redux/reducerUser";
import MyPetCard from './petCard';
import SwitchRole from './switchRole';


function HommiePetOwner() {
  const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
  const { currentOwner } = useSelector((state: ProjectState) => state.petOwnerReducer);
  const itemList: string[] = ["My Activities", "Events", "Profile"];

  
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const rolesLst = Object.keys(currentUser.roles).filter((i) => i !== "pet_owner");

  const fetchProfile= async ()=> {
    try {
      const res = await client.getUserProfile(currentUser);
      dispatch(setCurrentOwner(
        { 
          ...currentUser,
          firstName: res.firstName,
          lastName: res.lastName,
          email: res.email,
          address: {...res.address},
          phone: res.phone,
          role: "pet_owner",
          card_type: res.typeCreditcard,
          card_number: res.cardNumber
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
            <MyPetCard />           
            <div className="flex-grow-1">
                <h4>Current User Info</h4>
                <pre>{JSON.stringify(currentOwner, null, 2)}</pre>
            </div>
      </div>
    </>
    );
}
export default HommiePetOwner;