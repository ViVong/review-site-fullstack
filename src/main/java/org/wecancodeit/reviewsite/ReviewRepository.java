package org.wecancodeit.reviewsite;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
	
	Collection<Review> findByCategoriesContains(Tag category);

	Review findByTitle(String relatedReviews);

}