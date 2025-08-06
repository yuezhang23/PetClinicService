import { ProjectState } from "../store";
import * as connect from "./connect";
import { useNavigate } from "react-router";
import {setPet, setPets} from "../../utils/redux/reducerPet"
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { Pet } from "src/utils/types";
import { Link } from "react-router-dom";
import { FaHome } from "react-icons/fa";


function MyPetCard() {
    const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
    const { currentPets } = useSelector((state: ProjectState) => state.petReducer);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const fetchPets = async () => {
        // console.log(currentUser)
        try {
            const rep = await connect.findAllPetsByOwner(currentUser);
            dispatch(setPets(rep));
        } catch (err) {
            navigate(`../Hommie/currentRole=pet_owner}/${err}`);
        }
    };


    const checkRecords = (pet : Pet) => {
        dispatch(setPet(pet));
        navigate(`./family/${pet.pet_name}`);
    }


    useEffect(() => {
        fetchPets();
    }, []);

    return (
        <div className="pt-4 container">
            <div className="text-danger ">
                <FaHome className="fs-1 text-warning"/>{currentUser.firstName}'s Families
            </div>
            <hr />  
            <div className="row">
                <div className="row row-cols-1 row-cols-md-5 g-3">
                    {currentPets && currentPets.map((pet : Pet) => (
                        <div key={pet.patient_id} className="col" style={{ width: 300}}>
                            <div className="card border-0">
                                <figure className="figure ">
                                    <img src={`/images/${pet.pet_name}.jpeg`} style={{ height: 250}} className="figure-img img-fluid rounded-circle"/>
                                    <figcaption className="figure-caption text-right">A caption for the above image.</figcaption>
                                </figure>
                                <div className="card-body text-center">
                                    <Link className="card-title" to={`./${pet.pet_name}`}
                                          style={{ textDecoration: "none", color: "navy", fontWeight: "bold" }}>
                                        <h4>{pet.pet_name}</h4>  <h5> <strong className="text-warning">Category  : </strong>{pet.breed_name}</h5>                          
                                    </Link> 
                                    <p className="card-text text-middle">B-day: {pet.date_of_birth.toString()}<br></br> Height : {pet.height_in_cm}<br></br> Weight: {pet.weight_in_lb}</p>
                                    <button type="button" className="rounded-5"
                                     onClick={(event) => { event.preventDefault(); checkRecords(pet);}}>
                                        Records
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
export default MyPetCard;