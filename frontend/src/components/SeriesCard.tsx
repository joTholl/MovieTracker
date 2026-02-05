import type {SeriesOut} from "../types/SeriesOut.ts";
import type {Watchable} from "../types/Watchable.ts";

type SeriesCardProps = {
    series: SeriesOut
}

export default function SeriesCard({series}: SeriesCardProps) {
    const firstWatchable: Watchable = series.seasons?.[0]?.watchables?.[0]

    return (
        <>

            <div className="movie-card" style={{backgroundImage: `url(${series.thumbnail})`}}>
                <div className="movie-info">
                    <h3>{series.title}</h3>
                    <p>{series.seasons.length} Season(s)
                        · {firstWatchable.releaseDate} · {firstWatchable.ageRating}</p>
                    <p>{series.seasons?.[0]?.streamables?.join(", ") ?? "Keine Anbieter"}</p>
                </div>
            </div>

        </>
    )
}
