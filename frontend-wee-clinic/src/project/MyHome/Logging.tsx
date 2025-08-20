import { useDispatch, useSelector} from "react-redux";
import { setCurrentUser } from "../../utils/redux/reducerUser";
import { ProjectState } from "../store";
import { useLocation, useNavigate } from "react-router";
import * as connect from "../User/client";
import {FaMailBulk, FaUser } from "react-icons/fa";


function Logging() {
  const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const {pathname} = useLocation();
  
  
  const fetchRole = async (e : any) => {
    e.preventDefault();
    try {
      console.log('Attempting to authenticate user:', currentUser.userID);
      
      const data = await connect.findUserRolesById(currentUser);
      console.log('Authentication successful, user roles:', data);
      
      dispatch(setCurrentUser({...currentUser, roles: {...data}}));
      
      if (Object.entries(data).length === 1) {
        navigate(`./currentRole=${Object.keys(data)[0]}`);
      } 
      else {
        navigate(`./currentRole/`);
      }
    } catch (error : any) {
      console.error('Authentication error:', error);
      
      if (error.response) {
        // Server responded with error
        const errorMessage = error.response.data?.error || error.response.data?.message || 'Authentication failed';
        alert(`Login failed: ${errorMessage}`);
      } else if (error.request) {
        // Request was made but no response received
        alert('Login failed: No response from server. Please check if the account service is running.');
      } else {
        // Something else happened
        alert('Login failed: ' + (error.message || 'Unknown error'));
      }
    }
  };
     

  const selectRole = (key : string, value : string) => {
      dispatch(setCurrentUser({...currentUser, userID : value, role: key}));
      navigate(`./currentRole=${key}`);
  };


  return (
    <div className='row justify-content-center'>
      <form onSubmit={fetchRole} 
        className={!pathname.includes("currentRole")? 'w-25 border border-secondary rounded text-center':"d-none"}>
          <div className="inputbox">
            <label ><FaMailBulk/> Username</label>
            <input className='mt-5' onChange={(e) => dispatch(setCurrentUser({...currentUser, userID: e.target.value}))} name="name" type="name" autoComplete="your name" required></input>
          </div>
          <div className="inputbox">
            <label> <FaUser/> Password</label>
            <input className='mt-5' onChange={(e) => dispatch(setCurrentUser({...currentUser, password: e.target.value}))} name="password" type="password" autoComplete="****" required></input>
          </div>
          <div >
            <button type='submit' className='m-2 rounded-5'>Log In</button>    
          </div>
          <div className="register m-2">
            <a href="##">Forget Password ?</a><br></br>
            Don't have an account ?<a href="##"> Register!! </a>
          </div>
      </form> 

      <form className={pathname.includes("currentRole/")? 'text-center': "d-none"}>
        <div>   
          <p className="m-4">Please Select a <strong className="text-primary">Role</strong></p>
          <div className="d-flex justify-content-center">
            {Object.entries(currentUser.roles).map(([key, value]) => (
              <li key={key} className="list-group-item ">
                <button type="button" className="mx-3 rounded-circle" onClick={() => selectRole(key, value)}> {key} </button> 
              </li>
            ))}
          </div>
        </div>        
      </form> 
    </div>
  );
}

export default Logging;