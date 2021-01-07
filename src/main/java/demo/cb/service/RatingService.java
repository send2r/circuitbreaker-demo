package demo.cb.service;

import demo.cb.model.Rating;
import demo.cb.repo.RatingRepository;
import demo.cb.service.exception.RatingNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private static Logger logger = LoggerFactory.getLogger(RatingService.class);
    RatingRepository ratingRepository;
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @CircuitBreaker(name = "ratingSvc", fallbackMethod = "getRatingFallback")
    public Rating getRating(String id) {
        return ratingRepository.findById(id).orElseThrow(RatingNotFoundException::new);
    }

    public Rating getRatingFallback(String id, Throwable throwable) {
        logger.error("Error occurred - executing fallback");
        if (throwable instanceof RatingNotFoundException) {
            throw (RatingNotFoundException)throwable;
        }
        return new Rating(id, Rating.UNKNOWN);
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }
}
