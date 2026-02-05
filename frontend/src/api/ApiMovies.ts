
import type {MovieOut} from "../types/MovieOut.ts";

export async function getAllMovies(): Promise<MovieOut[]> {
    const res = await fetch("/api/movie");
    if (!res.ok) throw new Error(`GET /api/movie failed (${res.status})`);
    return (await res.json()) as MovieOut[];
}

export async function getMovieById(id: string): Promise<MovieOut> {
    const res = await fetch(`/api/movie/${id}`);
    if (!res.ok) throw new Error(`GET /api/movie/${id} failed (${res.status})`);
    return (await res.json()) as MovieOut;
}