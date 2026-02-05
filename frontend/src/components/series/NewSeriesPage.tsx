import {WatchableForm} from "../watchable/WatchableForm.tsx";
import {SeasonForm} from "../season/SeasonForm.tsx";
import {SeriesForm} from "./SeriesForm.tsx";
import {useState} from "react";
import "../../styles/forms.css"
import type {WatchableInDto} from "../../types/WatchableInDto.ts";
import type {SeasonInDto} from "../../types/SeasonInDto.ts";


export default function NewSeriesPage() {
    const [watchables, setWatchables] = useState<WatchableInDto[]>([]);
    const [seasons, setSeasons] = useState<SeasonInDto[]>([]);

    return (
        <div className="page">
            <WatchableForm
                onSaved={(watchable) => setWatchables((prev) => [...prev, watchable])}
            />
            <SeasonForm
                watchables={watchables}
                onSaved={(season) => setSeasons((prev) => [...prev, season])}
            />
            <SeriesForm seasons={seasons} />
        </div>
    );
}