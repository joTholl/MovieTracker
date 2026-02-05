import {useState} from "react";
import axios from "axios";
import type {WatchableInDto} from "../../types/WatchableInDto.ts";
import type {SeasonInDto} from "../../types/SeasonInDto.ts";


type SeasonFormProps = {
    watchables: WatchableInDto[];
    onSaved: (season: SeasonInDto) => void;
};

export function SeasonForm({watchables, onSaved}: SeasonFormProps) {
    const [seasonNumber, setSeasonNumber] = useState(1);
    const [streamables, setStreamables] = useState<string[]>([]);


    const saveSeason = () => {
        axios.post("http://localhost:8080/api/seasons", {
            seasonNumber,
            watchableInDtos: watchables,
            streamables,
        })
            .then((res) => onSaved(res.data._id));
    };

    return (
        <section className="card">
            <h3>Season</h3>
            <div className="form-group">
                <label>Season Number:
                    <input
                        type="number"
                        placeholder="Season Nummer"
                        value={seasonNumber}
                        onChange={(e) => setSeasonNumber(Number(e.target.value))}
                    />
                </label>
                <input
                    placeholder="Streamables (Netflix, Prime...)"
                    onChange={(e) =>
                        setStreamables(e.target.value.split(",").map((s) => s.trim()))
                    }
                />
                <p className="hint">Watchables verknüpft: {watchables !== undefined ? watchables.length : 0}</p>
                <button className="submitBtn" onClick={saveSeason}>Season speichern</button>
            </div>
        </section>
    );
}
