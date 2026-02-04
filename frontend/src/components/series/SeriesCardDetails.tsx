
import type {Series} from "../../types/Series.ts";
import "../../styles/CardsAndDetails.css"
import SeasonCardPreview from "../season/SeasonCardPreview.tsx";

type Props = {
    series: Series;
};

export default function SeriesCardDetails({ series }: Props) {
   // const s = series.seasons;

    return (
        <article className="details">
            <h2 className="details__title">{series.title}</h2>
            {series.seasons.map((s) => (
                <SeasonCardPreview key={s.id} season={s}/>))
            }
        </article>
    );
}