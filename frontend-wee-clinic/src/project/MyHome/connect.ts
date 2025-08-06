import axios from "axios";

export const BASE_API = process.env.REACT_APP_API_BASE;

// export const getAllEmps = async () => {
//     const response = await axios.get(`${BASE_API}/emps`);
//     return response.data;
// }


export const findAllPetsByOwner = async (user: any) => {
    const response = await axios.post(`${BASE_API}/clinic/service/current=pet_owner/family`, user);
    return response.data;
}


export const findAllAppointmentsByPet = async (user: any, petName : any) => {
    const response = await axios.post(`${BASE_API}/clinic/service/current=pet_owner/family=${petName}`, user);
    return response.data;
}


export const findClinicAddressById = async (clinic_id : number) => {
    const response = await axios.get(`${BASE_API}/clinic/service/clinic=${clinic_id}`);
    return response.data;
}


export const findVetprofileByAppointment = async (apt_id : number) => {
    const response = await axios.get(`${BASE_API}/clinic/service/profile/role=vet/appointment=${apt_id}`);
    return response.data;
}


