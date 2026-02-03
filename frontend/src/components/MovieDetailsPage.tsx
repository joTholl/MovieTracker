import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type {MovieOut} from "../types/MovieOut.ts";
import {getMovieById} from "../api/ApiMovies.ts";
import MovieDetailsCard from "./MovieCardDetails.tsx";


export default function MovieDetailsPage() {
    const { id } = useParams();
    const [movie, setMovie] = useState<MovieOut | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!id) return;

        getMovieById(id)
            .then((data) => {
                setMovie(data);
                setError(null);
            })
            .catch((e: unknown) => {
                setError(e instanceof Error ? e.message : "Unknown error");
            });
    }, [id]);

    if (!id) return <p>Missing id.</p>;
    if (error) return <p style={{ color: "salmon" }}>Error: {error}</p>;
    if (!movie) return <p>Loading…</p>;

    return (
        <section>
            <MovieDetailsCard movie={movie} />
        </section>
    );
}