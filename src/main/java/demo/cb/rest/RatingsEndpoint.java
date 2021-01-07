package demo.cb.rest;

import demo.cb.model.Rating;
import demo.cb.service.RatingService;
import demo.cb.service.exception.RatingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingsEndpoint {
    @Autowired
    RatingService ratingService;

    @GetMapping("/{id}")
    public Rating getRating(@PathVariable String id) {
        return ratingService.getRating(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Rating postRating(@RequestBody Rating rating) {
       return ratingService.save(rating);
    }

    @ExceptionHandler(RatingNotFoundException.class)
    public ResponseEntity<String> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}
