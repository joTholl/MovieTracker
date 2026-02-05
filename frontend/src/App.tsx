import {Route, Routes} from "react-router-dom";
import Movie from "./components/Movie.tsx";
import Navbar from "./Navbar.tsx";
import Filterbar from "./Filterbar.tsx";
import Series from "./components/Series.tsx";
import SeriesDetail from "./components/SeriesDetail.tsx";


function App() {
    return (
        <>
            <header>
                <Navbar />
            </header>

            <main className="page-layout">
                <Filterbar />

                <section className="movie-section">
                    <Routes>
                        <Route path="/movie" element={<Movie />} />
                        <Route path="/series" element={<Series/>}/>
                        <Route path={"/series/:id"} element={<SeriesDetail/>}/>
                    </Routes>
                </section>
            </main>
        </>
    )
}

export default App
