import type {MovieOut} from "../types/MovieOut.ts";
import {Link} from "react-router-dom";

type MovieCardProps = {
    movie: MovieOut
}

export default function MovieCard({movie}:MovieCardProps) {
    return(
        <>
            <Link to={`/movies/${movie.id}`}>
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
            </Link>

        </>
    )
}