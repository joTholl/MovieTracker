import type {Season} from "./Season.ts";

export type Series = {
    id: string;
    title: string;
    seasons: Season[];
    imageUrl: string;
};