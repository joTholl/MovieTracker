import type {MovieOut} from "../../types/MovieOut.ts";
import "../../styles/CardsAndDetails.css"


type Props = {
    movie: MovieOut;
};

export default function MovieCardDetails({ movie }: Props) {
    const w = movie.watchable;

    return (
        <article className="details">
            <h2 className="details__title">{w.title}</h2>
            <div className="details__grid">
                <div>
                    <p><b>Movie ID:</b> {movie.id}</p>
                    <p><b>Streamable:</b> {movie.streamable.length ? movie.streamable.join(", ") : "—"}</p>
                    <p><b>Release date:</b> {w.releaseDate}</p>
                    <p><b>Duration:</b> {w.duration}</p>
                    <p><b>Actors:</b> {w.actors.length ? w.actors.join(", ") : "—"}</p>
                    <p><b>Directors:</b> {w.directors.length ? w.directors.join(", ") : "—"}</p>
                    <p><b>Genres:</b> {w.genres.length ? w.genres.join(", ") : "—"}</p>
                    {w.episode > 0 && <p><b>Episode:</b> {w.episode}</p> }
                    <p><b>Age rating:</b> {w.ageRating}+</p>
                </div>
            </div>
        </article>
    );
}