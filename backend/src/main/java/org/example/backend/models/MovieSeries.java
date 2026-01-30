package org.example.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;

@With
public record MovieSeries(@Id String id, String title, String[] movieIds){
}
