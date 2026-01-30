package org.example.backend.models;

import org.springframework.data.annotation.Id;

public record MovieSeries(@Id String id, String title, String[] movieIds){
}
