import React from 'react';
import 'react-toastify/dist/ReactToastify.css';
import { Route, Routes} from "react-router-dom";
import "../../hommie.css"
import Logging from './Logging';
import HommiePetOwner from './homePetOwner';
import PetProfile from './homePet';
import HommieDonor from './homeDonor';
import HommieVet from './homeVet';
import PublicProfile from '../User/PublicProfile';


function Hommie() {  
  return (
      <div >  
        <Routes>
            <Route path="/*" element={ <Logging/>} />
            <Route path="/currentRole=pet_owner" element={<HommiePetOwner/>} />
            <Route path="/currentRole=donor" element={<HommieDonor/>} />
            <Route path="/currentRole=veterinarian" element={<HommieVet/>} />
            <Route path="/currentRole=pet_owner/family/*" element={<PetProfile/>} />
            <Route path="/currentRole=pet_owner/profile" element={<PublicProfile/>} />
            <Route path="/currentRole=veterinarian/profile" element={<PublicProfile/>} />
            <Route path="/currentRole=admin/profile" element={<PublicProfile />} />
        </Routes>
      </div>
  );
}
export default Hommie;