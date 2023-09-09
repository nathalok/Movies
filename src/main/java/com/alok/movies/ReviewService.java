package com.alok.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ReviewRepository reviewRepository ;

    public Review createReview(String reviewBody, String imdbId){

        Review review = new Review(reviewBody);

        Review review2 = reviewRepository.insert(review);

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review2))
                .first();

        return review2;

    }
}
