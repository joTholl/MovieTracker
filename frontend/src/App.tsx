import {Route, Routes} from "react-router-dom";
import Navbar from "./Navbar.tsx";
import HomePage from "./components/Homepage.tsx";
import MovieDetailsPage from "./components/MovieDetailsPage.tsx";
import Login from "./components/Login.tsx";
import Filterbar from "./Filterbar.tsx";
import Movie from "./components/Movie.tsx";

function App() {
    return (
        <>
            <header>
                <Navbar />
                <Login/>
            </header>
            <Routes>
                <Route path="/" element={<HomePage />}></Route>
                <Route path="/movies/:id" element={<MovieDetailsPage />} />
            </Routes>
            <section className="movie-section">
                <Routes>
                    <Route path="/movie" element={<Movie />} />
                </Routes>
            </section>

            <main className="page-layout">
                <Filterbar />
            </main>
        </>
    )
}

export default App
