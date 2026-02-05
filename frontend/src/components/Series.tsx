import {useSeries} from "./SeriesService.ts";
import SeriesCard from "./SeriesCard.tsx";
import {Link, useNavigate} from "react-router-dom";
import Filterbar from "../Filterbar.tsx";
import "../styles/series.css"

export default function Series() {
    const nav = useNavigate();
    const {Series} = useSeries("api/series")

    function navNewSeries() {
        nav("/newSeries")
    }

    return (
        <>

            <main className="page-layout">
                <Filterbar/>
                <button className="submitBtn" onClick={navNewSeries}>New Series</button>

                <div className="movie-row">
                    {Series.map(series => (
                        <Link to={`/series/${series.id}`}
                              className="movie-card"
                              style={{ backgroundImage: `url(${series.thumbnail})` }}>
                            <SeriesCard series={series}/>
                        </Link>
                    ))}
                </div>
            </main>


        </>
    )
}
