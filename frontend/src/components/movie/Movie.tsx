import {useMovie} from "../../api/MovieService.ts";
import MovieCard from "./MovieCard.tsx";
import Filterbar from "../../Filterbar.tsx";

export default function Movie() {

    const { Movies } = useMovie("api/movie")

    return(
        <>
            <main className="page-layout">
                <Filterbar />
                    <div className="movie-row">
                        {Movies.map(movie => (
                            <MovieCard movie={movie}/>
                        ))}
                    </div>
            </main>
</>
    )
}