package org.wecancodeit.reviewsite;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {
	
	@Resource
	TagRepository categoryRepo;
	
	@Resource
	ReviewRepository reviewRepo;
	
	@Resource
	CommentRepository commentRepo;

	@RequestMapping("/")
	public String findAllReviews(Model model) {
		model.addAttribute("reviews", reviewRepo.findAll());
		return ("reviews");
	}
	
	@RequestMapping("/review")
	public String findOneReview(@RequestParam(value="id")long id, Model model) throws ReviewNotFoundException {
		Optional<Review> review = reviewRepo.findById(id);
		
		if(review.isPresent()) {
			model.addAttribute("reviews", review.get());
			model.addAttribute("categories", categoryRepo.findByReviewsContains(review.get()));
			return "review";
		}
		throw new ReviewNotFoundException();
	}
	
	@RequestMapping("/category")
	public String findOneCategory(@RequestParam(value="id") long id, Model model) throws TagNotFoundException {
		Optional<Tag> category = categoryRepo.findById(id);
		
		if(category.isPresent()) {
			model.addAttribute("categories", category.get());
			return "category";
		}
		throw new TagNotFoundException();
	}

	@RequestMapping("/showCategories")
	public String findAllCategories(Model model) {
		model.addAttribute("categories", categoryRepo.findAll());
		return "categories";
	}
	
	@PostMapping("/addTag")
	public String addTag(@RequestParam(required = true) String tagName, String relatedReviews) {
		if (categoryRepo.findByName(tagName) == null) {
			categoryRepo.save(new Tag(tagName));
		}
		if (reviewRepo.findByTitle(relatedReviews) != null){
			reviewRepo.findByTitle(relatedReviews).addCategory(categoryRepo.findByName(tagName));
			reviewRepo.save(reviewRepo.findByTitle(relatedReviews));
		}
		else {
			reviewRepo.save(new Review(1, 1, relatedReviews, "", "", categoryRepo.findByName(tagName)));
		}
		return "redirect:/showCategories";
	}
	
	@PostMapping("/addComment")
	public String addComment(String username, String comment, long id) {
		Comment newComment = commentRepo.save(new Comment(username, comment));
		reviewRepo.findById(id).get().addComment(newComment);
		reviewRepo.save(reviewRepo.findById(id).get());
		return "redirect:/review?id=" + id;
	}
}
