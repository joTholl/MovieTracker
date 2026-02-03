package org.example.backend.repositories;


import org.example.backend.models.Series;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository  extends MongoRepository<Series, String> {
}
