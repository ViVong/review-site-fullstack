package org.wecancodeit.reviewsite;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {
	
	@Resource
	ReviewRepository reviewRepo;
	
	@Resource
	TagRepository categoryRepo;
	
	@RequestMapping("")
	public Iterable<Review> findAllReviews(){
		return reviewRepo.findAll();
	}
	
	@RequestMapping("/{id}")
	public Optional<Review> findOneReview(@PathVariable long id){
		return reviewRepo.findById(id);
	}
	
	@RequestMapping("/tags/{tagName}")
	public Collection<Review> findAllReviewsByTopic(@PathVariable(value="tagName") String tagName){
		Tag category = categoryRepo.findByName(tagName);
		return reviewRepo.findByCategoriesContains(category);
	}
}
