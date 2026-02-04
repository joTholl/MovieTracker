
import type {Series} from "../types/Series.ts";

export async function getAllSeries(): Promise<Series[]> {
    const res = await fetch("/api/series");
    if (!res.ok) throw new Error(`GET /api/series failed (${res.status})`);
    return (await res.json()) as Series[];
}