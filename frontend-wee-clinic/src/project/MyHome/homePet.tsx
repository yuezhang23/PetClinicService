import React, { useEffect, useState } from 'react';
import 'react-toastify/dist/ReactToastify.css';
import * as connect from "./connect";
import CryptoJS from "crypto-js";
import "../../hommie.css"
import * as client from "../User/client"
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import "bootstrap/dist/css/bootstrap.min.css";
import { useSelector } from 'react-redux';
import { ProjectState } from '../store';
import { useLocation, useNavigate } from 'react-router';
import { Appointment } from 'src/utils/types';
import { FaAddressCard, FaPhone } from 'react-icons/fa';
import {contact, Vet} from '../../utils/types'
import { EMPTY_USER, EMPTY_VET } from 'src/utils/constants';

function PetProfile() {
    const {pathname} = useLocation();
    const { currentPet} = useSelector((state: ProjectState) => state.petReducer);
    const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
    const [appointments, setApts] = useState<Appointment[]>();
    const navigate = useNavigate();
    const [aptIds, setAptIds] = useState([]);

    const [contact, setContact] = useState<contact>();

    const petName = pathname.slice(pathname.lastIndexOf("/") + 1);
    
    const fetchAptProfile= async ()=> {
        try {
        const res = await connect.findAllAppointmentsByPet(currentUser, petName);
        setApts(res);
        setAptIds(res.map((i : Appointment) => i.appointment_id));
        } catch (err) {
            navigate("../currentRole=pet_owner" + err);
        }
    }

    const handleClinicAddress = async (clinic_id : number) => {
        try {
        const res = await connect.findClinicAddressById(clinic_id);
        setContact({phone: res.clinicPhone, 
            address: {street: res.address.streetNumber + " " + res.address.streetName, 
                city: res.address.town + " "+ res.address.stateAbbrev+","+res.address.zip, 
                country: res.address.country}});
        } catch (err) {
            navigate("../currentRole=pet_owner" + err);
        }
    }

    const handleVetProfile = async (apt_id : number, vet_name : string) => {
        try {
            if (currentUser.lastName +"," + currentUser.firstName === vet_name) {
                navigate(`../../Clinic/emp_profile/private/current`);
            } else { 
                const res = await client.getVetProfileFromAppointment(apt_id, vet_name);
                const vet: Vet = {
                    ...EMPTY_VET,
                    personal: {
                        ...EMPTY_VET.personal,
                        userID: res.vet_id,
                        email: res.email,
                        phone: res.phone,
                        firstName: res.emp_first_name,
                        lastName: res.emp_last_name,
                    },
                    statement: res.statement,
                    clinic_id: res.clinic_id,
                    vet_license: res.vet_license,
                    vet_certificate: res.vet_certificate,
                    work_years: res.work_years,
                    specialization: res.specialization
                };
                navigate(`../../Clinic/emp_profile/public/current/`, {state: {user : vet, type : "vet"}});
            }
        } catch (err) {
            navigate("../currentRole=pet_owner" + err);
        }
    }

    useEffect(() => {
      fetchAptProfile();
    }, [currentPet]);   

    return (
        <div className='mx-5'>

            <button className="col btn btn-sm bg-warning-subtle rounded-5 m-2" onClick={() => navigate(-1)}> 》〉Back </button>
            <div className="row">
                <span className='d-flex justify-content-between'>

                <h4 className='text-primary'>Appointment Info for <strong className='text-danger bold'>{currentPet.pet_name}</strong></h4>
                </span>

                <div className="list-group d-flex rounded-3">
                    {appointments && appointments.map((i) => (
                        <div className='list-group-item list-group-item-action'>
                            <span className="col mx-2 d-flex justify-content-between">
                                <h6 className="mb-3">Appointment ID: {i.appointment_id}</h6>
                                <small>{Math.floor((new Date().getTime() - new Date(i.meeting_time).getTime()) / (1000 * 3600 * 24))} days ago</small>
                            </span>        
                            <div className='row d-flex'> 
                                <div className="col ">
                                    <a href="##" className="list-group-item list-group-item-action rounded-3">
                                        Images Info</a>
                                </div>

                                <div className='col flex-grow-1'>
                                    <ul className="list-group form-control" >
                                        <li className="list-group-item">Clinic Id: {i.clinic_id} 
                                            <p className='float-end'>
                                                <button type="button" className='btn text-primary' data-bs-toggle="collapse" data-bs-target={`#${i.clinic_id}`} 
                                                    aria-expanded="false" aria-controls={`${i.clinic_id}`} onClick={()=>handleClinicAddress(i.clinic_id)}><strong><FaPhone/><br></br>Contact</strong></button>
                                            </p>
                                            <div className="collapse mt-3" id={`${i.clinic_id}`}>
                                                <div className="card card-body">
                                                    <div className='row text-primary'>
                                                        <div >
                                                            <FaPhone className='me-3 '/>{contact?.phone}
                                                        </div>
                                                        <div >
                                                            <FaAddressCard className='text-primary'/>
                                                        </div>
                                                        <div className='ms-3 border-0'>
                                                            <li className="list-group-item border-0 text-primary"> Street: {contact?.address.street}</li>
                                                            <li className="list-group-item border-0 text-primary"> City: {contact?.address.city}</li>
                                                            <li className="list-group-item border-0 text-primary"> Country:{contact?.address.country}</li>   
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                        <li className="list-group-item">Room Id: {i.room}</li>
                                        <li className="list-group-item ">Date: {i.meeting_time}</li>
                                        <li className="list-group-item ">Description: {i.basic_description}</li>
                                        <li className="list-group-item ">Vet on Call: <button onClick={()=> handleVetProfile(i.appointment_id, i.veterinarian)} 
                                                type='button' className='btn text-primary'><strong>{i.veterinarian}</strong></button> </li>
                                        <li className="list-group-item ">Calculated Expenditure: ${i.visit_expenditure}</li>
                                        <li className="list-group-item ">Actual Paid:${i.confirmed_amt}</li>
                                    </ul>         
                                </div>
                            </div>
                        </div>
                    )
                )}
                </div>
            </div>
        </div>
      );
}
export default PetProfile;