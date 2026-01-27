package org.example.backend.repos;

import org.example.backend.models.Watchable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchableRepo extends MongoRepository<Watchable, String> {
}
