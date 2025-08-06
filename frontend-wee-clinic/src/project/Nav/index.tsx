
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { ProjectState } from "../store";
import { FaHeart, FaUser } from "react-icons/fa";
import '../../index.css';
import { resetState } from "../../utils/redux/reducerUser";


function Nav() {
  const { currentUser } = useSelector((state: ProjectState) => state.userReducer);
  const dispatch = useDispatch();
  const {pathname } = useLocation();
  const navigate = useNavigate();

  const handleProfile = () =>{

  }

  const logout = () =>{
    dispatch(resetState());
    if (pathname.includes("Hommie")) {
      navigate(`./Hommie`);
      
    }
  }

  return (
    <div className="">
      <div className="navbar navbar-expand-lg navbar-dark bg-success">
        <Link className="navbar-brand d-inline-block align-top" to={"/Home"}><img src={`/images/logo.jpeg`} width='230px' height='150px' className="rounded-circle"/></Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerTarget" aria-controls="navbarTogglerTarget" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse justify-content-start" id="navbarTogglerTarget">
          <ul className="navbar-nav me-3 mb-2 mb-lg-0 fs-2">
              <li className="nav-item " key={0}>
                  <Link className={`nav-link ${pathname.includes("Home") && "active fw-bold "}`} to={"/Home"}>
                      About
                  </Link>
              </li>
              <li className="nav-item" key={1}>
                  <Link className={`nav-link ${pathname.includes("Service") && "active fw-bold"}`} to={"/Service"}>
                      Shop
                  </Link>
              </li>

              <li className="nav-item" key={2}>
                <Link className={`nav-link ${pathname.includes("Hommie") && "active fw-bold"}`} 
                to={
                  Object.entries(currentUser.roles).length === 0
                    ? "/Hommie"
                    : currentUser.role === "Guest"
                      ? `/Hommie/currentRole`  
                      : `/Hommie/currentRole=${currentUser.role}`           
                  }>
                    MyHome
                </Link>
              </li>
            
              <li className="nav-item" key={3}>
                  <Link className={`nav-link ${pathname.includes("Event") && "active fw-bold"}`} to={"/Event"}>
                      Event
                  </Link>
              </li>
              <li className="nav-item" key={4}>
                <Link type="button" className="btn btn-lg btn-warning rounded-circle mx-3" to={"/Donate"}>
                  Donate <br></br> <FaHeart className="text-danger"/>
                </Link>
             </li>
          </ul>
          <input className="p-2 rounded-4 border-0 mx-2" type="search" placeholder="Search..." aria-label="Search"></input>
          <button type="button" className="rounded-5"> Advanced Search </button> 
        </div>
      
        <div className="d-flex flow-grow-1">
          <button className={currentUser.role !== "guest" ? "btn text-warning": "btn border-0 text-warning disabled"} onClick={()=> handleProfile()}>

          <h6 className={currentUser.firstName !== "Guest"? "text-warning": "text-white"}> <FaUser/>Hello,{currentUser.firstName} </h6>
          </button>            
          {currentUser.role !== "guest" && (
            <button className= 'btn btn-sm rounded-circle text-white' onClick={()=> logout()}>
            Log Out
            </button>)}
        </div>
      </div>             
    </div >
  )
}

export default Nav;
