package org.example.backend.models;

import lombok.With;

import java.util.List;

@With
public record Series(String id, String title, List<Season> seasons, String imageUrl) {
}
