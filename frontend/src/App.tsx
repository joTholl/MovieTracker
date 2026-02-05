import {Route, Routes} from "react-router-dom";
import Navbar from "./Navbar.tsx";
import HomePage from "./components/Homepage.tsx";
import MovieDetailsPage from "./components/movie/MovieDetailsPage.tsx";
import Movie from "./components/movie/Movie.tsx";
import SeriesDetailsPage from "./components/series/SeriesDetailsPage.tsx";
import WatchableDetailsPage from "./components/watchable/WatchableDetailsPage.tsx";
import Series from "./components/Series.tsx";
import NewSeriesPage from "./components/series/NewSeriesPage.tsx";



function App() {
    return (
        <>
            <header>
                <Navbar />
            </header>

            <section className="movie-section">
                <Routes>
                    <Route path="/" element={<HomePage/>}></Route>
                    <Route path="/movie" element={<Movie />} />
                    <Route path="/series" element={<Series/>}/>
                    <Route path="/movie/:id" element={<MovieDetailsPage/>}/>
                    <Route path="/series/:id" element={<SeriesDetailsPage/>}/>
                    <Route path="/watchables/:id" element={<WatchableDetailsPage/>}/>
                    <Route path="/newSeries" element={<NewSeriesPage/>}/>
                </Routes>
            </section>
        </>
    )
}

export default App
