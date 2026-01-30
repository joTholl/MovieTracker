package org.example.backend.models;

import java.util.List;

public record MovieDto(String watchableID, List<String> streamable) {
}
