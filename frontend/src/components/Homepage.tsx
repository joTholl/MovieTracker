import { useEffect, useState } from "react";

import  "../styles/HomeStyles.css"
import {getAllMovies} from "../api/ApiMovies.ts";
import type {MovieOut} from "../types/MovieOut.ts";
import MovieCardPreview from "./MovieCardPreview.tsx";


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

    return (
        // <section> = a grouped section of the page
        <section>
            {/* <h1> = main title of the page */}
            <h1>Movie Tracker</h1>

            {error && (
                // <p> = paragraph (good for error messages)
                <p style={{ color: "salmon" }}>Error: {error}</p>
            )}

            {!error && movies.length === 0 && (
                <p>No movies found yet.</p>
            )}

            {/* <div> = layout container */}
            <div className="movieGrid">
                {movies.map((m) => (
                    <MovieCardPreview key={m.id} movie={m} />
                ))}
            </div>
        </section>
    );
}