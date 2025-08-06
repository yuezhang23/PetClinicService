import { configureStore } from "@reduxjs/toolkit";
import {userReducer, vetReducer, petOwnerReducer, adminReducer, donorReducer} from "../../utils/redux/reducerUser";
import {petReducer} from "../../utils/redux/reducerPet"
import { Pet, User, Vet, Employee, Doner, petOwner } from "src/utils/types";


export interface ProjectState {
  userReducer: {
    currentUser: User;
  }

  vetReducer: {
    currentVet: Vet;
  }

  adminReducer: {
    currentAdmin: Employee;
  }

  petOwnerReducer: {
    currentOwner: petOwner;
  }

  donorReducer: {
    currentDonor: Doner;

  }

  petReducer: {
    currentPet: Pet;
    currentPets: Pet[];

  }

  
}
const store = configureStore({
  reducer: {
    userReducer,
    petReducer,
    petOwnerReducer,
    donorReducer,
    vetReducer,
    adminReducer
  }
});

export default store;