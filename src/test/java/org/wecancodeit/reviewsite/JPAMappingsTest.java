package org.wecancodeit.reviewsite;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {
	
	@Resource 
	private TestEntityManager entityManager;
	
	@Resource
	private ReviewRepository reviewRepo;
	
	@Resource
	private TagRepository categoryRepo;
	
	@Test
	public void shouldSaveAndLoadReviews() {
		Review review = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content."));
		long reviewId = review.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Review> result = reviewRepo.findById(reviewId);
		review = result.get();
		assertThat(review.getTitle(), is("A Test Review"));
	}
	
	@Test
	public void shouldGenerateReviewId() {
		Review review = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content."));
		long reviewId = review.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(reviewId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadCategory() {
		Tag category = categoryRepo.save(new Tag("Name"));
		long categoryId = category.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Tag> result = categoryRepo.findById(categoryId);
		category = result.get();
		assertThat(category.getName(), is("Name"));
	}

	@Test
	public void shouldEstablishReviewToCategoryRelation() {
		Tag category = new Tag("Test Reviews");
		
		Review review1 = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content.", category));
		Review review2 = reviewRepo.save(new Review(8, 12, "The second half of the review", "imagine.jpg", "Theres is still nothing in this content.", category));
		
		category = categoryRepo.save(category);
		long categoryId = category.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Tag> result = categoryRepo.findById(categoryId);
		category = result.get();
		assertThat(category.getReviews(), containsInAnyOrder(review1, review2));
	}
	
	@Test
	public void shouldFindReviewsForCategories() {
		Tag category = categoryRepo.save(new Tag("Test Reviews"));
		Tag category2 = categoryRepo.save(new Tag("Second Test Reviews"));
		
		Review review = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content.", category, category2));

		entityManager.flush();
		entityManager.clear();
		
		Collection<Tag> isReview = categoryRepo.findByReviewsContains(review);
		
		assertThat(isReview, containsInAnyOrder(category, category2));
	}
	
	@Test
	public void shouldFindCoursesForReviewId() {
		Tag category = categoryRepo.save(new Tag("Test Reviews"));
		Tag category2 = categoryRepo.save(new Tag("Second Test Reviews"));
		
		Review review = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content.", category, category2));
		long reviewId = review.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Tag> isReview = categoryRepo.findByReviewsId(reviewId);
		
		assertThat(isReview, containsInAnyOrder(category, category2));
	}
	
	
}
