package org.example.backend.enums;

public enum SearchFilter {
    ID("id"),
    TITLE("title"),
    ACTORS("actors"),
    DURATION("duration"),
    DIRECTORS("directors"),
    RELEASE("releaseDate"),
    GENRES("genres"),
    EPISODE("episode"),
    AGE_RATING("ageRating");

    private final String filterName;

    SearchFilter(String filterName) {
        this.filterName = filterName;
    }

    public String fieldName() {
        return filterName;
    }
}
