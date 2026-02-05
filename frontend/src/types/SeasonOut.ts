import type {Watchable} from "./Watchable.ts";

export type SeasonOut ={
    id: string;
    seasonNumber: number;
    watchables: Watchable[];
    streamables: string[];

}