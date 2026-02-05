import {useSeries} from "./SeriesService.ts";
import SeriesCard from "./SeriesCard.tsx";
import {Link} from "react-router-dom";
import Filterbar from "../Filterbar.tsx";

export default function Series() {

    const { Series } = useSeries("api/series")

    return(
        <>
            <main className="page-layout">
                <Filterbar />
                    <div className="movie-row">
                        {Series.map(series => (
                            <Link to={`/series/${series.id}`}>
                                <SeriesCard series={series}/>
                            </Link>
                        ))}
                    </div>
            </main>
</>
    )
}
