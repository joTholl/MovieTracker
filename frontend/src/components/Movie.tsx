import {useMovie} from "../api/MovieService.ts";
import MovieCard from "./MovieCard.tsx";

export default function Movie() {

    const { Movies } = useMovie("api/movie")

    return(
        <>
            {Movies.map(movie => (
                <MovieCard movie={movie}/>
            ))}
        </>
    )
}