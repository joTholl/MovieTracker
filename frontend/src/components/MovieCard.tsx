import type {Movie} from "../types/Movie.ts";

type MovieCardProps = {
    movie: Movie
}

export default function MovieCard({movie}:MovieCardProps) {
    return(
        <>
            <div>
                <h1>Movie ID: {movie.id}</h1>
                <h1>Watchable ID: {movie.watchableID}</h1>
            </div>
        </>
    )
}