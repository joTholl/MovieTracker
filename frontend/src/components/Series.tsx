import {useSeries} from "./SeriesService.ts";
import SeriesCard from "./SeriesCard.tsx";

export default function Series() {

    const { Series } = useSeries("api/series")

    return(
        <div className="movie-row">
            {Series.map(series => (
                <SeriesCard series={series}/>
            ))}
        </div>
    )
}