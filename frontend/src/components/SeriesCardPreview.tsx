
import type {Series} from "../types/Series.ts";

type Props = {
    serie: Series;
};

export default function SeriesCardPreview({serie}: Props) {
    return (
        <>
            <div
                className="movie-card"
                style={{backgroundImage: `url(${serie.imageUrl})`}}
            >
                <div className="movie-info">
                    <h3>{serie.title}</h3>
                    {/*<p>{serie.watchable.duration}h · {serie.watchable.releaseDate} · {serie.watchable.ageRating}</p>
                    <p>{serie.streamable.join(", ")}</p>*/}
                </div>
            </div>
        </>
    )
}