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
	private CategoryRepository categoryRepo;
	
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
		Category category = categoryRepo.save(new Category("Name"));
		long categoryId = category.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Category> result = categoryRepo.findById(categoryId);
		category = result.get();
		assertThat(category.getName(), is("Name"));
	}

	@Test
	public void shouldEstablishReviewToCategoryRelation() {
		//category is owner
		Review review1 = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content."));
		Review review2 = reviewRepo.save(new Review(8, 12, "The second half of the review", "imagine.jpg", "Theres is still nothing in this content."));
		
		Category category = new Category("Test Reviews", review1, review2);
		category = categoryRepo.save(category);
		long categoryId = category.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Category> result = categoryRepo.findById(categoryId);
		category = result.get();
		assertThat(category.getReviews(), containsInAnyOrder(review1, review2));
	}
	
	@Test
	public void shouldFindReviewsForCategories() {
		Review review = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content."));
		
		Category category = categoryRepo.save(new Category("Test Reviews", review));
		Category category2 = categoryRepo.save(new Category("Second Test Reviews", review));

		entityManager.flush();
		entityManager.clear();
		
		Collection<Category> isReview = categoryRepo.findByReviewsContains(review);
		
		assertThat(isReview, containsInAnyOrder(category, category2));
	}
	
	@Test
	public void shouldFindCoursesForReviewId() {
		Review review = reviewRepo.save(new Review(4, 20, "A Test Review", "image.jpg", "Theres is nothing in this content."));
		long reviewId = review.getId();
		
		Category category = categoryRepo.save(new Category("Test Reviews", review));
		Category category2 = categoryRepo.save(new Category("Second Test Reviews", review));
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Category> isReview = categoryRepo.findByReviewsId(reviewId);
		
		assertThat(isReview, containsInAnyOrder(category, category2));
	}
	
	
}