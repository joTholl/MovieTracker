package org.example.backend.dtos;

import lombok.With;
import org.example.backend.enums.SearchFilter;

@With
public record FilterDto(SearchFilter searchFilter, String input) {
}
