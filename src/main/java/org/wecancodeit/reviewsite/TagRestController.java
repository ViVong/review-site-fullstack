package org.wecancodeit.reviewsite;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagRestController {

	@Resource
	ReviewRepository reviewRepo;
	
	@Resource
	TagRepository categoryRepo;
	
	@RequestMapping
	public Iterable<Tag> findAllTags(){
		return categoryRepo.findAll();
	}
	
	@RequestMapping("/{id}")
	public Optional<Tag> findOneTag(@PathVariable long id){
		return categoryRepo.findById(id);
	}
	
	@RequestMapping("/{tagName}/reviews")
	public Collection<Review> findAllReviewsByTag(@PathVariable(value="tagName") String tagName){
		Tag category = categoryRepo.findByName(tagName);
		return reviewRepo.findByCategoriesContains(category);
	}
}
