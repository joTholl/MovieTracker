import type {MovieOut} from "../types/MovieOut.ts";

type MovieCardProps = {
    movie: MovieOut
}

export default function MovieCard({movie}:MovieCardProps) {
    return(
        <>
            <div>
                <h1>Movie ID: {movie.id}</h1>
                <h1>Watchable ID: {movie.watchable.id}</h1>
                <h1>{movie.watchable.title}</h1>
                <h1>{movie.watchable.actors + " "}</h1>
                <h1>{movie.watchable.duration}</h1>
                <h1>{movie.watchable.directors + " "}</h1>
                <h1>{movie.watchable.releaseDate}</h1>
                <h1>{movie.watchable.genres + " "}</h1>
                <h1>{movie.watchable.ageRating}</h1>
                <h1>{movie.streamable}</h1>
            </div>
        </>
    )
}