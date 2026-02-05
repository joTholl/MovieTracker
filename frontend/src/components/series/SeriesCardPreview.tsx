
import type {Series} from "../../types/Series.ts";
import {Link} from "react-router-dom";
import "../../styles/movie.css"

type Props = {
    serie: Series;
};

export default function SeriesCardPreview({serie}: Props) {
    return (
        <>
            <Link className="card__link" to={`/series/${serie.id}`}>
                <div className="movie-card" style={{backgroundImage: `url(${serie.imageUrl})`}}>
                    <div className="movie-info">
                        <h3>{serie.title}</h3>
                    </div>
                </div>
            </Link>
        </>
    )
}