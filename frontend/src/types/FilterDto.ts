export type SearchFilter = "TITLE" | "ACTORS" | "DIRECTORS" | "GENRES";

export type FilterDto = {
    searchFilter: SearchFilter;
    input: string;
};