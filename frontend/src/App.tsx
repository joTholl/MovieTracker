import {Route, Routes} from "react-router-dom";
import Movie from "./components/Movie.tsx";
import Navbar from "./Navbar.tsx";
import Login from "./components/Login.tsx";

function App() {


  return (
    <>
        <header>
            <Navbar />
            <Login/>
        </header>
      <h1>Hello World!</h1>
        <Routes>
            <Route path="/movie" element={<Movie />}></Route>
        </Routes>
    </>
  )
}

export default App
