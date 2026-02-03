import { useEffect, useMemo, useState } from "react";

import  "../styles/HomeStyles.css"
import {getAllMovies} from "../api/ApiMovies.ts";
import type {MovieOut} from "../types/MovieOut.ts";
import MovieCardPreview from "./MovieCardPreview.tsx";

function pickRandomMax<T>(items: T[], max: number): T[] {
    // simple shuffle + slice
    const copy = [...items];
    for (let i = copy.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [copy[i], copy[j]] = [copy[j], copy[i]];
    }
    return copy.slice(0, max);
}

export default function HomePage() {
    const [movies, setMovies] = useState<MovieOut[]>([]);
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

    const randomMovies = useMemo(() => pickRandomMax(movies, 10), [movies]);

    return (
        // <section> = a grouped section of the page
        <section>
            {/* <h1> = main title of the page */}
            <h1>Movie Tracker</h1>

            {error && (
                // <p> = paragraph (good for error messages)
                <p style={{ color: "salmon" }}>Error: {error}</p>
            )}

            {!error && randomMovies.length === 0 && (
                <p>No movies found yet.</p>
            )}

            {/* <div> = layout container */}
            <div className="movieGrid">
                {randomMovies.map((m) => (
                    <MovieCardPreview key={m.id} movie={m} />
                ))}
            </div>
        </section>
    );
}