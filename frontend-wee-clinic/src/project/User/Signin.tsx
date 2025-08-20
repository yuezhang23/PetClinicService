// import { Fragment, useState } from "react";
// import { Link, useNavigate } from "react-router-dom";
// import * as client from "./client";
// import { useDispatch } from "react-redux";
// import { setCurrentUser } from "./reducer";
// import { AppContextProvider } from "src/components/AppContextProvider";
// // import axios from "axios";
// // axios.defaults.withCredentials = true;

// export default function Signin() {
//   const [credentials, setCredentials] = useState<any>({ username: "", password: ""});
//   const navigate = useNavigate();
//   const [error, setError] = useState(null);
//   const dispatch = useDispatch();
  
//   const signin = async () => {
//     try {
//       const currentUser = await client.signin(credentials);
//       console.log(credentials)

//       dispatch(setCurrentUser(currentUser));
//       navigate("/User/Profile");
//     } catch (err: any) {
//       setError(err.response.data);
//     }
//   };
  
//   return (
//       <AppContextProvider>
//       <Fragment>
//       <h1>Sign In</h1>
//       {error && <div className="alert alert-danger my-1">{error}</div>}
//       <input value={credentials.username} className="form-control mb-2" onChange={(e) =>
//         setCredentials({ ...credentials, username: e.target.value })}/>
//       <input value={credentials.password} className="form-control mb-2" type="password"
//        onChange={(e) =>
//         setCredentials({ ...credentials, password: e.target.value })}/>
//       <button onClick={signin} className="btn bg-success-subtle form-control mb-2" > Signin </button>

//       <Link to={"/User/Signup"} className="btn bg-warning-subtle form-control">
//         Signup
//       </Link>
//       </Fragment>
//       </AppContextProvider>
//   );
// }

export {};
