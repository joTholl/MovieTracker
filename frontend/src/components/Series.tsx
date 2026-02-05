import {useSeries} from "./SeriesService.ts";
import SeriesCard from "./SeriesCard.tsx";
import {Link} from "react-router-dom";

export default function Series() {

    const { Series } = useSeries("api/series")

    return(
        <div className="movie-row">
            {Series.map(series => (
                <Link to={`/series/${series.id}`}>
                    <SeriesCard series={series}/>
                </Link>
            ))}
        </div>
    )
}