package com.AAAW.MovieAPP.Repositories;

import com.AAAW.MovieAPP.models.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepo extends MongoRepository<Media, String> {
}
