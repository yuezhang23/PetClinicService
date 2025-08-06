import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate} from "react-router";
import { useEffect, useState } from "react";
import * as fClient from "../Follows/followClient";
import * as client from "../client"
import axios from "axios";
import "../../../hommie.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { ProjectState } from "src/project/store";
import { Doner, Employee, petOwner, User, Vet } from "src/utils/types";
import { EMPTY_EMPLOYEE, EMPTY_USER, EMPTY_VET } from "src/utils/constants";
import { FaAccessibleIcon, FaComment, FaDiscord, FaMarker, FaSmileWink, FaStar } from "react-icons/fa";
import RatingDialog from "./rating";
export const BASE_API = process.env.REACT_APP_API_BASE;
// axios.defaults.withCredentials = true;


export default function PublicProfile() {

  const loc = useLocation();
  // console.log("here" + loc.state)
  // const {appointID, emp_name} = loc.state;
  const navigate = useNavigate();
  const dispatch = useDispatch();


  const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
  const { currentVet } = useSelector((state: ProjectState) => state.vetReducer);
  const { currentAdmin } = useSelector((state: ProjectState) => state.adminReducer);
  const { currentOwner } = useSelector((state: ProjectState) => state.petOwnerReducer);
  const { currentDonor } = useSelector((state: ProjectState) => state.donorReducer);

  const [user, setUser] = useState<Vet | Employee | petOwner | Doner | User>(EMPTY_USER);

  const [follows, setFollows] = useState([]);
  const [isSelf, setIsSelf] = useState(false);
  const [profile, setProfile] = useState({ _id: "", description: "", favs: [] });
  const [filter, setFilter] = useState('');
  const contentVetList = ["Is the vet giving feedback in time", "Is the service satisfying overall", "Will you consider seeing this vet in the future"];
  const [invoiceList, setInvoiceList] = useState([])

  const handleSelectChange = (e : any) => {
  //   setFilter(e.target.value.toLowerCase());
  }

  const updateUser = async () => {
  //   try {
  //     const user = await client.updateUser(profile._id, profile);
  //     dispatch(setCurrentUser(user));
  //     alert('Profile successfully updated.');
  //   } catch (err) {
  //     console.log(err.response.data.message)
  //   }
  };

  const fetchProfile= async ()=> {
    try {
      if (loc.state === null) {
        // private profile
        if (currentUser.role === "veterinarian") {
          setUser({...currentVet})
        } 
        if (currentUser.role === "pet_owner") {
          setUser({...currentOwner})
          const res = await client.getUserInvoices(currentUser);
          dispatch(setInvoiceList([...res]))
        } 
        if (currentUser.role === "donor") {
          setUser({...currentDonor})
        } 
        if (currentUser.role === "administrative") {
          setUser({...currentAdmin})
        } 
      } else {
        // public profile
        const {user, type} = loc.state;      
        if (type === 'vet') {
          setUser({...EMPTY_VET, ...user})
        } else {
          setUser({...EMPTY_EMPLOYEE, ...user})
        }
      }
    } catch (err) {
      navigate(`./${err}`);
    }
  }


  const fetchFollows = async () => {
    // try {
    //   if (currentUser) {
    //   const fos = await fClient.findFollowsOfAUser(currentUser._id);
    //   setFollows(fos);
    // }
    // const follows = await fClient.followsNumber(profileId);
    // const followers = await fClient.followersNumber(profileId);
    // setFoNumber(follows)
    // setFerNumber(followers)
    // } catch (err) {
    //   navigate("/User/Profile")
    // }
  }

  const following = () => {
    if (!currentUser || !follows) return false;
  }
  
  const followUser = async () => {
    if (currentUser.role === "guest") {
      alert("Hi, Guest! Please Sign in to follow the vet")
      navigate("../Hommie")
      return;
    }
    if (currentUser.roles["veterinarian"] || currentUser.roles["administrative"] || currentUser.roles["accountant"]) {
      alert("Sorry, you work here, can't Follow!")
      return;
    }
    const status = await fClient.followsUser(user?.userID);
    fetchFollows();
  }

  const unfollowUser = async () => {
    if (currentUser.role === "guest") {
      alert("Hi, Guest! Please Sign in to follow the vet")
      navigate("../Hommie")
      return;
    }
    const status = await fClient.unfollowsUser(user?.userID);
    fetchFollows();
  }

  useEffect(() => {
    fetchProfile();
  }, [profile]);


  return (
    <div className="container">
      <button className="btn btn-sm bg-warning-subtle rounded-5 mb-2" onClick={() => navigate(-1)}> 》〉Back </button>
      <div className="card d-flex justify-content-between">
        <div className="card-header row">
          <h3 className="col me-2"><FaSmileWink className="fs-1 text-warning mx-2 "/>{user?.firstName},{user?.lastName}</h3>
          <div className="col d-flex justify-content-end align-items-center">
            {following() && currentUser && (currentUser.userID !== user?.userID) ? (
              <button onClick={unfollowUser} className="btn bg-danger-subtle ">
                - Unfollow
              </button>
              ):(
              <button onClick={followUser} className="btn bg-warning-subtle ">
                + Follow
              </button>
            )}
          </div>
        </div>
        <div className="card-body">
          <div className="row">
            <figure className="col-4 figure ms-2">
                <img src={`/images/emp/${user?.userID}.png`} style={{ height: 250}} className="figure-img img-fluid rounded-circle"/>
                <figcaption className="figure-caption text-start">A caption for the above image.</figcaption>
            </figure>
            <ul className="col list-group form-control"> 
              <li className={'specialization' in user ? 'list-group-item' : 'd-none'}> Specialization: {'specialization' in user ? user.specialization : ''}</li>
              <li className={'vet_certificate' in user ? 'list-group-item' : 'd-none'}> Certificate: {'vet_certificate' in user ? user.vet_certificate : ''}</li>
              <li className={'work_years' in user ? 'list-group-item' : 'd-none'}> Work Years: {'work_years' in user ? user.work_years : ''}</li>
              <li className={'vet_license' in user ? 'list-group-item' : 'd-none'}> Licence: {'vet_license' in user ? user.vet_license : ''}</li>
              <li className="list-group-item"> Email: {user.email}</li>
              <li className="list-group-item"> Phone: {user.phone}</li>
              <li className="list-group-item"> Location: {}</li>
            </ul>
          </div>
  
          <div className="w-75">
            <p >
                <button className='btn bg-primary-subtle w-25' data-bs-toggle="collapse" data-bs-target={`#${user?.userID}`} 
                  aria-controls={`${user?.userID}`} ><strong className="mx-2 text-danger">
                    <FaStar className="me-2"/> 
                    {'clinic_id' in user && 'Comment'} 
                    {'card_type' in user && 'Invoices Records'} 
                    </strong></button>
            </p>
            <div className={'clinic_id' in user ?  "collapse mt-3" : 'd-none'} id={`${user?.userID}`}>
                <div className="card card-body">
                    <RatingDialog contents={contentVetList}/>
                </div>
            </div>
            <div className={'card_type' in user ?  "collapse mt-3" : 'd-none'} id={`${user?.userID}`}>
                <div className="card card-body">
                    <RatingDialog contents={invoiceList}/>
                </div>
            </div>
          </div>


        {isSelf ? <div className="profile-container">
          <button className="badge bg-success-subtle text-dark mt-3 mb-2 float-end" 
                onClick={updateUser}> Update Your Fav Beer To Display</button>
          <h4 className="mt-4">Multi Select Your Favorite Beers</h4>
          <input
            type="text"
            value={filter}
            // onChange={handleFilterChange}
            placeholder="Search beers..."
            className="form-control mb-2"
          />
          <select multiple className="form-control" style={{ overflowY: 'scroll' }} 
            onChange = {(e) => {handleSelectChange(e)}} >
          </select>
        </div> :
        <div className="accordion mt-3" id="accordionExample">
          <div className="accordion-item">
            <h2 className="accordion-header" id="headingOne">
              <button className="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                {'statement' in user && 'Something about ME...'} {'card_type' in user && 'Personal Info'}
              </button>
            </h2>
            <div id="collapseOne" className="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
              <div className="accordion-body">
                  {'statement' in user && (user.statement.length > 0 ? user.statement : <p>Coming Up...</p> )}
                  {'card_type' in user &&   <p>Credit Card Number: {user.card_type} </p> }
                  {'card_type' in user &&   <p>Credit Card Number: {user.card_number} </p> }
              </div>
            </div>
          </div>
        </div>
        }
      </div>      
    </div>


    </div>
  )

}