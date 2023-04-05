import './App.css';
import { BrowserRouter as Router, Link, Route, Routes } from 'react-router-dom';
import HomeComponent from './Components/HomeComponent';
import LoginComponent from './Components/LoginComponent';
import CreateuserComponent from './Components/CreateuserComponent';
import CreateOrModifyPart from './Components/CreateOrModifyPartComponent';
import ListPartsComponent from './Components/ListPartsComponent';
import AuthService from './Service/AuthService';
import { useEffect, useState } from 'react';
import ListBoxesComponent from './Components/ListBoxesComponent';
import ListProjectsComponent from './Components/ListProjectsComponent';
import CreateOrModifyProjectComponent from './Components/CreateOrModifyProjectComponent';
import PartToProjectComponent from './Components/PartToProjectComponent';
import ListLackOfPartsComponent from './Components/ListLackOfPartsComponent';
import ListReadyProjectsComponent from './Components/ListReadyProjectsComponent';
import ShowPartsOfProjectComponent from './Components/ShowPartsOfProjectComponent';


function App() {

  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser()

    if (user) {
      setCurrentUser(user)
    }

  }, [])

  const logout = () => {
    AuthService.logout()
    setCurrentUser(undefined)
  }
  return (
    <div>
      <Router>
        <nav className='navbar navbar-expand navbar-dark bg-dark'>
          <div className='navbar-nav mr-auto'>
            <li>
              <Link to={"/"} className='nav-link'>
                <span>Kezdőlap</span>
              </Link>
            </li>
            {currentUser && currentUser.roles.includes("admin") && (
              <li>
                <Link to={"/createuser"} className='nav-link'>
                  <span>Felhasználó létrehozása</span>
                </Link>
              </li>
            )}
            {currentUser && currentUser.roles.includes("storeleader") && (
              <li>
                <Link to={"/createpart"} className='nav-link'>
                  <span>Alkatrész hozzáadása</span>
                </Link>
              </li>
            )}
            {currentUser && currentUser.roles.includes("storeleader") && (
              <li>
                <Link to={"/listparts"} className='nav-link'>
                  <span>Alkatrészek</span>
                </Link>
              </li>
            )}
            {currentUser && currentUser.roles.includes("storeleader") && (
              <li>
                <Link to={"/listlackofparts"} className='nav-link'>
                  <span>Hiányzó alkatrészek</span>
                </Link>
              </li>
            )}
            {currentUser && currentUser.roles.includes("expert") && (
              <li>
                <Link to={"/listparts"} className='nav-link'>
                  <span>Alkatrészek</span>
                </Link>
              </li>
            )}
            {currentUser && currentUser.roles.includes("expert") && (
              <li>
                <Link to={"/listprojects"} className='nav-link'>
                  <span>Projektek</span>
                </Link>
              </li>
            )}
            {currentUser && currentUser.roles.includes("storeemployee") && (
              <li>
                <Link to={"/listprojectswithoutprereservation"} className='nav-link'>
                  <span>Projektek</span>
                </Link>
              </li>
            )}
            {currentUser && currentUser.roles.includes("expert") && (
              <li>
                <Link to={"/createproject"} className='nav-link'>
                  <span>Új projekt</span>
                </Link>
              </li>
            )}
            {currentUser ? (
              <li>
                <Link to={"/login"} className='nav-link' onClick={logout}>
                  <span>Kijelentkezés</span>
                </Link>
              </li>

            ) :
              (
                <li>
                  <Link to={"/login"} className='nav-link'>
                    <span>Bejelentkezés</span>
                  </Link>
                </li>

              )

            }
          </div>
        </nav>

        <div className='container'>
          <Routes>
            <Route exact path='/' element={<HomeComponent />} />
            <Route path='/login' element={<LoginComponent />} />
            <Route path='createuser' element={<CreateuserComponent />} />
            <Route path='/createpart' element={<CreateOrModifyPart />} />
            <Route path='/createpart/:id' element={<CreateOrModifyPart />} />
            <Route path='/listparts' element={<ListPartsComponent />} />
            <Route path='/listboxes/:id' element={<ListBoxesComponent />} />
            <Route path='/listprojects' element={<ListProjectsComponent />} />
            <Route path='/createproject' element={<CreateOrModifyProjectComponent />} />
            <Route path='/createproject/:id' element={<CreateOrModifyProjectComponent />} />
            <Route path='/parttoproject/:id' element={<PartToProjectComponent />} />
            <Route path='/listlackofparts' element={<ListLackOfPartsComponent />} />
            <Route path='/listprojectswithoutprereservation' element={<ListReadyProjectsComponent />} />
            <Route path='/showpartsofproject/:id' element={<ShowPartsOfProjectComponent />} />
          </Routes>
        </div>

      </Router>

    </div>
  );
}

export default App;
