package org.example.backend.repositories;
import org.example.backend.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
