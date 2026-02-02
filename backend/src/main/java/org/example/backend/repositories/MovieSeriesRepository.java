package org.example.backend.repositories;

import org.example.backend.models.MovieSeries;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSeriesRepository extends MongoRepository<MovieSeries,String> {
}
