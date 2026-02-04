
import type {Watchable} from "../types/Watchable.ts";

export async function getAllWatchables(): Promise<Watchable[]> {
    const res = await fetch("/api/watchables");
    if (!res.ok) throw new Error(`GET /api/watchables failed (${res.status})`);
    return (await res.json()) as Watchable[];
}

export async function getWatchableById(id: string): Promise<Watchable> {
    const res = await fetch(`/api/watchables/${id}`);
    if (!res.ok) throw new Error(`GET /api/watchables/${id} failed (${res.status})`);
    return (await res.json()) as Watchable;
}