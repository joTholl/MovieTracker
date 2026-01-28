package org.example.backend.repositories;

import org.example.backend.DTOs.SeasonWatchableIdDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SeasonRepository extends MongoRepository<SeasonWatchableIdDTO,String> {


}
