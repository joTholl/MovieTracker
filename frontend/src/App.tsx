import {Route, Routes} from "react-router-dom";
import Navbar from "./Navbar.tsx";
import HomePage from "./components/Homepage.tsx";
import MovieDetailsPage from "./components/movie/MovieDetailsPage.tsx";
import Movie from "./components/movie/Movie.tsx";
import SeriesDetailsPage from "./components/series/SeriesDetailsPage.tsx";
import WatchableDetailsPage from "./components/watchable/WatchableDetailsPage.tsx";
import Series from "./components/Series.tsx";

function App() {
    return (
        <>
            <header>
                <Navbar />
            </header>
            <Routes>
                <Route path="/" element={<HomePage/>}></Route>
                <Route path="/movies/:id" element={<MovieDetailsPage/>}/>
                <Route path="/series/:id" element={<SeriesDetailsPage/>}/>
                <Route path="/watchables/:id" element={<WatchableDetailsPage/>}/>
            </Routes>

            <section className="movie-section">
                <Routes>
                    <Route path="/movie" element={<Movie/>} />
                    <Route path="/series" element={<Series/>}/>
                </Routes>
            </section>
        </>
    )
}

export default App
