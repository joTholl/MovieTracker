import {useMovie} from "./MovieService.ts";
import MovieCard from "./MovieCard.tsx";

export default function Movie() {

    const { Movies } = useMovie("api/movie")

    return(
        <div className="movie-row">
            {Movies.map(movie => (
                <MovieCard movie={movie}/>
            ))}
        </div>
    )
}