package org.example.backend.repositories;


import org.example.backend.dtos.SeriesSeasonIdDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository  extends MongoRepository<SeriesSeasonIdDto, String> {
}
