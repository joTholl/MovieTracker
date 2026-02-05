import type {SeasonOut} from "./SeasonOut.ts";

export type SeriesOut = {
    id: string;
    title: string;
    seasons: SeasonOut[];
    thumbnail: string;
}