import './App.css'
import {Route, Routes} from "react-router-dom";
import Movie from "./components/Movie.tsx";

function App() {


  return (
    <>
        <Routes>
            <Route path="/movie" element={<Movie />}></Route>
        </Routes>
    </>
  )
}

export default App
