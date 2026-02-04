import type {Watchable} from "../../types/Watchable.ts";
import "../../styles/CardsAndDetails.css"
import {Link} from "react-router-dom";

type Props = {
    watchable: Watchable;
};

export default function WatchableCardPreview({watchable}: Props) {
    return (
        <article className="card">
            <Link className="card__link" to={`/watchables/${watchable.id}`} state={{watchable}}>
                <h3 className="card__title">{watchable.title}</h3>
                <p className="card__meta">Episode: {watchable.episode}</p>
            </Link>
        </article>
    );
}