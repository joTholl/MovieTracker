import {Route, Routes} from "react-router-dom";
import Navbar from "./Navbar.tsx";
import HomePage from "./components/Homepage.tsx";
import MovieDetailsPage from "./components/MovieDetailsPage.tsx";

function App() {


  return (
    <>
        <header>
            <Navbar />
        </header>
        <Routes>
            <Route path="/" element={<HomePage />}></Route>
            <Route path="/movies/:id" element={<MovieDetailsPage />} />
        </Routes>
    </>
  )
}

export default App
