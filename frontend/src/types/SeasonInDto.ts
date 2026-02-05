import type {WatchableInDto} from "./WatchableInDto.ts";

export type SeasonInDto ={
    seasonNumber: number;
    watchables: WatchableInDto[];
    streamables: string[];

}