import {BrowserRouter as Router, Routes, Route} from "react-router-dom";

import './App.css'
import PizzaList from "./Components/PizzaList";
import CreatePizza from "./Components/CreatePizza";
import Home from "./Components/Home";


function App() {

    return(
        <Router>
            <Routes>
                <Route path={'/'} element={<Home/>}></Route>
                <Route path={'/pizzas'} element={<PizzaList/>}/>
                <Route path={'/create_form'} element={<CreatePizza/>}/>
            </Routes>
        </Router>
    )
}

export default App;
