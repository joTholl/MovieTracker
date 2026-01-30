package org.example.backend.repositories;

import org.example.backend.dtos.SeasonWatchableIdDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SeasonRepository extends MongoRepository<SeasonWatchableIdDto,String> {


}
