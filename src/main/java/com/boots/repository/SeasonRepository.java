package com.boots.repository;

import com.boots.entity.Hotel;
import com.boots.entity.Season;
import org.springframework.data.repository.CrudRepository;

public interface SeasonRepository extends CrudRepository<Season, String> {

}
