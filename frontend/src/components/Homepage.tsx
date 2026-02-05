import { useEffect, useState } from "react";

import  "../styles/HomeStyles.css"
import {getAllMovies} from "../api/ApiMovies.ts";
import type {MovieOut} from "../types/MovieOut.ts";
import Filterbar from "../Filterbar.tsx";
import MovieCard from "./movie/MovieCard.tsx";
import {getAllSeries} from "../api/ApiSeries.ts";
import type {Series} from "../types/Series.ts";
import SeriesCardPreview from "./series/SeriesCardPreview.tsx";


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
        <div>
            <main className="page-layout">
                <Filterbar/>
                <div className="movie-row">
                    {movies.map((m) => (
                        <MovieCard key={m.id} movie={m}/>
                    ))}
                    {series.map((s) => (
                        <SeriesCardPreview key={s.id} serie={s}/>
                    ))}
                </div>
                <div className="movie-row">
                </div>
            </main>
        </div>
    );
}