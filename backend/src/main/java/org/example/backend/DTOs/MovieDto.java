package org.example.backend.DTOs;

import java.util.List;

public record MovieDto(String watchableID, List<String> streamable) {
}
