import type {Watchable} from "./Watchable.ts";

export type MovieOut = {
    id: string;
    watchable: Watchable;
    streamable: string[]
};