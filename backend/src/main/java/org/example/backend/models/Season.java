package org.example.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.util.List;

@With
public record Season(@Id String id, int seasonNumber, List<String> watchablesId, List<String> streamable) {
}
