package org.example.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.util.List;

@With
public record Movie(@Id String id, String watchableID, List<String> streamable) {
}
