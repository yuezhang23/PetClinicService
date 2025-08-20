export interface contact { 
  phone : String;
  address: {
    street: String, city: string, country: string
  }};


export interface roleType {
  [key: string]: string; 
}

export type Transaction = {
  id: string
  amount: number
  employee: Employee
  doner: Doner
  date: string
  approved: boolean
}

export type Appointment = {
  clinic_id :number
  appointment_id: number
  meeting_time: string
  pet: string
  pet_owner: string
  basic_description: string
  veterinarian: string
  room: number
  visit_expenditure: number
  confirmed_amt: number
  received_date: string
}

export type PersonalInfo = {
  userID: string
  password: string
  firstName: string
  lastName: string
  role: string
  roles: roleType
  email: string
  phone: string
  address: object
}

export type Client = {
  personal: PersonalInfo
  card_type: string
  card_number: number
}

export type petOwner = Client & {
  pet_family: [] 
}

export type Doner = {
  client: Client
  donate_times: number
  personal_statement: string
  birthday: string
  type: string
  profession_background: string
}

export type Employee = {
  personal: PersonalInfo
  clinic_id: number
  statement?: string
}

export type Vet = Employee & {
  vet_license: string
  vet_certificate: string
  work_years: number
  specialization: string
}

export type User = {
  userID: string,
  password: string,
  role: string,
  roles: roleType,
  email: string,
  phone: string,
  firstName: string,
  lastName: string,
  address: object
}


export type Pet = {
  patient_id: number,
  pet_name: string, 
  height_in_cm: GLfloat, 
  weight_in_lb: GLfloat, 
  pet_status: string,
  date_of_birth: Date,
  breed_name: string
}


export type Claim = 
{ _id: string; brewery_ref?: string; brewery_name: string; 
  owner?: string; legalName: string; additional?: string,
  completed?:boolean, approved?:boolean
};


export type PaginatedResponse<TData> = {
  data: TData
  nextPage: number | null
}

export type PaginatedRequestParams<TData> = {
  data: TData 
  page: number | null
}

export type RequestByEmployeeParams = {
  employeeId: string
}

export type RequestByClientParams = {
  clientId: string
}

export type SetTransactionApprovalParams = {
  transactionId: string
  value: boolean
}
