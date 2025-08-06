import { Client, Doner, Employee, petOwner, Transaction, User, Vet } from "./types"

export const EMPTY_USER: User = {
  userID: "", 
  password: "", 
  roles: {}, 
  role: "guest",
  firstName: "Guest",
  lastName: "",
  address:{} ,
  email: "",
  phone: ""
}

export const EMPTY_EMPLOYEE: Employee = { 
  ...EMPTY_USER,
  clinic_id: -1,
  statement: ""
}

export const EMPTY_VET: Vet = {
  ...EMPTY_EMPLOYEE,
  vet_license: "",
  vet_certificate: "",
  work_years: -1,
  specialization: ""
}


export const EMPTY_CLIENT: Client = {
  ...EMPTY_USER,
  card_type: "",
  card_number: 0
}

export const EMPTY_PETOWNER: petOwner = {
  ...EMPTY_CLIENT,
  pet_family: []
}

export const EMPTY_DONER: Doner = {
  ...EMPTY_CLIENT,
  donate_times: 0,
  personal_statement: "",
  birthday: "00/00/0000",
  type: "",
  profession_background: "",
}

export const EMPTY_Transaction: Transaction = {
  id: "",
  amount: 0,
  employee: EMPTY_EMPLOYEE,
  doner: EMPTY_DONER,
  date: "1989-10-02",
  approved: false
}
