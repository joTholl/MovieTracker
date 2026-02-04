import type {MovieOut} from "../types/MovieOut.ts";

type MovieCardProps = {
    movie: MovieOut
}

export default function MovieCard({movie}:MovieCardProps) {
    return(
        <>
            <div
                className="movie-card"
                style={{ backgroundImage: `url(${movie.thumbnail})` }}
            >
                <div className="movie-info">
                    <h3>{movie.watchable.title}</h3>
                    <p>{movie.watchable.duration}h · {movie.watchable.releaseDate} · {movie.watchable.ageRating}</p>
                    <p>{movie.streamable.join(", ")}</p>
                </div>
            </div>
        </>
    )
}