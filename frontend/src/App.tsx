import {Route, Routes} from "react-router-dom";
import Navbar from "./Navbar.tsx";
<<<<<<< feature-MovieSeries
import HomePage from "./components/Homepage.tsx";
import MovieDetailsPage from "./components/MovieDetailsPage.tsx";
=======
import Login from "./components/Login.tsx";
import Filterbar from "./Filterbar.tsx";
>>>>>>> master

function App() {
    return (
        <>
            <header>
                <Navbar />
                <Login/>
            </header>

            <main className="page-layout">
                <Filterbar />

<<<<<<< feature-MovieSeries
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
=======
                <section className="movie-section">
                    <Routes>
                        <Route path="/movie" element={<Movie />} />
                    </Routes>
                </section>
            </main>
        </>
    )
>>>>>>> master
}

export default App
