import type {Watchable} from "./Watchable.ts";

export type Season = {
    id: string;
    seasonNumber: number;
    watchables: Watchable[];
    streamables: string[];
};