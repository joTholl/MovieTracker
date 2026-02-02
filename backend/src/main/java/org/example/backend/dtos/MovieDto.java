package org.example.backend.dtos;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.util.List;

@With
public record MovieDto(@Id String watchableID, List<String> streamable) {
}
