package org.example.backend.dtos;

import java.util.List;

public record MovieInDto(String watchableID, List<String> streamable) {
}
