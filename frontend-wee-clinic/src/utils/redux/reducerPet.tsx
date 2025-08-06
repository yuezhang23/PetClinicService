import { createSlice } from "@reduxjs/toolkit";

// const initialState = {
//     items : [],
//     item :  {},
// };

// const setItem = (state :any, action : PayloadAction<Pet>) => {
//     state.item = action.payload;
// };

// const setItems =(state: any, action: PayloadAction<Pet>) =>{
//     state.items = action.payload;
// };

// const addItem = (state: any, action: any) => {
//     state.items.push(action.payload);
// };


// const updateItem = (state :any, action :any) => {
//     state.items = state.items.map((item :any) => item._id === action.payload._id ? action.payload: item);
// };

// const deleteItem = (state :any, action :any) => {
//     state.items = state.items.filter((item: any)=> item._id !== action.payload);
// };

// const resetItem = (state: any) => {
//     state.item = initialState.item
// };
// const resetItems = (state: any) => {
//     state.items = initialState.items
// };


const initialState = {
    currentPets: [],
    currentPet: {_id: -1, petName: "", breed_name: "", birthdate: "0000/00/00", weight: 0.0, height: 0.0, statusOfHealth: ""},
 }
  

const PetSlice =  createSlice({
    name: "Pet",
    initialState,
    reducers : {
        // addPet: addItem,
        // updatePet: updateItem,
        // deletePet: deleteItem,
        setPet(state, action) {
            state.currentPet = action.payload
        },
        setPets(state, action) {
            state.currentPets = action.payload
        }
        // resetPet: resetItem,
        // resetPets: resetItems
    }
});
export const {setPet, setPets} = PetSlice.actions;
export const petReducer =  PetSlice.reducer;
