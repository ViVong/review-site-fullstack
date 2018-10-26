package org.wecancodeit.reviewsite;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReviewPopulator implements CommandLineRunner{

	@Resource
	private ReviewRepository reviewRepo;
	
	@Resource
	private CategoryRepository categoryRepo;
	
	@Override
	public void run(String... args) throws Exception {
		
		Category top10 = new Category("Top 10s");
		Category joke = new Category("Joke Reviews");
		Category pieces = new Category("Short Pieces");
		top10 = categoryRepo.save(top10);
		joke = categoryRepo.save(joke);
		pieces = categoryRepo.save(pieces);
		
		Review review = new Review( 8, 20, "A Review on Reviews", "/review.jpg", "Reviews are lame and I don't like them. However I must express how I feel about them and their ilk in the only way I know how. By writing reviews about them.", joke);
		Review weather = new Review(8, 1, "Top 7 Weather to Stay Indoors To", "/rain.jpg", "10. Sleet 9. Hail 8. Snow 7. Rain 6. Blizzard 5. Tornados 4. Hurricanes", top10, joke);
		Review mexico = new Review(1, 21, "Taco Seasoning, The Ultimate Condiment?", "/taco.jpg", "From quesadillas to burritos to tacos, we all know mexican food is delicious. However the taste of Mexico isn't limited to only their cuisine. Salt and pepper is the a staple and we put it on everything. So why not the same with taco seasoning? Try it and find out.", joke, pieces);
		Review flowers = new Review(12, 17, "Why Flowers are Overrated", "/flowers.jpg", "Flowers might smell 'Nice' If you like nature and all that but the truth is they are poison.", pieces);
		Review irish = new Review( 7, 07, "Top 10 Irish products", "/shamrock.jpg", "10. Twisted shamrocks\n9. Electric Bagpipes 8. Fake Pots of Gold 7. Rainbow Loafers 6. Irish Coffee 5. Square of Turf", top10);
		review = reviewRepo.save(review);
		weather = reviewRepo.save(weather);
		mexico = reviewRepo.save(mexico);
		flowers = reviewRepo.save(flowers);
		irish = reviewRepo.save(irish);
	}
}
