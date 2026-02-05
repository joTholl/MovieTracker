import SeriesCard from "./SeriesCard.tsx";
import {useSeries} from "./SeriesService.ts";
import {useParams} from "react-router-dom";

export default function SeriesDetail() {
    const {Series} = useSeries("api/series/" + useParams())
    console.log(Series)
    return (
        <>
            <header className="series-header">
                <SeriesCard series={Series[0]}/>
            </header>
        </>
    )
}