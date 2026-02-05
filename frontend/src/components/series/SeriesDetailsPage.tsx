import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import type {Series} from "../../types/Series.ts";
import {getSeriesById} from "../../api/ApiSeries.ts";
import SeriesCardDetails from "./SeriesCardDetails.tsx";

export default function SeriesDetailsPage() {
    const { id } = useParams();
    const [series, setSeries] = useState<Series | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!id) return;

        getSeriesById(id)
            .then((data) => {
                setSeries(data);
                setError(null);
            })
            .catch((e: unknown) => {
                setError(e instanceof Error ? e.message : "Unknown error");
            });
    }, [id]);

    if (!id) return <p>Missing id.</p>;
    if (error) return <p style={{ color: "salmon" }}>Error: {error}</p>;
    if (!series) return <p>Loading…</p>;

    return (
        <section>
            <SeriesCardDetails series={series} />
        </section>
    );
}