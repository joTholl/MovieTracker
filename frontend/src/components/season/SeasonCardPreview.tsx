
import type {Season} from "../../types/Season.ts";
import "../../styles/CardsAndDetails.css"
import WatchableCardPreview from "../watchable/WatchableCardPreview.tsx";

type SeasonType = {
    season:Season
}
export default function SeasonCardPreview(props: Readonly<SeasonType>) {
    return (
        <article>
            <h2 className="details__title">Season {props.season.seasonNumber}</h2>
            <div className="details__grid">
                <div>
                    <p><b>Season Id:</b> {props.season.id}</p>
                    <p><b>{props.season.watchables.map((w) => <WatchableCardPreview watchable={w}/>)}</b></p>
                </div>
            </div>
        </article>
    )
}