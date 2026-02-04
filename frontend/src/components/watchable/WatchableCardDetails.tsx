import type {Watchable} from "../../types/Watchable.ts";
import "..//..//styles/CardsAndDetails.css"

type Props = {
    watchable: Watchable
}

export default function WatchableCardDetails(props:Readonly<Props>){
    return (
        <article className="details">
            <h2 className="details__title">{props.watchable.title}</h2>
            <div className="details__grid">
                <div>
                    <p><b>Movie ID:</b> {props.watchable.id}</p>
                    <p><b>Release date:</b> {props.watchable.releaseDate}</p>
                    <p><b>Duration:</b> {props.watchable.duration}</p>
                    <p><b>Actors:</b> {props.watchable.actors.length ? props.watchable.actors.join(", ") : "—"}</p>
                    <p><b>Directors:</b> {props.watchable.directors.length ? props.watchable.directors.join(", ") : "—"}</p>
                    <p><b>Genres:</b> {props.watchable.genres.length ? props.watchable.genres.join(", ") : "—"}</p>
                    {props.watchable.episode > 0 && <p><b>Episode:</b> {props.watchable.episode}</p> }
                    <p><b>Age rating:</b> {props.watchable.ageRating}+</p>
                </div>
            </div>
        </article>

    )
}