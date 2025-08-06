import './App.css';
import Project from '../src/project';
import {HashRouter} from "react-router-dom";
import {Routes, Route} from "react-router";

export function App() {
   return (
    <HashRouter>
      <div>
        <Routes>
          <Route path="/*" element={<Project/>} />
        </Routes>
      </div>
    </HashRouter>
);}
