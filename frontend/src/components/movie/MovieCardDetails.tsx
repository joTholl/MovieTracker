import type {MovieOut} from "../../types/MovieOut.ts";
import "../../styles/CardsAndDetails.css"
import MovieCard from "./MovieCard.tsx";
import {useMovie} from "../../api/MovieService.ts";


type Props = {
    movie: MovieOut;
};

export default function MovieDetailsCard({ movie }: Props) {
    const w = movie.watchable;
    const { Movies } = useMovie("/api/movie")

    console.log("Movies:", Movies, typeof Movies, Array.isArray(Movies));
    return (
        <article className="details-layout">
            <div className="details-left">
                <div className="details-banner">
                    <img src={movie.thumbnail} alt="Thumbnail" />
                    <h1>{w.title}</h1>
                 <p><b>Movie ID:</b> {movie.id}</p>
                </div>
                <div>
                    <div className="details">
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
            </div>
            <div className="details-side">
                <div>
                    <h1>Other Movies</h1>
                    {Movies
                        .filter(m => m.id !== movie.id)
                        .map(m => (
                            <MovieCard key={m.id} movie={m} />
                        ))}
                </div>
            </div>
        </article>
    );
}