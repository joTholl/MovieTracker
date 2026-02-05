import {useState} from "react";
import axios from "axios";
import type {SeasonInDto} from "../../types/SeasonInDto.ts";

type SeriesFormProps = {
    seasons: SeasonInDto[];
};

export function SeriesForm({seasons}: SeriesFormProps) {
    const [title, setTitle] = useState("");
    const [thumbnail, setThumbnail] = useState("");

    const saveSeries = () => {
        axios.post("http://localhost:8080/api/series", {
            title,
            seasonInDtos: seasons,
            thumbnail,
        });
    };


    return (
        <section className="card">
            <h3>Series</h3>
            <div className="form-group">
                <input
                    placeholder="Titel"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />
                <input
                    placeholder="Thumbnail URL"
                    value={thumbnail}
                    onChange={(e) => setThumbnail(e.target.value)}
                />
                <p className="hint">Seasons verknüpft: {seasons !== undefined ? seasons.length : 0}</p>
                <button className="submitBtn" onClick={saveSeries}>Series speichern</button>
            </div>
        </section>
    );
}
