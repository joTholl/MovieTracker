import type {Watchable} from "../../types/Watchable.ts";
import "../../styles/CardsAndDetails.css"


type Props = {
    watchable: Watchable;
};

export default function WatchableCardPreview({ watchable }: Props) {
    return (
        <article className="card">
            <h3 className="card__title">{watchable.title}</h3>
            <p className="card__meta">Episode: {watchable.episode}</p>
        </article>
    );
}