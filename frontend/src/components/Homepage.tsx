import { useEffect, useState } from "react";

import  "../styles/HomeStyles.css"
import {getAllMovies} from "../api/ApiMovies.ts";
import type {MovieOut} from "../types/MovieOut.ts";
import MovieCardPreview from "./MovieCardPreview.tsx";
import Filterbar from "../Filterbar.tsx";
import MovieCard from "./MovieCard.tsx";
import {getAllSeries} from "../api/ApiSeries.ts";
import type {Series} from "../types/Series.ts";
import SeriesCardPreview from "./SeriesCardPreview.tsx";


export default function HomePage() {
    const [movies, setMovies] = useState<MovieOut[]>([]);
    const [series, setSeries] = useState<Series[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getAllMovies()
            .then((data) => {
                setMovies(data);
                setError(null);
            })
            .catch((e: unknown) => {
                setError(e instanceof Error ? e.message : "Unknown error");
            });
    }, []);

    useEffect(() => {
        getAllSeries()
            .then((data) => {
                setSeries(data);
                setError(null);
            })
            .catch((e: unknown) => {
                setError(e instanceof Error ? e.message : "Unknown error");
            });
    }, []);

    return (
        // <section> = a grouped section of the page
        <div>
            <main className="page-layout">
                <Filterbar />

                <div className="movie-row">
                    {movies.map((m) => (
                        <MovieCard key={m.id} movie={m} />
                    ))}
                </div>
                <div className="movie-row">
                    {series.map((s) => (
                        <SeriesCardPreview key={s.id} serie={s}/>
                    ))}
                </div>
            </main>
        </div>
    );
}