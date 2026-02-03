import { Link } from "react-router-dom";
import type {MovieOut} from "../types/MovieOut.ts";
import  "../styles/HomeStyles.css"
import  "../styles/CardsAndDetails.css"

type Props = {
    movie: MovieOut;
};

export default function MovieCardPreview({ movie }: Props) {
    return (
        <article className="card">
            <Link className="card__link" to={`/movies/${movie.id}`}>
                <h3 className="card__title">{movie.watchable.title}</h3>
                <p className="card__meta">
                    {movie.watchable.releaseDate} • Age {movie.watchable.ageRating}+
                </p>
            </Link>
        </article>
    );
}