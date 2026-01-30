package org.example.backend.DTOs;

import java.util.List;

public record MovieInDto(String watchableID, List<String> streamable) {
}
