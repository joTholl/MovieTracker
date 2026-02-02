package org.example.backend.dtos;

import java.util.List;

public record MovieDto(String watchableID, List<String> streamable) {
}
