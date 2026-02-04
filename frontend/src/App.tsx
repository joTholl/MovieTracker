import {Route, Routes} from "react-router-dom";
import Movie from "./components/Movie.tsx";
import Navbar from "./Navbar.tsx";
import Login from "./components/Login.tsx";
import Filterbar from "./Filterbar.tsx";

function App() {
    return (
        <>
            <header>
                <Navbar />
                <Login/>
            </header>

            <main className="page-layout">
                <Filterbar />

                <section className="movie-section">
                    <Routes>
                        <Route path="/movie" element={<Movie />} />
                    </Routes>
                </section>
            </main>
        </>
    )
}

export default App
