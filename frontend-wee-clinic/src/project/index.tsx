
import { Routes, Route } from 'react-router';
import { Navigate } from 'react-router-dom';
import { Provider } from 'react-redux';
import Nav from './Nav';
import store from './store';
import ClinicHome from './Home';
// import Signup from './User/Signup';
// import Signin from './User/Signin';
// import Logging from './User/Logging';
// import Profile from './User/Profile';
// import UserTable from './User/Table';
import PublicProfile from './User/PublicProfile';
// import FollowDetails from './User/Follows/FollowDetails';
// import Claims from './User/Claims';
import Donate from './Donate/donate';
import Hommie from './MyHome/index';
import { AppContextProvider } from 'src/components/AppContextProvider';
import { Fragment } from 'react';

function Project() {
  return (
    <Provider store={store}>
          <div className='col'>
            <Nav />
            <AppContextProvider>
              <Fragment>
                <main className="MainContainer">  
                <Routes>
                    <Route path="/" element={<Navigate to="Home" />} />
                    <Route path="Hommie/*" element={<Hommie/>} />
                    <Route path="Home/*" element={<ClinicHome/>} />
                    <Route path="Donate" element={<Donate/>} />
                    <Route path="Clinic/emp_profile/public/*" element={<PublicProfile />} />

                    {/* <Route path="User/Signin" element={<Signin />} /> */}
                    {/* <Route path="User/Signup" element={<Signup />} /> */}
                    {/* <Route path="User/Profile" element={<Profile />} /> */}
                    {/* <Route path="User/Profile/:profileId/follows" element={<FollowDetails />} /> */}
                    {/* <Route path="User/Owner/:ownerId/Claims" element={<Claims />} /> */}
                    {/* <Route path="User/Admin/Users" element={<UserTable />} /> */}
                </Routes>
                </main>
              </Fragment>
              </AppContextProvider>
          </div>
    </Provider>

    )
}

export default Project;
