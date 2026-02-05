import type {SeasonInDto} from "./SeasonInDto.ts";

export type SeriesInDto = {
    title: string;
    seasons: SeasonInDto[];
    thumbnail: string;
}