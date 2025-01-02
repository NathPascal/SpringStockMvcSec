package fr.fms;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStockMvcSecApplication implements CommandLineRunner {
	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringStockMvcSecApplication.class, args);
	}

	@Override
	public void run(String... args) {
//		try {
//			Category smartphone = categoryRepository.save(new Category(null, "Smartphone", null));
//			Category ordinateur = categoryRepository.save(new Category(null, "Ordinateur", null));
//			Category tablette = categoryRepository.save(new Category(null, "Tablette", null));
//			Category imprimante = categoryRepository.save(new Category(null, "Imprimante", null));
//			Category camera = categoryRepository.save(new Category(null, "Camera", null));
//			Category tv = categoryRepository.save(new Category(null, "TV", null));
//			Category telescope = categoryRepository.save(new Category(null, "Telescope", null));
//			Category gps = categoryRepository.save(new Category(null, "Gps", null));
//			Category enceinte = categoryRepository.save(new Category(null, "Enceinte", null));
//
//			articleRepository.save(new Article(null, "Samsung Galaxy S8", 250, smartphone));
//			articleRepository.save(new Article(null, "Samsung Galaxy S9", 300, smartphone));
//			articleRepository.save(new Article(null, "Apple iPhone 10", 500, smartphone));
//			articleRepository.save(new Article(null, "Google Pixel 5", 350, smartphone));
//			articleRepository.save(new Article(null, "Xiaomi Poco F3", 150, smartphone));
//			articleRepository.save(new Article(null, "Xiaomi Mi 11", 100, smartphone));
//			articleRepository.save(new Article(null, "OnePlus 9 Pro", 200, smartphone));
//
//			articleRepository.findAll().forEach(a -> System.out.println(a));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}


