import { createSlice } from "@reduxjs/toolkit";
import { EMPTY_DONER, EMPTY_EMPLOYEE, EMPTY_PETOWNER, EMPTY_USER, EMPTY_VET } from "../constants";

const initialState = {
  currentUser: EMPTY_USER,
}

const userSlice = createSlice({
  name: "user",
  initialState :initialState,
  reducers: {
    setCurrentUser(state, action) {
      state.currentUser = action.payload;
    },
    resetState(state) {
      return initialState;
    },
  },
});

export const {setCurrentUser, resetState} = userSlice.actions;
export const userReducer = userSlice.reducer;


const initialStateAdmin = {
  currentAdmin : EMPTY_EMPLOYEE
}

const adminSlice = createSlice({
  name: "Admin",
  initialState :initialStateAdmin,
  reducers: {
    setCurrentAdmin(state, action) {
      state.currentAdmin = action.payload;
    },
    resetStateAdmin(state) {
      return initialStateAdmin;
    },
  },
});

export const {setCurrentAdmin, resetStateAdmin} = adminSlice.actions;
export const adminReducer =  adminSlice.reducer;




const initialStateVet = {
  currentVet: EMPTY_VET
}

const vetSlice = createSlice({
  name: "vet",
  initialState :initialStateVet,
  reducers: {
    setCurrentVet(state, action) {
      state.currentVet = action.payload;
    },
    resetStateVet(state) {
      return initialStateVet;
    },
  },
});

export const {setCurrentVet, resetStateVet} = vetSlice.actions;
export const vetReducer =  vetSlice.reducer;



const initialStateDonor = {
  currentDonor: EMPTY_DONER
}

const donorSlice = createSlice({
  name: "donor",
  initialState :initialStateDonor,
  reducers: {
    setCurrentDonor(state, action) {
      state.currentDonor = action.payload;
    },
    resetStateDonor(state) {
      return initialStateDonor;
    },
  },
});

export const {setCurrentDonor, resetStateDonor} = donorSlice.actions;
export const donorReducer =  donorSlice.reducer;



const initialStatePetowner = {
  currentOwner: EMPTY_PETOWNER,
}

const petOwnerSlice = createSlice({
  name: "petOwner",
  initialState :initialStatePetowner,
  reducers: {
    setCurrentOwner(state, action) {
      state.currentOwner = action.payload;
    },
    resetStateOwner(state) {
      return initialStatePetowner;
    },
  },
});

export const {setCurrentOwner, resetStateOwner} = petOwnerSlice.actions;
export const petOwnerReducer =  petOwnerSlice.reducer;
