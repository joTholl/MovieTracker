package org.example.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;

@With
public record Movie(@Id String id, String watchableID) {
}
