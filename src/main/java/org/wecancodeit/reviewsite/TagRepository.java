package org.wecancodeit.reviewsite;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

	Collection<Tag> findByReviewsContains(Review review);
	Collection<Tag> findByReviewsId(Long id);
	Tag findByName(String tagName);

}