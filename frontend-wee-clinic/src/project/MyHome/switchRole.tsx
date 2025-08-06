
import { Link, useLocation, useNavigate } from "react-router-dom";
import { FaCircle, FaMagento } from "react-icons/fa";
import { createUser } from "../User/client";
import { setCurrentUser } from "src/utils/redux/reducerUser";
import { useDispatch, useSelector } from "react-redux";
import { ProjectState } from "../store";

interface model {
    roleLst: string[];
    eventLst: string[]; 
  }


const SwitchRole : React.FC<model> =({roleLst, eventLst}) => {
    const {pathname} = useLocation();
    const role = pathname.substring(pathname.indexOf("=") + 1);
    const roles = roleLst.filter((i) => i !== role);
    const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const switchRole = (key : string) => {
        const uid = currentUser.roles[key];
        dispatch(setCurrentUser({...currentUser, userID : uid, role: key}));
        navigate(`${pathname.replace(role, key)}`);
    };

    return (
        <div className='justify-content-start'>
            <button data-bs-toggle="dropdown" className='btn btn-primary ms-3 rounded-4 text-white'>
            <strong >SWITCH</strong>
            </button>
            <div className={`dropdown-menu wd-full-frame`}>
            {roles[0] !== 'guest' && roles.map((link : any) => (
                <button className="dropdown-item" onClick={() => switchRole(link)} > {link} </button>
                // <Link className="dropdown-item" to={pathname.replace(role, link)} > {link} </Link>
            ))} 
            </div>
            <FaMagento></FaMagento>
            <FaCircle className='text-warning'></FaCircle>     
            {eventLst.map(
                (i : string) => <>
                    <FaMagento></FaMagento>
                    <FaCircle className='text-warning'></FaCircle>
                    <FaMagento></FaMagento>
                    <button type='button' className="rounded-5" onClick={() => navigate(`${i.toLowerCase()}`)}> <strong>{i}</strong> </button>
                </>        
            )}     
       
        </div>
    );
}
export default SwitchRole;