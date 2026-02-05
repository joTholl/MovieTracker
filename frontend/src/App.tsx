import {Route, Routes} from "react-router-dom";
import Navbar from "./Navbar.tsx";
import HomePage from "./components/Homepage.tsx";
import MovieDetailsPage from "./components/movie/MovieDetailsPage.tsx";
import Login from "./components/Login.tsx";
import Movie from "./components/movie/Movie.tsx";

function App() {
    return (
        <>
            <header>
                <Navbar />
            </header>
            <Routes>
                <Route path="/" element={<HomePage />}></Route>
                <Route path="/movies/:id" element={<MovieDetailsPage />} />
                <Route path="login" element={<Login/>}/>
            </Routes>

            <section className="movie-section">
                <Routes>
                    <Route path="/movie" element={<Movie />} />
                </Routes>
            </section>
        </>
    )
}

export default App
