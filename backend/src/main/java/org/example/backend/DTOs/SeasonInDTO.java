package org.example.backend.DTOs;

import lombok.With;
import org.example.backend.models.Watchable;

import java.util.List;

@With
public record SeasonInDTO(int seasonNumber, List<Watchable> watchables, List<String> streamables) {
}
