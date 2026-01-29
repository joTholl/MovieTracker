package org.example.backend.repositories;

import org.example.backend.models.Watchable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchableRepository extends MongoRepository<Watchable, String> {
}
